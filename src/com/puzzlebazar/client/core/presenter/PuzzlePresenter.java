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
import com.philbeaudoin.gwtp.dispatch.client.DispatchAsync;
import com.philbeaudoin.gwtp.mvp.client.EventBus;
import com.philbeaudoin.gwtp.mvp.client.PresenterImpl;
import com.philbeaudoin.gwtp.mvp.client.View;
import com.philbeaudoin.gwtp.mvp.client.proxy.Place;
import com.philbeaudoin.gwtp.mvp.client.proxy.PlaceManager;
import com.philbeaudoin.gwtp.mvp.client.proxy.PlaceRequest;
import com.philbeaudoin.gwtp.mvp.client.proxy.Proxy;
import com.philbeaudoin.gwtp.mvp.client.proxy.RevealRootLayoutContentEvent;
import com.philbeaudoin.gwtp.mvp.client.annotations.NameToken;
import com.philbeaudoin.gwtp.mvp.client.annotations.ProxyCodeSplit;
import com.puzzlebazar.client.ActionCallback;
import com.puzzlebazar.client.NameTokens;
import com.puzzlebazar.client.puzzle.heyawake.presenter.HeyawakePresenter;
import com.puzzlebazar.client.puzzle.heyawake.presenter.HeyawakePresenter.Factory;
import com.puzzlebazar.client.resources.Translations;
import com.puzzlebazar.shared.action.CreateNewPuzzleAction;
import com.puzzlebazar.shared.action.CreateNewPuzzleResult;
import com.puzzlebazar.shared.puzzle.heyawake.model.HeyawakePuzzle;

/**
 * The top-level presenter that contains puzzles pages.
 * 
 * @author Philippe Beaudoin
 */
public class PuzzlePresenter extends PresenterImpl<PuzzlePresenter.MyView, PuzzlePresenter.MyProxy> {

  public static final String PARAM_ACTION = "action";
  public static final String PARAM_ID = "id";
  public static final String ACTION_PLAY = "play";
  public static final String ACTION_EDIT = "edit";
  public static final String ACTION_NEW = "new";   // TODO get rid of this. Have a new puzzle page instead.
                                                   // Then when the new puzzle page receives the puzzle ID it 
                                                   // navigates here in edit mode.
  public static final String DEFAULT_ACTION = ACTION_PLAY;
  public static final long   INVALID_ID = Long.MAX_VALUE;

  public static final Object TYPE_RevealTopBarContent = new Object();
  public static final Object TYPE_RevealPuzzleContent = new Object();

  public interface MyView extends View {
    public Widget getUiWidget();
  }

  @ProxyCodeSplit
  @NameToken( NameTokens.puzzlePage )
  public interface MyProxy extends Proxy<PuzzlePresenter>, Place {}  


  private final PlaceManager placeManager;
  private final DispatchAsync dispatcher;
  private final Translations translations;
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

  /**
   * This is the {@link HeyawakePuzzle} associated with this presenter. It gets 
   * loaded from the server each time the state is reloaded.
   * 
   * TODO Right now it's hardcoded as a HeyawakePuzzle but it should be some superinterface like
   *      Puzzle.
   */
  private HeyawakePuzzle puzzle = null;


  private String action = DEFAULT_ACTION;
  private long id = INVALID_ID;

  @Inject
  public PuzzlePresenter(
      final EventBus eventBus,
      final PlaceManager placeManager,
      final DispatchAsync dispatcher,
      final Translations translations,
      final MyView view, 
      final MyProxy proxy,
      final TopBarPresenter topBarPresenter,
      final HeyawakePresenter.Factory heyawakePresenterFactory ) {
    super(eventBus, view, proxy);

    this.placeManager = placeManager;
    this.dispatcher = dispatcher;
    this.translations = translations;
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

    releasePuzzle();

    if( action == ACTION_PLAY ) {
    } else if( action == ACTION_EDIT ) {
    } if( action == ACTION_NEW ) {
      // TODO We should fire an event on the bus to get the latest
      //      information on the new puzzle to create. Most likely
      //      a HeyawakePuzzleInfo.
      String title = "Dummy title";
      int width = 4;
      int height = 7;

      dispatcher.execute( new CreateNewPuzzleAction(title, width, height), 
          new ActionCallback<CreateNewPuzzleResult>(translations.operationFailedRetry()){
        @Override
        public void onSuccess(CreateNewPuzzleResult result) {
          puzzle = result.getPuzzle();
          puzzlePresenterWidget = heyawakePresenterFactory.create(getView().getUiWidget(), puzzle);
          puzzlePresenterWidget.bind();
          setContent( TYPE_RevealPuzzleContent, puzzlePresenterWidget, false );
          action = ACTION_EDIT;
          id = result.getPuzzle().getId();
          getProxy().onPresenterChanged(PuzzlePresenter.this);
        }
      } );
    }
  }


  @Override
  public void prepareFromRequest(PlaceRequest placeRequest) {
    super.prepareFromRequest(placeRequest);

    action = placeRequest.getParameter(PARAM_ACTION, DEFAULT_ACTION);
    if( action.equals(ACTION_PLAY) )
      action = ACTION_PLAY;
    else if( action.equals(ACTION_EDIT) )
      action = ACTION_EDIT;
    else if( action.equals(ACTION_NEW) )
      action = ACTION_NEW;
    else {
      placeManager.revealErrorPlace( placeRequest.getNameToken() );
      return;
    }

    try {
      id = Long.valueOf( placeRequest.getParameter(PARAM_ID, null) );
    } catch( NumberFormatException e ) {
      id = INVALID_ID;
    }

    if( id == INVALID_ID && action != ACTION_NEW ) {
      placeManager.revealErrorPlace( placeRequest.getNameToken() );
      return;
    }

  }

  @Override
  public PlaceRequest prepareRequest(PlaceRequest placeRequest) {
    PlaceRequest result = super.prepareRequest(placeRequest);

    if( action != DEFAULT_ACTION )
      result = result.with( PARAM_ACTION, action );
    if( id != INVALID_ID )
      result = result.with( PARAM_ID, Long.toString(id) );
    return result;
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
    clearContent( TYPE_RevealPuzzleContent );
  }
}
