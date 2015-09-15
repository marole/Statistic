/*
 * ArtSoft 2015.
 */

package com.artsoft.stat.business.domain.test.integration;


import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLPropertiesConfiguration;


/**
 * The configuration class for statistic repository module integration test.
 */
public class Config
{
    private static final String IT_CONFIG_FILE_NAME = "it-config.xml";
    private static final String DB_JNDI_PROPERTY_NAME = "db_jndi";

    /** Data set with all entities used for testing. */
    public static final String ALL_ENTITIES_DB_XML = "all_entities_db.xml";

    /** Data set with resolution entity used for testing. */
    public static final String RESOLUTION_ENTITY_DB_XML = "resolution_entity_db.xml";

    /** SQL used to truncate db data. */
    public static final String TRUNCATE_ALL_DB_SQL = "truncate_db.sql";

    /** JNDI of data source. */
    public static final String DB_JNDI;


    static {
        try {
            XMLPropertiesConfiguration config = new XMLPropertiesConfiguration(IT_CONFIG_FILE_NAME);
            config.setThrowExceptionOnMissing(true);

            DB_JNDI = config.getString(DB_JNDI_PROPERTY_NAME);
        }
        catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Instantiates a new config.
     */
    protected Config()
    {
    }
}
