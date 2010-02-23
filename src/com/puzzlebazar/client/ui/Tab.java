package com.puzzlebazar.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.resources.Resources;

/**
 * A tab contained within an {@link TabbedContainer}.
 * 
 * @author beaudoin
 */
public class Tab extends Composite implements HasText {

  interface Binder extends UiBinder<Widget, Tab> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField
  Hyperlink hyperlink;


  /**
   * Using a static  
   */
  @Inject static Resources resources;
  
  /**
   * This constructor is 
   */
  Tab() {
    initWidget( binder.createAndBindUi( this ) );
  }

  /**
   * Sets the text displayed on the tab.
   * 
   * @param text The text.
   */
  @Override
  public void setText(String text) {
    hyperlink.setText(text);
  }

  /**
   * Gets the text displayed on the tab.
   * 
   * @return The text.
   * 
   * @see com.google.gwt.user.client.ui.HasText#getText()
   */
  @Override
  public String getText() {
    return hyperlink.getText();
  }
  
  /**
   * Sets the history token this tab links to.
   * 
   * @param historyToken The history token.
   */
  public void setHistoryToken(String historyToken) {
    hyperlink.setTargetHistoryToken(historyToken);      
  }

  /**
   * Should not be called directly. Call
   * {@link TabbedContainer#setActiveTab(Tab)} instead.
   */
  void activate() {
    removeStyleName( resources.style().inactive() );
    addStyleName( resources.style().active() );
  }

  /**
   * Should not be called directly. Call
   * {@link TabbedContainer#setActiveTab(Tab)} instead.
   */
  void deactivate() {
    removeStyleName( resources.style().active() );
    addStyleName( resources.style().inactive() );
  }


}
