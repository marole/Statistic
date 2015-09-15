/*
 * ArtSoft 2015.
 */

package com.artsoft.stat.business.domain.model;


/**
 * The Class Constants.
 */
public class Constants
{
    /**
     * The Class Query.
     */
    public static class Query
    {
        /** The width parameter for JPQL. */
        public static final String WIDTH_PARAM = "width";

        /** The height parameter for JPQL. */
        public static final String HEIGHT_PARAM = "height";


        /**
         * Instantiates a new query.
         */
        protected Query()
        {
        }
    }

    /**
     * The Class Query.
     */
    public static class Schema
    {
        private static final String GENERATOR_TABLE_NAME_SUFIX = "_id_seq";

        /** The name of Resolution entity generator table. */
        public static final String RESOLUTION_GENERATOR_TABLE_NAME = "Resolution" + GENERATOR_TABLE_NAME_SUFIX;

        /** The name of Statistic entity generator table. */
        public static final String STATISTIC_GENERATOR_TABLE_NAME = "Statistic" + GENERATOR_TABLE_NAME_SUFIX;


        /**
         * Instantiates a new schema.
         */
        protected Schema()
        {
        }
    }


    /** The initial odd number for hash calculation. */
    public static final int INIT_ODD_NUMBER = 17;

    /** The multiplier odd number for hash calculation. */
    public static final int MULTI_ODD_NUMBER = 31;


    /**
     * Instantiates a new constants.
     */
    protected Constants()
    {
    }
}
