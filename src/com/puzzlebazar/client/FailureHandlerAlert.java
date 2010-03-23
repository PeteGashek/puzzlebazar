package com.puzzlebazar.client;

import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.proxy.ProxyFailureHandler;
import com.puzzlebazar.client.resources.Translations;

public class FailureHandlerAlert implements ProxyFailureHandler {

  private final Translations translations;

  @Inject
  public FailureHandlerAlert( Translations translations ) {
    this.translations = translations;
  }
  
  @Override
  public void onFailedGetPresenter(Throwable caught) {
    Window.alert( translations.codeLoadFailure() );
  }

}
