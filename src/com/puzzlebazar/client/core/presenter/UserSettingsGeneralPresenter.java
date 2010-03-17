package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ListBox;
import com.google.inject.Inject;
import com.philbeaudoin.platform.dispatch.client.DispatchAsync;
import com.philbeaudoin.platform.mvp.client.View;
import com.philbeaudoin.platform.mvp.client.PresenterImpl;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.proxy.Place;
import com.philbeaudoin.platform.mvp.client.proxy.PlaceManager;
import com.philbeaudoin.platform.mvp.client.proxy.SetContentEvent;
import com.philbeaudoin.platform.mvp.client.proxy.TabContentProxy;
import com.puzzlebazar.client.ActionCallback;
import com.puzzlebazar.client.CurrentUser;
import com.puzzlebazar.client.core.proxy.UserSettingsTabProxy;
import com.puzzlebazar.client.resources.Translations;
import com.puzzlebazar.client.util.MonitorHandler;
import com.puzzlebazar.client.util.ChangeMonitor;
import com.puzzlebazar.shared.action.EditUser;
import com.puzzlebazar.shared.action.NoResult;
import com.puzzlebazar.shared.model.User;
import com.puzzlebazar.shared.util.AvailableLocales;

/**
 * This is the presenter of the general tab in the user settings page.
 * 
 * @author Philippe Beaudoin
 */
public class UserSettingsGeneralPresenter 
extends PresenterImpl<UserSettingsGeneralPresenter.MyView, UserSettingsGeneralPresenter.MyProxy>
implements MonitorHandler  {

  public interface MyView extends View {
    HasText getEmail();
    HasText getNickname();
    HasText getRealName();
    ListBox getLanguage();
    void setApplyEnabled(boolean enabled);
    HasClickHandlers getApply();
    HasClickHandlers getCancel();
    void addLanguage(String languageName);
  }

  public interface MyProxy extends TabContentProxy<UserSettingsGeneralPresenter>, Place {}

  private final DispatchAsync dispatcher;
  private final PlaceManager placeManager;
  private final AvailableLocales availableLocales;
  private final CurrentUser currentUser;
  private final ChangeMonitor changeMonitor;
  private final Translations translations;

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
      final Translations translations ) {
    super(eventBus, view, proxy );
    this.dispatcher = dispatcher;
    this.placeManager = placeManager;
    this.availableLocales = availableLocales;
    this.currentUser = currentUser;
    this.changeMonitor = changeMonitor;
    this.translations = translations;
    changeMonitor.setHandler( this );
  }

  @Override
  protected void onBind() {
    super.onBind();
    registerHandler( view.getApply().addClickHandler(
        new ClickHandler() {          
          @Override
          public void onClick(ClickEvent event) {
            apply();
          }
        } ) );
    registerHandler( view.getCancel().addClickHandler(
        new ClickHandler() {          
          @Override
          public void onClick(ClickEvent event) {
            cancel();
          }
        } ) );
    
    for( int i=0; i < availableLocales.getNbLocales(); ++i ) {
      view.addLanguage( availableLocales.getLocale(i).getName() );
    }
  }

  @Override
  public void onReveal() {
    super.onReveal();
    User user = currentUser.getUser(); 
    int localeIndex = availableLocales.findLocaleIndex(user.getLocale());
    if( localeIndex < 0 )
      localeIndex = availableLocales.getDefaultLocaleIndex();
    view.getEmail().setText( user.getEmail() );
    view.getNickname().setText( user.getNickname() );
    view.getRealName().setText( user.getRealName() );
    view.getLanguage().setSelectedIndex( localeIndex );
    view.setApplyEnabled(false);
    changeMonitor.bind();
    changeMonitor.monitorWidget( view.getNickname() );
    changeMonitor.monitorWidget( view.getRealName() );
    changeMonitor.monitorWidget( view.getLanguage() );
  }
  
  @Override
  public void onHide() {
    super.onHide();
    changeMonitor.unbind();
  }

  @Override
  protected void setContentInParent() {
    SetContentEvent.fire(eventBus, UserSettingsTabProxy.TYPE_SetTabContent, this);
  }

  @Override
  public void changeDetected() {
    view.setApplyEnabled(true);
  }

  @Override
  public void changeReverted() {
    view.setApplyEnabled(false);
  }
  
  private void apply() {
    int localeIndex = view.getLanguage().getSelectedIndex();
    User user = currentUser.getUser();
    user.setNickname( view.getNickname().getText() );
    user.setRealname( view.getRealName().getText() );
    user.setLocale( availableLocales.getLocale(localeIndex).getLocale() );
    dispatcher.execute( new EditUser(user), new ActionCallback<NoResult>(translations.operationFailedRetry()){
      @Override
      public void onSuccess(NoResult result) {
        DisplayShortMessageEvent.fireClearMessage(eventBus);
        changeMonitor.reset();
        currentUser.fetchUser();
      }
    }  );
  }

  private void cancel() {
    changeMonitor.reset();
    placeManager.navigateBack();
  }

}
