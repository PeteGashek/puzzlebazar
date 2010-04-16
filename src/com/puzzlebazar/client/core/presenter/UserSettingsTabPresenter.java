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

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.View;
import com.philbeaudoin.gwtp.mvp.client.EventBus;
import com.philbeaudoin.gwtp.mvp.client.RequestTabsHandler;
import com.philbeaudoin.gwtp.mvp.client.TabPanel;
import com.philbeaudoin.gwtp.mvp.client.TabContainerPresenterImpl;
import com.philbeaudoin.gwtp.mvp.client.proxy.Proxy;
import com.philbeaudoin.gwtp.mvp.client.proxy.RevealContentEvent;
import com.philbeaudoin.gwtp.mvp.client.proxy.RevealContentHandler;
import com.philbeaudoin.gwtp.mvp.client.annotations.ContentSlot;
import com.philbeaudoin.gwtp.mvp.client.annotations.ProxyCodeSplitBundle;
import com.philbeaudoin.gwtp.mvp.client.annotations.RequestTabs;


/**
 * This is a tabbed presenter that will contain the different tabs 
 * for user settings page.
 * 
 * @author Philippe Beaudoin
 */
public class UserSettingsTabPresenter extends TabContainerPresenterImpl<UserSettingsTabPresenter.MyView,UserSettingsTabPresenter.MyProxy> {
  
  @ContentSlot
  public static final Type<RevealContentHandler<?>> TYPE_RevealTabContent  = new Type<RevealContentHandler<?>>();
  
  @RequestTabs
  public static final Type<RequestTabsHandler> TYPE_RequestTabs = new Type<RequestTabsHandler>();
  
  public interface MyView extends TabPanel, View {}

  @ProxyCodeSplitBundle(
      bundleClass = TabbedPresenterBundle.class, 
      id = TabbedPresenterBundle.ID_UserSettingsTabPresenter )
  public interface MyProxy extends Proxy<UserSettingsTabPresenter> {}
  
  @Inject
  public UserSettingsTabPresenter(
      final EventBus eventBus, 
      final MyView view, 
      final MyProxy proxy ) {
    super(eventBus, view, proxy, TYPE_RevealTabContent, TYPE_RequestTabs );   
  }

  @Override
  protected void revealInParent() {
    RevealContentEvent.fire(eventBus, SplitMainPresenter.TYPE_RevealCenterContent, this);
  }

}
