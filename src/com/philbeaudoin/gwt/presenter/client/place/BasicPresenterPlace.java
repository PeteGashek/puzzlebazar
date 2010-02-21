package com.philbeaudoin.gwt.presenter.client.place;

import com.philbeaudoin.gwt.presenter.client.Presenter;


/**
 * A simple implementation of {@link PresenterPlace} for {@link PresenterWithPlace}s
 * that don't need to be prepared for display. These are mostly presenters which
 * don't have any initialisation parameters.
 *
 * @author David Peterson
 * @param <T>
 * The presenter class type.
 */
public abstract class BasicPresenterPlace<T extends Presenter> extends PresenterPlace<T> {
  private final T presenter;

  public BasicPresenterPlace( T presenter, PlaceManager placeManager ) {
    super(placeManager);
    this.presenter = presenter;
  }

  @Override
  public T getPresenter() {
    return presenter;
  }

}
