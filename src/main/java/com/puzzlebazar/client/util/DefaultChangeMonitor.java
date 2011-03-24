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

package com.puzzlebazar.client.util;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.puzzlebazar.client.resources.Translations;

/**
 * @author Philippe Beaudoin
 */
public final class DefaultChangeMonitor extends ChangeMonitorImpl {

  private final PlaceManager placeManager;
  private final Translations translations;

  @Inject
  public DefaultChangeMonitor(
    final PlaceManager placeManager,
    final Translations translations) {
    super();
    this.placeManager = placeManager;
    this.translations = translations;
  }

  @Override
  public void changeDetected() {
    placeManager.setOnLeaveConfirmation(
        translations.changeDetected());
    super.changeDetected();
  }

  @Override
  public void changeReverted() {
    placeManager.setOnLeaveConfirmation(null);
    super.changeReverted();
  }
}