package org.hkfree.ospf.model.map.impl;

import java.awt.Color;
import java.awt.Paint;
import java.io.Serializable;

public class RelationEdge extends AEdge implements Serializable {

    private static final long serialVersionUID = 1L;
    private String medium;


    @Override
    public String getLabel() {
	if (!vertex1.isVisible() || !vertex2.isVisible()) {
	    return null;
	}
	if (getMedium() == null) {
	    return "-";
	}
	return rb.getString("lltd.medium." + getMedium());
    }


    @Override
    public Paint getLineColor() {
	if (!vertex1.isVisible() || !vertex2.isVisible()) {
	    return null;
	}
	if (isHover()) {
	    return Color.ORANGE;
	}
	return Color.MAGENTA;
    }


    public String getMedium() {
	return medium;
    }


    public void setMedium(String medium) {
	this.medium = medium;
    }
}
