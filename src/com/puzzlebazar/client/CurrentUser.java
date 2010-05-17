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

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.philbeaudoin.gwtp.dispatch.client.DispatchAsync;
import com.philbeaudoin.gwtp.mvp.client.EventBus;
import com.puzzlebazar.client.core.presenter.CurrentUserChangedEvent;
import com.puzzlebazar.shared.action.GetCurrentUserAction;
import com.puzzlebazar.shared.action.GetUserResult;
import com.puzzlebazar.shared.model.User;

/**
 * A class that keeps information about the currently logged-in user.
 * This should be singleton.
 * 
 * @author Philippe Beaudoin
 */
public class CurrentUser {
  
  private final EventBus eventBus;
  private final DispatchAsync dispatcher;
  private final int refreshDelay;
  private final int retryDelay;
  private final Timer fetchUserTimer;
  
  private User user = null;
  private boolean confirmed = false;
  
  
  /**
   * Creates
   * 
   * @param eventBus     The {@link EventBus}.
   * @param dispatcher   The {@link DispatchAsync}.
   */
  @Inject
  public CurrentUser( 
      final EventBus eventBus,
      final DispatchAsync dispatcher ) {
    this.eventBus = eventBus;
    this.dispatcher = dispatcher;
    // TODO These should be injected when GIN supports toInstance injection
    this.refreshDelay = 5000;
    this.retryDelay = 10000;
    this.fetchUserTimer = new Timer() {
      @Override
      public void run() {
        fetchUser();
      }      
    };
    
    fetchUser();    
  }
  
  /**
   * Fetches the user information from the server. Fires a
   * {@link CurrentUserChangedEvent} when successful.
   */
  public void fetchUser() {
    fetchUserTimer.cancel();  
    dispatcher.execute( new GetCurrentUserAction(), new AsyncCallback<GetUserResult>() {
      
      @Override
      public void onFailure(Throwable caught) {
        confirmed = true; // Async call is back. We know if user is logged-in or not.
        failed();
      }

      @Override
      public void onSuccess(GetUserResult result) {
        confirmed = true; // Async call is back. We know if user is logged-in or not.
        if( result != null ) {
          user = result.getUser();
          CurrentUserChangedEvent.fire( eventBus, CurrentUser.this );
          scheduleFetchUser( refreshDelay );
        }
        else {
          failed();
        }
      }

      private void failed() {
        user = null; // Nobody is logged in
        scheduleFetchUser( retryDelay );
        CurrentUserChangedEvent.fire( eventBus, null );
      }
    } );
  
  }
  
  /**
   * Calls {@link fetchUser} after a given amount of time.
   * 
   * @param retryDelay The time to wait until doing the call (in milliseconds).
   */
  private void scheduleFetchUser(int delay) {
    fetchUserTimer.cancel();
    fetchUserTimer.schedule( delay );
  }

  /**
   * @return A copy of the currently logged on {@link User}, or {@code null} if no user is logged on.
   */
  public User getUserCopy() {
    if( user == null )
      return null;
    return user.clone();
  }  
  
  /**
   * Sets the currently logged on user.
   * 
   * @param user The {@link User} to consider as the current user. 
   */
  public void setUser( User user ) {
    this.user = user;
    CurrentUserChangedEvent.fire( eventBus, this );
  }
  
  /**
   * Checks if the currently logged in user is an administrator.
   * 
   * @return <code>true</code> if the current user is an administrator. 
   *         <code>false</code> if he is not logged in or if he is not administrator.  
   */
  public boolean isAdministrator() {
    return user != null && user.isAdministrator();
  }

  /**
   * Check if a user is logged in. Will return false if the user is unconfirmed,
   * see {@link #isConfirmed()}.
   * 
   * @return <code>true</code> if a user is logged in. <code>false</code> otherwise. 
   */
  public boolean isLoggedIn() {
    return user != null;
  }

  /**
   * The user is unconfirmed when the application starts, before the first asynchronous
   * call to the server has returned. As soon as this call as returned, we know if the
   * user is logged in or not and this method will return <code>true</code>. At that
   * point, call {@link #isLoggedIn()}.
   * 
   * @return <code>true</code> if the user is confirmed. <code>false</code> otherwise. 
   */
  public boolean isConfirmed() {
    return confirmed ;
  }

  public String getEmail() {
    if( user == null )
      return null;
    return user.getEmail();
  }

  public String getLocale() {
    if( user == null )
      return null;
    return user.getLocale();
  }

  public String getNickname() {
    if( user == null )
      return null;
    return user.getNickname();
  }

  public String getRealName() {
    if( user == null )
      return null;
    return user.getRealName();
  }
  
}
