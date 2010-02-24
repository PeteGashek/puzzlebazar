package com.puzzlebazar.client.presenter;

import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
import com.philbeaudoin.gwt.presenter.client.Presenter;
import com.philbeaudoin.gwt.presenter.client.PresenterDisplay;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.PresenterWrapper;
import com.philbeaudoin.gwt.presenter.client.Slot;
import com.puzzlebazar.client.place.TabPresenterPlace;
import com.puzzlebazar.client.ui.HasTabs;

public abstract class TabbedPresenter<D extends PresenterDisplay & HasTabs, W extends PresenterWrapper<?>> 
extends BasicPresenter<D, W>  {
  
  public abstract static class TabSlot<P extends TabbedPresenter<?,?>> extends Slot<P> {
    public TabSlot(Provider<P> presenter) {
      super(presenter);
    }
    @Override
    protected void displayContent() {
      if( activePresenter != null )
        getPresenter().getDisplay().setTabContent( activePresenter.getWidget() );
    }
  }
  
  public TabbedPresenter(final D display, final EventBus eventBus, final W wrapper, 
      final Slot<? extends Presenter> parentSlot, final TabPresenterPlace<?,?>... tabPresenterPlaces ) {
    super(display, eventBus, wrapper, parentSlot);
    for( TabPresenterPlace<?,?> place : tabPresenterPlaces )
      addTabPresenterPlace(place);
  }

  public void addTabPresenterPlace( final TabPresenterPlace<?,?> place ) {
    display.addTab( place.getText(), place.getName() );
  }
  
}
