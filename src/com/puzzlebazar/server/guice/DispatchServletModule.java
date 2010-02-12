package com.puzzlebazar.server.guice;

import com.google.inject.servlet.ServletModule;

import net.customware.gwt.dispatch.server.service.DispatchServiceServlet;

public class DispatchServletModule extends ServletModule {

  @Override
  public void configureServlets() {
    serve("/puzzlebazar/dispatch").with(DispatchServiceServlet.class);
  }

}
