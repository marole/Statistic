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
import com.artsoft.stat.business.logic.api.StatisticServiceRemote;
import com.artsoft.stat.web.services.rest.domain.Statistic;
import com.artsoft.stat.web.services.rest.logic.decoder.DecoderException;
import com.artsoft.stat.web.services.rest.logic.decoder.RequestDecoder;


/**
 * The Statistic request Handler class.
 */
public class StatisticRequestHandler
{
    private static final Log logger = LogFactory.getLog(StatisticRequestHandler.class);
    private final RequestDecoder decoder;
    private final StatisticServiceRemote statisticDataHandler;


    /**
     * Instantiates a new statistic request handler.
     *
     * @param decoder the request data decoder
     * @param statisticDataHandler the statistic data handler
     */
    public StatisticRequestHandler(final RequestDecoder decoder, final StatisticServiceRemote statisticDataHandler)
    {
        this.decoder = decoder;
        this.statisticDataHandler = statisticDataHandler;
    }


    /**
     * Invoke handling statistic request data.
     *
     * @param request the request data
     * @throws DecoderException if an error was encountered while decoding request data
     * @throws IllegalArgumentException if request data is null
     */
    public void invoke(final String request) throws DecoderException, IllegalArgumentException
    {
        if (logger.isDebugEnabled()) {
            logger.debug("Start handling new statistic request: " + IOUtils.LINE_SEPARATOR + request);
        }

        if (request == null) {
            logger.info("Received request data is null.");
            throw new IllegalArgumentException("Received request data is null.");
        }

        StatisticEntity entity = buildStatEntityObject(request);
        handleStatisticData(entity);

        logger.debug("Statistic request hzandling ended.");
    }


    /**
     * Builds the statistic entity object.
     *
     * @param requestData the request data
     * @return the statistic entity
     * @throws DecoderException if an error was encountered while decoding request data
     */
    private StatisticEntity buildStatEntityObject(final String requestData) throws DecoderException
    {
        Statistic stat = decoder.decode(requestData);

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
