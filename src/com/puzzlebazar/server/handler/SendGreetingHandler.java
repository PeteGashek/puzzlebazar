package com.puzzlebazar.server.handler;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import com.puzzlebazar.shared.rpc.SendGreeting;
import com.puzzlebazar.shared.rpc.SendGreetingResult;

public class SendGreetingHandler implements ActionHandler<SendGreeting, SendGreetingResult> {
  private final Provider<ServletContext> servletContext;
  private final Provider<HttpServletRequest> servletRequest;

  @Inject
  public SendGreetingHandler(
      final Provider<ServletContext> servletContext,       
      final Provider<HttpServletRequest> servletRequest) {
    this.servletContext = servletContext;
    this.servletRequest = servletRequest;
  }

  @Override
  public SendGreetingResult execute(final SendGreeting action,
      final ExecutionContext context) throws ActionException {
    final String name = action.getName();

    try {
      String serverInfo = servletContext.get().getServerInfo();

      String userAgent = servletRequest.get().getHeader("User-Agent");

      final String message = "Hello, " + name + "! I am running " + serverInfo + ". It looks like you are using: " + userAgent;

      //final String message = "Hello " + action.getName(); 

      return new SendGreetingResult(name, message);
    }
    catch (Exception cause) {
      throw new ActionException(cause);
    }
  }

  @Override
  public void rollback(final SendGreeting action,
      final SendGreetingResult result,
      final ExecutionContext context) throws ActionException {
    // Nothing to do here
  }

  @Override
  public Class<SendGreeting> getActionType() {
    return SendGreeting.class;
  }
}
