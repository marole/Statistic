/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.api;


import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


/**
 * The RESTful interface for statistic resource.
 */
@Path("stats")
public interface StatResourceRest
{

    /**
     * Creates a new statistics.
     *
     * @param is the input stream of request
     * @return the response
     * @throws InputRequestException the input data exception
     * @throws IllegalArgumentException if stream object is null
     */
    @POST
    @Consumes("application/xml")
    @Produces("text/plain")
    Response createNewStatistics(final InputStream is) throws InputRequestException, IllegalArgumentException;
}
