package com.puzzlebazar.client.core.view;

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


import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.ViewImpl;
import com.puzzlebazar.client.core.presenter.PagePresenter;
import com.puzzlebazar.client.core.presenter.PuzzlePresenter;

/**
 * The {@link com.philbeaudoin.gwtp.mvp.client.View} for {@link PagePresenter}.
 * 
 * @author Philippe Beaudoin
 */
public class PuzzleView extends ViewImpl implements PuzzlePresenter.MyView {

  interface Binder extends UiBinder<DockLayoutPanel, PuzzleView> { }
  protected static final Binder binder = GWT.create(Binder.class);

  private final DockLayoutPanel widget;

  @UiField 
  FlowPanel topBarContainer;

  @UiField 
  LayoutPanel puzzleContainer;

  @UiField
  HTML uiWidget;

  @Inject
  public PuzzleView() {
    widget = binder.createAndBindUi(this);
  }

  @Override 
  public Widget asWidget() {
    return widget;
  }

  @Override
  public void setContent(Object slot, Widget content) {
    if( slot == PuzzlePresenter.TYPE_RevealTopBarContent ) 
      setTopBarContent( content );
    else if( slot == PuzzlePresenter.TYPE_RevealPuzzleContent ) 
      setPuzzleContent( content );
    else
      super.setContent(slot, content);
  }

  private void setTopBarContent(Widget topBarContent) {
    topBarContainer.clear();
    if( topBarContent != null )
      topBarContainer.add( topBarContent );
  }

  private void setPuzzleContent(Widget puzzleContent) {
    puzzleContainer.clear();
    if( puzzleContent != null )
      puzzleContainer.add( puzzleContent );
  }

  @Override
  public Widget getUiWidget() {
    return uiWidget;
  }

}
