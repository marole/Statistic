/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.business.domain.dao;


/**
 * The base exception class of Dao bean.
 */
public class StatisticDaoException extends RuntimeException
{
    private static final long serialVersionUID = 1L;


    /**
     * Instantiates a new statistic dao exception.
     */
    public StatisticDaoException()
    {
        // Default exception constructor is needed.
    }


    /**
     * Instantiates a new statistic dao exception.
     *
     * @param message the message
     */
    public StatisticDaoException(final String message)
    {
        super(message);
    }


    /**
     * Instantiates a new statistic dao exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public StatisticDaoException(final String message, final Throwable cause)
    {
        super(message, cause);
    }


    /**
     * Instantiates a new statistic dao exception.
     *
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public StatisticDaoException(final String message, final Throwable cause, final boolean enableSuppression,
        final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    /**
     * Instantiates a new statistic dao exception.
     *
     * @param cause the cause
     */
    public StatisticDaoException(final Throwable cause)
    {
        super(cause);
    }
}
