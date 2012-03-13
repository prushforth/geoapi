/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2011-2012 Open Geospatial Consortium, Inc.
 *    All Rights Reserved. http://www.opengeospatial.org/ogc/legal
 *
 *    Permission to use, copy, and modify this software and its documentation, with
 *    or without modification, for any purpose and without fee or royalty is hereby
 *    granted, provided that you include the following on ALL copies of the software
 *    and documentation or portions thereof, including modifications, that you make:
 *
 *    1. The full text of this NOTICE in a location viewable to users of the
 *       redistributed or derivative work.
 *    2. Notice of any changes or modifications to the OGC files, including the
 *       date changes were made.
 *
 *    THIS SOFTWARE AND DOCUMENTATION IS PROVIDED "AS IS," AND COPYRIGHT HOLDERS MAKE
 *    NO REPRESENTATIONS OR WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 *    TO, WARRANTIES OF MERCHANTABILITY OR FITNESS FOR ANY PARTICULAR PURPOSE OR THAT
 *    THE USE OF THE SOFTWARE OR DOCUMENTATION WILL NOT INFRINGE ANY THIRD PARTY
 *    PATENTS, COPYRIGHTS, TRADEMARKS OR OTHER RIGHTS.
 *
 *    COPYRIGHT HOLDERS WILL NOT BE LIABLE FOR ANY DIRECT, INDIRECT, SPECIAL OR
 *    CONSEQUENTIAL DAMAGES ARISING OUT OF ANY USE OF THE SOFTWARE OR DOCUMENTATION.
 *
 *    The name and trademarks of copyright holders may NOT be used in advertising or
 *    publicity pertaining to the software without specific, written prior permission.
 *    Title to copyright in this software and any associated documentation will at all
 *    times remain with copyright holders.
 */
package org.opengis.test.report;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Collections;

import org.opengis.util.FactoryException;
import org.opengis.referencing.IdentifiedObject;
import org.opengis.referencing.AuthorityFactory;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import org.opengis.metadata.Identifier;
import org.opengis.referencing.crs.CRSAuthorityFactory;


/**
 * Generates a list of object identified by authority codes for a given
 * {@linkplain AuthorityFactory authority factory}.
 *
 * <p>This class recognizes the following property values. Note that default values are
 * automatically generated for the {@code "COUNT.*"} and {@code "PERCENT.*"} entries.</p>
 *
 * <table border="1" cellspacing="0">
 *   <tr bgcolor="#CCCCFF"><th>Key</th>          <th>Remarks</th>   <th>Meaning</th></tr>
 *   <tr><td>{@code TITLE}</td>                  <td>&nbsp;</td>    <td>Title of the web page to produce.</td></tr>
 *   <tr><td>{@code DESCRIPTION}</td>            <td>optional</td>  <td>Description to write after the introductory paragraph.</td></tr>
 *   <tr><td>{@code OBJECTS.KIND}</td>           <td>&nbsp;</td>    <td>Kind of objects listed in the page (e.g. "<cite>Coordinate Reference Systems</cite>").</td></tr>
 *   <tr><td>{@code FACTORY.NAME}</td>           <td>&nbsp;</td>    <td>The name of the authority factory.</td></tr>
 *   <tr><td>{@code FACTORY.VERSION}</td>        <td>&nbsp;</td>    <td>The version of the authority factory.</td></tr>
 *   <tr><td>{@code FACTORY.VERSION.SUFFIX}</td> <td>optional</td>  <td>An optional text to write after the factory version.</td></tr>
 *   <tr><td>{@code PRODUCT.NAME}</td>           <td>&nbsp;</td>    <td>Name of the product for which the report is generated.</td></tr>
 *   <tr><td>{@code PRODUCT.VERSION}</td>        <td>&nbsp;</td>    <td>Version of the product for which the report is generated.</td></tr>
 *   <tr><td>{@code PRODUCT.URL}</td>            <td>&nbsp;</td>    <td>URL where more information is available about the product.</td></tr>
 *   <tr><td>{@code JAVADOC.GEOAPI}</td>         <td>&nbsp;</td>    <td>Base URL of GeoAPI javadoc.</td></tr>
 *   <tr><td>{@code COUNT.OBJECTS}</td>          <td>generated</td> <td>Number of identified objects.</td></tr>
 *   <tr><td>{@code PERCENT.VALIDS}</td>         <td>generated</td> <td>Percentage of objects successfully created.</td></tr>
 *   <tr><td>{@code PERCENT.ANNOTATED}</td>      <td>generated</td> <td>Percentage of objects having an {@linkplain Row#annotation annotation}.</td></tr>
 *   <tr><td>{@code FILENAME}</td>               <td>&nbsp;</td>    <td>Name of the file to create if the {@link #write(File)} argument is a directory.</td></tr>
 * </table>
 *
 * <p><b>How to use this class:</b></p>
 * <ul>
 *   <li>Create a {@link Properties} map with the values documented in the above table. Default
 *       values exist for many keys, but may depend on the environment. It is safer to specify
 *       values explicitly when they are known, except the <cite>generated</cite> ones.</li>
 *   <li>Create a new {@code AuthorityCodesReport} with the above properties map
 *       given to the constructor.</li>
 *   <li>Invoke one of the {@link #add(CRSAuthorityFactory) add} method
 *       for the factory of identified objects to include in the HTML page.</li>
 *   <li>Invoke {@link #write(File)}.</li>
 * </ul>
 *
 * @author Martin Desruisseaux (Geomatys)
 * @version 3.1
 *
 * @since 3.1
 */
public class AuthorityCodesReport extends Report {
    /**
     * A single row in the table produced by {@link AuthorityCodesReport}. Instances of this
     * class are created by the {@link AuthorityCodesReport#createRow(String, IdentifiedObject)
     * AuthorityCodesReport.createRow(…)} methods. Subclasses of {@code AuthorityCodesReport}
     * can override those methods in order to modify the content of a row.
     *
     * @author Martin Desruisseaux (Geomatys)
     * @version 3.1
     *
     * @since 3.1
     */
    protected static class Row implements Comparable<Row> {
        /**
         * The authority code.
         */
        public final String code;

        /**
         * The object name, or {@code null} if none. By default, this field is set to the value of
         * <code>{@linkplain IdentifiedObject#getName()}.{@link Identifier#getCode() getCode()}</code>.
         */
        public String name;

        /**
         * A remark to display after the name, or {@code null} if none. By default, this field is
         * set to one of the following values:
         * <p>
         * <ul>
         *   <li>If the object creation was successful, the {@link IdentifiedObject#getRemarks()}
         *       localized to the {@linkplain AuthorityCodesReport#getLocale() report locale}.</li>
         *   <li>Otherwise, the {@link FactoryException} localized message.</li>
         * </ul>
         */
        public String remark;

        /**
         * {@code true} if an exception occurred while creating the identified object.
         * If {@code true}, then the {@link #remark} field will contains the exception
         * localized message.
         */
        public boolean hasError;

        /**
         * {@code true} if this authority code is deprecated, or {@code false} otherwise.
         */
        public boolean isDeprecated;

        /**
         * A small symbol to put before the {@linkplain #code} and {@linkplain #name},
         * or 0 (the default) if none. Implementations can use this field for putting a
         * mark before objects having some particular characteristics, for example a CRS
         * having unusual axes order.
         */
        public char annotation;

        /**
         * Creates a new row for the given authority code.
         *
         * @param code The authority code.
         */
        public Row(final String code) {
            this.code = code;
        }

        /**
         * Writes this row to the given stream.
         */
        final void write(final Appendable out, final boolean highlight) throws IOException {
            out.append("<tr");              if (highlight)          out.append(" class=\"HL\"");
            out.append("><td>");            if (annotation != 0)    out.append(annotation);
            out.append("</td><td><code>");  if (code       != null) out.append(code);
            out.append("</code></td><td>"); if (name       != null) out.append(name);
            out.append("</td><td");         if (hasError)           out.append(" class=\"error\"");
            out.append('>');                if (remark     != null) out.append(remark);
            out.append("</td></tr>");
        }

        /**
         * Returns a string representation of this row, for debugging purpose only.
         */
        @Override
        public String toString() {
            final StringBuilder buffer = new StringBuilder(64);
            try {
                write(buffer, false);
            } catch (IOException e) {
                throw new AssertionError(e); // Should never happen.
            }
            return buffer.toString();
        }

        /**
         * Compares this row with the given one for order. The default implementation
         * {@linkplain String#split(String) splits} the code spaces (or scopes) from the
         * codes using the {@code ":"} separator, then compares each elements. This method tries
         * to compare the elements as numeric values if possible (i.e. 4326 is less than 27561).
         * If the codes can not be compared as numerical values, then they are compared as strings
         * using a {@linkplain String#CASE_INSENSITIVE_ORDER case-insensitive comparator}.
         *
         * <p>Subclasses can override this method if they want a different rows ordering.</p>
         */
        @Override
        public int compareTo(final Row o) {
            return IdentifiedObjects.compare(code.split(IdentifiedObjects.SEPARATOR),
                                           o.code.split(IdentifiedObjects.SEPARATOR));
        }
    }

    /**
     * The list of objects identified by the codes declared by the authority factory. Elements
     * are added in this list by any of the {@link #add(CRSAuthorityFactory) add} methods.
     */
    protected final List<Row> rows;

    /**
     * Creates a new report generator using the given property values.
     * See the class javadoc for a list of expected values.
     *
     * @param properties The property values, or {@code null} for the default values.
     */
    public AuthorityCodesReport(final Properties properties) {
        super(properties);
        rows = new ArrayList<Row>(1024);
        defaultProperties.setProperty("TITLE", "Authority codes for ${OBJECTS.KIND}");
        defaultProperties.setProperty("OBJECTS.KIND", "Identified Objects");
        defaultProperties.setProperty("FACTORY.VERSION.SUFFIX", "");
    }

    /**
     * Sets the default product name and factory name.
     */
    private void setDefault(final AuthorityFactory factory) {
        setVendor("PRODUCT", factory.getVendor());
        setVendor("FACTORY", factory.getAuthority());
    }

    /**
     * Adds the given row to the {@link #rows} list, of non-null.
     */
    private void add(final Row row) {
        if (row != null) {
            rows.add(row);
        }
    }

    /**
     * Adds the Coordinate Reference Systems identified by all codes available from the given
     * CRS authority factory. More specifically this method performs the following steps:
     * <p>
     * <ul>
     *   <li>Get the list of available codes for type {@link CoordinateReferenceSystem}
     *     with {@link CRSAuthorityFactory#getAuthorityCodes(Class)}.</li>
     *   <li>For each code, try to instantiate an object with
     *     {@link CRSAuthorityFactory#createCoordinateReferenceSystem(String)}, then:
     *     <ul>
     *       <li>In case of success, invoke {@link #createRow(String, IdentifiedObject)};</li>
     *       <li>In case of failure, invoke {@link #createRow(String, FactoryException)}.</li>
     *     </ul>
     *   </li>
     *   <li>If the {@code createRow(…)} method returned a non-null
     *       instance, add the created row to the {@link #rows} list.</li>
     * </ul>
     * <p>
     * Subclasses can override the above-cited {@code createRow(…)}
     * methods in order to customize the table content.
     *
     * @param  factory The factory from which to get Coordinate Reference System instances.
     * @throws FactoryException If a non-recoverable error occurred while querying the factory.
     */
    public void add(final CRSAuthorityFactory factory) throws FactoryException {
        setDefault(factory);
        defaultProperties.setProperty("TITLE", "Authority codes for Coordinate Reference Systems");
        defaultProperties.setProperty("OBJECTS.KIND", "Coordinate Reference Systems (CRS)");
        defaultProperties.setProperty("FILENAME", "CRS-Codes.html");
        for (final String code : factory.getAuthorityCodes(CoordinateReferenceSystem.class)) {
            final CoordinateReferenceSystem crs;
            try {
                crs = factory.createCoordinateReferenceSystem(code);
            } catch (FactoryException exception) {
                add(createRow(code, exception));
                continue;
            }
            add(createRow(code, crs));
        }
    }

    /**
     * Adds the objects identified by the given codes. More specifically this method performs
     * the following steps:
     * <p>
     * <ul>
     *   <li>For each code, try to instantiate an object with
     *     {@link AuthorityFactory#createObject(String)}, then:
     *     <ul>
     *       <li>In case of success, invoke {@link #createRow(String, IdentifiedObject)};</li>
     *       <li>In case of failure, invoke {@link #createRow(String, FactoryException)}.</li>
     *     </ul>
     *   </li>
     *   <li>If the {@code createRow(…)} method returned a non-null
     *       instance, add the created row to the {@link #rows} list.</li>
     * </ul>
     * <p>
     * Subclasses can override the above-cited {@code createRow(…)}
     * methods in order to customize the table content.
     *
     * @param  factory The factory from which to get the objects.
     * @param  codes The authority codes of the objects to create.
     * @throws FactoryException If a non-recoverable error occurred while querying the factory.
     */
    public void add(final AuthorityFactory factory, final Iterable<String> codes) throws FactoryException {
        setDefault(factory);
        for (final String code : codes) {
            final IdentifiedObject object;
            try {
                object = factory.createObject(code);
            } catch (FactoryException exception) {
                add(createRow(code, exception));
                continue;
            }
            add(createRow(code, object));
        }
    }

    /**
     * Creates a new row for the given authority code and identified object. Subclasses
     * can override this method in order to customize the table content.
     *
     * @param  code    The authority code of the created object.
     * @param  object  The object created from the given authority code.
     * @return The created row, or {@code null} if the row should be ignored.
     */
    protected Row createRow(final String code, final IdentifiedObject object) {
        final Row row = new Row(code);
        if (object != null) {
            final Identifier name = object.getName();
            if (name != null) {
                row.name = name.getCode();
            }
            row.remark = toString(object.getRemarks());
        }
        return row;
    }

    /**
     * Creates a new row for the given authority code and exception. Subclasses
     * can override this method in order to customize the table content.
     *
     * @param  code      The authority code of the object to create.
     * @param  exception The exception that occurred while creating the identified object.
     * @return The created row, or {@code null} if the row should be ignored.
     */
    protected Row createRow(final String code, final FactoryException exception) {
        final Row row = new Row(code);
        row.hasError = true;
        if (exception != null) {
            row.remark = exception.getLocalizedMessage();
            if (row.remark == null) {
                row.remark = exception.toString();
            }
        }
        return row;
    }

    /**
     * Formats the identified objects as a HTML page in the given file.
     *
     * @param  destination The file to generate.
     * @return The given {@code destination} file.
     */
    @Override
    public File write(File destination) throws IOException {
        final int numRows = rows.size();
        int numValids = 0, numAnnotations = 0;
        for (final Row row : rows) {
            if (!row.hasError) numValids++;
            if (row.annotation != 0) numAnnotations++;
        }
        defaultProperties.setProperty("COUNT.OBJECTS",     Integer.toString(numRows));
        defaultProperties.setProperty("PERCENT.VALIDS",    Integer.toString(100 * numValids / numRows) + '%'); // Really want rounding toward 0.
        defaultProperties.setProperty("PERCENT.ANNOTATED", Integer.toString(Math.round(100f * numAnnotations / numRows)) + '%');
        Collections.sort(rows);
        /*
         * The above initialization needs to be done before to start
         * the actual content writing. Now we can write the HTML table.
         */
        destination = toFile(destination);
        filter("AuthorityCodes.html", destination);
        return destination;
    }

    /**
     * Invoked by {@link Report} every time a {@code ${FOO}} occurrence is found.
     */
    @Override
    final void writeContent(final BufferedWriter out, final String key) throws IOException {
        if (!"CONTENT".equals(key)) {
            super.writeContent(out, key);
            return;
        }
        int c = 0;
        for (final Row row : rows) {
            writeIndentation(out, 8);
            row.write(out, (c & 2) != 0);
            out.newLine();
            c++;
        }
    }
}