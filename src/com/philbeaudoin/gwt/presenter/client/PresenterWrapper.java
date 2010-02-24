package com.philbeaudoin.gwt.presenter.client;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Provider;

/**
 * A light-weight singleton wrapper that lets presenter attach to their own
 * {@link PresenterChangedEvent} and {@link PresenterRevealedEvent}.
 * The relationship between a presenter and wrapper is two-way.
 * 
 * @author beaudoin
 */
public class PresenterWrapper<P extends Presenter> {

  private final Type<PresenterChangedHandler> presenterChangedEventType = new Type<PresenterChangedHandler>();
  private final Type<PresenterRevealedHandler> presenterRevealedEventType = new Type<PresenterRevealedHandler>();
  protected final EventBus eventBus;
  private final Provider<P> presenter;
  
  public class ChangedEvent extends PresenterChangedEvent {
    public ChangedEvent(P presenter) {
      super(presenter);
    }
    public Type<PresenterChangedHandler> getAssociatedType() {
      return presenterChangedEventType;
    }
  }

  public class RevealedEvent extends PresenterRevealedEvent {
    public RevealedEvent(P presenter) {
      super(presenter);
    }
    public Type<PresenterRevealedHandler> getAssociatedType() {
      return presenterRevealedEventType;
    }
  }

  public PresenterWrapper( EventBus eventBus, Provider<P> presenter ) {
    this.eventBus = eventBus;
    this.presenter = presenter;
  }
  
  public P getPresenter() {
    return presenter.get();
  }

  public Type<PresenterChangedHandler> getPresenterChangedEventType() {
    return presenterChangedEventType;
  }

  public Type<PresenterRevealedHandler> getPresenterRevealedEventType() {
    return presenterRevealedEventType;
  }
  
  public void firePresenterChangedEvent() {
    eventBus.fireEvent( new ChangedEvent(getPresenter()) );
  }

  public void firePresenterRevealedEvent() {
    eventBus.fireEvent( new RevealedEvent(getPresenter()) );
  }

}
