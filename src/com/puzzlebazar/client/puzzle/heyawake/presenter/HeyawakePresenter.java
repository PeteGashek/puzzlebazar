package com.puzzlebazar.client.puzzle.heyawake.presenter;

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

import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseUpHandlers;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.EventBus;
import com.philbeaudoin.gwtp.mvp.client.PresenterWidgetImpl;
import com.philbeaudoin.gwtp.mvp.client.View;
import com.puzzlebazar.shared.puzzle.heyawake.model.HeyawakePuzzle;
import com.puzzlebazar.shared.util.Has2DSize;

/**
 * This {@link PresenterWidget} is responsible of handling all the logic specific
 * to a {@link HeyawakePuzzle}.
 * 
 * @author Philippe Beaudoin
 */
public class HeyawakePresenter extends PresenterWidgetImpl<HeyawakePresenter.MyView> {

  /**
   * This factory is used to create new views for the {@link HeyawakePresenter}.
   * 
   * @author Philippe Beaudoin
   */
  public interface ViewFactory {
    /**
     * Create a new view for a {@link HeyawakePresenter}.
     * 
     * @param uiWidget The {@link Widget} receiving the ui input. Must implement
     *   the following interfaces: {@link HasMouseDownHandlers},
     *   {@link HasMouseUpHandlers}, {@link HasMouseMouveHandlers},
     *   {@link HasMouseOutHandlers}.
     * @param puzzleSize An {@link Has2DSize} object that indicates the size of the puzzle.
     * @return The newly created view for this {@link HeyawakePresenter}.
     */
    public MyView create(
        Widget uiWidget,
        Has2DSize puzzleSize );
  }

  public interface MyView extends View {
  }
  

  /**
   * Use this factory to create {@link HeyawakePresenter} objects.
   * 
   * @author Philippe Beaudoin
   */
  public interface Factory {
    /**
     * Create a new {@link HeyawakePresenter}.
     * 
     * @param uiWidget The {@link Widget} receiving the ui input. Must implement
     *   the following interfaces: {@link HasMouseDownHandlers},
     *   {@link HasMouseUpHandlers}, {@link HasMouseMouveHandlers},
     *   {@link HasMouseOutHandlers}.
     * @param puzzle The {@link HeyawakePuzzle} associated with this presenter.
     * @return The newly created {@link HeyawakePresenter}.
     */
    public HeyawakePresenter create(
        Widget uiWidget,
        HeyawakePuzzle puzzle);
  }
  
  public static class FactoryImpl implements Factory {
    private final EventBus eventBus;
    private final ViewFactory viewFactory;

    @Inject
    public FactoryImpl(EventBus eventBus, ViewFactory viewFactory) {
      this.eventBus = eventBus;
      this.viewFactory = viewFactory;
    }
    
    @Override
    public HeyawakePresenter create(
        Widget uiWidget,
        HeyawakePuzzle puzzle) {
      return new HeyawakePresenter(eventBus, viewFactory.create(uiWidget, puzzle) );
    }
  }

  private HeyawakePuzzle puzzle = null;
  
  private HeyawakePresenter(
      final EventBus eventBus, 
      final MyView view) {
    super(
        false,
        eventBus, 
        view);
  }
  
  @Override
  protected void onBind() {
    super.onBind();
    
  }
  
  /**
   * Attaches a specific {@link HeyawakePuzzle} to this presenter widget. 
   * 
   * @param puzzle The {@link HeyawakePuzzle} to attach.
   */
  void attachPuzzle( HeyawakePuzzle puzzle ) {
    if( this.puzzle == puzzle )
      return;
    unbind();
    this.puzzle = puzzle;
    bind();
  }
}
