package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.Presenter;
import com.philbeaudoin.platform.mvp.client.View;
import com.philbeaudoin.platform.mvp.client.PresenterImpl;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyInt;
import com.philbeaudoin.platform.mvp.client.proxy.RevealContentHandler;
import com.philbeaudoin.platform.mvp.client.proxy.RevealRootContentEvent;
import com.philbeaudoin.platform.mvp.rebind.RevealEventType;
import com.philbeaudoin.platform.mvp.rebind.UseCodeSplit;

/**
 * The top-level presenter that contains typical pages of the application.
 * An exception are puzzles, that are displayed in their own top-level presenter.
 * 
 * @author Philippe Beaudoin
 */
public class PagePresenter extends PresenterImpl<PagePresenter.MyView, PagePresenter.MyProxy> {

  public static final Type<RevealContentHandler<?>> TYPE_RevealMainContent = new Type<RevealContentHandler<?>>();

  public interface MyView extends View {
    void setTopBarContent( Widget topBarContent );
    void setMainContent( Widget mainContent );
  }
  
  @UseCodeSplit
  public interface MyProxy extends ProxyInt<PagePresenter> {
  }  

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
  
  @RevealEventType( "TYPE_RevealMainContent" )
  public void setMainContent(Presenter content) {
    if( mainContent != content ) {
      if( mainContent != null )
        mainContent.notifyHide();
      mainContent = content;
      getView().setMainContent( content.getWidget() );
    }
  }

}
