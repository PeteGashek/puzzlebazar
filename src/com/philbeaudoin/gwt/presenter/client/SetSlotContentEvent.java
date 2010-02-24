package com.philbeaudoin.gwt.presenter.client;

import com.google.gwt.event.shared.GwtEvent;

public abstract class SetSlotContentEvent extends GwtEvent<SetSlotContentHandler> {

  private final Presenter presenter;

  /**
   * Constructs a new revelation event
   *
   * @param presenter The presenter.
   */
  public SetSlotContentEvent( Presenter presenter ) {
    this.presenter = presenter;
  }

  public Presenter getPresenter() {
    return presenter;
  }

  @Override
  protected void dispatch( SetSlotContentHandler handler ) {
    handler.onSetSlotContent( this );
  }  
  
}
