package com.philbeaudoin.gwt.presenter.client.proxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.philbeaudoin.gwt.presenter.client.Presenter;


/**
 * This annotation can be used to indicate the proxy interface
 * of a {@link Presenter}.
 * 
 * @author Philippe Beaudoin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ProxyInterface {}
