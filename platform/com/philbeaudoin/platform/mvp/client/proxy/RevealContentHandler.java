package com.philbeaudoin.platform.mvp.client.proxy;

import com.google.gwt.event.shared.EventHandler;
import com.philbeaudoin.platform.mvp.client.Presenter;

/**
 * This handler for {@link RevealContentHandler}. It should be implemented by any
 * {@link Proxy} class of a {@link Presenter} that accepts child presenters. Upon 
 * handling this event, the proxy should <b>first</b> set the content appropriately 
 * in the presenter, and then reveal the presenter.
 * 
 * @author Philippe Beaudoin
 */
public abstract class RevealContentHandler<P extends Presenter> implements EventHandler {

  private final ProxyImpl<P> proxy;

  public RevealContentHandler( final ProxyImpl<P> proxy ) {
    this.proxy = proxy;
  }

  /**
   * This is the dispatched method. Override {@link #onRevealContent(P, RevealContentEvent)} instead.
   * 
   * @param setContentEvent The event containing the presenter that wants to bet set as content.
   */
  public final void executeRevealContent(final RevealContentEvent setContentEvent) {
    proxy.getPresenter( new Callback<P>() {
      @Override public void execute(P presenter) {
        onRevealContent( presenter, setContentEvent );
      } 
    } );
  }

  /**
   * Called whenever a presenter wants to sets itself as content on some
   * parent presenter's slot. Upon handling this event, the proxy should <b>first</b> set the content appropriately 
   * in the presenter, and then reveal the presenter.
   * 
   * @param setContentEvent The event containing the presenter that wants to bet set as content.
   */
  public abstract void onRevealContent(P presenter, RevealContentEvent setContentEvent);

}
