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
import com.philbeaudoin.gwtp.mvp.client.proxy.Place;
import com.philbeaudoin.gwtp.mvp.client.proxy.RevealContentEvent;
import com.philbeaudoin.gwtp.mvp.client.proxy.TabContentProxy;
import com.philbeaudoin.gwtp.mvp.client.annotations.NameToken;
import com.philbeaudoin.gwtp.mvp.client.annotations.PlaceInstance;
import com.philbeaudoin.gwtp.mvp.client.annotations.ProxyCodeSplit;
import com.philbeaudoin.gwtp.mvp.client.annotations.TabInfo;
import com.puzzlebazar.client.NameTokens;

/**
 * This is the presenter of the general tab in the administration page.
 * 
 * @author Philippe Beaudoin
 */
public class AdminUsersPresenter 
extends PresenterImpl<AdminUsersPresenter.MyView, AdminUsersPresenter.MyProxy> {

  public interface MyView extends View { }

  @ProxyCodeSplit
  @NameToken( NameTokens.adminUsers )
  @PlaceInstance( "new com.puzzlebazar.client.AdminSecurePlace(nameToken, ginjector.getCurrentUser())" )
  @TabInfo(
      container = AdminTabPresenter.class, 
      priority = 1, 
      getLabel="ginjector.getTranslations().tabUsers()")
  public interface MyProxy extends TabContentProxy<AdminUsersPresenter>, Place {}

  @Inject
  public AdminUsersPresenter(final EventBus eventBus, 
      final MyView view, 
      final MyProxy proxy ) {
    super(eventBus, view, proxy );
  }

  @Override
  protected void revealInParent() {
    RevealContentEvent.fire(eventBus, AdminTabPresenter.TYPE_RevealTabContent, this);
  }
}
