package com.philbeaudoin.gwt.presenter.client.proxy;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.philbeaudoin.gwt.presenter.client.EventBus;

public abstract class DefaultPlaceManager implements PlaceManager, ValueChangeHandler<String> {

  private final EventBus eventBus;

  private final TokenFormatter tokenFormatter;

  public DefaultPlaceManager( EventBus eventBus, TokenFormatter tokenFormatter ) {
    this.eventBus = eventBus;
    this.tokenFormatter = tokenFormatter;

    // Register ourselves with the History API.
    History.addValueChangeHandler( this );
  }

  private void updateHistory( Place place ) {
    updateHistory( place.createRequest() );
  }

  // Updates History if it has changed, without firing another
  // 'ValueChangeEvent'.
  private void updateHistory( PlaceRequest request ) {
    try {
      String requestToken = tokenFormatter.toHistoryToken( request );
      String historyToken = History.getToken();
      if ( historyToken == null || !historyToken.equals( requestToken ) )
        History.newItem( requestToken, false );
    } catch ( TokenFormatException e ) {
      // Do nothing.
    }
  }

  /**
   * Fires a {@link PlaceRequestEvent} with the current history token, if
   * present. If no history token is set, <code>false</code> is returned.
   *
   * @return <code>true</code>
   */
  @Override
  public boolean fireCurrentPlace() {
    String current = History.getToken();
    if ( current != null && current.trim().length() > 0 ) {
      History.fireCurrentHistoryState();
      return true;
    }
    return false;
  }


  @Override
  public void onPlaceRevealed( Place place ) {
    updateHistory( place );
  }

  @Override
  public void onPlaceChanged( Place place ) {
    try {
      if ( place.matchesRequest( tokenFormatter.toPlaceRequest( History.getToken() ) ) ) {
        // Only update if the change comes from a place that matches
        // the current location.
        updateHistory( place );
      }
    } catch ( TokenFormatException e ) {
      // Do nothing...
    }
  }

  /**
   * Handles change events from {@link History}.
   */
  @Override
  public void onValueChange( ValueChangeEvent<String> event ) {
    try {
      PlaceRequestEvent.fire( eventBus, tokenFormatter.toPlaceRequest( event.getValue() ) );
    } catch ( TokenFormatException e ) {
      e.printStackTrace();
    }
  }

}
