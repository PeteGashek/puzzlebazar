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
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.TabInfo;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.TabContentProxyPlace;
import com.puzzlebazar.client.ActionCallback;
import com.puzzlebazar.client.CurrentUser;
import com.puzzlebazar.client.LoggedInGatekeeper;
import com.puzzlebazar.client.NameTokens;
import com.puzzlebazar.client.gin.PuzzlebazarGinjector;
import com.puzzlebazar.client.resources.Translations;
import com.puzzlebazar.client.util.ChangeMonitor;
import com.puzzlebazar.client.util.MonitorHandler;
import com.puzzlebazar.shared.action.EditUserAction;
import com.puzzlebazar.shared.action.NoResult;
import com.puzzlebazar.shared.model.User;
import com.puzzlebazar.shared.util.AvailableLocales;

/**
 * This is the presenter of the general tab in the user settings page.
 * 
 * @author Philippe Beaudoin
 */
public class UserSettingsGeneralPresenter 
extends Presenter<UserSettingsGeneralPresenter.MyView, UserSettingsGeneralPresenter.MyProxy>
implements MonitorHandler  {

  /**
   * The presenter's view.
   */
  public interface MyView extends View {
    HasText getEmail();
    HasText getNickname();
    HasText getRealName();
    ListBox getLanguage();
    void setApplyEnabled(boolean enabled);
    HasClickHandlers getApply();
    HasClickHandlers getCancel();
    HasClickHandlers getUndo();
    HasClickHandlers getRedo();
    void addLanguage(String languageName);
    Widget getExecuteSuccessMessage();
    Widget getUndoSuccessMessage();
  }

  /**
   * The presenter's proxy.
   */
  @ProxyCodeSplit
  @NameToken(NameTokens.userSettingsGeneral)
  @UseGatekeeper(LoggedInGatekeeper.class)
  public interface MyProxy extends TabContentProxyPlace<UserSettingsGeneralPresenter> { }
  
  @TabInfo(container = UserSettingsTabPresenter.class, priority = 0)
  public static String getTabLabel(PuzzlebazarGinjector ginjector) {
    return ginjector.getTranslations().tabGeneral();
  }

  private final DispatchAsync dispatcher;
  private final PlaceManager placeManager;
  private final AvailableLocales availableLocales;
  private final CurrentUser currentUser;
  private final ChangeMonitor changeMonitor;
  private final Translations translations;

  private HandlerRegistration undoHandlerRegistration;  
  private HandlerRegistration redoHandlerRegistration;  

  @Inject
  public UserSettingsGeneralPresenter(
      final EventBus eventBus,
      final DispatchAsync dispatcher,
      final PlaceManager placeManager,
      final MyView view, 
      final MyProxy proxy,
      final AvailableLocales availableLocales,
      final CurrentUser currentUser,
      final ChangeMonitor changeMonitor,
      final Translations translations) {
    super(eventBus, view, proxy);
    this.dispatcher = dispatcher;
    this.placeManager = placeManager;
    this.availableLocales = availableLocales;
    this.currentUser = currentUser;
    this.changeMonitor = changeMonitor;
    this.translations = translations;
    changeMonitor.setHandler(this);
  }

  @Override
  protected void onBind() {
    super.onBind();
    registerHandler(getView().getApply().addClickHandler(
        new ClickHandler() {          
          @Override
          public void onClick(ClickEvent event) {
            apply();
          }
        }));
    registerHandler(getView().getCancel().addClickHandler(
        new ClickHandler() {          
          @Override
          public void onClick(ClickEvent event) {
            cancel();
          }
        }));

    for (int i = 0; i < availableLocales.getNbLocales(); ++i) {
      getView().addLanguage(availableLocales.getLocale(i).getName());
    }
  }

  @Override
  protected void onReveal() {
    super.onReveal();
    fillView();
    MyView view = getView();
    changeMonitor.bind();    
    changeMonitor.monitorWidget(view.getNickname());
    changeMonitor.monitorWidget(view.getRealName());
    changeMonitor.monitorWidget(view.getLanguage());
  }

  private void fillView() {
    MyView view = getView();
    int localeIndex = availableLocales.findLocaleIndex(currentUser.getLocale());
    if (localeIndex < 0) {
      localeIndex = availableLocales.getDefaultLocaleIndex();
    }
    view.getEmail().setText(currentUser.getEmail());
    view.getNickname().setText(currentUser.getNickname());
    view.getRealName().setText(currentUser.getRealName());
    view.getLanguage().setSelectedIndex(localeIndex);
    view.setApplyEnabled(false);
  }

  @Override
  protected void onHide() {
    super.onHide();
    changeMonitor.unbind();
  }

  @Override
  protected void revealInParent() {
    RevealContentEvent.fire(this, UserSettingsTabPresenter.TYPE_RevealTabContent, this);
  }

  @Override
  public void changeDetected() {
    // Clear any remaining undo/redo message
    DisplayShortMessageEvent.fireClearMessage(this);
    getView().setApplyEnabled(true);
  }

  @Override
  public void changeReverted() {
    getView().setApplyEnabled(false);
  }

  private void apply() {
    MyView view = getView();
    int localeIndex = view.getLanguage().getSelectedIndex();
    final User newUser = currentUser.getUserCopy();    
    newUser.setNickname(view.getNickname().getText());
    newUser.setRealname(view.getRealName().getText());
    newUser.setLocale(availableLocales.getLocale(localeIndex).getLocale());
    final EditUserAction action = new EditUserAction(newUser, currentUser.getUserCopy());

    execute(action);
  }

  private void cancel() {
    changeMonitor.reset();
    placeManager.navigateBack();
  }

  private void execute(final EditUserAction action) {

    dispatcher.execute(action, new ActionCallback<NoResult>(translations.operationFailedRetry()) {

      @Override
      public void onSuccess(final NoResult result) {
        currentUser.setUser(action.getUser());
        fillView();
        if (undoHandlerRegistration != null) {
          undoHandlerRegistration.removeHandler();
        }

        undoHandlerRegistration = getView().getUndo().addClickHandler(
            new ClickHandler() {          
              @Override
              public void onClick(ClickEvent event) {
                undo(action, result);
              }
            });

        DisplayShortMessageEvent.fireMessage(UserSettingsGeneralPresenter.this, 
            getView().getExecuteSuccessMessage());
        changeMonitor.reset();
      }

    });  
  }
  
  private void undo(final EditUserAction action, NoResult result) {
    dispatcher.undo(action, result, new ActionCallback<Void>(translations.operationFailedRetry()) {

      @Override
      public void onSuccess(final Void voidResult) {
        currentUser.setUser(action.getPreviousUser());
        fillView();
        if (redoHandlerRegistration != null) {
          redoHandlerRegistration.removeHandler();
        }

        redoHandlerRegistration = getView().getRedo().addClickHandler(
            new ClickHandler() {          
              @Override
              public void onClick(ClickEvent event) {
                execute(action);
              }
            });

        DisplayShortMessageEvent.fireMessage(UserSettingsGeneralPresenter.this, 
            getView().getUndoSuccessMessage());
        changeMonitor.reset();
      }
    });
  }

}
