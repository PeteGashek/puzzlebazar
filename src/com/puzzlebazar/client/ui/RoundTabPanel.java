package com.puzzlebazar.client.ui;


import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.philbeaudoin.gwt.presenter.client.Tab;

/**
 * A widget that can show multiple tabs with rounded corners, each with its own content.
 * 
 * @author Philippe Beaudoin
 */
public class RoundTabPanel extends BaseTabPanel {

  interface Binder extends UiBinder<Widget, RoundTabPanel> { }
  private static final Binder binder = GWT.create(Binder.class);

  public RoundTabPanel() {
    initWidget( binder.createAndBindUi( this ) );
  }

  @Override
  protected Tab CreateNewTab(float priority) {
    return new SimpleTab(priority);
  }
  
}
