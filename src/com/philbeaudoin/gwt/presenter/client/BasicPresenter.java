package com.philbeaudoin.gwt.presenter.client;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.ui.Widget;
import com.philbeaudoin.gwt.presenter.client.proxy.PlaceRequest;
import com.philbeaudoin.gwt.presenter.client.proxy.PresenterProxy;
import com.philbeaudoin.gwt.presenter.client.proxy.SetContentEvent;
import com.philbeaudoin.gwt.presenter.client.proxy.SetContentHandler;

public abstract class BasicPresenter<D extends PresenterDisplay, P extends PresenterProxy> 
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
   * The light-weight {@PresenterProxy} around this presenter.
   */
  protected final P proxy;

  /**
   * The type of the event this presenter will fire when it is
   * revealed and needs to be set as content.
   * Can be null if the presenter is top-level of if this
   * presenter is inserted at bind-time and never modified 
   * or revealed directly.
   */
  protected Type<SetContentHandler> setContentEventType;
  
  /**
   * Creates a basic {@link Presenter}.
   * @param eventBus The event bus.
   * @param display  The display attached to this presenter.
   * @param proxy The presenter proxy.
   * @param setContentEventType The type of the event to fire when the presenter should be set as content
   */
  public BasicPresenter( final EventBus eventBus, final D display, final P proxy, 
      final Type<SetContentHandler> setContentEventType ) {
    this.display = display;
    this.eventBus = eventBus;
    this.proxy = proxy;
    this.setContentEventType = setContentEventType;
  }
  
  @Override
  public final D getDisplay() {
    return display;
  }

  @Override
  public final P getProxy() {
    return proxy;
  }

  @Override
  public void revealDisplay() {
    if( setContentEventType != null ) {
      eventBus.fireEvent( new SetContentEvent( setContentEventType, this) );
    }
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
