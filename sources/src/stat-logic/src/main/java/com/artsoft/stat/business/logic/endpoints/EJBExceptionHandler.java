/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.business.logic.endpoints;


import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;


/**
 * The interceptor binding for EJB Exception Handler.
 *
 * @author Marcin Olejarczyk
 */
@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface EJBExceptionHandler {
}
