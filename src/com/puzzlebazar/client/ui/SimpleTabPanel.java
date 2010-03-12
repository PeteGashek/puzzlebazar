package com.puzzlebazar.client.ui;


import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.philbeaudoin.platform.mvp.client.Tab;

/**
 * A widget that can show multiple tabs with rounded corners, each with its own content.
 * 
 * @author Philippe Beaudoin
 */
public class SimpleTabPanel extends BaseTabPanel {

  interface Binder extends UiBinder<Widget, SimpleTabPanel> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField
  Label title;


  /**
   * Creates a panel a title and simple tabs under it.
   * 
   * @param title The title to display on the panel
   */
  @UiConstructor public SimpleTabPanel( String title ) {
    initWidget( binder.createAndBindUi( this ) );
    this.title.setText( title );
  }

  @Override
  protected Tab CreateNewTab(float priority) {
    return new SimpleTab(priority);
  }
  
}
