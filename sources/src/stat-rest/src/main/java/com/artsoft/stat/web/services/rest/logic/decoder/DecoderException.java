/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.logic.decoder;


/**
 * The exception informs about problem during decode data to Statistic information.
 */
public class DecoderException extends RuntimeException
{
    private static final long serialVersionUID = 1L;


    /**
     * Constructs a new decoder exception with null as its detail message.
     */
    public DecoderException()
    {
        super();
    }


    /**
     * Constructs a new decoder exception with the specified detail message. The cause is not
     * initialized, and may subsequently be initialized by a call to
     * Throwable.initCause(java.lang.Throwable).
     * 
     * @param message the detail message. The detail message is saved for later retrieval by the
     *        Throwable.getMessage() method.
     */
    public DecoderException(final String message)
    {
        super(message);
    }


    /**
     * Instantiates a new decoder exception.
     * 
     * @param message the message
     * @param cause the cause
     */
    public DecoderException(final String message, final Throwable cause)
    {
        super(message, cause);
    }


    /**
     * Instantiates a new decoder exception.
     * 
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public DecoderException(final String message, final Throwable cause, final boolean enableSuppression,
        final boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    /**
     * Instantiates a new decoder exception.
     * 
     * @param cause the cause
     */
    public DecoderException(final Throwable cause)
    {
        super(cause);
    }
}
