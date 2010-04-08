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


import com.google.gwt.user.client.ui.Widget;
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
import com.puzzlebazar.client.puzzle.heyawake.presenter.HeyawakePresenter;
import com.puzzlebazar.client.puzzle.heyawake.presenter.HeyawakePresenter.Factory;
import com.puzzlebazar.shared.puzzle.heyawake.model.HeyawakePuzzle;
import com.puzzlebazar.shared.puzzle.heyawake.model.HeyawakePuzzleInfo;

/**
 * The top-level presenter that contains puzzles pages.
 * 
 * @author Philippe Beaudoin
 */
public class PuzzlePresenter extends PresenterImpl<PuzzlePresenter.MyView, PuzzlePresenter.MyProxy> {

  public static final Object TYPE_RevealTopBarContent = new Object();
  public static final Object TYPE_RevealPuzzleContent = new Object();

  public interface MyView extends View {
    public Widget getUiWidget();
  }
  
  @ProxyCodeSplit
  @NameToken( NameTokens.puzzlePage )
  public interface MyProxy extends Proxy<PuzzlePresenter>, Place {}  

  private final TopBarPresenter topBarPresenter;
  private final Factory heyawakePresenterFactory;
  
  /**
   * This is the {@link PresenterWidget} specific to the type of puzzle that is currently being
   * displayed. It gets created every time the state is reloaded.
   * 
   * TODO Right now it's hardcoded as a HeyawakePresenter but it should be some superinterface like
   *      PuzzlePresenterWidget.
   */
  private HeyawakePresenter puzzlePresenterWidget = null;
  
  @Inject
  public PuzzlePresenter(
      final EventBus eventBus, 
      final MyView view, 
      final MyProxy proxy,
      final TopBarPresenter topBarPresenter,
      final HeyawakePresenter.Factory heyawakePresenterFactory ) {
    super(eventBus, view, proxy);

    this.topBarPresenter = topBarPresenter;
    this.heyawakePresenterFactory = heyawakePresenterFactory;
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

  @Override
  protected void onReset() {
    super.onReset();
    
    // TODO Temp
    HeyawakePuzzleInfo puzzleInfo = new HeyawakePuzzleInfo(10,8);
    HeyawakePuzzle puzzle = new HeyawakePuzzle(puzzleInfo);
    
    releasePuzzle();
    puzzlePresenterWidget = heyawakePresenterFactory.create(getView().getUiWidget(), puzzle);
    puzzlePresenterWidget.bind();
    setContent( TYPE_RevealPuzzleContent, puzzlePresenterWidget );
  }
  
  @Override
  protected void onHide() {
    super.onHide();
    releasePuzzle();  
  }

  /**
   * Releases all resources related to the associated puzzle.
   * Makes sure the {@link PresenterWidget} of the associated puzzle is unbound
   * and that this puzzle is released.
   */
  private void releasePuzzle() {
    if( puzzlePresenterWidget != null )
      puzzlePresenterWidget.unbind();
    puzzlePresenterWidget = null;
  }
}
