package com.puzzlebazar.client.utils;

import com.google.gwt.user.client.ui.HasText;

/**
 * Classes of this type contains a collection of {@link ChangeMonitorUnit}
 * objects. It fires {@link #changeDetected()} once as soon as one 
 * object of the collection changes value. It fires 
 * {@link #changeReverted()} as soon as all the objects in the collection
 * recover their original value.
 * 
 * @author Philippe Beaudoin
 */
public interface ChangeMonitor extends ChangeHandler {

  /**
   * Starts monitoring changes on a new widget. This object will be
   * monitored until {@link #unbind()} is called.
   * 
   * @param widget The object to monitor. Should implement both
   *               {@link HasValueChangeHandlers<String>} otherwise
   *               change will not be monitored (silently).
   */
  public abstract void monitorWidget(HasText widget);

}