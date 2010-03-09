package com.puzzlebazar.client.core.presenter;

import com.google.inject.Inject;
import com.philbeaudoin.gwt.presenter.client.Display;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.PresenterWidget;
import com.philbeaudoin.gwt.presenter.client.PresenterWidgetImpl;

/**
 * The presenter for a single news item in the application.
 * For now, this is mostly a placeholder class demonstrating how
 * to use {@link PresenterWidget}.
 * 
 * @author Philippe Beaudoin
 */
public class NewsItemPresenter 
extends PresenterWidgetImpl<NewsItemPresenter.MyDisplay> {

  public interface MyDisplay extends Display {
    void setTitle( String title );    
  }

  @Inject
  public NewsItemPresenter(
      final EventBus eventBus, 
      final MyDisplay display) {
    super(
        eventBus, 
        display);
  }
  
  void setTitle( String title ) {
    display.setTitle( title );
  }

}
