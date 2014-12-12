/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.resources;


import java.io.IOException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artsoft.stat.business.logic.api.StatisticServiceRemote;
import com.artsoft.stat.web.services.rest.api.StatResourceRest;
import com.artsoft.stat.web.services.rest.api.StatResourceUnexpectedException;
import com.artsoft.stat.web.services.rest.common.Constants;
import com.artsoft.stat.web.services.rest.domain.Statistic;


/**
 * This class is implementation of statistic resource RESTful service.
 */
public class StatResourceService implements StatResourceRest
{
    private static final Log logger = LogFactory.getLog(StatResourceService.class);
    private StatisticRequestHandler handler;

    @EJB
    private StatisticServiceRemote statisticLogicBean;


    @Override
    public Response createNewStatistics(final Statistic statistic)
    {
        if (logger.isInfoEnabled()) {
            logger.info("-------------> Request received with statistic data: "
                + IOUtils.LINE_SEPARATOR + statistic);
        }

        if (statistic == null) {
            // received statistic data can not be null
            logger.info("Received message is null.");
            throw new IllegalArgumentException("Received message is null.");
        }

        logger.trace("Initiate sending statistic object to ejb.");

        // invoke statistic logic
        handler.invoke(statistic);

        logger.info("<------------- Request handled successfully.");
        return null;
    }


    @Override
    public Response getWadl(final String wadl)
    {
        if (logger.isInfoEnabled()) {
            logger.info("-------------> Request received for wadl info with query paramater '" + wadl + "'.");
        }

        // the wadl argument must be present, but must be empty
        if ((wadl == null) || !wadl.isEmpty()) {
            logger.info("Send server error due to incorrect wadl query paramater.");
            return Response.serverError().build();
        }

        // open the wadl file to present it to client
        URL wadlUrl = StatResourceService.class.getClassLoader().getResource(Constants.WADL_PATH);
        try {
            String wadlStr = IOUtils.toString(wadlUrl);

            logger.info("-------------> Response with wadl file contant.");
            return Response.ok(wadlStr).build();
        }
        catch (IOException e) {
            if (logger.isWarnEnabled()) {
                logger.warn("Problem with reading wadl file due to: " + e.getMessage() + ".");
            }
            throw new StatResourceUnexpectedException("Problem with reading wadl file.", e);
        }
    }


    @PostConstruct
    private void initialize()
    {
        logger.debug("Initialize staticstic rest handler.");

        // create handler with statistic logic reference
        handler = new StatisticRequestHandler(statisticLogicBean);
    }
}
