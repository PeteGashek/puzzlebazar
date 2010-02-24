package com.puzzlebazar.client.presenter;

import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
import com.philbeaudoin.gwt.presenter.client.Presenter;
import com.philbeaudoin.gwt.presenter.client.PresenterDisplay;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.PresenterWrapper;
import com.philbeaudoin.gwt.presenter.client.Slot;
import com.puzzlebazar.client.ui.HasTabs;
import com.puzzlebazar.client.ui.Tab;

public abstract class TabbedPresenter<D extends PresenterDisplay & HasTabs, W extends PresenterWrapper> 
extends BasicPresenter<D, W>  {

  public abstract static class TabPaneSlot<P extends TabbedPresenter<?,?>> extends Slot<P> {
    public TabPaneSlot(Provider<P> presenter) {
      super(presenter);
    }
    @Override
    protected void displayContent() {
      if( content != null ) {
        HasTabs display = getPresenter().getDisplay();
        display.setTabContent( content.getWidget() );
        if( content.getWrapper() instanceof TabPresenterWrapper<?,?> ) {
          Tab tab = ((TabPresenterWrapper<?,?>)content.getWrapper()).getTab();
          display.setActiveTab( tab );
        }
      }
    }
  }
  public TabbedPresenter(final D display, final EventBus eventBus, final W wrapper, 
      final Slot<? extends Presenter> parentSlot, final TabPresenterWrapper<?,?>... tabs ) {
    super(display, eventBus, wrapper, parentSlot);
    for( TabPresenterWrapper<?,?> tabWrapper : tabs )
      addTab(tabWrapper);
  }

  public void addTab( final TabPresenterWrapper<?,?> tabWrapper ) {
    tabWrapper.setTab( display.addTab( tabWrapper.getText(), tabWrapper.getHistoryToken() ) );
  }

}
