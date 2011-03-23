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
import com.gwtplatform.mvp.client.RequestTabsHandler;
import com.gwtplatform.mvp.client.TabContainerPresenter;
import com.gwtplatform.mvp.client.TabView;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplitBundle;
import com.gwtplatform.mvp.client.annotations.RequestTabs;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

/**
 * This is a tabbed presenter that will contain the different tabs for administration page. 
 * 
 * @author Philippe Beaudoin
 */
public class AdminTabPresenter extends TabContainerPresenter<AdminTabPresenter.MyView,AdminTabPresenter.MyProxy> {

  @ContentSlot
  public static final Type<RevealContentHandler<?>> TYPE_RevealTabContent = new Type<RevealContentHandler<?>>();

  @RequestTabs
  public static final Type<RequestTabsHandler> TYPE_RequestTabs = new Type<RequestTabsHandler>();
  
  /**
   * The presenter's view.
   */
  public interface MyView extends TabView { }

  /**
   * The presenter's proxy.
   */
  @ProxyCodeSplitBundle(
      bundleClass = TabbedPresenterBundle.class, 
      id = TabbedPresenterBundle.ID_AdminTabPresenter)
  public interface MyProxy extends Proxy<AdminTabPresenter> { }
  
  @Inject
  public AdminTabPresenter(final EventBus eventBus, 
      final MyView view, final MyProxy proxy) {
    super(eventBus, view, proxy, TYPE_RevealTabContent, TYPE_RequestTabs);   
  }  

  @Override
  protected void revealInParent() {
    RevealContentEvent.fire(this, SplitMainPresenter.TYPE_RevealCenterContent, this);
  }

}
