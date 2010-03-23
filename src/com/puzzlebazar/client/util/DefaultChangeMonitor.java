/**
 * 
 */
package com.puzzlebazar.client.util;

import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.proxy.PlaceManager;
import com.puzzlebazar.client.resources.Translations;

public final class DefaultChangeMonitor extends ChangeMonitorImpl {
  
  private final PlaceManager placeManager;
  private final Translations translations;

  @Inject
  public DefaultChangeMonitor(
    final PlaceManager placeManager,
    final Translations translations ) {
    super();
    this.placeManager = placeManager;
    this.translations = translations;
  }
  
  @Override
  public void changeDetected() {
    placeManager.setOnLeaveConfirmation( 
        translations.changeDetected() );
    super.changeDetected();
  }
  @Override
  public void changeReverted() {
    placeManager.setOnLeaveConfirmation( null );
    super.changeReverted();
  }
}