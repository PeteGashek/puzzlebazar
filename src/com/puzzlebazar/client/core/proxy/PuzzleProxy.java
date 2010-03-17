/**
 * 
 */
package com.puzzlebazar.client.core.proxy;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.proxy.PlaceImpl;
import com.philbeaudoin.platform.mvp.client.proxy.PlaceManager;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyImpl;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyPlace;
import com.puzzlebazar.client.CodeSplitProvider;
import com.puzzlebazar.client.NameTokens;
import com.puzzlebazar.client.core.presenter.PuzzlePresenter;

/**
 * The {@link Proxy} for {@link PuzzlePresenter}.
 * 
 * @author Philippe Beaudoin
 */
public class PuzzleProxy
extends ProxyPlace<PuzzlePresenter> 
implements PuzzlePresenter.MyProxy {

  public static class WrappedProxy extends ProxyImpl<PuzzlePresenter> {
    @Inject
    public WrappedProxy(EventBus eventBus,
        final AsyncProvider<PuzzlePresenter> presenter) {
      super(
        eventBus,
        new CodeSplitProvider<PuzzlePresenter>(presenter));
    }
  }

  @Inject
  public PuzzleProxy(
      final EventBus eventBus, 
      final PlaceManager placeManager, 
      final WrappedProxy wrappedProxy ) {
    super(
        eventBus, 
        placeManager, 
        wrappedProxy,
        new PlaceImpl( NameTokens.getPuzzlePage() ) );
  }
  
}