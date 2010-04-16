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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.ViewImpl;
import com.puzzlebazar.client.core.presenter.NewsItemPresenter;

public class NewsItemView extends ViewImpl implements NewsItemPresenter.MyView {

  interface Binder extends UiBinder<Widget, NewsItemView> { }
  protected static final Binder binder = GWT.create(Binder.class);  
  
  private final Widget widget;

  @UiField
  Label title;
  
  @Inject
  public NewsItemView() {
    widget = binder.createAndBindUi(this);
  }

  @Override 
  public Widget asWidget() {
    return widget;
  }
  
  @Override
  public void setTitle(String title) {
    this.title.setText( title );
  }

}