package com.puzzlebazar.client.ui;

import com.google.gwt.uibinder.client.UiConstructor;
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
public class SimpleTab extends Hyperlink implements Tab {

  /**
   * Using static injection because elements accessible within 
   * UIBinder don't collaborate very well with GIN/Guice. 
   */
  @Inject static Resources resources;

  private final float priority;

  @UiConstructor SimpleTab(float priority) {
    this.priority = priority;
    addStyleName( resources.style().simpletab() );
    addStyleName( resources.style().simpletab_inactive() );
  }

  @Override
  public void activate() {
    removeStyleName( resources.style().simpletab_inactive() );
    addStyleName( resources.style().simpletab_active() );
  }

  @Override
  public void deactivate() {
    removeStyleName( resources.style().simpletab_active() );
    addStyleName( resources.style().simpletab_inactive() );
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
