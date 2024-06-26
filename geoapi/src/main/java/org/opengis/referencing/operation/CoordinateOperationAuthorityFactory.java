/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    Copyright © 2004-2023 Open Geospatial Consortium, Inc.
 *    http://www.geoapi.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.opengis.referencing.operation;

import java.util.Set;
import org.opengis.referencing.AuthorityFactory;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.RegisterOperations;
import org.opengis.util.UnimplementedServiceException;
import org.opengis.util.FactoryException;
import org.opengis.annotation.UML;

import static org.opengis.annotation.Specification.*;


/**
 * Creates coordinate operation objects using authority codes.
 * External authorities are used to manage definitions of objects used in this interface.
 * The definitions of these objects are referenced using code strings.
 * A commonly used authority is the <a href="https://epsg.org">EPSG geodetic registry</a>.
 *
 * <h2>Default methods</h2>
 * All {@code create(…)} methods in this interface are optional.
 * If a method is not overridden by the implementer,
 * the default is to return an empty {@link Set} when possible,
 * or otherwise to throw an {@link UnimplementedServiceException}
 * with a message saying that the service is not supported.
 *
 * @author  Martin Desruisseaux (IRD, Geomatys)
 * @version 3.1
 * @since   1.0
 */
@UML(identifier="CT_CoordinateTransformationAuthorityFactory", specification=OGC_01009)
public interface CoordinateOperationAuthorityFactory extends AuthorityFactory {
    /**
     * Returns an operation method from a code.
     * While the {@code code} argument should be an authority code, implementations may also accept method names if
     * the argument is not a valid code and if the name is unambiguous (i.e., has exactly one match in this registry).
     * This flexibility is allowing the parsing of some formats such as version 1 of Well Known Text (<abbr>WKT</abbr>).
     *
     * <table class="ogc">
     *   <caption>Example of operation methods</caption>
     *   <tr><th>EPSG code</th><th>EPSG name</th>                      <th>OGC name</th></tr>
     *   <tr><td>9804</td>     <td>Mercator (variant A)</td>           <td>{@code Mercator_1SP}</td></tr>
     *   <tr><td>9805</td>     <td>Mercator (variant B)</td>           <td>{@code Mercator_2SP}</td></tr>
     *   <tr><td>9807</td>     <td>Transverse Mercator</td>            <td>{@code Transverse_Mercator}</td></tr>
     *   <tr><td>9801</td>     <td>Lambert Conic Conformal (1SP)</td>  <td>{@code Lambert_Conformal_Conic_1SP}</td></tr>
     *   <tr><td>9802</td>     <td>Lambert Conic Conformal (2SP)</td>  <td>{@code Lambert_Conformal_Conic_2SP}</td></tr>
     *   <tr><td>9820</td>     <td>Lambert Azimuthal Equal Area</td>   <td>{@code Lambert_Azimuthal_Equal_Area}</td></tr>
     *   <tr><td>9822</td>     <td>Albers Equal Area</td>              <td>{@code Albers_Conic_Equal_Area}</td></tr>
     *   <tr><td>9806</td>     <td>Cassini-Soldner</td>                <td>{@code Cassini_Soldner}</td></tr>
     *   <tr><td>9840</td>     <td>Orthographic</td>                   <td>{@code Orthographic}</td></tr>
     * </table>
     *
     * @param  code  the method identifier allocated by the authority.
     * @return the operation method for the given code.
     * @throws NoSuchAuthorityCodeException if the specified {@code code} was not found.
     * @throws FactoryException if the object creation failed for some other reason.
     *
     * @see MathTransformFactory#getAvailableMethods(Class)
     *
     * @departure extension
     *   This method has been added because OGC 01-009 does not define a factory
     *   method for creating operation methods.
     */
    default OperationMethod createOperationMethod(String code) throws FactoryException {
        throw new UnimplementedServiceException(this, OperationMethod.class);
    }

    /**
     * Returns a coordinate operation from a code.
     * This <abbr>API</abbr> is for advanced applications.
     * The preferred interface for extracting a coordinate operation is {@link RegisterOperations}.
     *
     * @param  code  value allocated by authority.
     * @return the operation for the given code.
     * @throws NoSuchAuthorityCodeException if the specified {@code code} was not found.
     * @throws FactoryException if the object creation failed for some other reason.
     *
     * @see RegisterOperations#findCoordinateOperation(String)
     */
    @UML(identifier="createFromTransformationCode", specification=OGC_01009)
    default CoordinateOperation createCoordinateOperation(String code) throws FactoryException {
        throw new UnimplementedServiceException(this, CoordinateOperation.class);
    }

    /**
     * Returns registered operations between two Coordinate Reference System (<abbr>CRS</abbr>) codes.
     * This method returns only the operations declared by the authority, with preferred operations first.
     * This method doesn't need to compute operations from {@code source} to {@code target} <abbr>CRS</abbr>
     * if no such operations were explicitly defined in the authority database.
     * Computation of arbitrary operations can be performed by
     * <code>{@linkplain RegisterOperations#findCoordinateOperations(CoordinateReferenceSystem,
     * CoordinateReferenceSystem) RegisterOperations.findCoordinateOperations}(sourceCRS, targetCRS)</code>
     * instead.
     *
     * @param  sourceCRS  coded value of source coordinate reference system.
     * @param  targetCRS  coded value of target coordinate reference system.
     * @return the operations from {@code sourceCRS} to {@code targetCRS}.
     * @throws NoSuchAuthorityCodeException if a specified code was not found.
     * @throws FactoryException if the object creation failed for some other reason.
     *
     * @see RegisterOperations#findCoordinateOperations(CoordinateReferenceSystem, CoordinateReferenceSystem)
     */
    @UML(identifier="createFromCoordinateSystemCodes", specification=OGC_01009)
    default Set<CoordinateOperation> createFromCoordinateReferenceSystemCodes(String sourceCRS, String targetCRS)
            throws FactoryException
    {
        return Set.of();
    }
}
