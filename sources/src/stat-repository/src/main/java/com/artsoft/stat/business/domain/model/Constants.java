/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.business.domain.model;


/**
 * The Class Constants.
 */
public abstract class Constants
{
    /**
     * The Class Query.
     */
    public abstract static class Query
    {
        /** The width parameter for JPQL. */
        public static final String WIDTH_PARAM = "width";

        /** The height parameter for JPQL. */
        public static final String HEIGHT_PARAM = "height";


        private Query()
        {
        }
    }

    /**
     * The Class Query.
     */
    public abstract static class Schema
    {
        private static final String GENERATOR_TABLE_NAME_SUFIX = "_id_seq";

        /** The name of Resolution entity generator table. */
        public static final String RESOLUTION_GENERATOR_TABLE_NAME = "Resolution" + GENERATOR_TABLE_NAME_SUFIX;

        /** The name of Statistic entity generator table. */
        public static final String STATISTIC_GENERATOR_TABLE_NAME = "Statistic" + GENERATOR_TABLE_NAME_SUFIX;


        private Schema()
        {
        }
    }


    private Constants()
    {
    }
}
