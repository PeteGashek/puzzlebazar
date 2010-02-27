package com.philbeaudoin.gwt.presenter.client;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContentProxyImpl;
import com.philbeaudoin.gwt.presenter.client.proxy.SetContentHandler;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContainerProxy;
import com.puzzlebazar.client.ui.HasTabs;
import com.puzzlebazar.client.ui.Tab;

public abstract class TabContainerPresenterImpl<D extends PresenterDisplay & HasTabs, P extends TabContainerProxy> 
extends PresenterImpl<D, P> implements TabContainerPresenter  {

  private Presenter tabContent = null;
  
  public TabContainerPresenterImpl(final EventBus eventBus, final D display, final P proxy, 
      final Type<SetContentHandler> setContentEventType, final TabContentProxyImpl<?>... tabs ) {
    super(eventBus, display, proxy, setContentEventType);
    for( TabContentProxyImpl<?> tabProxy : tabs )
      addTab(tabProxy);
  }

  public void addTab( final TabContentProxyImpl<?> tabProxy ) {
    tabProxy.setTab( display.addTab( tabProxy.getText(), tabProxy.getHistoryToken() ) );
  }

  @Override
  public void setTabContent(Presenter content) {
    if( tabContent != content ) {
      tabContent = content;
      getDisplay().setTabContent( content.getWidget() );
      if( content.getProxy() instanceof TabContentProxyImpl<?> ) {
        Tab tab = ((TabContentProxyImpl<?>)content.getProxy()).getTab();
        display.setActiveTab( tab );
      }
    }
  }
  
}
