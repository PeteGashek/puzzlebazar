/**
 * Copyright 2010 Philippe Beaudoin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.puzzlebazar.client.core.presenter;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * This event is sent to the {@link com.gwtplatform.mvp.client.EventBus}
 * whenever the system needs to display a message on the screen. It should
 * be processed by presenters that are responsible for displaying the message.
 * <p/>
 * If the method {@link #isAlreadyDisplayed()} returns {@code true},
 * it means a presenter already handled the request and it probably
 * shouldn't be displayed again.
 *
 * @author Philippe Beaudoin
 */
public class DisplayShortMessageEvent extends GwtEvent<DisplayShortMessageHandler> {

  public static final int LEVEL_MESSAGE = 0;
  public static final int LEVEL_WARNING = 1;
  public static final int LEVEL_ERROR = 2;
  public static final int LEVEL_SEVERE = 3;

  public static final int DURATION_PERMANENT = 0;
  public static final int DURATION_SHORT = 5000;
  public static final int DURATION_NORMAL = 10000;
  public static final int DURATION_LONG = 20000;

  private static final Type<DisplayShortMessageHandler> TYPE = new Type<DisplayShortMessageHandler>();

  public static Type<DisplayShortMessageHandler> getType() {
      return TYPE;
  }

  /**
   * Fires a new event to display short messages.
   *
   * @param source The source of this event (See {@link HasEventBus}).
   * @param message Any {@link Widget} containing the message to display. Pass {@code null} to clear displayed messages.
   * @param dismissable {@code true} if the message should be dismissable
   *                    by the user (i.e. a "close" button should be available.)
   * @param level The level of the message should be one of: {@code LEVEL_MESSAGE,
   * LEVEL_WARNING, LEVEL_ERROR, LEVEL_SEVERE}.
   * @param duration The duration of the message, in milliseconds. Pass 0 for a
   * permanent message. Should preferably be one of: {@code DURATION_PERMANENT,
   * DURATION_SHORT, DURATION_NORMAL, DURATION_LONG}.
   */
  public static void fire(HasHandlers source, Widget message, boolean dismissable, int level, int duration) {
    source.fireEvent(new DisplayShortMessageEvent(message, dismissable, level, duration));
  }

  /**
   * Fires a new event to display short messages. The message will be
   * dismissable, it has a level of {@code LEVEL_MESSAGE} and a normal
   * duration.
   *
   * @param source The source of this event (See {@link HasEventBus}).
   * @param message Any {@link Widget} containing the message to display.
   */
  public static void fireMessage(HasHandlers source, Widget message) {
    fire(source, message, true, LEVEL_MESSAGE, DURATION_NORMAL);
  }

  /**
   * Fires a new event to clear all displayed short messages.
   *
   * @param source The source of this event (See {@link HasEventBus}).
   */
  public static void fireClearMessage(HasHandlers source) {
    fire(source, null, false, LEVEL_MESSAGE, DURATION_PERMANENT);
  }

  /**
   * Fires a new event to display short error. The message will be
   * dismissable, it has a level of {@code LEVEL_ERROR} and a permanent
   * duration.
   *
   * @param source The source of this event (See {@link HasEventBus}).
   * @param message Any {@link Widget} containing the message to display.
   */
  public static void fireError(HasHandlers source, Widget message) {
    fire(source, message, true, LEVEL_ERROR, DURATION_PERMANENT);
  }

  /**
   * Fires a new event to display short messages. The message will be
   * dismissable, it has a level of {@code LEVEL_MESSAGE} and a normal
   * duration.
   *
   * @param source The source of this event (See {@link HasEventBus}).
   * @param message The message to display, can contain HTML markup.
   */
  public static void fireMessage(HasHandlers source, String message) {
    fire(source, new HTML(message), true, LEVEL_MESSAGE, DURATION_NORMAL);
  }

  /**
   * Fires a new event to display short error. The message will be
   * dismissable, it has a level of {@code LEVEL_ERROR} and a permanent
   * duration.
   *
   * @param source The source of this event (See {@link HasEventBus}).
   * @param message The message to display, can contain HTML markup.
   */
  public static void fireError(HasHandlers source, String message) {
    fire(source, new HTML(message), true, LEVEL_ERROR, DURATION_PERMANENT);
  }

  private final Widget message;
  private final boolean dismissable;
  private final int level;
  private final int duration;

  private boolean alreadyDisplayed;

  /**
   * Creates a new event to display short messages.
   *
   * @param message Any {@link Widget} containing the message to display.
   * @param dismissable {@code true} if the message should be dismissable
   *                    by the user (i.e. a "close" button should be available.)
   * @param level The level of the message must be one of: {@code LEVEL_MESSAGE,
   * LEVEL_WARNING, LEVEL_ERROR, LEVEL_SEVERE}.
   * @param duration The duration of the message, in milliseconds. Pass {@code DURATION_PERMANENT} for a
   * permanent message. Should preferably be one of: {@code DURATION_PERMANENT,
   * DURATION_SHORT, DURATION_NORMAL, DURATION_LONG}.
   */
  public DisplayShortMessageEvent(Widget message, boolean dismissable, int level, int duration) {
    this.message = message;
    this.dismissable = dismissable;
    this.level = level;
    this.duration = duration;
  }

  /**
   * @return The {@link Widget} containing the message to display. Can be {@code null},
   *         in which case displayed messages should be cleared.
   */
  public Widget getMessage() {
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
    if (message != null) {
      alreadyDisplayed = true;
    }
  }

  @Override
  protected void dispatch(DisplayShortMessageHandler handler) {
    handler.onDisplayShortMessage(this);
  }

  @Override
  public Type<DisplayShortMessageHandler> getAssociatedType() {
    return getType();
  }

}
