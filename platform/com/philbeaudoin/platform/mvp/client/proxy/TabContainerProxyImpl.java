package com.philbeaudoin.platform.mvp.client.proxy;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.Presenter;
import com.philbeaudoin.platform.mvp.client.TabContainerPresenter;


public class TabContainerProxyImpl<P extends TabContainerPresenter> extends
ProxyImpl<P> implements TabContainerProxy<P> {

  /**
   * The {@link Type} of the event used by {@link Presenter} classes that want
   * to be revealed within this container.
   */
  protected Type<RevealContentHandler<?>> revealTabContentEventType;

  /**
   * Creates a proxy class for a presenter that can contain tabs.
   */
  public TabContainerProxyImpl() {}

  @Inject
  protected void bind(EventBus eventBus) {
    eventBus.addHandler( revealTabContentEventType, new RevealContentHandler<P>(failureHandler, this){
      @Override
      public void onRevealContent(final P presenter, final RevealContentEvent revealContentEvent) {
        presenter.setTabContent( revealContentEvent.getContent() );
        presenter.reveal();
      }
    } );
  }
}
