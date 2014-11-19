/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.business.logic.api;


import javax.ejb.ApplicationException;


/**
 * The Class StatisticServiceApplicationException.
 */
@ApplicationException
public class StatisticServiceApplicationException extends StatisticServiceException
{
    private static final long serialVersionUID = 1L;


    /**
     * Instantiates a new statistic service application exception.
     */
    public StatisticServiceApplicationException()
    {
        super();
    }


    /**
     * Instantiates a new statistic service application exception.
     *
     * @param message the message
     */
    public StatisticServiceApplicationException(final String message)
    {
        super(message);
    }


    /**
     * Instantiates a new statistic service application exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public StatisticServiceApplicationException(final String message, final Throwable cause)
    {
        super(message, cause);
    }


    /**
     * Instantiates a new statistic service application exception.
     *
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public StatisticServiceApplicationException(final String message, final Throwable cause,
        final boolean enableSuppression, final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    /**
     * Instantiates a new statistic service application exception.
     *
     * @param cause the cause
     */
    public StatisticServiceApplicationException(final Throwable cause)
    {
        super(cause);
    }
}
