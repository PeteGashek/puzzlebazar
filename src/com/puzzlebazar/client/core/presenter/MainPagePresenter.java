package com.puzzlebazar.client.core.presenter;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.CodeSplitProvider;
import com.philbeaudoin.platform.mvp.client.IndirectProvider;
import com.philbeaudoin.platform.mvp.client.View;
import com.philbeaudoin.platform.mvp.client.PresenterImpl;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.proxy.Place;
import com.philbeaudoin.platform.mvp.client.proxy.Proxy;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyFailureHandler;
import com.philbeaudoin.platform.mvp.client.proxy.RevealContentEvent;
import com.philbeaudoin.platform.mvp.rebind.NameToken;
import com.philbeaudoin.platform.mvp.rebind.ProxyStandard;
import com.puzzlebazar.client.NameTokens;

/**
 * This is the presenter of the main application page.
 * 
 * @author Philippe Beaudoin
 */
public class MainPagePresenter 
extends PresenterImpl<MainPagePresenter.MyView, MainPagePresenter.MyProxy> {

  public static final Object TYPE_RevealNewsContent = new Object();

  public interface MyView extends View {}

  @ProxyStandard
  @NameToken( NameTokens.mainPage )
  public interface MyProxy extends Proxy<MainPagePresenter>, Place {}

  private final ProxyFailureHandler failureHandler;
  private final IndirectProvider<NewsItemPresenter> newsItemFactory;

  @Inject
  public MainPagePresenter(
      final ProxyFailureHandler failureHandler,
      final EventBus eventBus, 
      final MyView view,  
      final MyProxy proxy,
      final AsyncProvider<NewsItemPresenter> newsItemFactory ) {
    super( eventBus, view, proxy );
    this.failureHandler = failureHandler;
    this.newsItemFactory = new CodeSplitProvider<NewsItemPresenter>(newsItemFactory);
  }

  @Override
  protected void revealInParent() {
    RevealContentEvent.fire(eventBus, SplitMainPresenter.TYPE_RevealCenterContent, this);
  }

  // TODO Temporary
  private int index = 0;

  @Override
  public void onReveal() {
    super.onReveal();
    // TODO This is a temporary demonstration showing how to use PresenterWidget
    //      it will add news items every time the main page is reloaded

    for( int i=0; i<3; ++i ) {
      newsItemFactory.get( new AsyncCallback<NewsItemPresenter>(){
        @Override
        public void onFailure(Throwable caught) {
          failureHandler.onFailedGetPresenter(caught);
        }
        @Override
        public void onSuccess(NewsItemPresenter newsItemPresenter) {
          newsItemPresenter.setTitle( "Title " + index );
          index++;
          addContent( TYPE_RevealNewsContent, newsItemPresenter );
        }
      } );
    }
  }


}
