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
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.TabInfo;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.TabContentProxyPlace;
import com.puzzlebazar.client.LoggedInGatekeeper;
import com.puzzlebazar.client.NameTokens;

/**
 * This is the presenter of the accounts tab in the user settings page.
 * 
 * @author Philippe Beaudoin
 */
public class UserSettingsAccountsPresenter extends 
    Presenter<UserSettingsAccountsPresenter.MyView, UserSettingsAccountsPresenter.MyProxy> {

  /**
   * The presenter's view.
   */
  public interface MyView extends View { }

  /**
   * The presenter's proxy.
   */
  @ProxyCodeSplit
  @NameToken(NameTokens.userSettingsAccounts)
  @UseGatekeeper(LoggedInGatekeeper.class)
  @TabInfo(
      container = UserSettingsTabPresenter.class, 
      priority = 1,
      getLabel = "ginjector.getTranslations().tabAccounts()")
  public interface MyProxy extends TabContentProxyPlace<UserSettingsAccountsPresenter> { }

  @Inject
  public UserSettingsAccountsPresenter(
      final EventBus eventBus, 
      final MyView view,  
      final MyProxy proxy) {
    super(
        eventBus, 
        view, 
        proxy);
  }

  @Override
  protected void revealInParent() {
    RevealContentEvent.fire(this, UserSettingsTabPresenter.TYPE_RevealTabContent, this);
  }
  
  @Override
  protected void onReset() {
    super.onReset();
    DisplayShortMessageEvent.fireMessage(this, "Welcome to your connected accounts page!");
  }
}
