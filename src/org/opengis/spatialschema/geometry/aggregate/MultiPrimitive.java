/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.spatialschema.geometry.aggregate;

// OpenGIS direct dependencies
import org.opengis.spatialschema.geometry.primitive.Primitive;

// Annotations
///import org.opengis.annotation.UML;
///import static org.opengis.annotation.Obligation.*;


/**
 * Specialization of the {@linkplain Aggregate} interface that restricts the
 * elements to only being of type {@linkplain Primitive}.
 *
 * @author ISO/DIS 19107
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version 2.0
 */
///@UML (identifier="GM_MultiPrimitive")
public interface MultiPrimitive extends Aggregate {
    // No additional methods or members.
}
