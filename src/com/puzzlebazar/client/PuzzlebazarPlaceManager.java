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

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;
import com.puzzlebazar.client.gin.DefaultPlace;
import com.puzzlebazar.client.gin.MaxRetries;
import com.puzzlebazar.client.gin.RetryDelay;

/**
 * PlaceManager implementation for Puzzlebazar.
 * 
 * @author Philippe Beaudoin
 */
public class PuzzlebazarPlaceManager extends PlaceManagerImpl {

  private final PlaceRequest defaultPlaceRequest;
  private final CurrentUser currentUser;
  private final Timer retryTimer;
  private final int retryDelay;
  private final int maxRetries;
  private int nbRetry;

  @Inject
  public PuzzlebazarPlaceManager(final EventBus eventBus, 
      final TokenFormatter tokenFormatter,
      @DefaultPlace String defaultPlaceNameToken,
      @RetryDelay int retryDelay,
      @MaxRetries int maxRetries,
      final CurrentUser currentUser) {
    super(eventBus, tokenFormatter);

    this.defaultPlaceRequest = new PlaceRequest(defaultPlaceNameToken);
    this.currentUser = currentUser;
    this.retryDelay = retryDelay;    
    this.maxRetries = maxRetries;

    this.retryTimer = new Timer() {
      @Override
      public void run() {
        History.fireCurrentHistoryState();
      }    
    };
  }

  @Override
  public void revealDefaultPlace() {
    revealPlace(defaultPlaceRequest);
  }

  /**
   * This method will usually reveal the default place. When the application
   * launches, however, it is possible that the error comes from the fact
   * that the user hasn't had time to load yet (the asynchronous call to
   * the server hasn't yet returned). In this case, we wait a while and
   * if the user logs-in the meantime, we re-run the request.
   * 
   * @param invalidHistoryToken The history token that was not recognised.
   *        It will be retried if the user logs in.
   * 
   * @see PlaceManagerImpl#revealErrorPlace(String)
   */
  @Override
  public void revealErrorPlace(String invalidHistoryToken) {
    if (!currentUser.isConfirmed() && nbRetry < maxRetries) {
      nbRetry++;
      retryTimer.schedule(retryDelay);
    } else {
      revealDefaultPlace();
    }
  }

}