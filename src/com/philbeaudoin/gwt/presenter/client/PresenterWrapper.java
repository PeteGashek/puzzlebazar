package com.philbeaudoin.gwt.presenter.client;

import com.google.gwt.event.shared.GwtEvent.Type;

public interface PresenterWrapper {

  public abstract Presenter getPresenter();

  public abstract Type<PresenterChangedHandler> getPresenterChangedEventType();

  public abstract Type<PresenterRevealedHandler> getPresenterRevealedEventType();

  public abstract void firePresenterChangedEvent();

  public abstract void firePresenterRevealedEvent();

}