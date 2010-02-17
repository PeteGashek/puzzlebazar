//package com.puzzlebazar.client.presenter;
//
//import com.google.gwt.event.dom.client.ClickEvent;
//import com.google.gwt.event.dom.client.ClickHandler;
//import com.google.gwt.event.dom.client.HasClickHandlers;
//import com.google.gwt.user.client.ui.DialogBox;
//import com.google.gwt.user.client.ui.HasHTML;
//import com.google.gwt.user.client.ui.HasText;
//import com.google.inject.Inject;
//import com.google.inject.Provider;
//
//import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
//import com.philbeaudoin.gwt.presenter.client.EventBus;
//import com.philbeaudoin.gwt.presenter.client.Presenter;
//import com.philbeaudoin.gwt.presenter.client.widget.WidgetDisplay;
//
//import com.puzzlebazar.shared.event.GreetingSentEvent;
//import com.puzzlebazar.shared.event.GreetingSentEventHandler;
//
//
//public class GreetingResponsePresenter extends BasicPresenter<GreetingResponsePresenter.Display> implements Presenter {
//  public interface Display extends WidgetDisplay {
//    HasText getTextToServer();
//
//    HasHTML getServerResponse();
//
//    HasClickHandlers getClose();
//
//    DialogBox getDialogBox();
//  }
//
//
//  private final Provider<GreetingPresenter> greetingPresenter;
//
//  @Inject
//  public GreetingResponsePresenter(final Display display, final EventBus eventBus, final Provider<GreetingPresenter> greetingPresenter) {
//    super(display, eventBus);
//
//    this.greetingPresenter = greetingPresenter;
//    
//    bind();
//  }
//
//  @Override
//  protected void onBind() {
//    // Add a handler to close the DialogBox
//    display.getClose().addClickHandler(new ClickHandler() {
//      public void onClick(final ClickEvent event) {
//        display.getDialogBox().hide();
//        greetingPresenter.get().revealDisplay();
//        
//        // Not sure of a nice place to put these!
//        //    sendButton.setEnabled(true);
//        //    sendButton.setFocus(true);
//      }
//    });
//
//    eventBus.addHandler(GreetingSentEvent.TYPE, new GreetingSentEventHandler() {
//
//      @Override
//      public void onGreetingSent(final GreetingSentEvent event) {
//
//        display.getTextToServer().setText(event.getName());
//        display.getServerResponse().setHTML(event.getMessage());
//        revealDisplay();
//      }
//    });
//  }
//
//  @Override
//  protected void onUnbind() {
//    // Add unbind functionality here for more complex presenters.
//  }
//
//
//  @Override
//  protected void onRevealDisplay() {
//    display.getDialogBox().show();
//  }
//
//}
