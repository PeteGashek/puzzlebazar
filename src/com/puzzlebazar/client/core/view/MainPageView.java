package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.ViewImpl;
import com.puzzlebazar.client.core.presenter.MainPagePresenter;

public class MainPageView extends ViewImpl implements MainPagePresenter.MyView {

  interface Binder extends UiBinder<Widget, MainPageView> { }
  protected static final Binder binder = GWT.create(Binder.class);
  
  private final Widget widget;
  
  @UiField
  FlowPanel newsPanel;
  
  @Inject
  public MainPageView() {
    widget = binder.createAndBindUi(this);
  }
  
  @Override 
  public Widget asWidget() {
    return widget;
  }
  
  @Override
  public void addContent(Object slot, Widget content) {
    if( slot == MainPagePresenter.TYPE_RevealNewsContent )
      newsPanel.add( content );
    else
      super.addContent(slot, content);
  }

  @Override
  public void clearContent(Object slot) {
    if( slot == MainPagePresenter.TYPE_RevealNewsContent )
      newsPanel.clear();
    else
      super.clearContent(slot);
  }

}
