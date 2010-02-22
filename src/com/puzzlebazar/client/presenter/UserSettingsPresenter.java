package com.puzzlebazar.client.presenter;

import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.puzzlebazar.client.presenter.event.NewCenterContentEvent;
import com.puzzlebazar.client.ui.HasTabs;
import com.puzzlebazar.client.ui.Tab;


public class UserSettingsPresenter extends BasicPresenter<UserSettingsPresenter.Display> {

  public interface Display extends com.philbeaudoin.gwt.presenter.client.Display, HasTabs {
  }

  @Inject
  public UserSettingsPresenter(final Display display, final EventBus eventBus) {
    super(display, eventBus);
   
    bind();
  }  

  @Override
  protected void onBind() {
    Tab tab1 = display.addTab("Test", "settings");
    display.addTab("Return", "");
    display.setActiveTab(tab1);
  }

  @Override
  protected void onUnbind() {
    
  }

  @Override
  public void revealDisplay() {
    NewCenterContentEvent.fire(eventBus, this);    
  }


}
