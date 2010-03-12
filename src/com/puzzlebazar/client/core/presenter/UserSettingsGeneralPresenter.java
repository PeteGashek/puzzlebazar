package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.View;
import com.philbeaudoin.gwt.presenter.client.PresenterImpl;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.Place;
import com.philbeaudoin.gwt.presenter.client.proxy.PlaceManager;
import com.philbeaudoin.gwt.presenter.client.proxy.SetContentEvent;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContentProxy;
import com.puzzlebazar.client.CurrentUser;
import com.puzzlebazar.client.core.proxy.UserSettingsTabProxy;
import com.puzzlebazar.client.util.ChangeHandler;
import com.puzzlebazar.client.util.ChangeMonitor;
import com.puzzlebazar.shared.model.User;

/**
 * This is the presenter of the general tab in the user settings page.
 * 
 * @author Philippe Beaudoin
 */
public class UserSettingsGeneralPresenter 
extends PresenterImpl<UserSettingsGeneralPresenter.MyView, UserSettingsGeneralPresenter.MyProxy>
implements ChangeHandler {

  public interface MyView extends View {
    HasText getEmail();
    HasText getNickname();
    HasText getRealName();
    void setApplyEnabled(boolean enabled);
    HasClickHandlers getApply();
    HasClickHandlers getCancel();
  }

  public interface MyProxy extends TabContentProxy<UserSettingsGeneralPresenter>, Place {}

  private final PlaceManager placeManager;
  private final CurrentUser currentUser;
  private final ChangeMonitor changeMonitor;

  @Inject
  public UserSettingsGeneralPresenter(
      final EventBus eventBus,
      final PlaceManager placeManager,
      final MyView view, 
      final MyProxy proxy,
      final CurrentUser currentUser,
      final ChangeMonitor changeMonitor ) {
    super(eventBus, view, proxy );
    this.placeManager = placeManager;
    this.currentUser = currentUser;
    this.changeMonitor = changeMonitor;
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
  }

  @Override
  public void onReveal() {
    super.onReveal();
    User user = currentUser.getUser(); 
    view.getEmail().setText( user.getEmail() );
    view.getNickname().setText( user.getNickname() );
    view.getRealName().setText( user.getRealName() );
    view.setApplyEnabled(false);
    changeMonitor.bind();
    changeMonitor.monitorWidget( view.getNickname() );
    changeMonitor.monitorWidget( view.getRealName() );
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
    // TODO Commit changes
    placeManager.navigateBack();
  }

  private void cancel() {
    placeManager.setOnLeaveConfirmation(null);
    placeManager.navigateBack();
  }
}
