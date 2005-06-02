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

// Annotations
import org.opengis.annotation.UML;
import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * A {@linkplain GriddedSurface gridded surface} given as a family of conic sections whose
 * {@linkplain #getControlPoints control points} vary linearly. A 5-point ellipse with all
 * defining positions identical is a point. Thus, a truncated elliptical cone can be given
 * as a 2&times;5 set of control points
 * 
 * &lt;&lt;P1, P1, P1, P1, P1&gt;, &lt;P2, P3, P4, P5, P6&gt;&gt;.
 * 
 * P1 is the apex of the cone. P2, P3, P4, P5, and P6 are any five distinct points around
 * the base ellipse of the cone. If the horizontal curves are circles as opposed to ellipses,
 * then a circular cone can be constructed using &lt;&lt;P1, P1, P1&gt;, &lt;P2, P3, P4&gt;&gt;.
 *
 * @version <A HREF="http://www.opengis.org/docs/01-101.pdf">Abstract specification 5</A>
 * @author Martin Desruisseaux (IRD)
 * @since GeoAPI 2.0
 */
@UML(identifier="GM_Cone", specification=ISO_19107)
public interface Cone extends GriddedSurface {
}
