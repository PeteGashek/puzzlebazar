package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.shared.GwtEvent;
import com.philbeaudoin.platform.mvp.client.EventBus;

/**
 * This event is sent to the {@link EventBus} whenever the system
 * needs to display a message on the screen. It should be processed
 * by presenters that are responsible for displaying the message.
 * <p/>
 * If the method {@link #isAlreadyDisplayed()} returns {@code true},
 * it means a presenter already handled the request and it probably
 * shouldn't be displayed again. 
 * 
 * @author Philippe Beaudoin
 */
public class DisplayShortMessageEvent extends GwtEvent<DisplayShortMessageHandler> {

  public static int LEVEL_MESSAGE = 0;
  public static int LEVEL_WARNING = 1;
  public static int LEVEL_ERROR = 2;
  public static int LEVEL_SEVERE = 3;
  
  public static int DURATION_PERMANENT = 0;
  public static int DURATION_SHORT = 5000;
  public static int DURATION_NORMAL = 10000;
  public static int DURATION_LONG = 20000;
  
  private static final Type<DisplayShortMessageHandler> TYPE = new Type<DisplayShortMessageHandler>();
  
  public static Type<DisplayShortMessageHandler> getType() {
      return TYPE;
  }

  /**
   * Fires a new event to display short messages.
   * 
   * @param message The message to display. Pass {@code null} to clear displayed messages.
   * @param dismissable {@code true} if the message should be dismissable 
   *                    by the user (i.e. a "close" button should be available.)
   * @param level The level of the message should be one of: {@code LEVEL_MESSAGE,
   * LEVEL_WARNING, LEVEL_ERROR, LEVEL_SEVERE}.
   * @param duration The duration of the message, in milliseconds. Pass 0 for a
   * permanent message. Should preferably be one of: {@code DURATION_PERMANENT,
   * DURATION_SHORT, DURATION_NORMAL, DURATION_LONG}.
   */
  public static void fire( EventBus eventBus, String message, boolean dismissable, int level, int duration ) {
      eventBus.fireEvent( new DisplayShortMessageEvent( message, dismissable, level, duration ) );
  }


  /**
   * Fires a new event to display short messages. The message will be
   * dismissable, it has a level of {@code LEVEL_MESSAGE} and a normal
   * duration.
   * 
   * @param message The message to display.
   */
  public static void fire( EventBus eventBus, String message ) {
    fire( eventBus, message, true, LEVEL_MESSAGE, DURATION_NORMAL );
  }

  private final String message;
  private final boolean dismissable;
  private final int level;
  private final int duration;
  
  private boolean alreadyDisplayed = false;
  
  /**
   * Creates a new event to display short messages.
   * 
   * @param message The message to display.
   * @param dismissable {@code true} if the message should be dismissable 
   *                    by the user (i.e. a "close" button should be available.)
   * @param level The level of the message must be one of: {@code LEVEL_MESSAGE,
   * LEVEL_WARNING, LEVEL_ERROR, LEVEL_SEVERE}.
   * @param duration The duration of the message, in milliseconds. Pass {@code DURATION_PERMANENT} for a
   * permanent message. Should preferably be one of: {@code DURATION_PERMANENT,
   * DURATION_SHORT, DURATION_NORMAL, DURATION_LONG}.
   */
  public DisplayShortMessageEvent( String message, boolean dismissable, int level, int duration ) {
    this.message = message;
    this.dismissable = dismissable;
    this.level = level;
    this.duration = duration;
  }

  /**
   * @return The message to display. Can be {@code null}, in which case displayed messages should be cleared.
   */
  public String getMessage() {
    return message;
  }
 
  /**
   * Checks if the message is dismissable. A dismissable message should be
   * displayed with a UI mechanism that lets the user close the message if
   * desired. (i.e. a "close" button)
   * 
   * @return {@code true} if the message is dismissable, {@code false} otherwise.
   */
  public boolean isDismissable() {
    return dismissable;
  }
 
  /**
   * Checks the level of the message. The UI can be adjusted (i.e. the color changed)
   * depending on this level. Should be one of: {@code LEVEL_MESSAGE,
   * LEVEL_WARNING, LEVEL_ERROR, LEVEL_SEVERE}
   * 
   * @return The level of the message.
   */
  public int getLevel() {
    return level;
  }
  
  /**
   * Access the desired duration of the message. The UI should leave the message visible
   * at least for that amount of time. If the value is {@code DURATION_PERMANENT}, the
   * message should be displayed forever, or until dismissed if available.
   * 
   * @return The duration, in milliseconds.
   */
  public int getDuration() {
    return duration;
  }
  
  /**
   * Checks if the message has already been displayed. If {@code true}, then it probably
   * shouldn't be added again to another display.
   * 
   * @return {@code true} if the message has already been displayed, {@code false} otherwise.
   */
  public boolean isAlreadyDisplayed() {
    return alreadyDisplayed;
  }
  
  /**
   * Call this method whenever you display the message within a UI, so that no other UI displays it.
   * Don't call it if you're simply logging the message.
   * 
   * @see #isAlreadyDisplayed()
   */
  public void setAlreadyDisplayed() {
    // Null messages should clear every UI, so don't set the flag.
    if( message != null )
      alreadyDisplayed = true;
  }

  @Override
  protected void dispatch( DisplayShortMessageHandler handler ) {
    handler.onDisplayShortMessage( this );
  }

  @Override
  public Type<DisplayShortMessageHandler> getAssociatedType() {
    return getType();
  }
}
