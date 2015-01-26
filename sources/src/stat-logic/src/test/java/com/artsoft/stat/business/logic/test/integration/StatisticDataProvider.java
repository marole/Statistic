/*
 * ArtSoft 2015.
 */

package com.artsoft.stat.business.logic.test.integration;


import org.testng.annotations.DataProvider;


/**
 * The StatisticDataProvider class responsible of data providing for test classes.
 */
public class StatisticDataProvider
{
    /**
     * Correct statistic object provider.
     *
     * @return the object[][]
     */
    @DataProvider(name = "correctStatisticDataProvider")
    public static Object[][] correctStatisticDataProvider()
    {
        return new Object[][] {
            // statistic.name, statistic.value, resolution.width, resolution.height, dbFileName
            {"name0", "value0", 100, 200, Config.STATISTIC_DATASET_1},
            {"name1", "value1", 200, 400, Config.STATISTIC_DATASET_2}
        };
    }


    /**
     * Incorrect statistic object provider.
     *
     * @return the object[][]
     */
    @DataProvider(name = "incorrectStatisticDataProvider")
    public static Object[][] incorrectStatisticDataProvider()
    {
        return new Object[][] {
            {"name1", null, 100, 100},
            {"name2", "value2", -1, 200}
        };
    }
}
