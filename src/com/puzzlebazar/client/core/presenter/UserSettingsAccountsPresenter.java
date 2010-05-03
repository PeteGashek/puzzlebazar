/**
 * Copyright 2010 Philippe Beaudoin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.puzzlebazar.client.core.presenter;

import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.View;
import com.philbeaudoin.gwtp.mvp.client.PresenterImpl;
import com.philbeaudoin.gwtp.mvp.client.EventBus;
import com.philbeaudoin.gwtp.mvp.client.proxy.RevealContentEvent;
import com.philbeaudoin.gwtp.mvp.client.proxy.TabContentProxyPlace;
import com.philbeaudoin.gwtp.mvp.client.annotations.NameToken;
import com.philbeaudoin.gwtp.mvp.client.annotations.PlaceInstance;
import com.philbeaudoin.gwtp.mvp.client.annotations.ProxyCodeSplit;
import com.philbeaudoin.gwtp.mvp.client.annotations.TabInfo;
import com.puzzlebazar.client.NameTokens;

/**
 * This is the presenter of the accounts tab in the user settings page.
 * 
 * @author Philippe Beaudoin
 */
public class UserSettingsAccountsPresenter extends PresenterImpl<UserSettingsAccountsPresenter.MyView, UserSettingsAccountsPresenter.MyProxy> {

  public interface MyView extends View { }

  @ProxyCodeSplit
  @NameToken( NameTokens.userSettingsAccounts )
  @PlaceInstance( "new com.puzzlebazar.client.LoggedInSecurePlace(nameToken, ginjector.getCurrentUser())" )
  @TabInfo(
      container = UserSettingsTabPresenter.class, 
      priority = 1,
      getLabel="ginjector.getTranslations().tabAccounts()")
  public interface MyProxy extends TabContentProxyPlace<UserSettingsAccountsPresenter> {}

  @Inject
  public UserSettingsAccountsPresenter(
      final EventBus eventBus, 
      final MyView view,  
      final MyProxy proxy ) {
    super(
        eventBus, 
        view, 
        proxy );
  }

  @Override
  protected void revealInParent() {
    RevealContentEvent.fire(eventBus, UserSettingsTabPresenter.TYPE_RevealTabContent, this);
  }
  
  @Override
  protected void onReveal() {
    super.onReveal();
    DisplayShortMessageEvent.fireMessage(eventBus, "Welcome to your connected accounts page!" );
  }

  @Override
  protected void onHide() {
    super.onHide();
    DisplayShortMessageEvent.fireClearMessage(eventBus);
  }
}
