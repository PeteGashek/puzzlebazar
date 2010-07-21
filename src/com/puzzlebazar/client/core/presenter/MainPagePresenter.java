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

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.CodeSplitProvider;
import com.gwtplatform.mvp.client.IndirectProvider;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.PresenterImpl;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.proxy.ProxyFailureHandler;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.puzzlebazar.client.NameTokens;

/**
 * This is the presenter of the main application page.
 * 
 * @author Philippe Beaudoin
 */
public class MainPagePresenter 
extends PresenterImpl<MainPagePresenter.MyView, MainPagePresenter.MyProxy> {

  public static final Object TYPE_RevealNewsContent = new Object();

  public interface MyView extends View {}

  @ProxyStandard
  @NameToken( NameTokens.mainPage )
  public interface MyProxy extends ProxyPlace<MainPagePresenter> {}

  private final ProxyFailureHandler failureHandler;
  private final IndirectProvider<NewsItemPresenter> newsItemFactory;

  @Inject
  public MainPagePresenter(
      final ProxyFailureHandler failureHandler,
      final EventBus eventBus, 
      final MyView view,  
      final MyProxy proxy,
      final AsyncProvider<NewsItemPresenter> newsItemFactory ) {
    super( eventBus, view, proxy );
    this.failureHandler = failureHandler;
    this.newsItemFactory = new CodeSplitProvider<NewsItemPresenter>(newsItemFactory);
  }

  @Override
  protected void revealInParent() {
    RevealContentEvent.fire(eventBus, SplitMainPresenter.TYPE_RevealCenterContent, this);
  }

  // TODO Temporary
  private int index = 0;

  @Override
  protected void onReveal() {
    super.onReveal();
    // TODO This is a temporary demonstration showing how to use PresenterWidget
    //      it will add news items every time the main page is reloaded

    for( int i=0; i<3; ++i ) {
      newsItemFactory.get( new AsyncCallback<NewsItemPresenter>(){
        @Override
        public void onFailure(Throwable caught) {
          failureHandler.onFailedGetPresenter(caught);
        }
        @Override
        public void onSuccess(NewsItemPresenter newsItemPresenter) {
          newsItemPresenter.setTitle( "Title " + index );
          index++;
          addContent( TYPE_RevealNewsContent, newsItemPresenter );
        }
      } );
    }
  }


}
