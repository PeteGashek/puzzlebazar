package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.Display;
import com.philbeaudoin.gwt.presenter.client.PresenterImpl;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.proxy.Place;
import com.philbeaudoin.gwt.presenter.client.proxy.PlaceManager;
import com.philbeaudoin.gwt.presenter.client.proxy.SetContentEvent;
import com.philbeaudoin.gwt.presenter.client.proxy.TabContentProxy;
import com.puzzlebazar.client.CurrentUser;
import com.puzzlebazar.client.core.proxy.UserSettingsTabProxy;
import com.puzzlebazar.client.utils.ChangeHandler;
import com.puzzlebazar.client.utils.ChangeMonitor;
import com.puzzlebazar.shared.model.User;

/**
 * This is the presenter of the general tab in the user settings page.
 * 
 * @author Philippe Beaudoin
 */
public class UserSettingsGeneralPresenter 
extends PresenterImpl<UserSettingsGeneralPresenter.MyDisplay, UserSettingsGeneralPresenter.MyProxy>
implements ChangeHandler {

  public interface MyDisplay extends Display {
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
      final MyDisplay display, 
      final MyProxy proxy,
      final CurrentUser currentUser,
      final ChangeMonitor changeMonitor ) {
    super(eventBus, display, proxy );
    this.placeManager = placeManager;
    this.currentUser = currentUser;
    this.changeMonitor = changeMonitor;
    changeMonitor.setHandler( this );
  }

  @Override
  protected void onBind() {
    super.onBind();
    registerHandler( display.getApply().addClickHandler(
        new ClickHandler() {          
          @Override
          public void onClick(ClickEvent event) {
            apply();
          }
        } ) );
    registerHandler( display.getCancel().addClickHandler(
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
    display.getEmail().setText( user.getEmail() );
    display.getNickname().setText( user.getNickname() );
    display.getRealName().setText( user.getRealName() );
    display.setApplyEnabled(false);
    changeMonitor.bind();
    changeMonitor.monitorWidget( display.getNickname() );
    changeMonitor.monitorWidget( display.getRealName() );
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
    display.setApplyEnabled(true);
  }

  @Override
  public void changeReverted() {
    display.setApplyEnabled(false);
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
