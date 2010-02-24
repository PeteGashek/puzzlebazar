package com.philbeaudoin.gwt.presenter.client;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Abstract event type. Presenters should implement their own version of PresenterChangedEvent.
 * This event is fired whenever the presenter is changed in a way that requires a change to
 * the parameters on the URL.
 *
 * @author David Peterson
 */
public abstract class PresenterChangedEvent extends GwtEvent<PresenterChangedHandler> {

  private final Presenter presenter;

  public PresenterChangedEvent( Presenter presenter ) {
    this.presenter = presenter;
  }

  public Presenter getPresenter() {
    return presenter;
  }

  @Override
  protected void dispatch( PresenterChangedHandler handler ) {
    handler.onPresenterChanged( this );
  }
}
