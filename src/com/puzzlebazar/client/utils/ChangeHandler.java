package com.puzzlebazar.client.utils;

/**
 * The interface of an object that can react to changes
 * dected within another object, or a collection of other
 * objects.
 * 
 * @see ChangeMonitorUnit
 * @see ChangeMonitorImpl
 * 
 * @author Philippe Beaudoin
 */
public interface ChangeHandler {
  
  /**
   * Called whenever the associated object has changed.
   */
  public void changeDetected();
  
  /**
   * Called whenever all changes in the associated object 
   * have been reverted.
   */
  public void changeReverted();
}