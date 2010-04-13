package com.puzzlebazar.shared.model;

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

import java.util.Date;

/**
 * Any object that has an author, and can be created, edited and published.
 * 
 * @author Philippe Beaudoin
 */
public interface EditableObject {

  /**
   * Access the {@link UserImpl} who authored this object.
   * 
   * @return The author, a {@link UserImpl} or {@code null} if unknown.
   */
  public abstract User getAuthor();

  /**
   * Access the {@link Date} at which the object was originally created.
   * 
   * @return The {@link Date} at which the object was created.
   */
  public abstract Date getCreationDate();

  /**
   * Access the {@link Date} at which the object was last edited.
   * 
   * @return The {@link Date} at which the object was last edited, or {@code null} if it was
   *         not edited after being created.
   */
  public abstract Date getEditionDate();

  /**
   * Access the {@link Date} at which the object was last made public.
   * 
   * @return The {@link Date} at which the object was last made public, or {@code null} if it is not currently public.
   */
  public abstract Date getPublicationDate();

  /**
   * Verifies if the object was marked for deletion.
   * 
   * @return {@code true} if this object was marked for deletion, {@code false} otherwise.
   */
  public abstract boolean isDeleted();

  /**
   * Checks whether or not this object was rejected.
   * 
   * @return {@code true} if this object was rejected, {@code false} otherwise.
   */
  public abstract boolean wasRejected();

  /**
   * Checks whether or not the puzzle is currently public.
   * <p />
   * {@code isPublic()} must return {@code false} in all of the following situations:
   * <ul>
   * <li>{@link #isDeleted()} returns {@code true}</li>
   * <li>{@link #isValid()} returns {@code false}</li>
   * <li>{@link #isComplete()} returns {@code false}</li>
   * <li>{@link #wasRejected()} returns {@code true}</li>
   * </ul>
   * @return {@code true} if the puzzle is currently public, {@code false} otherwise.
   */
  public abstract boolean isPublic();

}