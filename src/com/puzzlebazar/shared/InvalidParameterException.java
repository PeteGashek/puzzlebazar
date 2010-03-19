package com.puzzlebazar.shared;

/**
 * This exception is thrown when an unexpected parameter is received
 * by a function. 
 * 
 * @author Philippe Beaudoin
 */
public class InvalidParameterException extends Exception {

  private static final long serialVersionUID = 2073080063127219154L;

  /**
   * Constructs an InvalidParameterException with no detail message.
   * A detail message is a String that describes this particular
   * exception.
   */
  public InvalidParameterException() {
    super();
  }

  /**
   * Constructs an InvalidParameterException with the specified
   * detail message.  A detail message is a String that describes
   * this particular exception.
   *
   * @param msg the detail message.  
   */
  public InvalidParameterException(String msg) {
    super(msg);
  }
}
