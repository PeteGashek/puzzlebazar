package com.philbeaudoin.gwt.dispatch.shared.secure;

import com.philbeaudoin.gwt.dispatch.shared.ServiceException;

public class InvalidSessionException extends ServiceException {

  private static final long serialVersionUID = -3591782650879045864L;

  public InvalidSessionException() {
    super();
  }

  public InvalidSessionException( String message ) {
    super( message );
  }
}
