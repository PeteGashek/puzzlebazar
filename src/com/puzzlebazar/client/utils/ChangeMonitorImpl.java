package com.puzzlebazar.client.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.HasText;
import com.philbeaudoin.gwt.presenter.client.HandlerContainerImpl;

/**
 * <b>Important:</b> This class participates in dependency injection.
 * Make sure you inject it, never instantiate it with <code>new</code>.
 * <p />
 * Inherit from this class and implement the methods
 * {@link #changeDetected()} and {@link #modificationReverted}.
 * 
 * @author Philippe Beaudoin
 */
public abstract class ChangeMonitorImpl 
extends HandlerContainerImpl
implements ChangeMonitor {

  /**
   * This class is used on monitored object.
   */
  private class MonitoredObjectHandler implements ChangeHandler {
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
  private final MonitoredObjectHandler handler = new MonitoredObjectHandler();
  private boolean changed = false;

  public ChangeMonitorImpl() {
    super();
  }


  /* (non-Javadoc)
   * @see com.puzzlebazar.client.utils.ChangeMonitor#monitorWidget(com.google.gwt.user.client.ui.HasText)
   */  
  public void monitorWidget( HasText widget ) {
    changeMonitors.add( new ChangeMonitorUnit(widget, handler) );
  }

  @Override
  protected void onUnbind() {
    super.onUnbind();
    for( ChangeMonitorUnit changeMonitor : changeMonitors  ) {
      changeMonitor.release();      
    }
    changeMonitors.clear();
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
}
