package com.puzzlebazar.server.guice;

import com.google.inject.servlet.ServletModule;
import com.philbeaudoin.gwt.dispatch.server.guice.GuiceStandardDispatchServlet;

public class DispatchServletModule extends ServletModule {

  @Override
  public void configureServlets() {
    serve("/puzzlebazar/dispatch").with(GuiceStandardDispatchServlet.class);
  }

}
