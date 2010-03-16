package com.puzzlebazar.client.util;

/**
 * The interface of an object that can react to changes
 * detected within another object, or a collection of other
 * objects.
 * 
 * @see ChangeMonitorUnit
 * @see ChangeMonitorImpl
 * 
 * @author Philippe Beaudoin
 */
public interface MonitorHandler {
  
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