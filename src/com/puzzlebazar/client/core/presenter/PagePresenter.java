package com.puzzlebazar.client.core.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.Presenter;
import com.philbeaudoin.platform.mvp.client.View;
import com.philbeaudoin.platform.mvp.client.PresenterImpl;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.proxy.Proxy;
import com.philbeaudoin.platform.mvp.client.proxy.RevealRootContentEvent;

/**
 * The top-level presenter that contains typical pages of the application.
 * An exception are puzzles, that are displayed in their own top-level presenter.
 * 
 * @author Philippe Beaudoin
 */
public class PagePresenter extends PresenterImpl<PagePresenter.MyView, PagePresenter.MyProxy> {

  public interface MyView extends View {
    void setTopBarContent( Widget topBarContent );
    void setMainContent( Widget mainContent );
  }
  
  public interface MyProxy extends Proxy<PagePresenter> {}  

  private final TopBarPresenter topBarPresenter;
  
  private Presenter mainContent = null;
  
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
    getView().setTopBarContent( topBarPresenter.getWidget() );
  }
  
  @Override
  protected void revealChildren() {
    super.revealChildren();
    topBarPresenter.reveal();
    if( mainContent != null )
      mainContent.reveal();
  }
  
  @Override
  protected void notifyHideChildren() {
    super.notifyHideChildren();
    topBarPresenter.notifyHide();    
    if( mainContent != null )
      mainContent.notifyHide();
  }  
  
  public void setMainContent(Presenter content) {
    if( mainContent != content ) {
      if( mainContent != null )
        mainContent.notifyHide();
      mainContent = content;
      getView().setMainContent( content.getWidget() );
    }
  }

}
