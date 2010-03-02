package com.puzzlebazar.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.philbeaudoin.gwt.presenter.client.Tab;

/**
 * A tab with rounded corner contained within an {@link RoundTabPanel}.
 * 
 * @author Philippe Beaudoin
 */
public class SimpleTab extends Composite implements Tab {

  interface Style extends CssResource {
    String active();
    String inactive();
  }

  interface Binder extends UiBinder<Widget, SimpleTab> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField 
  Style style;

  @UiField
  Hyperlink hyperlink;

  private final float priority;

  @UiConstructor SimpleTab(float priority) {
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
    removeStyleName( style.inactive() );
    addStyleName( style.active() );
  }

  @Override
  public void deactivate() {
    removeStyleName( style.active() );
    addStyleName( style.inactive() );
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
