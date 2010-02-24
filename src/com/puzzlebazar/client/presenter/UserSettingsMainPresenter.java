package com.puzzlebazar.client.presenter;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.PresenterDisplay;
import com.philbeaudoin.gwt.presenter.client.PresenterWrapper;

/**
 * This is the main presenter of the user settings tab presenter
 * 
 * @author beaudoin
 */
public class UserSettingsMainPresenter extends BasicPresenter<UserSettingsMainPresenter.Display, UserSettingsMainPresenter.Wrapper> {

  public interface Display extends PresenterDisplay {
  }

  public static class Wrapper extends PresenterWrapper<UserSettingsMainPresenter> {
    @Inject
    public Wrapper(EventBus eventBus, Provider<UserSettingsMainPresenter> presenter) {
      super(eventBus, presenter);
    }
  }

  @Inject
  public UserSettingsMainPresenter(final Display display, final EventBus eventBus, 
      final Wrapper wrapper, final UserSettingsPresenter.MainSlot parentSlot) {
    super(display, eventBus, wrapper, parentSlot);
    bind();
  }

  @Override
  protected void onBind() {
  }

  @Override
  protected void onUnbind() {
  }



}
