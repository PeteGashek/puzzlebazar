package com.philbeaudoin.gwt.presenter.client.proxy;

import com.google.gwt.event.shared.EventHandler;
import com.philbeaudoin.gwt.presenter.client.Presenter;

public abstract class SetContentHandler<P extends Presenter> implements EventHandler {

  private final PresenterProxyImpl<P> proxy;

  public SetContentHandler( final PresenterProxyImpl<P> proxy ) {
    this.proxy = proxy;
  }
  
  /**
   * This is the dispatched method. Override {@link onSetContent} instead.
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
