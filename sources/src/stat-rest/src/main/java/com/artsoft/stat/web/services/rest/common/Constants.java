/*
 * ArtSoft 2015.
 */

package com.artsoft.stat.web.services.rest.common;


import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLPropertiesConfiguration;


/**
 * The Class Constants.
 */
public class Constants
{
    /** Location of file contains xml schema of REST request data. */
    public static final String XML_SCHEMA_PATH;

    /** Context path for wadl files. */
    public static final String CONTEXT_DIR;

    private static final String CONFIG_FILE_NAME = "config.xml";


    static {
        try {
            XMLPropertiesConfiguration config = new XMLPropertiesConfiguration(CONFIG_FILE_NAME);
            config.setThrowExceptionOnMissing(true);

            XML_SCHEMA_PATH = config.getString("xml_schema_path");
            CONTEXT_DIR = config.getString("context_path");
        }
        catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Instantiates a new constants.
     */
    protected Constants()
    {
    }
}
