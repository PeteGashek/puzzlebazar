package com.puzzlebazar.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.philbeaudoin.platform.dispatch.client.DispatchAsync;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.puzzlebazar.client.core.presenter.CurrentUserChangedEvent;
import com.puzzlebazar.shared.action.GetUserInfo;
import com.puzzlebazar.shared.action.GetUserInfoResult;
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
      EventBus eventBus,
      DispatchAsync dispatcher
  ) {
    this.eventBus = eventBus;
    this.dispatcher = dispatcher;
    // TODO These should be injected when GIN supports toInstance injection
    this.refreshDelay = 5000;
    this.retryDelay = 10000;
    fetchUserInfo();
  }
  
  /**
   * Fetches the user information from the server. Fires a
   * {@link CurrentUserChangedEvent} when successful.
   */
  private void fetchUserInfo() {
  
    dispatcher.execute( new GetUserInfo(), new AsyncCallback<GetUserInfoResult>() {
      
      @Override
      public void onFailure(Throwable caught) {
        confirmed = true; // Async call is back. We know if user is logged-in or not.
        failed();
      }

      @Override
      public void onSuccess(GetUserInfoResult result) {
        confirmed = true; // Async call is back. We know if user is logged-in or not.
        if( result != null ) {
          user = result.getUserInfo();
          CurrentUserChangedEvent.fire( eventBus, result.getUserInfo() );
          scheduleFetch( refreshDelay );
        }
        else {
          failed();
        }
      }

      private void failed() {
        user = null; // Nobody is logged in
        scheduleFetch( retryDelay );
        CurrentUserChangedEvent.fire( eventBus, null );
      }
    } );
  
  }
  
  /**
   * Calls {@link fetchUserInfo} after a given amount of time.
   * 
   * @param retryDelay The time to wait until doing the call (in milliseconds).
   */
  private void scheduleFetch(int delay) {
    Timer timer = new Timer() {
      @Override
      public void run() {
        fetchUserInfo();
      }      
    };
    timer.schedule( delay );
  }

  public User getUser() {
    return user;
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
  
  
}
