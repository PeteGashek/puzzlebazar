package com.philbeaudoin.gwt.presenter.client.proxy;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.Presenter;
import com.philbeaudoin.gwt.presenter.client.TabContainerPresenter;


public class TabContainerProxyImpl<P extends TabContainerPresenter> extends
    PresenterProxyImpl<P> implements TabContainerProxy {

  /**
   * The {@link Type} of the event used by {@link Presenter} classes that want
   * to be set within this container.
   */
  private final Type<SetContentHandler<?>> setTabContentEventType;
  
  /**
   * Creates a proxy class for a presenter that can contain tabs.
   * 
   * @param eventBus The event bus.
   * @param presenter A provider for the {@link Presenter} of which this class is a proxy. 
   * @param setTabContentEventType The {@link Type} of the event used by 
   *        {@link Presenter} classes that want to be set within this container.
   */
  public TabContainerProxyImpl(final EventBus eventBus, 
      final CallbackProvider<P> presenter,
      final Type<SetContentHandler<?>> setTabContentEventType ) {
    super(eventBus, presenter);
    this.setTabContentEventType = setTabContentEventType;
  }

  @Override
  public void onBind() {
    super.onBind();
    registerHandler( eventBus.addHandler( setTabContentEventType, new SetContentHandler<P>(this){
      @Override
      public void onSetContent(final P presenter, final SetContentEvent setContentEvent) {
            presenter.setTabContent( setContentEvent.getContent() );
            presenter.reveal();
      }
    } ) );
  }
}
