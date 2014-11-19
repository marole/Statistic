/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.business.domain.dao;


/**
 * Thrown by dao when a transaction is required but is not active.
 */
public class TransactionRequiredDaoException extends StatisticDaoException
{
    private static final long serialVersionUID = 1L;


    /**
     * Constructs a new TransactionRequiredDaoException exception with null as its detail message.
     */
    public TransactionRequiredDaoException()
    {
    }


    /**
     * Constructs a new TransactionRequiredDaoException exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public TransactionRequiredDaoException(final String message)
    {
        super(message);
    }


    /**
     * Constructs a new TransactionRequiredDaoException exception with the specified detail message
     * and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public TransactionRequiredDaoException(final String message, final Throwable cause)
    {
        super(message, cause);
    }


    /**
     * Constructs a new TransactionRequiredDaoException exception with the specified detail message,
     * cause, suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message the detail message
     * @param cause the cause (A null value is permitted, and indicates that the cause is
     *        nonexistent or unknown)
     * @param enableSuppression whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     */
    public TransactionRequiredDaoException(final String message, final Throwable cause,
        final boolean enableSuppression, final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    /**
     * Constructs a new TransactionRequiredDaoException exception with the specified cause and a
     * detail message of (cause==null ? null : cause.toString()) (which typically contains the class
     * and detail message of cause). This constructor is useful for runtime exceptions that are
     * little more than wrappers for other throwables.
     *
     * @param cause the cause (which is saved for later retrieval by the Throwable.getCause()
     *        method). (A null value is permitted, and indicates that the cause is nonexistent or
     *        unknown.)
     */
    public TransactionRequiredDaoException(final Throwable cause)
    {
        super(cause);
    }
}
