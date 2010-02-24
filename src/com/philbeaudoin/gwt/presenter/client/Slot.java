package com.philbeaudoin.gwt.presenter.client;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Provider;

/**
 * A slot is a specific location within a {@link PresenterContainer} into which a
 * sub-presenter can display itself.
 * 
 * @author beaudoin
 */
public abstract class Slot<P extends Presenter>  {

  private Type<SetSlotContentHandler> type = new Type<SetSlotContentHandler>();

  class SetContentEvent extends SetSlotContentEvent {
    public SetContentEvent(Presenter presenter) {
      super(presenter);
    }
    @Override
    public Type<SetSlotContentHandler> getAssociatedType() {
      return type;
    }    
  }

  protected Presenter activePresenter = null;
  private final Provider<P> presenter;
  
  protected Slot(Provider<P> presenter) {
    this.presenter = presenter;
  }
  
  public P getPresenter() {
    return presenter.get();
  }
  
  public Type<SetSlotContentHandler> getSetContentEventType() {
    return type;
  }
  
  public void fireSetContentEvent( EventBus eventBus, Presenter presenter ) {
    eventBus.fireEvent( new SetContentEvent(presenter) );
  }

  
  public void setActivePresenter(Presenter presenter) {
    if( activePresenter  != presenter ) {
      activePresenter = presenter;
      displayContent();
    }
  }
  
  protected abstract void displayContent();
  
}
