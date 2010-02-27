package com.puzzlebazar.client.presenter;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
import com.philbeaudoin.gwt.presenter.client.Presenter;
import com.philbeaudoin.gwt.presenter.client.PresenterDisplay;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.SetContentHandler;
import com.puzzlebazar.client.proxy.BasicTabContentProxy;
import com.puzzlebazar.client.proxy.TabContainerProxy;
import com.puzzlebazar.client.ui.HasTabs;
import com.puzzlebazar.client.ui.Tab;

public abstract class BasicTabContainerPresenter<D extends PresenterDisplay & HasTabs, P extends TabContainerProxy> 
extends BasicPresenter<D, P> implements TabContainerPresenter  {

  private Presenter tabContent = null;
  
  public BasicTabContainerPresenter(final EventBus eventBus, final D display, final P proxy, 
      final Type<SetContentHandler> setContentEventType, final BasicTabContentProxy<?>... tabs ) {
    super(eventBus, display, proxy, setContentEventType);
    for( BasicTabContentProxy<?> tabProxy : tabs )
      addTab(tabProxy);
  }

  public void addTab( final BasicTabContentProxy<?> tabProxy ) {
    tabProxy.setTab( display.addTab( tabProxy.getText(), tabProxy.getHistoryToken() ) );
  }

  @Override
  public void setTabContent(Presenter content) {
    if( tabContent != content ) {
      tabContent = content;
      getDisplay().setTabContent( content.getWidget() );
      if( content.getProxy() instanceof BasicTabContentProxy<?> ) {
        Tab tab = ((BasicTabContentProxy<?>)content.getProxy()).getTab();
        display.setActiveTab( tab );
      }
    }
  }
  
}
