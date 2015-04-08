/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.common;


import org.apache.commons.io.IOUtils;


/**
 * The Helper class.
 */
public class Helper
{
    private Helper()
    {
    }


    /**
     * Exception cause chain generator for given throwable object.
     *
     * @param throwable the throwable object
     * @return the string with exception cause chain
     */
    public static String exceptionCauseChain(final Throwable throwable)
    {
        StringBuilder builder = new StringBuilder();

        // add problem message text or exception class name
        String message = throwable.getLocalizedMessage();
        if (message != null) {
            builder.append(message);
        }
        else {
            builder.append(throwable.getClass().getSimpleName());
        }

        if (throwable.getCause() != null) {
            String messageChain = exceptionCauseChain(throwable.getCause());
            if ((messageChain != null) && (messageChain.length() > 0)) {
                builder.append(IOUtils.LINE_SEPARATOR);
                builder.append("Caused by: ");
            }
            builder.append(messageChain);
        }

        return builder.toString();
    }
}
