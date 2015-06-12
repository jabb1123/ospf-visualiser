package org.hkfree.ospf.tools.load;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hkfree.ospf.model.lltd.Device;
import org.hkfree.ospf.model.lltd.LLTDModel;
import org.hkfree.ospf.model.lltd.Relation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The class for reading, parsing and return LLTD models.
 * @author Jan Schovánek
 */
public class LLTDLoader {

    public LLTDLoader() {}


    /**
     * Downloading and retrieving LLTD data
     * @param lltdModels
     * @param urlLltdData
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public static void loadLLTDData(List<LLTDModel> lltdModels, String urlLltdData) throws IOException,
	    ParserConfigurationException,
	    SAXException {
	List<LLTDModel> result = new ArrayList<LLTDModel>();
	// Download data
	URL url = new URL(urlLltdData);
	URLConnection conn = url.openConnection();
	BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	String line;
	List<String> modelsLines = new ArrayList<String>();
	// Obtaining lines containing models
	while ((line = br.readLine()) != null) {
	    if (line.endsWith("</lltd></td></tr>")) //thus the end of the line containing the string LLTD model
		modelsLines.add(line);
	}
	// parsing row and obtaining model
	for (String modelString : modelsLines) {
	    result.add(loadModel(modelString));
	}
	lltdModels.clear();
	lltdModels.addAll(result);
    }


    /**
     * Parses the line and returns a model containing LLTD
     * @param line line-containing model
     * @return LLTDModel
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    private static LLTDModel loadModel(String line) throws ParserConfigurationException, SAXException, IOException {
	LLTDModel model = new LLTDModel();
	List<String> traceroute = new ArrayList<String>();
	List<Device> devices = new ArrayList<Device>();
	List<Relation> relations = new ArrayList<Relation>();
	Device d = null;
	Relation r = null;
	// Acquired from the XML string
	Pattern pattern = Pattern.compile("<\\?xml.*</lltd>");
	Matcher matcher = pattern.matcher(line);
	matcher.find();
	DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	Document doc = newDocumentBuilder.parse(new ByteArrayInputStream(matcher.group(0).getBytes()));
	doc.getDocumentElement().normalize();
	// Obtaining a model from XML
	NodeList ips = doc.getElementsByTagName("traceroute");
	NodeList elems = doc.getElementsByTagName("device");
	NodeList links = doc.getElementsByTagName("relation");
	model.setDate(new Date(Long.valueOf(doc.getDocumentElement().getAttribute("millis"))));
	model.setPublicIP(doc.getDocumentElement().getAttribute("publicIP"));
	// Reads the IP address from traceroute
	for (int i = 0; i < ips.getLength(); i++) {
	    Element e = (Element) ips.item(i);
	    traceroute.add(e.getElementsByTagName("ip").item(0).getTextContent());
	}
	// the device is loading
	for (int i = 0; i < elems.getLength(); i++) {
	    Element e = (Element) elems.item(i);
	    d = new Device();
	    d.setSource(e.getAttribute("mask"));
	    d.setMachineName(e.getElementsByTagName("machineName").item(0).getTextContent());
	    d.setIpv4(e.getElementsByTagName("ipv4").item(0).getTextContent());
	    d.setIpv6(e.getElementsByTagName("ipv6").item(0).getTextContent());
	    devices.add(d);
	}
	// loading of the relationship (connections)
	for (int i = 0; i < links.getLength(); i++) {
	    Element e = (Element) links.item(i);
	    r = new Relation();
	    r.setFrom(getDevice(e.getAttribute("from"), devices));
	    r.setTo(getDevice(e.getAttribute("to"), devices));
	    r.setMedium(e.getElementsByTagName("medium").item(0).getTextContent());
	    relations.add(r);
	}
	model.setTraceroute(traceroute);
	model.setDevices(devices);
	model.setRelations(relations);
	return model;
    }


    /**
     * Returning the equipment according to its name (MAC address)
     * @param name device MAC Address
     * @param devices List all devices
     * @return null if it can not find (should not happen), other equipment
     */
    private static Device getDevice(String name, List<Device> devices) {
	for (Device d : devices) {
	    if (d.getSource().equals(name)) {
		return d;
	    }
	}
	// It may happen that the device is not found
	// Create new (to allow the compilation chart)
	Device d = new Device();
	d.setSource(name);
	return d;
    }
}
