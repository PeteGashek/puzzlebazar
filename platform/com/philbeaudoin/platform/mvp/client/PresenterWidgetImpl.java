package com.philbeaudoin.platform.mvp.client;

import com.google.gwt.user.client.ui.Widget;

public class PresenterWidgetImpl<V extends View>
    extends HandlerContainerImpl implements PresenterWidget {

  /**
   * The {@link EventBus} for the application.
   */
  protected final EventBus eventBus;
  
  protected boolean visible = false;
  
  /**
   * The view for the presenter.
   */
  protected final V view;
  

  public PresenterWidgetImpl(
      EventBus eventBus, 
      V view) {
    super();
    this.view = view;
    this.eventBus = eventBus;
  }

  @Override
  public final V getView() {
    return view;
  }

  @Override
  public final boolean isVisible() {
    return visible;
  }
  
  
  @Override
  public void onReveal() {
    visible = true;
  } 

  @Override
  public void onHide() {
    visible = false;    
  }

  @Override
  public Widget getWidget() {
    return getView().asWidget();
  }

}