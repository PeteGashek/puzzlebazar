package com.philbeaudoin.gwt.presenter.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Provider;

/**
 * A light-weight singleton wrapper that lets presenter attach to their own
 * {@link PresenterChangedEvent} and {@link PresenterRevealedEvent}.
 * The relationship between a presenter and wrapper is two-way.
 * 
 * @author beaudoin
 */
public class PresenterWrapper<P extends Presenter> extends HandlerContainer {

  private final Type<PresenterChangedHandler> presenterChangedEventType = new Type<PresenterChangedHandler>();
  private final Type<PresenterRevealedHandler> presenterRevealedEventType = new Type<PresenterRevealedHandler>();
  protected final EventBus eventBus;
  private final Provider<P> presenter;
  private final List<Slot<?>> slots = new ArrayList<Slot<?>>();
  
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

  public PresenterWrapper( EventBus eventBus, Provider<P> presenter, Slot<?>... slots ) {
    this.eventBus = eventBus;
    this.presenter = presenter;
    for( Slot<?> slot : slots )
      this.slots.add( slot );
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

  @Override
  protected void onBind() {
    for( Slot<?> slot : slots )
      addSlot( slot );
  }

  @Override
  protected void onUnbind() {
  }

  /**
   * Adds a slot to this {@link PresenterWrapper}. The slot will listen for its
   * specific event and will reveal the Presenter whenever needed.
   *
   * @param slot The slot.
   */
  protected void addSlot( final Slot<?> slot ) {
    registerHandler( eventBus.addHandler( slot.getSetContentEventType(), 
        new SetSlotContentHandler() {
          @Override
          public void onSetSlotContent(SetSlotContentEvent event) {
            slot.setActivePresenter( event.getPresenter() );
            getPresenter().revealDisplay();
          }
    } ) );
    
  }
}
