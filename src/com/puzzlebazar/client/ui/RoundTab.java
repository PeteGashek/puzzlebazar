package com.puzzlebazar.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.Tab;
import com.puzzlebazar.client.resources.Resources;

/**
 * A tab with rounded corner contained within an {@link RoundTabPanel}.
 * 
 * @author Philippe Beaudoin
 */
public class RoundTab extends Composite implements Tab {

  interface Binder extends UiBinder<Widget, RoundTab> { }
  private static final Binder binder = GWT.create(Binder.class);

  /**
   * Using static injection because elements accessible within 
   * UIBinder don't collaborate very well with GIN/Guice. 
   */
  @Inject static Resources resources;
  
  @UiField
  Hyperlink hyperlink;

  private final float priority;

  @UiConstructor RoundTab(float priority) {
    this.priority = priority;
    initWidget( binder.createAndBindUi( this ) );
  }

  @Override
  public void setText(String text) {
    hyperlink.setText(text);
  }

  @Override
  public String getText() {
    return hyperlink.getText();
  }
  
  @Override
  public void setTargetHistoryToken(String historyToken) {
    hyperlink.setTargetHistoryToken(historyToken);      
  }

  @Override
  public void activate() {
    removeStyleName( resources.style().roundtab_inactive() );
    addStyleName( resources.style().roundtab_active() );
  }

  @Override
  public void deactivate() {
    removeStyleName( resources.style().roundtab_active() );
    addStyleName( resources.style().roundtab_inactive() );
  }

  @Override
  public float getPriority() {
    return priority;
  }

  @Override
  public Widget asWidget() {
    return this;
  }

}
