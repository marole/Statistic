/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.business.logic.api;


/**
 * The Class StatisticServiceInternalException.
 */
public class StatisticServiceUnpredictableException extends StatisticServiceException
{
    private static final long serialVersionUID = 1L;


    /**
     * Instantiates a new statistic service internal exception.
     */
    public StatisticServiceUnpredictableException()
    {
        super();
    }


    /**
     * Instantiates a new statistic service internal exception.
     *
     * @param message the message
     */
    public StatisticServiceUnpredictableException(final String message)
    {
        super(message);
    }


    /**
     * Instantiates a new statistic service internal exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public StatisticServiceUnpredictableException(final String message, final Throwable cause)
    {
        super(message, cause);
    }


    /**
     * Instantiates a new statistic service internal exception.
     *
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public StatisticServiceUnpredictableException(final String message, final Throwable cause,
        final boolean enableSuppression,
        final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    /**
     * Instantiates a new statistic service internal exception.
     *
     * @param cause the cause
     */
    public StatisticServiceUnpredictableException(final Throwable cause)
    {
        super(cause);
    }
}
