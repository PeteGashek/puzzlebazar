package com.puzzlebazar.client.presenter;

import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.PresenterDisplay;
import com.puzzlebazar.client.proxy.SplitMainProxy;
import com.puzzlebazar.client.proxy.TabContainerProxy;
import com.puzzlebazar.client.proxy.UserSettingsDetailsProxy;
import com.puzzlebazar.client.proxy.UserSettingsMainProxy;
import com.puzzlebazar.client.ui.HasTabs;


/**
 * This is a tabbed presenter that will contain the different tabs for user settings 
 * 
 * @author beaudoin
 */
public class UserSettingsPresenter extends BasicTabContainerPresenter<UserSettingsPresenter.Display,UserSettingsPresenter.Proxy> {

  public interface Display extends PresenterDisplay, HasTabs {
  }

  public interface Proxy extends TabContainerProxy {}
  
  @Inject
  public UserSettingsPresenter(final EventBus eventBus, final Display display, 
      final Proxy proxy,
      final UserSettingsMainProxy tab1,
      final UserSettingsDetailsProxy tab2 ) {
    super(eventBus, display, proxy, SplitMainProxy.TYPE_SetCenterContent, tab1, tab2 );   
  }  
  

}
