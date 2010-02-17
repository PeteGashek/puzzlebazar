package com.philbeaudoin.gwt.presenter.client;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Presenters can send this event to the {@link EventBus} to notify other
 * interested parties when the presenter has been 'revealed' on the screen. This
 * is particularly useful for situations where a presenter contains other
 * presenters and wants needs to reveal itself when a child presenter is
 * revealed.
 *
 * @author David Peterson
 */
public class PresenterRevealedEvent extends GwtEvent<PresenterRevealedHandler> {

  private static final GwtEvent.Type<PresenterRevealedHandler> TYPE = new GwtEvent.Type<PresenterRevealedHandler>();

  public static GwtEvent.Type<PresenterRevealedHandler> getType() {
    return TYPE;
  }

  /**
   * Fires a {@link PresenterRevealedEvent} into the {@link EventBus}, specifying that it
   * was the originator.
   *
   * @param eventBus  The event bus.
   * @param presenter The presenter.
   */
  public static void fire( EventBus eventBus, Presenter presenter ) {
    eventBus.fireEvent( new PresenterRevealedEvent( presenter ) );
  }

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

  @Override
  public GwtEvent.Type<PresenterRevealedHandler> getAssociatedType() {
    return getType();
  }

}
