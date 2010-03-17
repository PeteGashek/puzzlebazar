package com.puzzlebazar.client;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.proxy.Callback;
import com.philbeaudoin.platform.mvp.client.proxy.CallbackProvider;
import com.philbeaudoin.platform.mvp.client.proxy.PlaceManagerImpl;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyBase;
import com.philbeaudoin.platform.mvp.client.proxy.TokenFormatter;
import com.puzzlebazar.client.core.presenter.LinkColumnPresenter;
import com.puzzlebazar.client.core.presenter.TopBarPresenter;
import com.puzzlebazar.client.gin.DefaultPlace;


/**
 * PlaceManager implementation for Puzzlebazar
 */
public class PuzzlebazarPlaceManager extends PlaceManagerImpl {

  private final CallbackProvider<ProxyBase> defaultProxy;
  private final CodeSplitProvider<TopBarPresenter> topBarPresenter;
  private final CodeSplitProvider<LinkColumnPresenter> linkColumnPresenter;
  private final CurrentUser currentUser;
  private final Timer retryTimer;
  private final int retryDelay;
  private final int maxRetries;
  private int nbRetry = 0;

  @Inject
  public PuzzlebazarPlaceManager(
      final EventBus eventBus, 
      final TokenFormatter tokenFormatter,
      @DefaultPlace final AsyncProvider<ProxyBase> defaultProxy,
      final AsyncProvider<TopBarPresenter> topBarPresenter,
      final AsyncProvider<LinkColumnPresenter> linkColumnPresenter,
      final CurrentUser currentUser ) {
    super(eventBus, tokenFormatter);

    this.defaultProxy = new CodeSplitProvider<ProxyBase>(defaultProxy);
    this.topBarPresenter = new CodeSplitProvider<TopBarPresenter>(topBarPresenter);
    this.linkColumnPresenter = new CodeSplitProvider<LinkColumnPresenter>(linkColumnPresenter);
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
  public void initialize() {
    topBarPresenter.get( new Callback<TopBarPresenter>(){
      @Override
      public void execute(TopBarPresenter presenter) {
        presenter.reveal();
      }
    } );
    linkColumnPresenter.get( new Callback<LinkColumnPresenter>(){
      @Override
      public void execute(LinkColumnPresenter presenter) {
        presenter.reveal();
      }
    } );
    revealCurrentPlace();
  }

  @Override
  public void revealDefaultPlace() {
    defaultProxy.get( new Callback<ProxyBase>(){
      @Override
      public void execute(ProxyBase proxy) {
        proxy.reveal();
      }
    } );
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