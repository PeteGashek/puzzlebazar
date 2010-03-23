package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.gwtp.mvp.client.ViewImpl;
import com.puzzlebazar.client.core.presenter.SplitMainPresenter;
import com.puzzlebazar.client.resources.Resources;
import com.puzzlebazar.client.ui.ShortMessageBox;

public class SplitMainView extends ViewImpl implements SplitMainPresenter.MyView {

  interface Binder extends UiBinder<Widget, SplitMainView> { }
  protected static final Binder binder = GWT.create(Binder.class);
  
  private final Widget widget;
  
  @UiField 
  FlowPanel sideBarContainer;
  
  @UiField 
  FlowPanel centerContentContainer;

  @UiField 
  ShortMessageBox shortMessageBox;
  
  @UiField(provided = true)
  final Resources resources;  
  
  @Inject
  public SplitMainView( Resources resources ) {
    this.resources = resources;
    widget = binder.createAndBindUi(this);
  }
  
  @Override 
  public Widget asWidget() {
    return widget;
  }

  @Override
  public void setContent(Object slot, Widget content) {
    if( slot == SplitMainPresenter.TYPE_RevealSideBarContent ) 
      setSideBarContent( content );
    else if( slot == SplitMainPresenter.TYPE_RevealCenterContent ) 
      setCenterContent( content );
    else
      super.setContent(slot, content);
  }
    
  private void setSideBarContent(Widget sideBarContent) {
    sideBarContainer.clear();
    sideBarContainer.add( sideBarContent );
  }

  private void setCenterContent(Widget centerContent) {
    centerContentContainer.clear();
    centerContentContainer.add( centerContent );
  }

  @Override
  public void showMessage(String message, boolean dismissable) {
    // TODO Take dismissable into account
    shortMessageBox.setText( message );
  }


  @Override
  public void clearMessage() {
    shortMessageBox.clearText();
  }

  @Override
  public boolean hasSideBarContent() {
    return sideBarContainer.getWidgetCount() > 0;
  }
}
