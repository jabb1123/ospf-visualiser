package org.hkfree.ospf.model.map;

import java.util.ArrayList;
import java.util.List;

import org.hkfree.ospf.model.AbstractMapModel;
import org.hkfree.ospf.model.Constants;
import org.hkfree.ospf.model.ospf.OspfLinkData;
import org.hkfree.ospf.tools.NeighbourCostAndLink;
import org.hkfree.ospf.tools.geo.GPSPoint;

/**
 * Třída představující MapModel (model grafu reprezentovaný seznamem vrcholů
 * a hran mezi nimi)
 * @author Jakub Menzel
 * @author Jan Schovánek
 */
public class MapModel implements AbstractMapModel {

    private List<LinkEdge> linkEdges = new ArrayList<LinkEdge>();
    private List<RouterVertex> routerVertices = new ArrayList<RouterVertex>();


    /**
     * Konstruktor - vytvoří instanci třídy
     */
    public MapModel() {}


    /**
     * Metoda, která vytvoří instanci hrany dle na základě zadaných parametrů
     * @param id1
     * @param id2
     * @param name1
     * @param name2
     * @param cost1
     * @param cost2
     * @param gpsP1
     * @param gpsP2
     * @param linkIDv4
     * @param ospfLinksData
     */
    public void addLinkEdge(String id1, String id2, String name1, String name2, int cost1, int cost2, int cost1IPv6, int cost2IPv6, GPSPoint gpsP1,
	    GPSPoint gpsP2, String linkIDv4, String linkIDv6, List<OspfLinkData> ospfLinksData) {
	RouterVertex rv1 = getRouterVertexById(id1);
	RouterVertex rv2 = getRouterVertexById(id2);
	if (rv1 == null) {
	    routerVertices.add(new RouterVertex(id1, name1, gpsP1));
	    rv1 = routerVertices.get(routerVertices.size() - 1);
	    if (id1.contains(Constants.MULTILINK)) {
		routerVertices.get(routerVertices.size() - 1).setMultilink(true);
	    }
	}
	if (rv2 == null) {
	    routerVertices.add(new RouterVertex(id2, name2, gpsP2));
	    rv2 = routerVertices.get(routerVertices.size() - 1);
	    if (id2.contains(Constants.MULTILINK)) {
		routerVertices.get(routerVertices.size() - 1).setMultilink(true);
	    }
	}
	LinkEdge le = new LinkEdge();
	le.setRouterVertex1(rv1);
	le.setRouterVertex2(rv2);
	le.setCost1v4(cost1);
	le.setCost2v4(cost2);
	le.setCost1v6(cost1IPv6);
	le.setCost2v6(cost2IPv6);
	le.setLinkIDv4(linkIDv4);
	le.setLinkIDv6(linkIDv6);
	linkEdges.add(le);
    }



    /**
     * Přidá do MapModelu novou hranu a vrací odkaz na její instanci
     * @param rv1
     * @param rv2
     * @param cost1
     * @param cost2
     * @param linkID
     * @return linkEdge
     */
    public LinkEdge addLinkEdge(RouterVertex rv1, RouterVertex rv2, int cost1, int cost2, String linkID) {
	LinkEdge le = new LinkEdge();
	le.setLinkIDv4(linkID);
	
	//TODO add link edge dodelat
/*	if (rv1.isMultilink()) {
	    linkEdges.add(new LinkEdge(rv2, cost2, rv1, 0, linkID));
	} else {
	    if (rv2.isMultilink()) {
		linkEdges.add(new LinkEdge(rv1, cost1, rv2, 0, linkID));
	    } else
		linkEdges.add(new LinkEdge(rv1, cost1, rv2, cost2, linkID));
	}
*/	
	return linkEdges.get(linkEdges.size() - 1);
    }
    
    
    /**
     * Nalezne routerVertex dle id a vrati ho
     * @param id
     * @return pokud nenalezne, vraci null
     */
    private RouterVertex getRouterVertexById(String id) {
	for (RouterVertex rv : routerVertices) {
	    if (rv.getDescription().equals(id)) {
		return rv;
	    }
	}
	return null;
    }




    /**
     * Metoda vytvoření vrcholu v seznamu vrcholů
     * @return rv
     */
    public RouterVertex addFirstRouterVertex(String ip, String name) {
	routerVertices.add(new RouterVertex(ip, name));
	return routerVertices.get(routerVertices.size() - 1);
    }


    /**
     * Vytvoří nový routerVertex modelu
     */
    public void addRouterVertex() {
	routerVertices.add(new RouterVertex());
    }


    /**
     * Metoda, která vrátí počet vrcholů MapaModelu
     * @return count
     */
    public int routerVertexCount() {
	return routerVertices.size();
    }


    /**
     * Metoda, která vrátí počet hran MapaModelu
     * @return count
     */
    public int linkCount() {
	return linkEdges.size();
    }


    /**
     * Metoda, která vrátí všechny linkEdges MapaModelu
     * @return linkEdge
     */
    public List<LinkEdge> getLinkEdges() {
	return linkEdges;
    }


    /**
     * Metoda, která vrátí všechny routerVertexes MapaModelu
     * @return rvs
     */
    public List<RouterVertex> getRouterVertices() {
	return routerVertices;
    }


    /**
     * Metoda, která vrátí index zadaného vrcholu v Listu vrcholů
     * @return index
     */
    public int getIndexOfRouterVertex(RouterVertex v) {
	return routerVertices.indexOf(v);
    }


    /**
     * Metoda, která vrátí index zadaného vrcholu v Listu vrcholů
     * @return les
     */
    public List<LinkEdge> getIncidentEdges(RouterVertex v) {
	List<LinkEdge> incidentEdges = new ArrayList<LinkEdge>();
	for (LinkEdge le : linkEdges) {
	    if (le.getRVertex1().equals(v) || le.getRVertex2().equals(v))
		incidentEdges.add(le);
	}
	return incidentEdges;
    }


    /**
     * Vrací maximální hodnotu zeměpisné šířky
     * @return double
     */
    public double getMaximumLatitude() {
	double max = 0;
	for (RouterVertex r : routerVertices) {
	    if (r.getGpsLatitude() > max)
		max = r.getGpsLatitude();
	}
	return max;
    }


    /**
     * Vrací minimální hodnotu zeměpisné šířky
     * @return double
     */
    public double getMinimumLatitude() {
	double min = 0;
	for (RouterVertex r : routerVertices) {
	    if ((r.getGpsLatitude() < min && r.getGpsLatitude() != 0) || min == 0)
		min = r.getGpsLatitude();
	}
	return min;
    }


    /**
     * Vrací maximální hodnotu zeměpisné délky
     * @return double
     */
    public double getMaximumLongtitude() {
	double max = 0;
	for (RouterVertex r : routerVertices) {
	    if (r.getGpsLongtitude() > max)
		max = r.getGpsLongtitude();
	}
	return max;
    }


    /**
     * Vrací minimáln hodnotu zeměpisné délky
     * @return double
     */
    public double getMinimumLongtitude() {
	double min = 0;
	for (RouterVertex r : routerVertices) {
	    if ((r.getGpsLongtitude() < min && r.getGpsLongtitude() != 0) || min == 0)
		min = r.getGpsLongtitude();
	}
	return min;
    }


    /**
     * Vrací příznak, zda má model dva a více routerů s definovanými zeměpisnými souřadnicemi
     * @return boolean
     */
    public boolean hasMoreRouterWithGPSPositions() {
	int cnt = 0;
	for (RouterVertex r : routerVertices) {
	    if (r.getGpsLatitude() != 0 && r.getGpsLongtitude() != 0) {
		cnt++;
	    }
	}
	return (cnt > 1);
    }


    /**
     * Vrací seznam sousedních routerů s cenami daného routeru
     * @param routerVertex
     * @return NeighbourCostAndLink
     */
    public List<NeighbourCostAndLink> getNeighboursWithCosts(RouterVertex routerVertex) {
	List<NeighbourCostAndLink> neighbours = new ArrayList<NeighbourCostAndLink>();
	for (LinkEdge le : this.getLinkEdges()) {
	    if ((le.getRVertex1().equals(routerVertex) || le.getRVertex2().equals(routerVertex)) && le.isEnabled()) {
		if (!le.isEdgeOfMultilink()) {
		    if (le.getRVertex1().equals(routerVertex)) {
			neighbours.add(new NeighbourCostAndLink(le.getRVertex2(), le.getCost1v4(), le));
		    } else {
			neighbours.add(new NeighbourCostAndLink(le.getRVertex1(), le.getCost2v4(), le));
		    }
		} else {
		    int mcost = le.getCost1v4();
		    for (LinkEdge mle : getIncidentEdges(le.getRVertex2())) {
			if (!mle.getRVertex1().equals(routerVertex) && mle.isEnabled())
			    neighbours.add(new NeighbourCostAndLink(mle.getRVertex1(), mcost, le));
		    }
		}
	    }
	}
	return neighbours;
    }


    /**
     * Vrací hranu multispoje mezi danými vrcholy
     * @param router
     * @param multilinkvertex
     * @return le
     */
    public LinkEdge getMultilinkEdge(RouterVertex router, RouterVertex multilinkvertex) {
	for (LinkEdge le : getLinkEdges()) {
	    if (le.getRVertex1().equals(router) && le.getRVertex2().equals(multilinkvertex)) {
		return le;
	    }
	}
	return null;
    }
}
