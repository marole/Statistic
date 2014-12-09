/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.resources;


import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.UnmarshalException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artsoft.stat.web.services.rest.api.StatResourceApplicationException;
import com.artsoft.stat.web.services.rest.common.Helper;


/**
 * This is a handler class for unmarshal exceptions occurred in the system.
 *
 * @author Marcin Olejarczyk
 */
@Provider
public class UnmarshalExceptionHandler implements ExceptionMapper<UnmarshalException>
{
    private static final Log logger = LogFactory.getLog(UnmarshalExceptionHandler.class);


    @Override
    public Response toResponse(final UnmarshalException e)
    {
        logger.info("Input data unmarshal exception occured during handling request.");

        StatResourceApplicationException e2 = new StatResourceApplicationException(
            "Input data unmarshal exception occured during handling request.", e);
        return Response.status(Response.Status.BAD_REQUEST).entity(Helper.exceptionCauseChain(e2)).build();
    }
}
