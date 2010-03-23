package com.puzzlebazar.client.core.presenter;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwtp.mvp.client.ProviderBundle;

public class TabbedPresenterBundle extends ProviderBundle {

  public final static int ID_AdminTabPresenter = 0;
  public final static int ID_UserSettingsTabPresenter = 1;
  public final static int BUNDLE_SIZE = 2;
  
  @Inject
  TabbedPresenterBundle(
      final Provider<AdminTabPresenter> adminPresenter,
      final Provider<UserSettingsTabPresenter> userSettingsPresenter) {
    super( BUNDLE_SIZE );
    providers[ID_AdminTabPresenter] = adminPresenter;
    providers[ID_UserSettingsTabPresenter] = userSettingsPresenter;
  }  
}
