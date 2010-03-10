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
  
  private class MyTimer extends Timer {

    private String previousText = null;
    
    public void start() {
      previousText = null;
      this.schedule(50);
    }
    
    @Override
    public void run() {
      final String text = widget.getText();
      if( previousText == null || !previousText.equals(text) ) {
        previousText = text;
        checkChanges(text);
        this.schedule(1500);
      }
      else {
        assert handlerRegistration == null;
        handlerRegistration = ((HasAllKeyHandlers)widget).addKeyDownHandler( ChangeMonitorUnit.this );        
      }
    }      
  }
  
  private final HasText widget;
  private final ChangeHandler handler;
  private final MyTimer timer = new MyTimer();
  private HandlerRegistration handlerRegistration;
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
    checkChanges( event.getValue() );
  }

  @Override
  public void onKeyDown(KeyDownEvent event) {
    scheduleCheckChanges();
  }
  
  /**
   * Schedule a check changes after a key down a short way into
   * the future, to reduce UI stress. Only call from
   * onKeyDown, because it assumes the monitored object implements
   * HasAllKeyHandlers.
   */
  private void scheduleCheckChanges() {
    handlerRegistration.removeHandler();
    handlerRegistration = null;
    timer.start();
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
