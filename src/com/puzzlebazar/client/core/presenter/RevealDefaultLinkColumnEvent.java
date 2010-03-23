package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.shared.GwtEvent;
import com.philbeaudoin.gwtp.mvp.client.EventBus;


public class RevealDefaultLinkColumnEvent extends GwtEvent<RevealDefaultLinkColumnHandler> {

  private static final Type<RevealDefaultLinkColumnHandler> TYPE = new Type<RevealDefaultLinkColumnHandler>();
  
  public static Type<RevealDefaultLinkColumnHandler> getType() {
      return TYPE;
  }

  public static void fire( EventBus eventBus ) {
      eventBus.fireEvent( new RevealDefaultLinkColumnEvent() );
  }
  
  public RevealDefaultLinkColumnEvent() {
  }

  @Override
  protected void dispatch( RevealDefaultLinkColumnHandler handler ) {
    handler.onRevealDefaultLinkColumn( this );
  }

  @Override
  public Type<RevealDefaultLinkColumnHandler> getAssociatedType() {
    return getType();
  }

}
