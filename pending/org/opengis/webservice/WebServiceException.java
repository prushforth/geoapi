// $Header$
/*----------------    FILE HEADER  ------------------------------------------

 This file is part of deegree.
 Copyright (C) 2001 by:
 EXSE, Department of Geography, University of Bonn
 http://www.giub.uni-bonn.de/exse/
 lat/lon Fitzke/Fretter/Poth GbR
 http://www.lat-lon.de

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

 Contact:

 Andreas Poth
 lat/lon Fitzke/Fretter/Poth GbR
 Meckenheimer Allee 176
 53115 Bonn
 Germany
 E-Mail: poth@lat-lon.de

 Jens Fitzke
 Department of Geography
 University of Bonn
 Meckenheimer Allee 166
 53115 Bonn
 Germany
 E-Mail: jens.fitzke@uni-bonn.de

 
 ---------------------------------------------------------------------------*/
package org.opengis.webservice;


/**
 * 
 *
 * @author <a href="mailto:poth@lat-lon.de">Andreas Poth</a>
 */
public class WebServiceException extends Exception  {
    /**
     * Serial number for compatibility with different versions.
     */
//    private static final long serialVersionUID = **TODO**;

    /**
     * @todo document
     */
    private String locator;

    /**
     * @todo document
     */
    private final ExceptionCode code;

    /**
     * @param message
     */
    public WebServiceException(String message) {
        this(message, null, null);
    }

    /**
     * @param locator
     * @param message
     *
     * @revisit The argument order (locator before message) may surprises users who don't read
     *          carefully the javadoc. We could expect optional argument to appears next to
     *          mandatory ones.
     */
    public WebServiceException(String locator, String message) {
        this(message, locator, null);
    }
    
    /**
     * @param locator
     * @param message
     * @param code 
     *
     * @revisit The argument order (locator before message) may surprises users who don't read
     *          carefully the javadoc. We could expect optional argument to appears next to
     *          mandatory ones.
     */
    public WebServiceException(String locator, String message, ExceptionCode code) {
        super(message);
        this.locator = locator;
        this.code    = code;
    }

    /**
     * Returns the class/service that has caused the exception
     * 
     * @uml.property name="locator"
     */
    public String getLocator() {
        return locator;
    }

    /**
     * Sets the class/service that has caused the exception
     * 
     * @uml.property name="locator"
     */
    public void setLocator(String locator) {
        this.locator = locator;
    }

    /**
     * Returns the code for this exception.
     */
    public ExceptionCode getCode() {
        return code;
    }
}
