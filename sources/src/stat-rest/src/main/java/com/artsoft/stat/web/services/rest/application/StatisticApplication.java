/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.application;


import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.artsoft.stat.web.services.rest.common.Constants;
import com.artsoft.stat.web.services.rest.domain.Statistic;
import com.artsoft.stat.web.services.rest.resources.ExceptionHandler;
import com.artsoft.stat.web.services.rest.resources.StatResourceApplicationExceptionHandler;
import com.artsoft.stat.web.services.rest.resources.StatResourceExceptionHandler;
import com.artsoft.stat.web.services.rest.resources.StatResourceService;
import com.artsoft.stat.web.services.rest.resources.StatResourceWadlInfo;
import com.artsoft.stat.web.services.rest.resources.UnmarshalExceptionHandler;


/**
 * The statistic RESTful service application.
 */
@ApplicationPath(value = "/")
public class StatisticApplication extends Application
{
    /**
     * Instantiates a new statistic application.
     */
    public StatisticApplication()
    {
        StatisticContextResolver.registerSchema(Statistic.class, Constants.XML_SCHEMA_PATH);
    }


    @Override
    public Set<Class<?>> getClasses()
    {
        // return instanced classes
        Set<Class<?>> classes = new HashSet<>();

        classes.add(StatResourceService.class);
        classes.add(StatResourceWadlInfo.class);

        classes.add(StatisticContextResolver.class);

        classes.add(ExceptionHandler.class);
        classes.add(StatResourceApplicationExceptionHandler.class);
        classes.add(StatResourceExceptionHandler.class);
        classes.add(UnmarshalExceptionHandler.class);

        return classes;
    }


    @Override
    public Set<Object> getSingletons()
    {
        // no singleton classes
        return new HashSet<>();
    }
}
