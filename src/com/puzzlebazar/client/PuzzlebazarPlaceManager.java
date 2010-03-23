package com.puzzlebazar.client;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwtp.mvp.client.EventBus;
import com.philbeaudoin.gwtp.mvp.client.proxy.PlaceManagerImpl;
import com.philbeaudoin.gwtp.mvp.client.proxy.ProxyRaw;
import com.philbeaudoin.gwtp.mvp.client.proxy.TokenFormatter;
import com.puzzlebazar.client.gin.DefaultPlace;


/**
 * PlaceManager implementation for Puzzlebazar
 */
public class PuzzlebazarPlaceManager extends PlaceManagerImpl {

  private final Provider<ProxyRaw> defaultProxy;
  private final CurrentUser currentUser;
  private final Timer retryTimer;
  private final int retryDelay;
  private final int maxRetries;
  private int nbRetry = 0;

  @Inject
  public PuzzlebazarPlaceManager(
      final EventBus eventBus, 
      final TokenFormatter tokenFormatter,
      @DefaultPlace final Provider<ProxyRaw> defaultProxy,
      final CurrentUser currentUser ) {
    super(eventBus, tokenFormatter);

    this.defaultProxy = defaultProxy;
    this.currentUser = currentUser;
    // TODO These should be injected when GIN supports toInstance injection
    this.retryDelay = 500;    
    this.maxRetries = 4;

    this.retryTimer = new Timer(){
      @Override
      public void run() {
        History.fireCurrentHistoryState();
      }    
    };

  }

  @Override
  public void revealDefaultPlace() {
    defaultProxy.get().reveal();
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
  public void revealErrorPlace( String invalidHistoryToken ) {
    if( !currentUser.isConfirmed() && nbRetry < maxRetries ) {
      nbRetry++;
      retryTimer.schedule( retryDelay );
    } 
    else
      revealDefaultPlace();
  }

}