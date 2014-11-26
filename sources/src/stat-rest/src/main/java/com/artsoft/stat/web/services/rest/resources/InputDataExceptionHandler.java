/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.resources;


import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artsoft.stat.web.services.rest.api.InputRequestException;
import com.artsoft.stat.web.services.rest.common.Helper;


/**
 * The InputDataException handler class.
 */
@Provider
public class InputDataExceptionHandler implements ExceptionMapper<InputRequestException>
{
    private static final Log logger = LogFactory.getLog(InputDataExceptionHandler.class);


    /**
     * Instantiates a new input data exception handler.
     */
    public InputDataExceptionHandler()
    {
    }


    @Override
    public Response toResponse(final InputRequestException e)
    {
        // set bad request for response
        
        logger.info("Application problem with parsing input data provided by the client.");
        return Response.status(Response.Status.BAD_REQUEST).entity(Helper.exceptionCauseChain(e)).build();
    }
}
