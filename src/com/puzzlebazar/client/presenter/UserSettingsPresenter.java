package com.puzzlebazar.client.presenter;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.PresenterDisplay;
import com.philbeaudoin.gwt.presenter.client.PresenterWrapper;
import com.puzzlebazar.client.place.UserSettingsMainPresenterPlace;
import com.puzzlebazar.client.ui.HasTabs;


/**
 * This is a tabbed presenter that will contain the different tabs for user settings 
 * 
 * @author beaudoin
 */
public class UserSettingsPresenter extends TabbedPresenter<UserSettingsPresenter.Display,UserSettingsPresenter.Wrapper> {

  public interface Display extends PresenterDisplay, HasTabs {
  }

  public static class MainSlot extends TabSlot<UserSettingsPresenter> {
    @Inject
    public MainSlot(Provider<UserSettingsPresenter> presenter) {
      super(presenter);
    }
  }

  public static class Wrapper extends PresenterWrapper<UserSettingsPresenter> {
    @Inject
    public Wrapper(EventBus eventBus, Provider<UserSettingsPresenter> presenter) {
      super(eventBus, presenter);
    }
  }
  
  @Inject
  public UserSettingsPresenter(final Display display, final EventBus eventBus, 
      final Wrapper wrapper, final SplitMainPresenter.CenterSlot parentSlot,
      UserSettingsMainPresenterPlace tab1 ) {
    super(display, eventBus, wrapper, parentSlot, tab1 );   
    bind();
  }  

  @Override
  protected void onBind() {
  }

  @Override
  protected void onUnbind() {
    
  }

  

}
