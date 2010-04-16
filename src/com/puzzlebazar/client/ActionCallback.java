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

package com.puzzlebazar.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.EventBus;
import com.puzzlebazar.client.core.presenter.DisplayShortMessageEvent;
import com.puzzlebazar.client.resources.Translations;

/**
 * A specialization of AsyncCallback that offers an {@link #onError()} method. 
 * This method gets called whenever the callback fails. It can also be called
 * by subclasses to report a service error.
 * 
 * @author Philippe Beaudoin
 *
 * @param <T> The result type
 */
public abstract class ActionCallback<T> implements AsyncCallback<T> {

  // Uses static injection so that they can be instantiated as anonymous classes
  @Inject
  protected static Translations translations;
  
  @Inject
  private static EventBus eventBus;
  
  private final String errorMessage;
  
  /**
   * Initializes an action callback with the default error message.
   */
  public ActionCallback() {
    this.errorMessage = translations.actionCallbackFailed();
  }
  
  /**
   * Initializes an action with the specified error message.
   * 
   * @param errorMessage The desired error message.
   */
  public ActionCallback( String errorMessage ) {
    this.errorMessage = errorMessage;
  }
  
  @Override
  public void onFailure(Throwable caught) {
    onError( errorMessage );
  }

  /**
   * Reports an error by firing a {@link DisplayShortMessageEvent}.
   * 
   * @param message A short message, translated to the user's locale.
   */
  protected void onError(String message) {
    DisplayShortMessageEvent.fireError( eventBus, message );
  }
  
}
