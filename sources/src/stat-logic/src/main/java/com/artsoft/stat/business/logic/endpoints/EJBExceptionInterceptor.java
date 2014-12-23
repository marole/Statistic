/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.business.logic.endpoints;


import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artsoft.stat.business.logic.api.StatisticServiceException;
import com.artsoft.stat.business.logic.api.StatisticServiceUnpredictableException;


/**
 * The EJB exception interceptor.
 */
@EJBExceptionHandler
@Interceptor
public class EJBExceptionInterceptor
{
    private static final Log logger = LogFactory.getLog(EJBExceptionInterceptor.class);


    /**
     * Instantiates a new EJB exception interceptor.
     */
    public EJBExceptionInterceptor()
    {
    }


    /**
     * Invoke EJB exception handler.
     *
     * @param invocation the invocation context
     * @return the object
     */
    @AroundInvoke
    public Object invoke(final InvocationContext invocation)
    {
        if (logger.isTraceEnabled()) {
            logger.trace("EJB Exception Handler Interceptor for '" + invocation.getMethod() + "' is invoked.");
        }

        try {
            // call original business method
            return invocation.proceed();
        }
        catch (StatisticServiceException e) {
            // all exceptions defined by this EJB component will be thrown without any changes
            if (logger.isInfoEnabled()) {
                logger.info("Application statistic service exception occurred: " + e.getLocalizedMessage());
            }
            throw e;
        }
        catch (Exception e) {
            // All exceptions not defined by this EJB component will be thrown as component
            // exception.
            logger.error("System statistic service exception occurred. " + e.getLocalizedMessage());
            throw new StatisticServiceUnpredictableException("System statistic service exception occurred. ", e);
        }
        finally {
            logger.trace("EJB Exception Interceptor end.");
        }
    }
}
