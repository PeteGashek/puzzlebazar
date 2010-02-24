package com.puzzlebazar.client.presenter;

import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
import com.philbeaudoin.gwt.presenter.client.EventBus;
import com.philbeaudoin.gwt.presenter.client.Presenter;
import com.philbeaudoin.gwt.presenter.client.PresenterDisplay;
import com.philbeaudoin.gwt.presenter.client.PresenterWrapper;
import com.philbeaudoin.gwt.presenter.client.Slot;
import com.puzzlebazar.client.gin.annotations.DefaultMainPresenter;


public class AppPresenter extends BasicPresenter<AppPresenter.Display, AppPresenter.Wrapper> {

  public interface Display extends PresenterDisplay {
    void setTopBar( Widget topBar );
    void setMainContent( Widget mainContent );
  }
  
  public static class MainSlot extends Slot<AppPresenter> {
    @Inject
    public MainSlot(Provider<AppPresenter> presenter) {
      super(presenter);
    }
    @Override
    protected void displayContent() {
      if( activePresenter != null )
        getPresenter().getDisplay().setMainContent( activePresenter.getWidget() );
    }
  }

  public static class Wrapper extends PresenterWrapper<AppPresenter> {
    @Inject
    public Wrapper(EventBus eventBus, Provider<AppPresenter> presenter ) {
      super(eventBus, presenter);
    }
  }

  private final MainSlot mainSlot;
  private final TopBarPresenter topBarPresenter;
  private final Presenter defaultMainPresenter;

  @Inject
  public AppPresenter(final Display display, final EventBus eventBus, final Wrapper wrapper,
      final MainSlot mainSlot,
      final TopBarPresenter topBarPresenter,
      @DefaultMainPresenter final Presenter defaultMainPresenter ) {
    super(display, eventBus, wrapper, null);

    RootLayoutPanel.get().add(getWidget());
    
    this.mainSlot = mainSlot;
    this.topBarPresenter  = topBarPresenter;
    this.defaultMainPresenter = defaultMainPresenter;

    bind();
  }  

  @Override
  protected void onBind() {
    display.setTopBar( this.topBarPresenter.getWidget() );
    mainSlot.setPresenter( defaultMainPresenter );
  }

  @Override
  protected void onUnbind() {
  }

}
