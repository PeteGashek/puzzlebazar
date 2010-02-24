package com.philbeaudoin.gwt.presenter.client;

import java.util.List;

import com.google.gwt.event.shared.HandlerRegistration;

public abstract class HandlerContainer {

  private List<HandlerRegistration> handlerRegistrations = new java.util.ArrayList<HandlerRegistration>();
  private boolean bound = false;

  public HandlerContainer() {
    super();
  }


  /**
   * Called when the object is initialised. 
   * Can simply be the final call of the constructor of leaf classes,
   * or can be called by another management mechanism.
   * Multiple call to bind will not fail, the class will be bound once.
   * Any event handlers and other setup should be done here rather
   * than in the constructor.
   */
  public final void bind() {
    if ( !bound ) {
      onBind();
      bound = true;
    }
  }

  /**
   * Any {@link HandlerRegistration}s added will be removed when
   * {@link #unbind()} is called. This provides a handy way to track event
   * handler registrations when binding and unbinding.
   *
   * @param handlerRegistration The registration.
   */
  protected void registerHandler(HandlerRegistration handlerRegistration) {
    handlerRegistrations.add( handlerRegistration );
  }

  /**
   * Called after the presenter and display have been finished with for the
   * moment.
   */
  public final void unbind() {
    if ( bound ) {
      bound = false;

      for ( HandlerRegistration reg : handlerRegistrations ) {
        reg.removeHandler();
      }
      handlerRegistrations.clear();

      onUnbind();
    }  
  }

  /**
   * This method is called when binding the presenter. Any additional bindings
   * should be done here.
   */
  protected abstract void onBind();

  /**
   * This method is called when unbinding the presenter. Any handler
   * registrations recorded with {@link #registerHandler(HandlerRegistration)}
   * will have already been removed at this point.
   */
  protected abstract void onUnbind();

  /**
   * Returns true if the presenter is currently in a 'bound' state. That is,
   * the {@link #bind()} method has completed and {@link #unbind()} has not
   * been called.
   *
   * @return <code>true</code> if bound.
   */
  protected final boolean isBound() {
    return bound;
  }

}