/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.application;


import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;


/**
 * The custom ContextResolver class dedicated for Statistic application.
 *
 * @author Marcin Olejarczyk
 */
@Provider
public class StatisticContextResolver implements ContextResolver<JAXBContext>
{
    /**
     * The custom JAXBContext class providing schema validation for unmarshalling operation.
     *
     * @author Marcin Olejarczyk
     */
    private static class ValidatingJAXBContext extends JAXBContext
    {
        private static final Log logger = LogFactory.getLog(ValidatingJAXBContext.class);

        private final JAXBContext contextDelegate;
        private final Schema schema;


        /**
         * Instantiates a new validating JAXB context.
         *
         * @param schemaFile the schema file
         * @param classToBeBound the class to be bound with the schema
         *
         * @throws JAXBException the JAXB exception
         * @throws SAXException the SAX exception
         */
        protected ValidatingJAXBContext(final String schemaFile, final Class<?> classToBeBound) throws JAXBException,
            SAXException
        {
            contextDelegate = JAXBContext.newInstance(classToBeBound);
            schema = createXmlSchema(schemaFile);
        }


        /**
         * Instantiates a new validating JAXB context.
         *
         * @param schemaFile the schema file
         * @param classToBeBound the class to be bound
         *
         * @return the validating jaxb context instance
         *
         * @throws JAXBException the JAXB exception
         * @throws SAXException the SAX exception
         */
        public static ValidatingJAXBContext newInstance(final String schemaFile, final Class<?> classToBeBound)
            throws JAXBException, SAXException
        {
            if (logger.isDebugEnabled()) {
                logger.debug("Creation validating JAXB context for class '" + classToBeBound.getName()
                    + "' with schema '" + schemaFile + "'.");
            }

            return new ValidatingJAXBContext(schemaFile, classToBeBound);
        }


        @Override
        public Marshaller createMarshaller() throws JAXBException
        {
            return contextDelegate.createMarshaller();
        }


        @Override
        public Unmarshaller createUnmarshaller() throws JAXBException
        {
            if (logger.isDebugEnabled()) {
                logger.debug("Creation a new unmarshaller for schema '" + schema + "'.");
            }

            Unmarshaller unmarshaller = contextDelegate.createUnmarshaller();
            unmarshaller.setSchema(schema);

            return unmarshaller;
        }


        @SuppressWarnings("deprecation")
        @Override
        public javax.xml.bind.Validator createValidator() throws JAXBException
        {
            return contextDelegate.createValidator();
        }


        private Schema createXmlSchema(final String schemaFile) throws SAXException
        {
            Schema tempSchema;

            if (logger.isDebugEnabled()) {
                logger.debug("Creation schema for file '" + schemaFile + "'.");
            }

            if (schemaFile == null) {
                logger.debug("Schema file is empty.");
                tempSchema = null;
            }
            else {
                URL xmlSchemaUrl = StatisticContextResolver.class.getClassLoader().getResource(schemaFile);
                if (xmlSchemaUrl == null) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("XML schema not created for file '" + schemaFile + "'.");
                    }
                    tempSchema = null;
                }
                else {
                    tempSchema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(xmlSchemaUrl);
                    if (logger.isDebugEnabled()) {
                        logger.debug("XML schema created successfully for file '" + schemaFile + "'.");
                    }
                }
            }

            return tempSchema;
        }
    }

    private static final Log logger = LogFactory.getLog(StatisticContextResolver.class);
    private static Map<Class<?>, String> registeredSchemaFileMap = new HashMap<>();


    /**
     * Register schema.
     *
     * @param clas the class name for which schema file is registering
     * @param schemaFile the schema file for given class
     */
    public static void registerSchema(final Class<?> clas, final String schemaFile)
    {
        if (logger.isDebugEnabled()) {
            logger.debug("Registering schema file '" + schemaFile + "' for class '" + clas.getName() + "'.");
        }

        if (registeredSchemaFileMap.get(clas) != null) {
            return;
        }
        registeredSchemaFileMap.put(clas, schemaFile);

        if (logger.isDebugEnabled()) {
            logger.debug("Schema file '" + schemaFile + "' for class '" + clas.getName() + "' registered.");
        }
    }


    /**
     * Unregister schema.
     *
     * @param clas the class name for which schema file is unregistering
     */
    public static void unregisterSchema(final Class<?> clas)
    {
        if (logger.isDebugEnabled()) {
            logger.debug("Unregistering schema file for class '" + clas.getName() + "'.");
        }

        registeredSchemaFileMap.remove(clas);
    }


    @Override
    public JAXBContext getContext(final Class<?> clas)
    {
        if (logger.isDebugEnabled()) {
            logger.debug("Start creation JAXBContext instance for class '" + clas.getName() + "'.");
        }

        JAXBContext jaxbContext = null;
        try {
            jaxbContext = ValidatingJAXBContext.newInstance(registeredSchemaFileMap.get(clas), clas);
        }
        catch (JAXBException | SAXException e) {
            if (logger.isWarnEnabled()) {
                logger.warn("JAXBContext instance for class '" + clas.getName() + "' uncreated due to exception.", e);
            }
            return null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("JAXBContext instance created for class '" + clas.getName() + "'.");
        }

        return jaxbContext;
    }
}
