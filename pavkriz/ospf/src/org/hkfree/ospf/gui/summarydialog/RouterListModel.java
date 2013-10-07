package org.hkfree.ospf.gui.summarydialog;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import org.hkfree.ospf.model.ospf.Router;

/**
 * ListModel routerů
 * @author Jakub Menzel
 * @author Jan Schovánek
 */
public class RouterListModel implements ListModel {

    private List<Router> routerList = null;
    private List<Router> allRouters = null;
    private String selected = null;


    /**
     * Konstruktor
     * @param routerList
     */
    public RouterListModel(List<Router> routerList) {
	this.routerList = new ArrayList<Router>(routerList);
	this.allRouters = routerList;
	//selected = (String) this.getElementAt(0);
	selected = routerList.get(0).getId();
    }


    /**
     * Vrací vybraný objekt
     * @return Object
     */
    public Object getSelectedItem() {
	return selected;
    }


    /**
     * Nastavuje vybranou položku
     * @param item
     */
    public void setSelectedItem(Object item) {
	selected = (String) item;
    }


    public void addListDataListener(ListDataListener arg0) {
    }


    /**
     * Vrací položku na dané pozici
     * @param i
     * @return Object
     */
    public Object getElementAt(int i) {
	if (routerList.get(i).getName() != null && !routerList.get(i).getName().isEmpty())
	    return routerList.get(i).getId() + "   -   " + routerList.get(i).getName();
	else
	    return routerList.get(i).getId();
    }


    /**
     * Vrací vybraný router
     * @param router
     */
    public void setSelectedRouter(Router router) {
	if (router.getName() != null && !router.getName().isEmpty())
	    selected = router.getId() + "   -   " + router.getName();
	else
	    selected = router.getId();
    }


    /**
     * Vrací počet routerů
     * @return int
     */
    public int getSize() {
	return routerList.size();
    }


    public void removeListDataListener(ListDataListener arg0) {
    }


    /**
     * Vrací router na dané pozici
     * @param index
     * @return Router
     */
    public Router getRouterByIndex(int index) {
	if (index >= 0 && routerList.size() > index)
	    return routerList.get(index);
	else
	    return null;
    }


    /**
     * Aktualizuje seznam
     * @param searchText
     * @param mode
     */
    public void updadteList(String searchText, int mode) {
	routerList.clear();
	switch (mode) {
	    case OspfRoutersPanel.START_WITH_STEXT:
		for (Router r : allRouters) {
		    if (r.getId().startsWith(searchText)
			    || r.getName().toLowerCase().startsWith(searchText.toLowerCase())) {
			routerList.add(r);
		    }
		}
		break;
	    case OspfRoutersPanel.ENDS_WITH_STEXT:
		for (Router r : allRouters) {
		    if (r.getId().endsWith(searchText)
			    || r.getName().toLowerCase().endsWith(searchText.toLowerCase())) {
			routerList.add(r);
		    }
		}
		break;
	    default:
		for (Router r : allRouters) {
		    if (r.getId().contains(searchText)
			    || r.getName().toLowerCase().contains(searchText.toLowerCase())) {
			routerList.add(r);
		    }
		}
		break;
	}
	if (routerList.size() > 0)
	    selected = (String) this.getElementAt(0);
	else
	    selected = null;
    }
}
