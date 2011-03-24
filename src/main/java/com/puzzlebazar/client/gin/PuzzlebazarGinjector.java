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

package com.puzzlebazar.client.gin;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyFailureHandler;
import com.puzzlebazar.client.AdminGatekeeper;
import com.puzzlebazar.client.CurrentUser;
import com.puzzlebazar.client.LoggedInGatekeeper;
import com.puzzlebazar.client.core.presenter.AdminGeneralPresenter;
import com.puzzlebazar.client.core.presenter.AdminUsersPresenter;
import com.puzzlebazar.client.core.presenter.LinkColumnPresenter;
import com.puzzlebazar.client.core.presenter.MainPagePresenter;
import com.puzzlebazar.client.core.presenter.PagePresenter;
import com.puzzlebazar.client.core.presenter.PuzzlePresenter;
import com.puzzlebazar.client.core.presenter.SplitMainPresenter;
import com.puzzlebazar.client.core.presenter.TabbedPresenterBundle;
import com.puzzlebazar.client.core.presenter.UserSettingsAccountsPresenter;
import com.puzzlebazar.client.core.presenter.UserSettingsGeneralPresenter;
import com.puzzlebazar.client.resources.Resources;
import com.puzzlebazar.client.resources.Translations;

/**
 * @author Philippe Beaudoin
 */
@GinModules({ DispatchAsyncModule.class, PuzzlebazarClientModule.class })
public interface PuzzlebazarGinjector extends Ginjector {

  PlaceManager getPlaceManager();
  EventBus getEventBus();
  Resources getResources();
  DispatchAsync getDispatcher();
  ProxyFailureHandler getProxyFailureHandler();
  CurrentUser getCurrentUser();
  Translations getTranslations();

  LoggedInGatekeeper getLoggedInGatekeeper();
  AdminGatekeeper    getAdminGatekeeper();

  Provider<PagePresenter> getPagePresenter();
  AsyncProvider<SplitMainPresenter> getSplitMainPresenter();
  AsyncProvider<TabbedPresenterBundle> getTabbedPresenterBundle();
  Provider<MainPagePresenter> getMainPagePresenter();
  Provider<LinkColumnPresenter> getLinkColumnPresenter();
  AsyncProvider<PuzzlePresenter> getPuzzlePresenter();
  AsyncProvider<AdminGeneralPresenter> getAdminGeneralPresenter();
  AsyncProvider<AdminUsersPresenter> getAdminUsersPresenter();
  AsyncProvider<UserSettingsGeneralPresenter> getUserSettingsGeneralPresenter();
  AsyncProvider<UserSettingsAccountsPresenter> getUserSettingsAccountsPresenter();
}