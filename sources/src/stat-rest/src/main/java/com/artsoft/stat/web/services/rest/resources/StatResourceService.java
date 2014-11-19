/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.resources;


import java.io.InputStream;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import com.artsoft.stat.business.logic.api.StatisticServiceRemote;
import com.artsoft.stat.web.services.rest.api.InputRequestException;
import com.artsoft.stat.web.services.rest.api.StatResourceRest;
import com.artsoft.stat.web.services.rest.api.StatResourceRestUnpredictableException;
import com.artsoft.stat.web.services.rest.common.Constants;
import com.artsoft.stat.web.services.rest.domain.Statistic;
import com.artsoft.stat.web.services.rest.logic.decoder.DecoderException;
import com.artsoft.stat.web.services.rest.logic.decoder.RequestDecoder;
import com.artsoft.stat.web.services.rest.logic.decoder.XmlRequestDecoder;


/**
 * This class is implementation of statistic resource RESTful service.
 */
public class StatResourceService implements StatResourceRest
{
    private static final Log logger = LogFactory.getLog(StatResourceService.class);
    private static final RequestDecoder decoder; // thread-safe
    private StatisticRequestHandler handler;

    @EJB
    private StatisticServiceRemote ejb;


    static {
        logger.info("Start creating JAX-RS request decoder.");

        try {
            decoder = createXmlRequestDecoder();
        }
        catch (Exception e) {
            logger.info("Unpredictable exception occured during creating XML rerquest decoder.");
            throw new StatResourceRestUnpredictableException(
                "Unpredictable exception occured during creating XML rerquest decoder.", e);
        }

        logger.info("JAX-RS request decoder created successfully.");
    }

    
    /**
     * Instantiates a new statistic resource service.
     */
    public StatResourceService()
    {
    }
    
    /**
     * Creates the xml request decoder.
     * 
     * @return the xml request decoder
     * @throws SAXException if a SAX error occurs during parsing
     * @throws JAXBException if an error was encountered while creating the JAXBContext
     */
    private static RequestDecoder createXmlRequestDecoder() throws SAXException, JAXBException
    {
        logger.debug("Start creating XML Decoder object.");

        URL xmlSchemaUrl = StatResourceService.class.getClassLoader().getResource(Constants.XML_SCHEMA_PATH);

        XmlRequestDecoder xmlDecoder = new XmlRequestDecoder(Statistic.class);
        xmlDecoder.setXmlSchema(xmlSchemaUrl);

        logger.debug("XML Decoder object created successfully.");
        return xmlDecoder;
    }


    @Override
    public Response createNewStatistics(final InputStream is) throws InputRequestException, IllegalArgumentException
    {
        logger.trace("New message received for creating new statistic.");

        if (is == null) {
            logger.info("Received message stream is null.");
            throw new IllegalArgumentException("Received message stream is null.");
        }

        try {
            // convert input data as InputStream to String
            String requestData = IOUtils.toString(is);
            if (logger.isInfoEnabled()) {
                logger.info("-------------> Message received for creating new statistic with data: "
                    + IOUtils.LINE_SEPARATOR + requestData);
            }

            // invoke statistic logic
            handler.invoke(requestData);
        }
        catch (DecoderException e) {
            // exception is related with statistic data encoding
            logger.info("Exception occured during dencoding received data.");
            throw new InputRequestException("Exception occured during dencoding received data.", e);
        }
        catch (Exception e) {
            logger.info("Unpredictable exception occured during handling new statistic request.");
            throw new StatResourceRestUnpredictableException(
                "Unpredictable exception occured during handling new statistic request.", e);
        }

        logger.info("<------------- Message handled.");
        return null;
    }


    @PostConstruct
    private void initialize()
    {
        logger.info("Start creating staticstic request handler.");

        handler = new StatisticRequestHandler(decoder, ejb);
    }
}
