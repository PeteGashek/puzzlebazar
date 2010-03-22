package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.View;
import com.philbeaudoin.platform.mvp.client.PresenterImpl;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.proxy.Proxy;
import com.philbeaudoin.platform.mvp.client.proxy.RevealContentHandler;
import com.philbeaudoin.platform.mvp.client.proxy.RevealRootContentEvent;
import com.philbeaudoin.platform.mvp.rebind.ContentSlot;
import com.philbeaudoin.platform.mvp.rebind.UseCodeSplit;

/**
 * The top-level presenter that contains typical pages of the application.
 * An exception are puzzles, that are displayed in their own top-level presenter.
 * 
 * @author Philippe Beaudoin
 */
public class PagePresenter extends PresenterImpl<PagePresenter.MyView, PagePresenter.MyProxy> {

  @ContentSlot
  public static final Type<RevealContentHandler<?>> TYPE_RevealMainContent = new Type<RevealContentHandler<?>>();  

  public static final Object TYPE_RevealTopBarContent = new Object();
    
  public interface MyView extends View {
  }
  
  @UseCodeSplit
  public interface MyProxy extends Proxy<PagePresenter> {
  }  

  private final TopBarPresenter topBarPresenter;
  
  @Inject
  public PagePresenter(
      final EventBus eventBus, 
      final MyView view, 
      final MyProxy proxy,
      final TopBarPresenter topBarPresenter ) {
    super(eventBus, view, proxy);

    this.topBarPresenter = topBarPresenter;
  }  

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire( eventBus, this );
  }

  @Override
  protected void onReveal() {
    super.onReveal();
    setContent( TYPE_RevealTopBarContent, topBarPresenter );
  }

}
