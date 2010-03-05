package com.puzzlebazar.client.core.presenter;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.PresenterImpl;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.ProxyPlace;
import com.puzzlebazar.client.core.proxy.SplitMainProxy;

/**
 * This is the presenter of the main application page.
 * 
 * @author Philippe Beaudoin
 */
public class MainPagePresenter extends PresenterImpl<MainPagePresenter.Display, MainPagePresenter.Proxy> {


  public interface Display extends com.philbeaudoin.gwt.presenter.client.Display { }

  public interface Proxy extends ProxyPlace {}
  
  @Inject
  public MainPagePresenter(
      final EventBus eventBus, 
      final Provider<Display> display,  
      final Proxy proxy ) {
    super( eventBus, display, proxy, SplitMainProxy.TYPE_SetCenterContent );
  }

}
