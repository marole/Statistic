/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.logic.decoder;


import com.artsoft.stat.web.services.rest.domain.Statistic;


/**
 * The RequestDecoder is a interface of decode data to Statistic information.
 *
 * A RequestDecoder object is thread safe and applications are encouraged to share it across many
 * clients.
 */
public interface RequestDecoder
{
    /**
     * Decode request data to Statistic object.
     *
     * @param request the request data to decode
     * @return the statistic object obtained from request data
     * @throws DecoderException if an error was encountered while decoding data
     * @throws IllegalArgumentException if parameter is illegal
     */
    Statistic decode(String request) throws DecoderException, IllegalArgumentException;
}
