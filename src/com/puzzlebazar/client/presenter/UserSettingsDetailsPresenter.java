package com.puzzlebazar.client.presenter;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.PresenterDisplay;
import com.puzzlebazar.client.place.UserSettingsDetailsPlace;
import com.puzzlebazar.client.resources.Translations;

/**
 * This is the details presenter of the user settings tab presenter
 * 
 * @author beaudoin
 */
public class UserSettingsDetailsPresenter extends BasicPresenter<UserSettingsDetailsPresenter.Display, UserSettingsDetailsPresenter.Wrapper> {

  public interface Display extends PresenterDisplay { }

  public static class Wrapper extends TabPresenterWrapper<UserSettingsDetailsPresenter, UserSettingsDetailsPlace> {
    @Inject
    public Wrapper(final EventBus eventBus, final Provider<UserSettingsDetailsPresenter> presenter, 
        final Provider<UserSettingsDetailsPlace> place, final Translations translations) {
      super(eventBus, presenter, place, translations);
    }
    @Override
    public String getText() {
      return translations.details();
    }
  }

  @Inject
  public UserSettingsDetailsPresenter(final Display display, final EventBus eventBus, 
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
