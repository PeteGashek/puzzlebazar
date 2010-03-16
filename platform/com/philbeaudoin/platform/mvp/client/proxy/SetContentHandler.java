package com.philbeaudoin.platform.mvp.client.proxy;

import com.google.gwt.event.shared.EventHandler;
import com.philbeaudoin.platform.mvp.client.Presenter;

public abstract class SetContentHandler<P extends Presenter> implements EventHandler {

  private final ProxyImpl<P> proxy;

  public SetContentHandler( final ProxyImpl<P> proxy ) {
    this.proxy = proxy;
  }
  
  /**
   * This is the dispatched method. Override {@link #onSetContent(P, SetContentEvent)} instead.
   * 
   * @param setContentEvent The event containing the presenter that wants to bet set as content.
   */
  public final void executeSetContent(final SetContentEvent setContentEvent) {
    proxy.getPresenter( new Callback<P>() {
      @Override public void execute(P presenter) {
        onSetContent( presenter, setContentEvent );
      } 
    } );
  }
  
  /**
   * Called whenever a presenter wants to sets itself as content on some
   * parent presenter's slot.
   * 
   * @param setContentEvent The event containing the presenter that wants to bet set as content.
   */
  public abstract void onSetContent(P presenter, SetContentEvent setContentEvent);

}