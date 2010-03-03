package com.puzzlebazar.client.core.presenter;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.puzzlebazar.client.Bundle;

public class TabbedPresenterBundle extends Bundle {

  public final static int ID_AdminPresenter = 0;
  public final static int ID_UserSettingsPresenter = 1;
  public final static int BUNDLE_SIZE = 2;
  
  @Inject
  TabbedPresenterBundle(
      final Provider<AdminPresenter> adminPresenter,
      final Provider<UserSettingsPresenter> userSettingsPresenter) {
    super( BUNDLE_SIZE );
    providers[ID_AdminPresenter] = adminPresenter;
    providers[ID_UserSettingsPresenter] = userSettingsPresenter;
  }  
}
