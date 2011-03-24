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

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.TabInfo;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.TabContentProxyPlace;
import com.puzzlebazar.client.AdminGatekeeper;
import com.puzzlebazar.client.NameTokens;
import com.puzzlebazar.client.gin.PuzzlebazarGinjector;

/**
 * This is the presenter of the general tab in the administration page.
 *
 * @author Philippe Beaudoin
 */
public class AdminUsersPresenter
extends Presenter<AdminUsersPresenter.MyView, AdminUsersPresenter.MyProxy> {

  /**
   * The presenter's view.
   */
  public interface MyView extends View { }

  /**
   * The presenter's proxy.
   */
  @ProxyCodeSplit
  @NameToken(NameTokens.adminUsers)
  @UseGatekeeper(AdminGatekeeper.class)
  public interface MyProxy extends TabContentProxyPlace<AdminUsersPresenter> { }

  @TabInfo(container = AdminTabPresenter.class, priority = 1)
  public static String getTabLabel(PuzzlebazarGinjector ginjector) {
    return ginjector.getTranslations().tabUsers();
  }

  @Inject
  public AdminUsersPresenter(final EventBus eventBus,
      final MyView view, final MyProxy proxy) {
    super(eventBus, view, proxy);
  }

  @Override
  protected void revealInParent() {
    RevealContentEvent.fire(this, AdminTabPresenter.TYPE_RevealTabContent, this);
  }
}
