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

package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.puzzlebazar.client.core.presenter.PagePresenter;

/**
 * The {@link com.gwtplatform.mvp.client.View} for {@link PagePresenter}.
 * 
 * @author Philippe Beaudoin
 */
public class PageView extends ViewImpl implements PagePresenter.MyView {

  interface Binder extends UiBinder<Widget, PageView> { }
  protected static final Binder binder = GWT.create(Binder.class);

  private final Widget widget;
  
  @UiField 
  FlowPanel topBarContainer;
  
  @UiField 
  FlowPanel mainContentContainer;
  
  @Inject
  public PageView() {
    widget = binder.createAndBindUi(this);
  }
  
  @Override 
  public Widget asWidget() {
    return widget;
  }

  @Override
  public void setContent(Object slot, Widget content) {
    if( slot == PagePresenter.TYPE_RevealMainContent ) 
      setMainContent( content );
    else if( slot == PagePresenter.TYPE_RevealTopBarContent ) 
      setTopBarContent( content );
    else
      super.setInSlot(slot, content);
  }
    
  private void setMainContent(Widget mainContent) {
    mainContentContainer.clear();
    if( mainContent != null )
      mainContentContainer.add( mainContent );
  }

  private void setTopBarContent(Widget topBarContent) {
    topBarContainer.clear();
    if( topBarContent != null )
      topBarContainer.add( topBarContent );
  }


}
