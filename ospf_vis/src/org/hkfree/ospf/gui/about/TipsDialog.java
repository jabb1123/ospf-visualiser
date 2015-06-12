package org.hkfree.ospf.gui.about;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.swing.JDialog;
import javax.swing.JEditorPane;

import org.hkfree.ospf.model.Constants;
import org.hkfree.ospf.model.Constants.LANGUAGE;
import org.hkfree.ospf.tools.Factory;

/**
 * A class that represents dialog "Tips"
 * @author Jan Schovánek
 */
public class TipsDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private ResourceBundle rb = Factory.getRb();
    private LANGUAGE lng;


    /**
     * Constructor - method calls for the creation of the layout window - 
     * the window sets the necessary parameters
     * @param lng
     */
    public TipsDialog(Frame okno, LANGUAGE lng) {
	super(okno);
	this.lng = lng;
	createGUI(okno);
	this.setTitle(rb.getString("tips.title"));
	this.setSize(new Dimension(500, 660));
	this.setLocationRelativeTo(null);
    }


    /**
     * The method, which uses components and creates the whole layout of the main window
     */
    private void createGUI(Frame okno) {
	this.setLayout(new BorderLayout());
	try {
	    String path = Constants.URL_TIPS + "tips_" + lng.name().substring(0, 2) + ".html";
	    JEditorPane ep = new JEditorPane(ClassLoader.getSystemResource(path));
	    ep.setEditable(false);
	    this.add(ep);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }


    @Override
    public void actionPerformed(ActionEvent e) {}
}
