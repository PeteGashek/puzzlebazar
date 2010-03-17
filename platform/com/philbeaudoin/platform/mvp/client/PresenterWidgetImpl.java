package com.philbeaudoin.platform.mvp.client;

import com.google.gwt.user.client.ui.Widget;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyPlace;

public abstract class PresenterWidgetImpl<V extends View>
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
  public final void reveal() {
    if( visible )
      return;
    visible = true;
    onReveal();
    revealChildren();
  } 

  @Override
  public final void notifyHide() {
    if( !visible )
      return;
    visible = false;    
    notifyHideChildren();
    onHide();
  }

  /**
   * <b>Important:</b> Make sure you call your superclass {@link #onReveal()}.
   * <p />
   * This method will be called whenever the presenter is revealed. Override
   * it to perform any action (such as refreshing content) that needs
   * to be done when the presenter is revealed.
   * <p />
   * This should never be called directly. Call 
   * {@link ProxyPlace#reveal()} instead.
   */
  protected void onReveal() {}

  /**
   * <b>Important:</b> Make sure you call your superclass {@link #onHide()}.
   * <p />
   * Override this method to perform any clean-up operations. For example,
   * objects created directly or indirectly during the call to
   * {@link #onReveal()} should be disposed of in this methods.
   */
  protected void onHide() {}
  
  /**
   * Subclasses <b>must</b> override this method and call {@link PresenterWidget#reveal()}
   * on all their children presenters.
   */
  protected void revealChildren() {}

  /**
   * Subclasses <b>must</b> override this method and call {@link PresenterWidget#notifyHide()}
   * on all their children presenters.
   */
  protected void notifyHideChildren() {}
  
  @Override
  public Widget getWidget() {
    return getView().asWidget();
  }

}