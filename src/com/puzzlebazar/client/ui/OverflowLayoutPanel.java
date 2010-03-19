package com.puzzlebazar.client.ui;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.layout.client.Layout.Layer;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * A layout panel that allows its children to overflow around their desired
 * layer. This is useful for example to mix 'percentage' positioning and 
 * 'pixel' poxitioning.
 * 
 * @author Philippe Beaudoin
 */
public class OverflowLayoutPanel extends LayoutPanel {
  
  public void insert(Widget widget, int beforeIndex) {
    super.insert(widget, beforeIndex);
    ((Layer)widget.getLayoutData()).getContainerElement().getStyle().setOverflow(Overflow.VISIBLE);
  }
}
