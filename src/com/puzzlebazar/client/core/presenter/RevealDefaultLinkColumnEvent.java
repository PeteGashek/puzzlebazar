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
import com.gwtplatform.mvp.client.HasEventBus;


public class RevealDefaultLinkColumnEvent extends GwtEvent<RevealDefaultLinkColumnHandler> {

  private static final Type<RevealDefaultLinkColumnHandler> TYPE = new Type<RevealDefaultLinkColumnHandler>();
  
  public static Type<RevealDefaultLinkColumnHandler> getType() {
      return TYPE;
  }

  public static void fire( HasEventBus source ) {
    source.fireEvent( new RevealDefaultLinkColumnEvent() );
  }
  
  public RevealDefaultLinkColumnEvent() {
  }

  @Override
  protected void dispatch( RevealDefaultLinkColumnHandler handler ) {
    handler.onRevealDefaultLinkColumn( this );
  }

  @Override
  public Type<RevealDefaultLinkColumnHandler> getAssociatedType() {
    return getType();
  }

}
