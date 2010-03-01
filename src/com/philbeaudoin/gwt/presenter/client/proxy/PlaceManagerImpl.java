package com.philbeaudoin.gwt.presenter.client.proxy;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.philbeaudoin.gwt.presenter.client.EventBus;

public abstract class PlaceManagerImpl implements PlaceManager, ValueChangeHandler<String>, ClosingHandler {

  private final EventBus eventBus;
  private final TokenFormatter tokenFormatter;

  private String onLeaveQuestion = null;
  private HandlerRegistration windowClosingHandlerRegistration = null;
  private String currentHistoryToken = "";
  private String currentLocation = "";


  public PlaceManagerImpl( EventBus eventBus, TokenFormatter tokenFormatter ) {
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
      if ( historyToken == null || !historyToken.equals( requestToken ) ) {
        History.newItem( requestToken, false );
      }
      currentHistoryToken = requestToken;
      currentLocation = Window.Location.getHref();
    } catch ( TokenFormatException e ) {
      // Do nothing.
    }
  }

  /**
   * Reveals the place corresponding to the current value of the history token
   * in the URL bar. This will result in a {@link PlaceRequestEvent} being fired.
   * No check is made to see if the history token is valid. 
   * Your application should override this so that a default place is revealed
   * when no history token is set.
   *
   * @return <code>true</code> if he history token is set, <code>false</code> otherwise.
   */
  @Override
  public boolean revealCurrentPlace() {
    String current = History.getToken();
    if ( current != null && current.trim().length() > 0 ) {
      History.fireCurrentHistoryState();
      return true;
    }
    return false;
  }

  /**
   * Called whenever the current place has changed in a way that requires history parameters to be 
   * modified.
   *
   * @param place The place that has changed.
   */
  public final void onPlaceChanged( Place place ) {
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
   * Called whenever a new place has been revealed. Don't call this directly.
   *
   * @param place The place that has been revealed.
   */
  public final void onPlaceRevealed( Place place ) {
    updateHistory( place );
  }

  /**
   * Handles change events from {@link History}.
   */
  @Override
  public final void onValueChange( ValueChangeEvent<String> event ) {
    try {
      PlaceRequestEvent.fire( eventBus, tokenFormatter.toPlaceRequest( event.getValue() ) );
    } catch ( TokenFormatException e ) {
      e.printStackTrace();
    }
  }

  @Override
  public final void setOnLeaveConfirmation( String question ) {
    if( question == null && onLeaveQuestion  == null ) return;
    if( question != null && onLeaveQuestion == null )
      windowClosingHandlerRegistration = Window.addWindowClosingHandler(this);
    if( question == null && onLeaveQuestion != null )
      windowClosingHandlerRegistration.removeHandler();
    onLeaveQuestion = question;
  }

  @Override
  public final void onWindowClosing(Window.ClosingEvent event) {
    event.setMessage(onLeaveQuestion);
    DeferredCommand.addCommand( new Command() {
      @Override
      public void execute() {
        Window.Location.replace(currentLocation);
      }
    });
  }

  @Override
  public final boolean confirmLeaveState() {
    if( onLeaveQuestion == null ) return true;
    boolean confirmed =  Window.confirm( onLeaveQuestion );
    if( confirmed ) {
      // User has confirmed, don't ask any more question.
      setOnLeaveConfirmation( null );
    } else {
      History.newItem(currentHistoryToken, false);
    }
    return confirmed;
  }
}
