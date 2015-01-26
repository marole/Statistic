/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.common;


import java.util.HashSet;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLPropertiesConfiguration;


/**
 * The Class Constants.
 */
public abstract class Constants
{
    /** Location of file contains xml schema of REST request data. */
    public static final String XML_SCHEMA_PATH;

    /** Context path for wadl files. */
    public static final String CONTEXT_DIR;

    /** Set of files with no client access. */
    public static final Set<String> FILES_WITH_NO_CLIENT_ACCESS = new HashSet<>();

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
}
