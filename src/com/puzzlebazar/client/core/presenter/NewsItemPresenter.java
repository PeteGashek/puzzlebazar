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

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * The presenter for a single news item in the application.
 * For now, this is mostly a placeholder class demonstrating how
 * to use {@link PresenterWidget}.
 * 
 * @author Philippe Beaudoin
 */
public class NewsItemPresenter 
extends PresenterWidget<NewsItemPresenter.MyView> {

  /**
   * The presenter's view.
   */
  public interface MyView extends View {
    void setTitle(String title);    
  }

  @Inject
  public NewsItemPresenter(
      final EventBus eventBus, 
      final MyView view) {
    super(
        eventBus, 
        view);
  }
  
  void setTitle(String title) {
    getView().setTitle(title);
  }

}
