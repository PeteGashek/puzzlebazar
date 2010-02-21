package com.puzzlebazar.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.puzzlebazar.client.gin.PuzzlebazarGinjector;
import com.puzzlebazar.client.presenter.event.CurrentUserInfoAvailableEvent;
import com.puzzlebazar.client.presenter.event.LoginURLsAvailableEvent;
import com.puzzlebazar.shared.action.GetLoginURLs;
import com.puzzlebazar.shared.action.GetLoginURLsResult;
import com.puzzlebazar.shared.action.GetUserInfo;
import com.puzzlebazar.shared.action.GetUserInfoResult;

public class Puzzlebazar implements EntryPoint {
  private final PuzzlebazarGinjector injector = GWT.create(PuzzlebazarGinjector.class);


  public void onModuleLoad() {

    injector.getAppPresenter();
    injector.getResources().style().ensureInjected();

    injector.getPlaceManager().fireCurrentPlace();

    getLoginURLs();
    getUserInfo();
  }

  private void getLoginURLs() {
    String url = Window.Location.getHref();

    injector.getDispatcher().execute( new GetLoginURLs(url), new AsyncCallback<GetLoginURLsResult>() {

      @Override
      public void onFailure(Throwable caught) {
        Window.alert("Server connection error.");
      }

      @Override
      public void onSuccess(GetLoginURLsResult result) {
        LoginURLsAvailableEvent.fire( injector.getEventBus(), result.getLoginURL(), result.getLogoutURL() );
      }
    } );
  }

  private void getUserInfo() {
    injector.getDispatcher().execute( new GetUserInfo(), new AsyncCallback<GetUserInfoResult>() {

      @Override
      public void onFailure(Throwable caught) {
        Window.alert("Server connection error.");
      }

      @Override
      public void onSuccess(GetUserInfoResult result) {
        if( result != null )
          CurrentUserInfoAvailableEvent.fire( injector.getEventBus(), result.getUserInfo() );
      }
    } );
  }

}
