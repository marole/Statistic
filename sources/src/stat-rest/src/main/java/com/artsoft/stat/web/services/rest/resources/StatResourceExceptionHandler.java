/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.resources;


import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artsoft.stat.web.services.rest.api.StatResourceException;
import com.artsoft.stat.web.services.rest.common.Helper;


/**
 * This is a handler class for unexpected statistic exceptions occurred in the system.
 *
 * @author Marcin Olejarczyk
 */
@Provider
public class StatResourceExceptionHandler implements ExceptionMapper<StatResourceException>
{
    private static final Log logger = LogFactory.getLog(StatResourceExceptionHandler.class);


    @Override
    public Response toResponse(final StatResourceException e)
    {
        logger.error("Unexpected exception occured during handling request.", e);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Helper.exceptionCauseChain(e)).build();
    }
}
