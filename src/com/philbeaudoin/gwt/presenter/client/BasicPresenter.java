package com.philbeaudoin.gwt.presenter.client;

import com.google.gwt.user.client.ui.Widget;
import com.philbeaudoin.gwt.presenter.client.place.PlaceRequest;

public abstract class BasicPresenter<D extends PresenterDisplay, W extends PresenterWrapper<?>> 
extends HandlerContainer implements Presenter {

  /**
   * The display for the presenter.
   */
  protected final D display;

  /**
   * The {@link EventBus} for the application.
   */
  protected final EventBus eventBus;
  
  /**
   * The light-weight {@PresenterWrapper} around this presenter.
   */
  protected final W wrapper;

  /**
   * The slot into which this presenter should display itself. 
   * Can be null if the presenter is top-level of if it is
   * statically inserted in a slot and never modified or revealed.
   */
  protected Slot<? extends Presenter> parentSlot;
  
  /**
   * Creates a basic {@link Presenter}.
   * 
   * @param display  The display attached to this presenter.
   * @param eventBus The event bus.
   * @param wrapper The presenter wrapper.
   */
  public BasicPresenter( final D display, final EventBus eventBus, final W wrapper, 
      final Slot<? extends Presenter> parentSlot ) {
    this.display = display;
    this.eventBus = eventBus;
    this.wrapper = wrapper;
    this.parentSlot = parentSlot;
  }
  
  @Override
  public final D getDisplay() {
    return display;
  }

  protected final W getWrapper() {
    return wrapper;
  }

  @Override
  public void revealDisplay() {
    if( parentSlot != null ) {
      parentSlot.setPresenter( this );
      parentSlot.getPresenter().revealDisplay();
    }
  }
  
  /**
   * Fires the {@link PresenterChangedEvent} specific to that presenter to the {@link EventBus}.
   * Call this method any time the presenter's state has been modified.
   */
  protected final void firePresenterChangedEvent() {
    wrapper.firePresenterChangedEvent();
  }

  /**
   * Fires the {@link PresenterRevealedEvent} specific to that presenter to the {@link EventBus}.
   * Call this method any time the presenter's state has been modified.
   */
  protected final void firePresenterRevealedEvent() {
    wrapper.firePresenterRevealedEvent();
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
