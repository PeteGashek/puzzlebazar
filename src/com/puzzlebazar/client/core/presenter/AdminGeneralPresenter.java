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
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.TabInfo;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.TabContentProxyPlace;
import com.puzzlebazar.client.AdminGatekeeper;
import com.puzzlebazar.client.NameTokens;
import com.puzzlebazar.client.gin.PuzzlebazarGinjector;

/**
 * This is the presenter of the general tab in the administration page.
 * 
 * @author Philippe Beaudoin
 */
public class AdminGeneralPresenter 
extends Presenter<AdminGeneralPresenter.MyView, AdminGeneralPresenter.MyProxy> {

  // TODO Remove, only for testing purposes right now.
  @ContentSlot
  public static final Type<RevealContentHandler<?>> TYPE_DummySetContent = new Type<RevealContentHandler<?>>();

  /**
   * The presenter's view.
   */
  public interface MyView extends View { }

  /**
   * The presenter's proxy.
   */
  @ProxyCodeSplit
  @NameToken(NameTokens.adminGeneral)
  @UseGatekeeper(AdminGatekeeper.class)
  public interface MyProxy extends TabContentProxyPlace<AdminGeneralPresenter> { }

  @TabInfo(container = AdminTabPresenter.class, priority = 0)
  public static String getTabLabel(PuzzlebazarGinjector ginjector) {
    return ginjector.getTranslations().tabGeneral();
  }
  
  @Inject
  public AdminGeneralPresenter(final EventBus eventBus, 
      final MyView view, final MyProxy proxy) {
    super(eventBus, view, proxy);
  }

  @Override
  protected void revealInParent() {
    RevealContentEvent.fire(this, AdminTabPresenter.TYPE_RevealTabContent, this);
  }
}
