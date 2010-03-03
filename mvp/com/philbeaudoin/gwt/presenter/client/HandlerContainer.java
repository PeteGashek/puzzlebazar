package com.philbeaudoin.gwt.presenter.client;

import java.util.List;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;

public abstract class HandlerContainer {

  private final List<HandlerRegistration> handlerRegistrations = new java.util.ArrayList<HandlerRegistration>();
  private final boolean autoBind;
  
  private boolean bound = false;

  /**
   * Creates a handler container class with automatic binding.
   * 
   * @see #HandlerContainer( boolean autoBind )
   */
  public HandlerContainer() {
    this(true);
  }

  /**
   * Creates a handler container class with or without automatic binding.
   * If automatic binding is requested, the {@link #bind()} method will
   * be called automatically after the class is instantiated through
   * Guice/GIN dependency injection mechanism. Otherwise, the user
   * is responsible for calling {@link #bind()}.
   * 
   * @param autoBind True to request automatic binding, false otherwise.
   */
  public HandlerContainer( boolean autoBind ) {
    super();
    this.autoBind = autoBind;
  }

  /**
   * Never call this directly. This method is used only by Guice/GIN
   * dependency injection mechanism. 
   */
  @Inject
  final void automaticBind() {
    if( autoBind )
      bind();
  }
  
  /**
   * Call this method after the object is constructed in order to
   * bind all its handlers.
   * <p />
   * When automatic binding is used (see {@link #HandlerContainer( 
   * boolean autoBind )}), this will be called immediately after the 
   * object is constructed through Guice/GIN dependency injection 
   * mechanism. This is done so that any singleton are correctly 
   * initialised. For this reason you should never call {@link 
   * #bind()} directly from the constructor.
   * <p />
   * If you are not using automatic binding, or if you later call 
   * {@link #unbind()} on this object, you will have to call {@link 
   * #bind()} again manually to make sure its handlers are bound. 
   * <p />
   * Multiple call to bind will not fail, the class will be bound once.
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
   * Called after the object has been finished with for the moment.
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
   * <b>Important :</b> Make sure you call your parent class onBind().
   * <p />
   * This method is called when binding the object.
   * Any event handlers should be initialised here rather
   * than in the constructor. Other costly initialisation should
   * be done here too, in order to speed-up construction.
   * <p />
   * Handlers registered by calling {@link #registerHandler
   * (HandlerRegistration)} will be removed automatically. Any other 
   * initialisation that takes place here (or as a side-effect of
   * what is done here) should be taken down in {@link #unbind()}.
   * <p />
   * This method will never be invoked more then once, or if it is,
   * the second time will necessarily be preceded by an invocation
   * of {@link #unbind()}.
   */
  public void onBind() {}

  /**
   * <b>Important :</b> Make sure you call your parent class onUnbind().
   * <p />
   * This method is called when unbinding the object. Any handler
   * registrations recorded with {@link #registerHandler
   * (HandlerRegistration)} will have already been removed at this 
   * point. You should take down any other initialisation that 
   * took place in {@link #unbind()}
   * <p />
   * This method will never be invoked more then once, or if it is,
   * the second time will necessarily be preceded by an invocation
   * of {@link #bind()}.
   */
  public void onUnbind() {}

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