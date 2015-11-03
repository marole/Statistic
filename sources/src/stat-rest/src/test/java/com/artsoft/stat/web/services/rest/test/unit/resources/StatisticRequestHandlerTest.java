/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.test.unit.resources;


import static org.mockito.Mockito.verify;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.artsoft.stat.business.domain.model.ResolutionEntity;
import com.artsoft.stat.business.domain.model.StatisticEntity;
import com.artsoft.stat.business.logic.api.StatisticServiceRemote;
import com.artsoft.stat.web.services.rest.domain.Resolution;
import com.artsoft.stat.web.services.rest.domain.Statistic;
import com.artsoft.stat.web.services.rest.resources.StatisticRequestHandler;


/**
 * The StatisticRequestHandler unit test class.
 */
public class StatisticRequestHandlerTest
{
    @Mock
    private StatisticServiceRemote statisticService;


    /**
     * Test invoking related components with correct data.
     */
    @Test
    public void testInvokeCorrectData()
    {
        Resolution resolution = new Resolution();
        resolution.setHeight(600);
        resolution.setWidth(800);
        Statistic statistic = new Statistic();
        statistic.setName("name");
        statistic.setValue("value");
        statistic.setResolution(resolution);
        ResolutionEntity resolutionEntity = new ResolutionEntity(resolution.getWidth(), resolution.getHeight());
        StatisticEntity statisticEntity = new StatisticEntity(statistic.getName(), statistic.getValue(),
            resolutionEntity);

        // when
        StatisticRequestHandler statisticRequestHandler = new StatisticRequestHandler(statisticService);
        statisticRequestHandler.invoke(statistic);

        // then
        verify(statisticService).newStatistic(statisticEntity);
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
