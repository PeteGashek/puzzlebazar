package com.puzzlebazar.client.presenter;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
import com.philbeaudoin.gwt.presenter.client.EventBus;



public class AppPresenter extends BasicPresenter<AppPresenter.Display> {

  public interface Display extends com.philbeaudoin.gwt.presenter.client.Display, HasWidgets {
  }

  private final Provider<GreetingPresenter> greetingPresenter;

  @Inject
  public AppPresenter(final Display display, final EventBus eventBus,       
      Provider<GreetingPresenter> greetingPresenter,
      Provider<GreetingResponsePresenter> greetingResponsePresenter ) {
    super(display, eventBus);

    this.greetingPresenter = greetingPresenter;
    greetingResponsePresenter.get();

    bind();
  }
  

  @Override
  protected void onBind() {
    display.add( greetingPresenter.get().getDisplay().asWidget() );
  }

  @Override
  protected void onRevealDisplay() {
  }

  @Override
  protected void onUnbind() {
    display.remove( greetingPresenter.get().getDisplay().asWidget() );
  }
}
