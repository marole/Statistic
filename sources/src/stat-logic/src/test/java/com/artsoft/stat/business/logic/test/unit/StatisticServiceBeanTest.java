
package com.artsoft.stat.business.logic.test.unit;


import static org.mockito.Mockito.verify;

import javax.validation.Validation;
import javax.validation.Validator;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.artsoft.stat.business.domain.dao.ResolutionDao;
import com.artsoft.stat.business.domain.dao.StatisticDao;
import com.artsoft.stat.business.domain.model.ResolutionEntity;
import com.artsoft.stat.business.domain.model.StatisticEntity;
import com.artsoft.stat.business.logic.api.InputDataViolationException;
import com.artsoft.stat.business.logic.endpoints.StatisticServiceBean;
import com.artsoft.stat.business.logic.test.integration.StatisticDataProvider;


/**
 * The StatisticServiceBean unit test class.
 */
public class StatisticServiceBeanTest
{
    @Spy
    private final Validator validatorSpy = Validation.buildDefaultValidatorFactory().getValidator();

    @Mock
    private ResolutionDao resolutionDaoMock;

    @Mock
    private StatisticDao statisticDaoMock;

    @InjectMocks
    private StatisticServiceBean statisticServiceBean;


    /**
     * Test receiving a new correct statistic object.
     * 
     * @param name the name
     * @param value the value
     * @param width the width
     * @param height the height
     * @param dbFileName database file name (not used)
     */
    @Test(dataProvider = "correctStatisticDataProvider", dataProviderClass = StatisticDataProvider.class)
    public void testCorrectStatisticReceived(final String name, final String value, final int width,
        final int height, final String dbFileName)
    {
        // given
        StatisticEntity statisticEntity = new StatisticEntity(name, value, new ResolutionEntity(width, height));

        // when
        statisticServiceBean.newStatistic(statisticEntity);

        // then
        verify(resolutionDaoMock).findByWidthAndHeight(width, height);
        verify(statisticDaoMock).create(statisticEntity);
    }


    /**
     * Test receiving a new incorrect statistic object.
     * 
     * @param name the name
     * @param value the value
     * @param width the width
     * @param height the height
     */
    @Test(dataProvider = "incorrectStatisticDataProvider",
        expectedExceptions = InputDataViolationException.class,
        dataProviderClass = StatisticDataProvider.class)
    public void testIncorrectStatisticReceived(final String name, final String value, final int width,
        final int height)
    {
        // given
        StatisticEntity statisticEntity = new StatisticEntity(name, value, new ResolutionEntity(width, height));

        // when
        statisticServiceBean.newStatistic(statisticEntity);

        // then
    }


    /**
     * Test null statistic received.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullStatisticReceived()
    {
        // given
        
        // when
        statisticServiceBean.newStatistic(null);

        // then
    }


    /**
     * Sets the up.
     */
    @BeforeMethod
    protected void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }
}
