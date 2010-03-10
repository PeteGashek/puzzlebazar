package com.puzzlebazar.client.util;

import com.google.gwt.event.dom.client.HasAllKeyHandlers;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasText;

/**
 * A class that monitor changes to a specific object that implements
 * both {@link HasValueChangeHandlers<String>} and {@link HasText}.
 * This object does not participate in dependancy injection, but you
 * should call {@link #release()} whenever you're done using it.
 * This class is usually best used through {@link ChangeMonitorImpl}.
 * 
 * @author Philippe Beaudoin
 */
public class ChangeMonitorUnit 
implements ValueChangeHandler<String>, KeyDownHandler {
  
  private final HasText widget;
  private final ChangeHandler handler;
  private final HandlerRegistration handlerRegistration;
  private final Timer timer;
  private boolean timerRunning = false;
  private String  originalValue;
  private boolean changed = false;
  
  /**
   * Creates an object to monitor change within an object.
   * 
   * @param widget The object to monitor. Should implement both
   *               {@link HasValueChangeHandlers<String>} otherwise
   *               change will not be monitored (silently).
   * @param handler The {@link ChangeHandler} to notify when change are detected or reverted.
   */
  @SuppressWarnings("unchecked")
  public ChangeMonitorUnit( 
      final HasText widget,
      final ChangeHandler handler ) {
    this.widget = widget;
    this.handler = handler;
    this.timer = new Timer(){
      @Override
      public void run() {
        checkChanges(widget.getText());
        timerRunning = false;
      }      
    }; 

    revert();
    
    if ( widget instanceof HasAllKeyHandlers )
      handlerRegistration = ((HasAllKeyHandlers)widget).addKeyDownHandler( this );
    else if ( widget instanceof HasValueChangeHandlers<?> )
      handlerRegistration = ((HasValueChangeHandlers<String>)widget).addValueChangeHandler( this );    
    else
      handlerRegistration = null;
  }

  /**
   * Call this method when you're done using the monitor.
   */
  public void release() {
    if( handlerRegistration != null )
      handlerRegistration.removeHandler();
    timer.cancel();
  }
  
  /**
   * Checks if the monitored object has changed.
   * 
   * @return <code>true</code> if the object has changed, <code>false</code> otherwise.
   */
  public boolean hasChanged() {
    return changed;
  }
  
  /**
   * Revert to the original value that was contained in the widget.
   * Does not notify the handler. 
   */
  public void revert() {
    originalValue = widget.getText();
    changed = false;
  }

  @Override
  public void onValueChange(ValueChangeEvent<String> event) {
    scheduleCheckChanges();
  }

  @Override
  public void onKeyDown(KeyDownEvent event) {
    scheduleCheckChanges();
  }
  
  /**
   * Schedule a check changes a short way into
   * the future, to reduce UI stress.
   */
  private void scheduleCheckChanges() {
    if( !timerRunning ) {
      timer.schedule( 500 );
      timerRunning = true;
    }
  }

  private void checkChanges(String value) {
    boolean newChanged = !value.equals( originalValue );
    if( changed == newChanged )
      return;
    changed = newChanged;
    if( changed )
      handler.changeDetected();
    else
      handler.changeReverted();
  }

  
}
