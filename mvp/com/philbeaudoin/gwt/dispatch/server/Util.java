package com.philbeaudoin.gwt.dispatch.server;

import java.util.logging.Logger;

public class Util {

  /**
   * Logs a severe exception, together with the class that
   * generated it.
   * 
   * @param log       The {@link Logger}.
   * @param location  The object that generated the exception.
   * @param exception The generated exception.
   */
  public static void logException(
      Logger log, 
      Object location,
      Exception exception) {

      log.severe( "Error in '" + location.getClass().getName() + "'. : " +
          exception.toString() );
    }

}
