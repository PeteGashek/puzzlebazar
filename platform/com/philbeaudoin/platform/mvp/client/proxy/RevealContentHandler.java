package com.philbeaudoin.platform.mvp.client.proxy;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.philbeaudoin.platform.mvp.client.Presenter;

/**
 * This is the handler class for {@link RevealContentEvent}. It should be used by any
 * {@link Proxy} class of a {@link Presenter} that accepts child presenters. When 
 * this handler is triggered, the proxy should <b>first</b> set the content appropriately 
 * in the presenter, and then reveal the presenter.
 * 
 * @author Philippe Beaudoin
 */
public class RevealContentHandler<P extends Presenter> implements EventHandler {

  private final ProxyFailureHandler failureHandler;
  private final ProxyImpl<P> proxy;

  public RevealContentHandler( final ProxyFailureHandler failureHandler, final ProxyImpl<P> proxy ) {
    this.failureHandler = failureHandler;
    this.proxy = proxy;
  }

  /**
   * This is the dispatched method. Reveals 
   * 
   * @param revealContentEvent The event containing the presenter that wants to bet set as content.
   */
  public final void onRevealContent(final RevealContentEvent revealContentEvent) {
    proxy.getPresenter( new AsyncCallback<P>() {
      @Override
      public void onFailure(Throwable caught) {
        failureHandler.onFailedGetPresenter( caught );
      }
      @Override
      public void onSuccess(P presenter) {
        presenter.setContent( revealContentEvent.getAssociatedType(), revealContentEvent.getContent() );
        presenter.reveal();       
      } 
    } );
  }

}
