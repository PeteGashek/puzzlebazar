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
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

/**
 * @author Philippe Beaudoin
 */
public class SplitMainPresenter 
extends Presenter<SplitMainPresenter.MyView, SplitMainPresenter.MyProxy>
implements DisplayShortMessageHandler {

  @ContentSlot
  public static final Type<RevealContentHandler<?>> TYPE_RevealSideBarContent = new Type<RevealContentHandler<?>>();
  
  @ContentSlot
  public static final Type<RevealContentHandler<?>> TYPE_RevealCenterContent = new Type<RevealContentHandler<?>>();
  
  /**
   * The presenter's view.
   */
  public interface MyView extends View {
    void showMessage(Widget message, boolean dismissable);
    void clearMessage();
    boolean hasSideBarContent();
  }

  /**
   * The presenter's proxy.
   */
  @ProxyCodeSplit
  public interface MyProxy extends Proxy<SplitMainPresenter> { }

  @Inject
  public SplitMainPresenter(
      final EventBus eventBus, 
      final MyView view, 
      final MyProxy proxy) {
    super(eventBus, view, proxy);
  }  

  @Override
  protected void revealInParent() {
    RevealContentEvent.fire(this, PagePresenter.TYPE_RevealMainContent, this);
  }

  @Override
  protected void onBind() {
    super.onBind();
    addRegisteredHandler(DisplayShortMessageEvent.getType(), this);
  }

  @Override
  protected void onReveal() {
    super.onReveal();
    if (!getView().hasSideBarContent()) {
      RevealDefaultLinkColumnEvent.fire(this);
    }
  }

  @Override
  public void onDisplayShortMessage(DisplayShortMessageEvent event) {
    if (!isVisible() || event.isAlreadyDisplayed()) {
      return;
    }
    Widget message = event.getMessage();    
    if (message == null) {
      getView().clearMessage();
    } else {
      // TODO Take duration into account
      getView().showMessage(message, event.isDismissable());
    }
    event.setAlreadyDisplayed();
  }
}
