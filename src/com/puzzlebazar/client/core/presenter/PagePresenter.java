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
import com.philbeaudoin.gwtp.mvp.client.PresenterImpl;
import com.philbeaudoin.gwtp.mvp.client.EventBus;
import com.philbeaudoin.gwtp.mvp.client.proxy.Proxy;
import com.philbeaudoin.gwtp.mvp.client.proxy.RevealContentHandler;
import com.philbeaudoin.gwtp.mvp.client.proxy.RevealRootContentEvent;
import com.philbeaudoin.gwtp.mvp.client.annotations.ContentSlot;
import com.philbeaudoin.gwtp.mvp.client.annotations.ProxyStandard;

/**
 * The top-level presenter that contains typical pages of the application.
 * An exception are puzzles, that are displayed in their own top-level presenter.
 * 
 * @author Philippe Beaudoin
 */
public class PagePresenter extends PresenterImpl<PagePresenter.MyView, PagePresenter.MyProxy> {

  @ContentSlot
  public static final Type<RevealContentHandler<?>> TYPE_RevealMainContent = new Type<RevealContentHandler<?>>();  

  public static final Object TYPE_RevealTopBarContent = new Object();
    
  public interface MyView extends View {
  }
  
  @ProxyStandard
  public interface MyProxy extends Proxy<PagePresenter> {
  }  

  private final TopBarPresenter topBarPresenter;
  
  @Inject
  public PagePresenter(
      final EventBus eventBus, 
      final MyView view, 
      final MyProxy proxy,
      final TopBarPresenter topBarPresenter ) {
    super(eventBus, view, proxy);

    this.topBarPresenter = topBarPresenter;
  }  

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire( eventBus, this );
  }

  @Override
  protected void onReveal() {
    super.onReveal();
    setContent( TYPE_RevealTopBarContent, topBarPresenter );
  }
  
  @Override
  protected void onHide() {
    super.onHide();
    clearContent( TYPE_RevealTopBarContent );
  }

  @Override
  protected void onReset() {
    super.onReset();
    DisplayShortMessageEvent.fireClearMessage(eventBus);
  }

}


