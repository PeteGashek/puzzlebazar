package com.philbeaudoin.gwt.presenter.client;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import com.philbeaudoin.gwt.presenter.client.place.PlaceRequest;

import java.util.List;

public abstract class BasicPresenter<D extends Display> implements Presenter {

  /**
   * The display for the presenter.
   */
  protected final D display;

  /**
   * The {@link EventBus} for the application.
   */
  protected final EventBus eventBus;

  private List<HandlerRegistration> handlerRegistrations = new java.util.ArrayList<HandlerRegistration>();

  private boolean bound = false;


  /**
   * Creates a basic top-level {@link Presenter}, that is, a presenter without a parent.
   * 
   * @param display  The display attached to this presenter.
   * @param eventBus The event bus.
   */
  public BasicPresenter( D display, EventBus eventBus ) {
    this.display = display;
    this.eventBus = eventBus;
  }
  
  @Override
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
  protected void registerHandler( HandlerRegistration handlerRegistration ) {
    handlerRegistrations.add( handlerRegistration );
  }

  @Override
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

  @Override
  public final boolean isBound() {
    return bound;
  }

  @Override
  public final D getDisplay() {
    return display;
  }

  /**
   * Fires a {@link PresenterChangedEvent} to the {@link EventBus}.
   * Call this method any time the presenter's state has been modified.
   */
  protected void firePresenterChangedEvent() {
    PresenterChangedEvent.fire( eventBus, this );
  }

  /**
   * Fires a {@link PresenterRevealedEvent} to the {@link EventBus}.
   * Call this method any time the presenter's state has been modified.
   */
  protected void firePresenterRevealedEvent() {
    PresenterRevealedEvent.fire( eventBus, this );
  }  

  @Override
  public void prepareFromRequest(PlaceRequest request) {
    // By default, no parameter to extract from request.
  }

  @Override
  public PlaceRequest prepareRequest(PlaceRequest request) {
    // By default, no parameter to add to request
    return request;
  }    

  @Override
  public Widget getWidget() {
    return display.asWidget();
  }
  
}
