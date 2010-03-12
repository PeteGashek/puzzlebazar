package com.puzzlebazar.client.util;

import com.google.gwt.user.client.ui.HasText;
import com.philbeaudoin.platform.mvp.client.HandlerContainer;

/**
 * Classes of this type contains a collection of {@link ChangeMonitorUnit}
 * objects. It fires {@link #changeDetected()} once as soon as one 
 * object of the collection changes value. It fires 
 * {@link #changeReverted()} as soon as all the objects in the collection
 * recover their original value.
 * 
 * @author Philippe Beaudoin
 */
public interface ChangeMonitor extends ChangeHandler, HandlerContainer {

  /**
   * Indicates a handler that will be called whenever a
   * change is detected.
   * 
   * @param handler The {@link ChangeHandler} to call.
   */
  void setHandler(ChangeHandler handler);

  /**
   * Starts monitoring changes on a new widget. This object will be
   * monitored until {@link #unbind()} is called.
   * 
   * @param widget The object to monitor. Should implement both
   *               {@link HasValueChangeHandlers<String>} otherwise
   *               change will not be monitored (silently).
   */
  public abstract void monitorWidget(HasText widget);

  /**
   * Revert all the changes so that objects go back to their original
   * values. Does not notify the associated {@link ChangeHandler}.
   */
  void revert();

}