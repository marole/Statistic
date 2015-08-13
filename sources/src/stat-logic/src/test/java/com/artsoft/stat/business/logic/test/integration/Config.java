/*
 * ArtSoft 2015.
 */

package com.artsoft.stat.business.logic.test.integration;


import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLPropertiesConfiguration;


/**
 * The configuration class for statistic repository module integration test.
 */
public class Config
{
    private static final String IT_CONFIG_FILE_NAME = "it-config.xml";
    private static final String DB_JNDI_PROPERTY_NAME = "db_jndi";
    private static final String DATASET_PATH_PREFIX = "/datasets/";

    /** Data set with statistic entity 1 used for testing. */
    public static final String STATISTIC_DATASET_1 = DATASET_PATH_PREFIX + "statistic_db_1.xml";

    /** Data set with statistic entity 2 used for testing. */
    public static final String STATISTIC_DATASET_2 = DATASET_PATH_PREFIX + "statistic_db_2.xml";

    /** Data set with resolution entity used for testing. */
    public static final String RESOLUTION_ENTITY_DB_XML = "resolution_entity_db.xml";

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
