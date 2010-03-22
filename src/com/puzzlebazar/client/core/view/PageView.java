package com.puzzlebazar.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.philbeaudoin.platform.mvp.client.ViewImpl;
import com.puzzlebazar.client.core.presenter.PagePresenter;

/**
 * The {@link com.philbeaudoin.platform.mvp.client.View} for {@link PagePresenter}.
 * 
 * @author Philippe Beaudoin
 */
public class PageView extends ViewImpl implements PagePresenter.MyView {

  interface Binder extends UiBinder<Widget, PageView> { }
  protected static final Binder binder = GWT.create(Binder.class);

  private final Widget widget;
  
  @UiField 
  FlowPanel topBarContainer;
  
  @UiField 
  FlowPanel mainContentContainer;
  
  @Inject
  public PageView() {
    widget = binder.createAndBindUi(this);
  }
  
  @Override 
  public Widget asWidget() {
    return widget;
  }

  @Override
  public void setContent(Object slot, Widget content) {
    if( slot == PagePresenter.TYPE_RevealMainContent ) 
      setMainContent( content );
    else if( slot == PagePresenter.TYPE_RevealTopBarContent ) 
      setTopBarContent( content );
    else
      super.setContent(slot, content);
  }
    
  private void setMainContent(Widget mainContent) {
    mainContentContainer.clear();
    mainContentContainer.add( mainContent );
  }

  private void setTopBarContent(Widget topBarContent) {
    topBarContainer.clear();
    topBarContainer.add( topBarContent );
  }


}
