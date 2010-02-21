package com.puzzlebazar.client.presenter;

import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.puzzlebazar.client.presenter.event.NewCenterContentEvent;


public class UserSettingsPresenter extends BasicPresenter<UserSettingsPresenter.Display> {

  public interface Display extends com.philbeaudoin.gwt.presenter.client.Display {
  }

  @Inject
  public UserSettingsPresenter(final Display display, final EventBus eventBus) {
    super(display, eventBus);
   
    bind();
  }  

  @Override
  protected void onBind() {
  }

  @Override
  protected void onUnbind() {
    
  }

  @Override
  public void revealDisplay() {
    NewCenterContentEvent.fire(eventBus, this);    
  }


}
