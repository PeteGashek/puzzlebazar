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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Command;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.puzzlebazar.client.ActionCallback;
import com.puzzlebazar.client.CurrentUser;
import com.puzzlebazar.shared.action.LogoutAction;
import com.puzzlebazar.shared.action.NoResult;

/**
 * @author Philippe Beaudoin
 */
public class TopBarPresenter extends PresenterWidget<TopBarPresenter.MyView>
implements CurrentUserChangedHandler {

  /**
   * The presenter's view.
   */
  public interface MyView extends View {
    void setLoggedIn(String username, boolean isAdministrator);
    void setLoggedOut();
    void setPostSignInCallback(Command postSignInCallback);
    HasClickHandlers getSignOut();
  }

  private final PlaceManager placeManager;
  private final DispatchAsync dispatcher;
  private final CurrentUser currentUser;

  @Inject
  public TopBarPresenter(
      final EventBus eventBus,
      final PlaceManager placeManager,
      final DispatchAsync dispatcher,
      final MyView view,
      final CurrentUser currentUser) {
    super(eventBus, view);

    this.placeManager = placeManager;
    this.dispatcher = dispatcher;
    this.currentUser = currentUser;
  }

  @Override
  protected void onBind() {
    super.onBind();

    registerHandler(getView().getSignOut().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        doSignOut();
      }
    }));

    getView().setPostSignInCallback(new Command() {
      @Override
      public void execute() {
        currentUser.fetchUser();
      }
    });

    addRegisteredHandler(CurrentUserChangedEvent.getType(), this);
  }

  @Override
  protected void onReveal() {
    super.onReveal();
    checkUserStatus();
  }

  private void checkUserStatus() {
    if (currentUser.isLoggedIn()) {
      getView().setLoggedIn(currentUser.getEmail(), currentUser.isAdministrator());
    } else {
      getView().setLoggedOut();
    }
  }

  @Override
  public void onCurrentUserChanged(CurrentUserChangedEvent event) {
    checkUserStatus();
  }

  private void doSignOut() {
    getView().setLoggedOut();
    placeManager.revealDefaultPlace();
    dispatcher.execute(new LogoutAction(), new ActionCallback<NoResult>() {
      @Override
      public void onSuccess(NoResult noResult) { }
    });
  }

}