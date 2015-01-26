/*
 * ArtSoft 2015.
 */

package com.artsoft.stat.business.logic.test.utils;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;

import com.artsoft.stat.business.domain.model.ResolutionEntity;
import com.artsoft.stat.business.domain.model.StatisticEntity;
import com.artsoft.utils.database.DbTesterHelper;


/**
 * The class for assert for statistic entities.
 */
public class EntitiesHelper
{
    /**
     * The databse schema description.
     */
    public static class Schema
    {
        /** The Resolution table name. */
        public static final String RESOLUTION_TABLE_NAME = "resolution";

        /** The Statistic table name. */
        public static final String STATISTIC_TABLE_NAME = "statistic";
    }


    private static final Map<Integer, ResolutionEntity> RESOLUTION_ENTITY_REF =
        new HashMap<Integer, ResolutionEntity>() {
            private static final long serialVersionUID = 1L;
            {
                put(1, new ResolutionEntity(100, 200));
                put(2, new ResolutionEntity(300, 400));
            }
        };

    private static final Map<Integer, StatisticEntity> STATISTIC_ENTITY_REF =
        new HashMap<Integer, StatisticEntity>() {
            private static final long serialVersionUID = 1L;
            {
                put(1, new StatisticEntity("name0", "value0", RESOLUTION_ENTITY_REF.get(1)));
            }
        };

    private static DbTesterHelper dbHelper;


    /**
     * Assert resolution entity equals to reference data.
     *
     * @param entity the resolution entity to assert
     * @param id the id of resolution entity in reference
     */
    public static void assertEqualsReference(final ResolutionEntity entity, final int id)
    {
        assertNotNull(entity);
        assertEquals(entity.getId(), id);
        assertEquals(entity.getHeight(), RESOLUTION_ENTITY_REF.get(id).getHeight());
        assertEquals(entity.getWidth(), RESOLUTION_ENTITY_REF.get(id).getWidth());
    }


    /**
     * Assert statistic entity equals to reference data.
     *
     * @param entity the statistic entity to assert
     */
    public static void assertEqualsReference(final StatisticEntity entity)
    {
        int id = 1;

        assertNotNull(entity);
        assertEquals(entity.getId(), id);
        assertEquals(entity.getName(), STATISTIC_ENTITY_REF.get(id).getName());
        assertEquals(entity.getValue(), STATISTIC_ENTITY_REF.get(id).getValue());
        assertEqualsReference(entity.getResolution(), entity.getResolution().getId());
    }


    /**
     * Assert dataset specified by parameter 'dataSetPath' with database according to given filter.
     *
     * @param dataSetPath the data set path
     * @param filterTables the filter tables
     * @throws DatabaseUnitException the database unit exception
     */
    public static void assertWithDb(final String dataSetPath, final String[] filterTables)
        throws DatabaseUnitException
    {
        IDataSet expectedDataSet = dbHelper.createDataSetFromFile(dataSetPath, filterTables);
        IDataSet actualDataSet = dbHelper.createDataSetFromDb(filterTables);
        Assertion.assertEquals(expectedDataSet, actualDataSet);
    }


    /**
     * Gets the db helper.
     *
     * @return the db helper
     */
    public static DbTesterHelper getDbHelper()
    {
        return dbHelper;
    }


    /**
     * New resolution entity instance.
     *
     * @return the resolution entity
     */
    public static ResolutionEntity newResolutionEntity()
    {
        return new ResolutionEntity(RESOLUTION_ENTITY_REF.get(1));
    }


    /**
     * New statistic entity instance.
     *
     * @return the statistic entity
     */
    public static StatisticEntity newStatisticEntity()
    {
        return new StatisticEntity(STATISTIC_ENTITY_REF.get(1));
    }


    /**
     * Sets the db helper.
     *
     * @param dbHelper the db helper
     */
    public static void setDbHelper(final DbTesterHelper dbHelper)
    {
        EntitiesHelper.dbHelper = dbHelper;
    }
}
