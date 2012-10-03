package org.hkfree.ospf.gui.mappanel;

import javax.swing.JPanel;

import org.hkfree.ospf.model.Constants.LAYOUT;
import org.hkfree.ospf.model.map.LinkEdge;
import org.hkfree.ospf.model.map.MapModel;
import org.hkfree.ospf.model.map.RouterVertex;
import org.hkfree.ospf.model.ospf.OspfModel;
import org.hkfree.ospf.model.ospf.Router;

import edu.uci.ics.jung.visualization.GraphZoomScrollPane;

/**
 * Třída představující okno návrhu sítě
 * @author Jakub Menzel
 * @author Jan Schovánek
 */
public class MapPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private MapManager manager = null;
    private MapGraphComponent graphComponent = null;
    private PropertiesPanel prop = null;
    private GraphZoomScrollPane graphPanel = null;


    /**
     * Konstruktor
     */
    public MapPanel() {
	graphComponent = new MapGraphComponent(this);
	manager = new MapManager(this);
	manager.setGraphComponent(graphComponent);
    }


    /**
     * Konstruktor
     */
    public MapPanel(OspfModel ospfModel) {
	this();
	manager.setOspfModel(ospfModel);
	createComponents();
    }


    /**
     * Konstruktor
     */
    public MapPanel(MapModel mapModel) {
	this();
	manager.setMapModel(mapModel);
	createComponents();
    }


    /**
     * Vytvoří komponenty GUI
     */
    private void createComponents() {
	prop = new PropertiesPanel(manager.getOspfModel());
	graphPanel = new GraphZoomScrollPane(graphComponent.getVisualizationComponent());
    }


    /**
     * Aktualizuje properties okno s informacemi o routeru/spoji
     * na který bylo kliknuto
     * @param o objekt routeru nebo spoje
     */
    public void setPropertiesPanel(Object o) {
	if (o instanceof RouterVertex) {
	    prop.actualizeValues((RouterVertex) o);
	}
	if (o instanceof LinkEdge) {
	    prop.actualizeValues((LinkEdge) o);
	}
    }


    /**
     * Volá manažera okna ke zpracování předaných modelů
     * @param processWholeModel
     * @param center
     * @param depth
     * @param layout 
     */
    public void processModelsAfterStart(boolean processWholeModel, Router center, int depth, LAYOUT layout) {
	manager.setLoadSettings(processWholeModel, center, depth);
	manager.loadMapModel();
	this.repaint();
    }


    /**
     * Vrací instanci managera okna
     * @return manager
     */
    public MapManager getMapDesignWinManager() {
	return manager;
    }


    /**
     * Vrací komponentu grafu
     * @return
     */
    public MapGraphComponent getGraphComponent() {
	return graphComponent;
    }


    public JPanel getGraphPanel() {
	return graphPanel;
    }


    public JPanel getPropertiesPanel() {
	return prop;
    }
}
