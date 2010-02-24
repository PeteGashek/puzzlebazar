package com.philbeaudoin.gwt.presenter.client;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Provider;

/**
 * A light-weight singleton wrapper that lets presenter define to their own
 * {@link PresenterChangedEvent} and {@link PresenterRevealedEvent}.
 * The relationship between a presenter and wrapper is two-way.
 * 
 * @author beaudoin
 */
public class BasicPresenterWrapper<P extends Presenter> implements PresenterWrapper {

  private final Type<PresenterChangedHandler> presenterChangedEventType = new Type<PresenterChangedHandler>();
  private final Type<PresenterRevealedHandler> presenterRevealedEventType = new Type<PresenterRevealedHandler>();
  protected final EventBus eventBus;
  private final Provider<P> presenter;
  
  public class ChangedEvent extends PresenterChangedEvent {
    public ChangedEvent(P presenter) {
      super(presenter);
    }
    @Override
    public Type<PresenterChangedHandler> getAssociatedType() {
      return presenterChangedEventType;
    }
  }

  public class RevealedEvent extends PresenterRevealedEvent {
    public RevealedEvent(P presenter) {
      super(presenter);
    }
    @Override
    public Type<PresenterRevealedHandler> getAssociatedType() {
      return presenterRevealedEventType;
    }
  }

  public BasicPresenterWrapper( EventBus eventBus, Provider<P> presenter ) {
    this.eventBus = eventBus;
    this.presenter = presenter;
  }
  
  @Override
  public P getPresenter() {
    return presenter.get();
  }

  @Override
  public Type<PresenterChangedHandler> getPresenterChangedEventType() {
    return presenterChangedEventType;
  }

  @Override
  public Type<PresenterRevealedHandler> getPresenterRevealedEventType() {
    return presenterRevealedEventType;
  }
  
  @Override
  public void firePresenterChangedEvent() {
    eventBus.fireEvent( new ChangedEvent(getPresenter()) );
  }

  @Override
  public void firePresenterRevealedEvent() {
    eventBus.fireEvent( new RevealedEvent(getPresenter()) );
  }

}
