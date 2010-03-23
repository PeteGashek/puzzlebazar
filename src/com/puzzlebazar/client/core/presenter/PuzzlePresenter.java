package com.puzzlebazar.client.core.presenter;

import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.EventBus;
import com.philbeaudoin.gwtp.mvp.client.PresenterImpl;
import com.philbeaudoin.gwtp.mvp.client.View;
import com.philbeaudoin.gwtp.mvp.client.proxy.Place;
import com.philbeaudoin.gwtp.mvp.client.proxy.Proxy;
import com.philbeaudoin.gwtp.mvp.client.proxy.RevealRootLayoutContentEvent;
import com.philbeaudoin.gwtp.mvp.rebind.NameToken;
import com.philbeaudoin.gwtp.mvp.rebind.ProxyCodeSplit;
import com.puzzlebazar.client.NameTokens;

/**
 * The top-level presenter that contains puzzles pages.
 * 
 * @author Philippe Beaudoin
 */
public class PuzzlePresenter extends PresenterImpl<PuzzlePresenter.MyView, PuzzlePresenter.MyProxy> {

  public static final Object TYPE_RevealTopBarContent = new Object();

  public interface MyView extends View {
  }
  
  @ProxyCodeSplit
  @NameToken( NameTokens.puzzlePage )
  public interface MyProxy extends Proxy<PuzzlePresenter>, Place {}  

  private final TopBarPresenter topBarPresenter;
  
  @Inject
  public PuzzlePresenter(
      final EventBus eventBus, 
      final MyView view, 
      final MyProxy proxy,
      final TopBarPresenter topBarPresenter ) {
    super(eventBus, view, proxy);

    this.topBarPresenter = topBarPresenter;
  }  

  @Override
  protected void revealInParent() {
    RevealRootLayoutContentEvent.fire( eventBus, this );
  }

  @Override
  protected void onReveal() {
    super.onReveal();
    setContent( TYPE_RevealTopBarContent, topBarPresenter );
  }

}
