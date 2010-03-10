package com.philbeaudoin.gwt.presenter.client;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This annotation can be used to indicate the view interface
 * of a {@link Presenter}.
 * 
 * @author Philippe Beaudoin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ViewInterface {}
