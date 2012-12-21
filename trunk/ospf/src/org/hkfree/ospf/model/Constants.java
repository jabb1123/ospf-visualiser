package org.hkfree.ospf.model;

/**
 * Konstanty a enumy pro aplikaci OspfVisualiser
 * @author Jan Schovánek
 */
public abstract class Constants {

    /** verze aplikace */
    public static final String APP_VERSION = "3.0.3";
    /** url k obrázkům */
    public static final String URL_IMG_GUI = "/images/";
    /** url k webovym strankam s tipy aplikace */
    public static final String URL_TIPS = "tips/";
    /** název souboru s nastavením */
    public static final String SETTINGS_FILE = "settings.properties";
    /** komentář do properties souboru s nastavením aplikace */
    public static final String SETTINGS_COMMENT = "OSPF VISUALISER SETTINGS";
    /** multilink, multispoj */
    public static final String MULTILINK = "MULTILINK";
    /** symetricky spoj */
    public static final String SYMETRIC = "SYMETRIC";
    /** maximalni pocet iteraci pro FR layout */
    public static int LAYOUT_FR_MAX_ITERATIONS = 800;
    /** vzdalenosti vrcholu od sebe */
    public static double LAYOUT_ATTRACTION = 0.55; // 0.55, vzdalenosti vrcholu od sebe 45
    /** vzdalenosti vrcholu na spoji od sebe */
    public static double LAYOUT_REPULSION = 0.18; // 0.18, vzdalenosti vrcholu na spoji od sobe 15
    // konstanty pro vyber zdroje pro nacteni dat
    public static final int FROM_DATE_TO_DATE = 0;
    public static final int ZIP_SERVER = 1;
    public static final int ZIP_LOCAL = 2;
    public static final int LOCAL_SOURCES = 3;
    public static final int TELNET = 4;
    public static final int CGI = 5;
    public static final int LOCAL = 1;
    public static final int REMOTE_SERVER = 2;
    public static final int FOLDER = 1;
    public static final int ZIP = 2;

    /**
     * Enum pro lokalizaci aplikace
     */
    public enum LANGUAGE {
	en_EN, cs_CZ;
    }

    /**
     * Enum pro nastavení pracovního režimu
     */
    public enum MODE {
	SHOW_NEIGHBORS,
	COST_CHANGING,
	SHORTEST_PATH,
	GPS,
	GPS_ALL,
	ZOOM,
	LOCK_ALL,
	LOCK_VERTEX,
	PICKING,
	TRANSFORMING,
	LAYOUT_FR_START,
	LAYOUT_SPRING_START,
	LAYOUT_SPRING_STOP,
	ADD_VERTEXES,
	ADD_EDGES,
	ASYMETRIC_LINKS,
	SHORTEST_PATH_TWO_ROUTERS,
	IPV6,
	NONE,
    }
}
