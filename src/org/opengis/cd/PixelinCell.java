/*
 * Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 */
package org.opengis.cd;

//J2SE direct dependencies
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

//OpenGIS direct dependencies
import org.opengis.util.CodeList;


/**
 * Specification of the way the image grid is associated with the image data attributes.
 *
 * @UML codelist CD_PixelinCell
 * @author ISO 19111
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version 2.0
 */
public final class PixelinCell extends CodeList {
    /**
     * Serial number for compatibility with different versions.
     */
    private static final long serialVersionUID = 2857889370030758462L;

    /**
     * The origin of the image coordinate system is the centre of a grid cell or image pixel.
     *
     * @UML conditional cell&nbsp;center
     */
    public static final PixelinCell CELL_CENTER = new PixelinCell(0, "cell center");

    /**
     * The origin of the image coordinate system is the corner of a grid cell, or half-way
     * between the centres of adjacent image pixels.
     *
     * @UML conditional cell&nbsp;corner
     */
    public static final PixelinCell CELL_CORNER = new PixelinCell(1, "cell corner");

    /**
     * List of all enumerations of this type.
     */
    private static final List VALUES = Collections.unmodifiableList(Arrays.asList(new PixelinCell[]{
            CELL_CENTER, CELL_CORNER }));

    /**
     * Constructs an enum with the given name.
     */
    private PixelinCell(final int ordinal, final String name) {
        super(ordinal, name);
    }

    /**
     * Returns the list of <code>PixelinCell</code>s.
     */
    public static List values() {
        return VALUES;
    }

    /**
     * Returns the list of enumerations of the same kind than this enum.
     */
    public List family() {
        return VALUES;
    }
}
