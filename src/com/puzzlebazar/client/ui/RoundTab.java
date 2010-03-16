package com.puzzlebazar.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.resources.Resources;

/**
 * A tab with rounded corner contained within an {@link RoundTabPanel}.
 * 
 * @author Philippe Beaudoin
 */
public class RoundTab extends BaseTab {

  interface Binder extends UiBinder<Widget, RoundTab> { }
  private static final Binder binder = GWT.create(Binder.class);

  @Inject static Resources resources;
  
  @UiConstructor RoundTab(float priority) {
    super(priority);
    initWidget( binder.createAndBindUi( this ) );
  }

  @UiFactory
  public static Resources getResources() {
    return resources;
  }
}
