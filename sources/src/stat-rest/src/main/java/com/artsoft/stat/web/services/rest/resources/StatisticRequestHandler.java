/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.resources;


import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artsoft.stat.business.domain.model.ResolutionEntity;
import com.artsoft.stat.business.domain.model.StatisticEntity;
import com.artsoft.stat.business.logic.api.InputDataViolationException;
import com.artsoft.stat.business.logic.api.StatisticService;
import com.artsoft.stat.web.services.rest.domain.Statistic;


/**
 * The Statistic request Handler class.
 */
public class StatisticRequestHandler
{
    private static final Log logger = LogFactory.getLog(StatisticRequestHandler.class);
    private final StatisticService statisticDataHandler;


    /**
     * Instantiates a new statistic request handler.
     *
     * @param statisticDataHandler the statistic data handler
     */
    public StatisticRequestHandler(final StatisticService statisticDataHandler)
    {
        this.statisticDataHandler = statisticDataHandler;
    }


    /**
     * Invoke handling statistic request data.
     *
     * @param stat the statistic data to handle
     *
     * @throws IllegalArgumentException if statistic data is null
     * @throws InputDataViolationException the statistic data is incorrect
     */
    public void invoke(final Statistic stat) throws IllegalArgumentException, InputDataViolationException
    {
        if (logger.isDebugEnabled()) {
            logger.debug("Start handling new statistic request: " + IOUtils.LINE_SEPARATOR + stat);
        }

        if (stat == null) {
            logger.info("Received statistic data is null.");
            throw new IllegalArgumentException("Received statistic data is null.");
        }

        StatisticEntity entity = buildStatEntityObject(stat);
        handleStatisticData(entity);

        logger.debug("Statistic request hzandling ended.");
    }


    /**
     * Builds the statistic entity object.
     *
     * @return the statistic entity data
     */
    private StatisticEntity buildStatEntityObject(final Statistic stat)
    {
        if (logger.isDebugEnabled()) {
            logger.debug("Decoded statistic data: " + stat);
        }

        try {
            ResolutionEntity resolutionEntity = new ResolutionEntity();
            BeanUtils.copyProperties(resolutionEntity, stat.getResolution());

            StatisticEntity statisticEntity = new StatisticEntity();
            statisticEntity.setResolution(resolutionEntity);
            statisticEntity.setName(stat.getName());
            statisticEntity.setValue(stat.getValue());

            if (logger.isDebugEnabled()) {
                logger.debug("Statistic entity object created: " + statisticEntity);
            }

            return statisticEntity;
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            logger.info("Error during using reflections.");
            throw new ReflectionException("Error during using reflections.", e);
        }
    }


    /**
     * Handler statistic data by server.
     *
     * @param statistic the statistic data
     * @throws InputDataViolationException the statistic data is incorrect
     */
    private void handleStatisticData(final StatisticEntity statistic) throws InputDataViolationException
    {
        logger.trace("Initiate sending statistic object to ejb.");

        statisticDataHandler.newStatistic(statistic);

        logger.info("Statistic data handled successfully by server.");
    }
}
