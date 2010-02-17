//package com.puzzlebazar.client.view;
//
//import com.google.gwt.core.client.GWT;
//import com.google.gwt.event.dom.client.HasClickHandlers;
//import com.google.gwt.user.client.ui.Button;
//import com.google.gwt.user.client.ui.DialogBox;
//import com.google.gwt.user.client.ui.HTML;
//import com.google.gwt.user.client.ui.HasHTML;
//import com.google.gwt.user.client.ui.HasText;
//import com.google.gwt.user.client.ui.Label;
//import com.google.gwt.user.client.ui.Widget;
//import com.google.gwt.uibinder.client.UiBinder;
//import com.google.gwt.uibinder.client.UiField;
//
//import com.philbeaudoin.gwt.presenter.client.widget.WidgetDisplay;
//
//import com.puzzlebazar.client.presenter.GreetingResponsePresenter;
//
//public class GreetingResponseView extends DialogBox implements GreetingResponsePresenter.Display {
//
//  interface Binder extends UiBinder<Widget, GreetingResponseView> { }
//  private static final Binder binder = GWT.create(Binder.class);
//
//  @UiField Button closeButton;
//  @UiField Label textToServerLabel;
//  @UiField HTML serverResponseLabel;
//
//  public GreetingResponseView() {
//    setText("Remote Procedure Call");
//    setAnimationEnabled(true);
//    setWidget(binder.createAndBindUi(this));
//  }
//
///*
//  closeButton = new Button("Close");
//
//    // We can set the id of a widget by accessing its Element
//    closeButton.getElement().setId("closeButton");
//
//    textToServerLabel = new Label();
//    serverResponseLabel = new HTML();
//
//    final VerticalPanel dialogVPanel = new VerticalPanel();
//
//    dialogVPanel.addStyleName("dialogVPanel");
//    dialogVPanel.add(new HTML("Sending name to the server:"));
//    dialogVPanel.add(textToServerLabel);
//    dialogVPanel.add(new HTML("Server replies:"));
//    dialogVPanel.add(serverResponseLabel);
//    dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
//    dialogVPanel.add(closeButton);
//
//    setWidget(dialogVPanel);
//  }
//*/
//
//  
//  public HasText getTextToServer() {
//    return textToServerLabel;
//  }
//
//  public HasHTML getServerResponse() {
//    return serverResponseLabel;
//  }
//
//  public HasClickHandlers getClose() {
//    return closeButton;
//  }
//
//  public DialogBox getDialogBox() {
//    return this;
//  }
//
//  /**
//   * Returns this widget as the {@link WidgetDisplay#asWidget()} value.
//   */
//  public Widget asWidget() {
//    return this;
//  }
//}
