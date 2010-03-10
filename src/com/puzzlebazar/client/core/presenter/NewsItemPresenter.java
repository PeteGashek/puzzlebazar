package com.puzzlebazar.client.core.presenter;

import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.View;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.PresenterWidget;
import com.philbeaudoin.gwt.presenter.client.PresenterWidgetImpl;
import com.philbeaudoin.gwt.presenter.client.ViewInterface;

/**
 * The presenter for a single news item in the application.
 * For now, this is mostly a placeholder class demonstrating how
 * to use {@link PresenterWidget}.
 * 
 * @author Philippe Beaudoin
 */
public class NewsItemPresenter 
extends PresenterWidgetImpl<NewsItemPresenter.MyView> {

  @ViewInterface
  public interface MyView extends View {
    void setTitle( String title );    
  }

  @Inject
  public NewsItemPresenter(
      final EventBus eventBus, 
      final MyView view) {
    super(
        eventBus, 
        view);
  }
  
  void setTitle( String title ) {
    view.setTitle( title );
  }

}
