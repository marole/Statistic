/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.business.logic.api;


import javax.ejb.ApplicationException;


/**
 * The input data violation exception class of StatisticService bean.
 */
@ApplicationException(rollback = true)
public class InputDataViolationException extends StatisticServiceApplicationException
{
    private static final long serialVersionUID = 1L;


    /**
     * Instantiates a new input data violation exception.
     */
    public InputDataViolationException()
    {
        super();
    }


    /**
     * Instantiates a new input data violation exception.
     *
     * @param message the message
     */
    public InputDataViolationException(final String message)
    {
        super(message);
    }


    /**
     * Instantiates a new input data violation exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public InputDataViolationException(final String message, final Throwable cause)
    {
        super(message, cause);
    }


    /**
     * Instantiates a new input data violation exception.
     *
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public InputDataViolationException(final String message, final Throwable cause, final boolean enableSuppression,
        final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    /**
     * Instantiates a new input data violation exception.
     *
     * @param cause the cause
     */
    public InputDataViolationException(final Throwable cause)
    {
        super(cause);
    }
}
