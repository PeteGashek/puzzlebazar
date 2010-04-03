package com.puzzlebazar.client.core.presenter;

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


import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.EventBus;
import com.philbeaudoin.gwtp.mvp.client.PresenterImpl;
import com.philbeaudoin.gwtp.mvp.client.View;
import com.philbeaudoin.gwtp.mvp.client.proxy.Place;
import com.philbeaudoin.gwtp.mvp.client.proxy.Proxy;
import com.philbeaudoin.gwtp.mvp.client.proxy.RevealRootLayoutContentEvent;
import com.philbeaudoin.gwtp.mvp.client.annotations.NameToken;
import com.philbeaudoin.gwtp.mvp.client.annotations.ProxyCodeSplit;
import com.puzzlebazar.client.NameTokens;

/**
 * The top-level presenter that contains puzzles pages.
 * 
 * @author Philippe Beaudoin
 */
public class PuzzlePresenter extends PresenterImpl<PuzzlePresenter.MyView, PuzzlePresenter.MyProxy> {

  public static final Object TYPE_RevealTopBarContent = new Object();

  public interface MyView extends View {
  }
  
  @ProxyCodeSplit
  @NameToken( NameTokens.puzzlePage )
  public interface MyProxy extends Proxy<PuzzlePresenter>, Place {}  

  private final TopBarPresenter topBarPresenter;
  
  @Inject
  public PuzzlePresenter(
      final EventBus eventBus, 
      final MyView view, 
      final MyProxy proxy,
      final TopBarPresenter topBarPresenter ) {
    super(eventBus, view, proxy);

    this.topBarPresenter = topBarPresenter;
  }  

  @Override
  protected void revealInParent() {
    RevealRootLayoutContentEvent.fire( eventBus, this );
  }

  @Override
  protected void onReveal() {
    super.onReveal();
    setContent( TYPE_RevealTopBarContent, topBarPresenter );
  }

}
