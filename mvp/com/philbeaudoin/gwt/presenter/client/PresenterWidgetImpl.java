package com.philbeaudoin.gwt.presenter.client;

import com.google.gwt.user.client.ui.Widget;

public class PresenterWidgetImpl<D extends Display>
    extends HandlerContainerImpl implements PresenterWidget {

  /**
   * The {@link EventBus} for the application.
   */
  protected final EventBus eventBus;
  
  /**
   * The display for the presenter.
   */
  protected final D display;
  

  public PresenterWidgetImpl(
      EventBus eventBus, 
      D display) {
    super();
    this.display = display;
    this.eventBus = eventBus;
  }

  @Override
  public final D getDisplay() {
    return display;
  }

  @Override
  public void onReveal() {} 

  @Override
  public void onHide() {}

  @Override
  public Widget getWidget() {
    return getDisplay().asWidget();
  }

}