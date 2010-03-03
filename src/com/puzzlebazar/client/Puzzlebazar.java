package com.puzzlebazar.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.puzzlebazar.client.core.presenter.CurrentUserInfoAvailableEvent;
import com.puzzlebazar.client.gin.PuzzlebazarGinjector;
import com.puzzlebazar.shared.action.GetUserInfo;
import com.puzzlebazar.shared.action.GetUserInfoResult;

public class Puzzlebazar implements EntryPoint {
  private final PuzzlebazarGinjector injector = GWT.create(PuzzlebazarGinjector.class);


  public void onModuleLoad() {

    injector.getResources().style().ensureInjected();

    injector.getPlaceManager().revealCurrentPlace();

    getUserInfo();
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
