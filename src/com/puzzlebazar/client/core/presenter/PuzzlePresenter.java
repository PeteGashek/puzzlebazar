package com.puzzlebazar.client.core.presenter;

import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.PresenterImpl;
import com.philbeaudoin.platform.mvp.client.View;
import com.philbeaudoin.platform.mvp.client.proxy.Proxy;
import com.philbeaudoin.platform.mvp.client.proxy.RevealRootLayoutContentEvent;

/**
 * The top-level presenter that contains puzzles pages.
 * 
 * @author Philippe Beaudoin
 */
public class PuzzlePresenter extends PresenterImpl<PuzzlePresenter.MyView, PuzzlePresenter.MyProxy> {

  public static final Object TYPE_RevealTopBarContent = new Object();

  public interface MyView extends View {
  }
  
  public interface MyProxy extends Proxy<PuzzlePresenter> {}  

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
