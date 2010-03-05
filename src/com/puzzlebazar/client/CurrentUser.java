package com.puzzlebazar.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.dispatch.client.DispatchAsync;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.puzzlebazar.client.core.presenter.CurrentUserInfoAvailableEvent;
import com.puzzlebazar.shared.action.GetUserInfo;
import com.puzzlebazar.shared.action.GetUserInfoResult;
import com.puzzlebazar.shared.model.UserInfo;

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

  private UserInfo userInfo = null;
  
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
   * {@link CurrentUserInfoAvailableEvent} when successful.
   */
  private void fetchUserInfo() {
  
    dispatcher.execute( new GetUserInfo(), new AsyncCallback<GetUserInfoResult>() {
      
      @Override
      public void onFailure(Throwable caught) {
        failed();
      }

      @Override
      public void onSuccess(GetUserInfoResult result) {
        if( result != null ) {
          userInfo = result.getUserInfo();
          CurrentUserInfoAvailableEvent.fire( eventBus, result.getUserInfo() );
          scheduleFetch( refreshDelay );
        }
        else {
          failed();
        }
      }

      private void failed() {
        userInfo = null; // Nobody is logged in
        scheduleFetch( retryDelay );
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

  public UserInfo getUserInfo() {
    return userInfo;
  }

  /**
   * Checks if the currently logged in user is an administrator.
   * 
   * @return <code>true</code> if the current user is an administrator. 
   *         <code>false</code> if he is not logged in or if he is not administrator.  
   */
  public boolean isAdministrator() {
    return userInfo != null && userInfo.isAdministrator();
  }

  /**
   * Check if a user is logged in.
   * 
   * @return <code>true</code> if a user is logged in. <code>false</code> otherwise. 
   */
  public boolean isLoggedIn() {
    return userInfo != null;
  }
  
  
}
