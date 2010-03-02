package com.puzzlebazar.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Widget;

/**
 * A tab with rounded corner contained within an {@link RoundTabPanel}.
 * 
 * @author Philippe Beaudoin
 */
public class RoundTab extends BaseTab {

  interface Binder extends UiBinder<Widget, RoundTab> { }
  private static final Binder binder = GWT.create(Binder.class);
  
  @UiConstructor RoundTab(float priority) {
    super(priority);
    initWidget( binder.createAndBindUi( this ) );
  }

}
