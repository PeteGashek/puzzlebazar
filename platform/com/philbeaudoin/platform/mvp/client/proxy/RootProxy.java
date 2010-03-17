package com.philbeaudoin.platform.mvp.client.proxy;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.HandlerContainerImpl;
import com.philbeaudoin.platform.mvp.client.Presenter;

/**
 * This is the proxy for the top-level of the application. It is
 * not connected to any presenter, instead it sets content within
 * GWT's {@link RootPanel} and {@link RootLayoutPanel}.
 * <p />
 * Fire a {@link RevealContentEvent} with type {@link #TYPE_RevealContent}
 * or {@link #TYPE_RevealLayoutContent} to set your presenter at the
 * top level. The choice depends on whether your presenter works
 * as a {@link com.google.gwt.user.client.ui.Panel} or as a
 * {@link com.google.gwt.user.client.ui.LayoutPanel}.
 * 
 * @author Philippe Beaudoin
 */
public class RootProxy extends HandlerContainerImpl {

  private final EventBus eventBus;

  private Presenter activePresenter = null;

  
  /**
   * Creates a proxy class for a presenter that can contain tabs.
   * 
   * @param eventBus The event bus.
   */
  @Inject
  public RootProxy(
      final EventBus eventBus ) {
    super();
    this.eventBus = eventBus;
  }

  @Override
  protected void onBind() {
    super.onBind();

    registerHandler( eventBus.addHandler( RevealRootContentEvent.getType(), new RevealRootContentHandler(){
      @Override
      public void onRevealContent(final RevealRootContentEvent revealContentEvent) {
        if( activePresenter != null ) {
          activePresenter.getWidget().removeFromParent();
          activePresenter.notifyHide();
        }
        activePresenter = revealContentEvent.getContent();
        RootPanel.get().add(activePresenter.getWidget());
        // Careful, no need to reveal activePresenter, it's already revealed.
      }
    } ) );    

    registerHandler( eventBus.addHandler( RevealRootLayoutContentEvent.getType(), new RevealRootLayoutContentHandler(){
      @Override
      public void onRevealContent(final RevealRootLayoutContentEvent revealContentEvent) {
        if( activePresenter != null ) {
          activePresenter.getWidget().removeFromParent();
          activePresenter.notifyHide();
        }
        activePresenter = revealContentEvent.getContent();
        RootLayoutPanel.get().add(activePresenter.getWidget());
        // Careful, no need to reveal activePresenter, it's already revealed.
      }
    } ) );  
    
  }
}