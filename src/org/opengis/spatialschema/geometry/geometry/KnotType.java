/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003-2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.spatialschema.geometry.geometry;

// J2SE directdependencies
import java.util.List;
import java.util.ArrayList;

// OpenGIS direct dependencies
import org.opengis.util.CodeList;

// Annotations
import org.opengis.annotation.UML;
import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * The type of a B-spline.
 * A B-spline is uniform if and only if all knots are of {@linkplain Knot#getMultiplicity
 * multiplicity 1} and they differ by a positive constant from the preceding knot. A B-spline
 * is quasi-uniform if and only if the knots are of multiplicity (degree+1) at the ends, of
 * multiplicity 1 elsewhere, and they differ by a positive constant from the preceding knot.
 * This code list is used to describe the distribution of knots in the parameter space of
 * various splines.
 *
 * @version <A HREF="http://www.opengis.org/docs/01-101.pdf">Abstract specification 5</A>
 * @author Martin Desruisseaux (IRD)
 * @since GeoAPI 2.0
 */
@UML(identifier="GM_KnotType", specification=ISO_19107)
public class KnotType extends CodeList<KnotType> {
    /**
     * Serial number for compatibility with different versions.
     */
    private static final long serialVersionUID = -431722533158166557L;

    /**
     * List of all enumerations of this type.
     * Must be declared before any enum declaration.
     */
    private static final List<KnotType> VALUES = new ArrayList<KnotType>(3);

    /**
     * The form of knots is appropriate for a uniform B-spline.
     */
    public static final KnotType UNIFORM = new KnotType("UNIFORM");

    /**
     * The form of knots is appropriate for a quasi-uniform B-spline.
     */
    public static final KnotType QUASI_UNIFORM = new KnotType("QUASI_UNIFORM");

    /**
     * The form of knots is appropriate for a piecewise Bezier curve.
     */
    public static final KnotType PIECEWISE_BEZIER = new KnotType("PIECEWISE_BEZIER");

    /**
     * Constructs an enum with the given name. The new enum is
     * automatically added to the list returned by {@link #values}.
     *
     * @param name The enum name. This name must not be in use by an other enum of this type.
     */
    public KnotType(final String name) {
        super(name, VALUES);
    }

    /**
     * Returns the list of {@code KnotType}s.
     */
    public static KnotType[] values() {
        synchronized (VALUES) {
            return (KnotType[]) VALUES.toArray(new KnotType[VALUES.size()]);
        }
    }

    /**
     * Returns the list of enumerations of the same kind than this enum.
     */
    public /*{KnotType}*/ CodeList[] family() {
        return values();
    }
}
