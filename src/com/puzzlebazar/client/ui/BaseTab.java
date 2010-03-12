package com.puzzlebazar.client.ui;

import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.philbeaudoin.platform.mvp.client.Tab;

public abstract class BaseTab extends Composite implements Tab {

  protected interface Style extends CssResource {
      String active();
      String inactive();
    }

  @UiField
  Style style;
  
  @UiField
  Hyperlink hyperlink;
  
  private final float priority;

  public BaseTab( float priority ) {
    super();
    this.priority = priority;
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