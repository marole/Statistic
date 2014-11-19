/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.logic.decoder;


import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.Arrays;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import com.artsoft.stat.web.services.rest.common.Helper;
import com.artsoft.stat.web.services.rest.domain.Statistic;


/**
 * The Xml request decoder is a class to decode xml data to Statistic information.
 *
 * A XmlRequestDecoder object is thread safe and applications are encouraged to share it across many
 * clients.
 */
public class XmlRequestDecoder implements RequestDecoder
{
    private static final Log logger = LogFactory.getLog(XmlRequestDecoder.class);
    private final JAXBContext jaxbContext; // thread-safe - JSR-000222 (page 34)
    private Schema schema; // immutable object, thread-safe


    /**
     * Instantiates a new xml request decoder.
     *
     * @param paramArrayOfClass the array of class representing data structure
     * @throws JAXBException if an error was encountered while creating the JAXBContext
     * @throws IllegalArgumentException if the array of class parameter is null
     */
    public XmlRequestDecoder(final Class<?>... paramArrayOfClass) throws JAXBException, IllegalArgumentException
    {
        if (logger.isTraceEnabled()) {
            logger.trace("Creating XmlRequestDecoder instance with array of class representing data structure: "
                + Arrays.toString(paramArrayOfClass));
        }

        if (paramArrayOfClass == null) {
            logger.info("Array of class is null.");
            throw new IllegalArgumentException("Array of class is null.");
        }

        jaxbContext = JAXBContext.newInstance(paramArrayOfClass);
        logger.debug("XmlRequestDecoder instance created successfully.");
    }


    @Override
    public Statistic decode(final String request) throws DecoderException, IllegalArgumentException
    {
        if (logger.isTraceEnabled()) {
            logger.trace("Start decoding received xml data to Statistic object. Received xml data"
                + IOUtils.LINE_SEPARATOR + request);
        }

        if (request == null) {
            logger.info("Request string to decode is null.");
            throw new IllegalArgumentException("Request string to decode is null.");
        }

        try (Reader readStream = new StringReader(request)) {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setSchema(schema);
            Statistic stat = (Statistic)unmarshaller.unmarshal(readStream);

            if (logger.isDebugEnabled()) {
                logger.debug("Decode xml data finished successfully with result: " + stat);
            }
            return stat;
        }
        catch (IOException | JAXBException e) {
            logger.info("Encoding XML data as Statistic object is not possible due to: "
                + Helper.exceptionCauseChain(e));
            throw new DecoderException("Encoding XML data as Statistic object is not possible.", e);
        }
    }


    /**
     * Sets the xml schema for decoder validation.
     *
     * @param xmlSchemaUrl the xml schema url location
     * @throws SAXException if a SAX error occurs during parsing
     */
    public void setXmlSchema(final URL xmlSchemaUrl) throws SAXException
    {
        if (logger.isDebugEnabled()) {
            logger.debug("Setting xml schema file as: " + xmlSchemaUrl);
        }

        if (xmlSchemaUrl == null) {
            schema = null;
            logger.debug("Schema instance is reset to null.");
        }
        else {
            schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(xmlSchemaUrl);
            logger.debug("Schema creation instance created.");
        }
    }
}
