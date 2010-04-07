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

import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.EventBus;
import com.philbeaudoin.gwtp.mvp.client.PresenterWidgetImpl;
import com.philbeaudoin.gwtp.mvp.client.View;
import com.puzzlebazar.shared.puzzle.heyawake.model.HeyawakePuzzle;

/**
 * This {@link PresenterWidget} is responsible of handling all the logic specific
 * to a {@link HeyawakePuzzle}.
 * 
 * @author Philippe Beaudoin
 */
public class HeyawakePresenterWidget extends PresenterWidgetImpl<HeyawakePresenterWidget.MyView> {

  // TODO Consider making this a final field and using a factory to create HeyawakePresenterWidget
  private HeyawakePuzzle puzzle = null;
  
  public interface MyView extends View {
    void setTitle( String title );    
  }

  @Inject
  public HeyawakePresenterWidget(
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
