package com.puzzlebazar.client.util;

import java.util.ArrayList;
import java.util.List;

import com.philbeaudoin.platform.mvp.client.HandlerContainerImpl;

/**
 * <b>Important:</b> This class participates in dependency injection.
 * Make sure you inject it, never instantiate it with <code>new</code>.
 * <p />
 * 
 * @author Philippe Beaudoin
 */
public class ChangeMonitorImpl 
extends HandlerContainerImpl
implements ChangeMonitor {

  /**
   * This class is used on monitored object.
   */
  private class MonitoredObjectHandler implements MonitorHandler {
    @Override
    public void changeDetected() {
      newChange();
    }

    @Override
    public void changeReverted() {
      newChangeReverted();
    }
  }

  private final List<ChangeMonitorUnit> changeMonitors = new ArrayList<ChangeMonitorUnit>();
  private final MonitoredObjectHandler subHandler = new MonitoredObjectHandler();
  private boolean changed = false;
  private MonitorHandler handler = null;
  
  /**
   * Creates a new {@link ChangeMonitor} object.
   */
  public ChangeMonitorImpl() {
    super();
  }
  
  @Override
  protected void onUnbind() {
    super.onUnbind();
    for( ChangeMonitorUnit changeMonitor : changeMonitors  ) {
      changeMonitor.release();      
    }
    changeMonitors.clear();
    changed = false;
  }    

  @Override
  public void setHandler( MonitorHandler handler ) {
    this.handler = handler;
  }
  
  @Override
  public void monitorWidget( Object widget ) {
    assert isBound() : "Change monitor must be bound before widgets can be monitored.";
    changeMonitors.add( new ChangeMonitorUnit(widget, subHandler) );
  }

  @Override
  public void changeDetected() {
    if( handler != null )
      handler.changeDetected();
  }

  @Override
  public void changeReverted() {
    if( handler != null )
      handler.changeReverted();
  }
  
  /**
   * A monitored object has recently changed, see if it affects our status.
   */
  private void newChange() {
    if( changed ) return;
    changed = true;
    changeDetected();      
  }

  /**
   * A monitored object has recently reverted its changes, see if it 
   * affects our status.
   */
  private void newChangeReverted() {
    for( ChangeMonitorUnit changeMonitor : changeMonitors  ) {
      if( changeMonitor.hasChanged() ) return;
    }
    changed = false;
    changeReverted();      
  }

  @Override
  public void reset() {
    for( ChangeMonitorUnit changeMonitor : changeMonitors  ) {
      changeMonitor.reset();
    }
    changed = false;
  }

}
