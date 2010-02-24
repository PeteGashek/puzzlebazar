package com.philbeaudoin.gwt.presenter.client;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Abstract event type. Presenters should implement their own version of PresenterChangedEvent.
 * This event is fired when the presenter has been 'revealed' on the screen. This
 * is particularly useful for situations where a presenter contains other
 * presenters and wants needs to reveal itself when a child presenter is
 * revealed.
 *
 * @author David Peterson
 */
public abstract class PresenterRevealedEvent extends GwtEvent<PresenterRevealedHandler> {

  private final Presenter presenter;

  /**
   * Constructs a new revelation event
   *
   * @param presenter The presenter.
   */
  public PresenterRevealedEvent( Presenter presenter ) {
    this.presenter = presenter;
  }

  public Presenter getPresenter() {
    return presenter;
  }

  @Override
  protected void dispatch( PresenterRevealedHandler handler ) {
    handler.onPresenterRevealed( this );
  }

}
