/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.common;


import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLPropertiesConfiguration;


/**
 * The Class Constants.
 */
public abstract class Constants
{
    private static final String CONFIG_FILE_NAME = "config.xml";

    /** Location of file contains xml schema of REST request data. */
    public static final String XML_SCHEMA_PATH;

    /** Location of file contains statistic wadl definition. */
    public static final String WADL_PATH;

    static {
        try {
            XMLPropertiesConfiguration config = new XMLPropertiesConfiguration(CONFIG_FILE_NAME);
            config.setThrowExceptionOnMissing(true);

            WADL_PATH = config.getString("wadl_path");
            XML_SCHEMA_PATH = config.getString("xml_schema_path");
        }
        catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }


    private Constants()
    {
    }
}
