package com.philbeaudoin.platform.mvp.client.proxy;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.Presenter;
import com.philbeaudoin.platform.mvp.client.TabContainerPresenter;


public class TabContainerProxyImpl<P extends TabContainerPresenter> extends
ProxyImpl<P> implements TabContainerProxy<P> {

  /**
   * The {@link Type} of the event used by {@link Presenter} classes that want
   * to be revealed within this container.
   */
  private final Type<RevealContentHandler<?>> revealTabContentEventType;

  /**
   * Creates a proxy class for a presenter that can contain tabs.
   * 
   * @param eventBus The event bus.
   * @param presenter A provider for the {@link Presenter} of which this class is a proxy. 
   * @param revealTabContentEventType The {@link Type} of the event used by 
   *        {@link Presenter} classes that want to be revealed within this container.
   */
  public TabContainerProxyImpl(final EventBus eventBus, 
      final CallbackProvider<P> presenter,
      final Type<RevealContentHandler<?>> revealTabContentEventType ) {
    super(eventBus, presenter);
    this.revealTabContentEventType = revealTabContentEventType;
  }

  @Override
  protected void onBind() {
    super.onBind();
    registerHandler( eventBus.addHandler( revealTabContentEventType, new RevealContentHandler<P>(this){
      @Override
      public void onRevealContent(final P presenter, final RevealContentEvent revealContentEvent) {
        presenter.setTabContent( revealContentEvent.getContent() );
        presenter.reveal();
      }
    } ) );
  }
}
