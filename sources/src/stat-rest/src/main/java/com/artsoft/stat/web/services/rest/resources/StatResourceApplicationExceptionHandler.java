/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.resources;


import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artsoft.stat.web.services.rest.api.StatResourceApplicationException;
import com.artsoft.stat.web.services.rest.common.Helper;


/**
 * This is a handler class for statistic resource application exceptions occurred in the system.
 */
@Provider
public class StatResourceApplicationExceptionHandler implements ExceptionMapper<StatResourceApplicationException>
{
    private static final Log logger = LogFactory.getLog(StatResourceApplicationExceptionHandler.class);


    @Override
    public Response toResponse(final StatResourceApplicationException e)
    {
        // handling application exception by send HTTP code 400

        logger.info("Statistic application exception occured during handling request.", e);
        return Response.status(Response.Status.BAD_REQUEST).entity(Helper.exceptionCauseChain(e)).build();
    }
}
