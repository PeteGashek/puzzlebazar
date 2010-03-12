package com.puzzlebazar.client.core.presenter;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.View;
import com.philbeaudoin.gwt.presenter.client.PresenterImpl;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.Callback;
import com.philbeaudoin.gwt.presenter.client.proxy.CallbackProvider;
import com.philbeaudoin.gwt.presenter.client.proxy.Place;
import com.philbeaudoin.gwt.presenter.client.proxy.Proxy;
import com.philbeaudoin.gwt.presenter.client.proxy.SetContentEvent;
import com.puzzlebazar.client.CodeSplitProvider;
import com.puzzlebazar.client.core.proxy.SplitMainProxy;
import com.puzzlebazar.client.resources.Translations;

/**
 * This is the presenter of the main application page.
 * 
 * @author Philippe Beaudoin
 */
public class MainPagePresenter extends 
PresenterImpl<MainPagePresenter.MyView, MainPagePresenter.MyProxy> {

  public interface MyView extends View {
    public void addNewsWidget( Widget widget );
    public void clearNewsWidgets();
  }

  public interface MyProxy extends Proxy<MainPagePresenter>, Place {}

  private final CallbackProvider<NewsItemPresenter> newsItemFactory;

  @Inject
  public MainPagePresenter(
      final EventBus eventBus, 
      final MyView view,  
      final MyProxy proxy,
      final Translations translations,
      final AsyncProvider<NewsItemPresenter> newsItemFactory ) {
    super( eventBus, view, proxy );

    this.newsItemFactory = new CodeSplitProvider<NewsItemPresenter>(newsItemFactory, translations);
  }

  @Override
  protected void setContentInParent() {
    SetContentEvent.fire(eventBus, SplitMainProxy.TYPE_SetCenterContent, this);
  }

  // TODO Temporary
  private int index = 0;

  @Override
  public void onReveal() {
    // TODO This is a temporary demonstration showing how to use PresenterWidget
    //      it will add news items every time the main page is reloaded

    for( int i=0; i<3; ++i ) {
      newsItemFactory.get( new Callback<NewsItemPresenter>(){
        @Override
        public void execute(NewsItemPresenter newsItemPresenter) {
          newsItemPresenter.setTitle( "Title " + index );
          index++;
          view.addNewsWidget( newsItemPresenter.getWidget() );
        }
      } );
    }
  }  

}
