/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.application;


import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.artsoft.stat.web.services.rest.resources.ExceptionHandler;
import com.artsoft.stat.web.services.rest.resources.InputDataExceptionHandler;
import com.artsoft.stat.web.services.rest.resources.StatResourceService;


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
    }
    
    


    @Override
    public Set<Class<?>> getClasses()
    {
        // return instanced classes
        
        Set<Class<?>> classes = new HashSet<>();
        classes.add(InputDataExceptionHandler.class);
        classes.add(ExceptionHandler.class);
        classes.add(StatResourceService.class);

        return classes;
    }


    @Override
    public Set<Object> getSingletons()
    {
        // no singleton classes
        
        return new HashSet<>();
    }
}
