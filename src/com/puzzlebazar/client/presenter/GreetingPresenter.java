//package com.puzzlebazar.client.presenter;
//
//import com.google.gwt.event.dom.client.ClickEvent;
//import com.google.gwt.event.dom.client.ClickHandler;
//import com.google.gwt.event.dom.client.HasClickHandlers;
//import com.google.gwt.user.client.Window;
//import com.google.gwt.user.client.rpc.AsyncCallback;
//import com.google.gwt.user.client.ui.HasValue;
//import com.google.inject.Inject;
//
//import net.customware.gwt.dispatch.client.DispatchAsync;
//
//import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
//import com.philbeaudoin.gwt.presenter.client.EventBus;
//import com.philbeaudoin.gwt.presenter.client.place.PlaceRequest;
//import com.philbeaudoin.gwt.presenter.client.widget.WidgetDisplay;
//
//import com.puzzlebazar.shared.event.GreetingSentEvent;
//import com.puzzlebazar.shared.rpc.SendGreeting;
//import com.puzzlebazar.shared.rpc.SendGreetingResult;
//
//public class GreetingPresenter extends BasicPresenter<GreetingPresenter.Display> {
//  /**
//   * The message displayed to the user when the server cannot be reached or
//   * returns an error.
//   */
//  private static final String SERVER_ERROR = "An error occurred while "
//    + "attempting to contact the server. Please check your network "
//    + "connection and try again.";
//
//  public interface Display extends WidgetDisplay {
//    public HasValue<String> getName();
//
//    public HasClickHandlers getSend();
//  }
//
//  private final DispatchAsync dispatcher;
//
//  @Inject
//  public GreetingPresenter(final Display display, final EventBus eventBus,
//      final DispatchAsync dispatcher ) {
//    super(display, eventBus);
//
//    this.dispatcher = dispatcher;
//
//    bind();
//  }
//
//  /**
//   * Try to send the greeting message
//   */
//  private void doSend() {
//
//    dispatcher.execute(new SendGreeting(display.getName().getValue()),
//        new AsyncCallback<SendGreetingResult>() {
//
//      @Override
//      public void onFailure(Throwable caught) {
//        Window.alert(SERVER_ERROR);
//      }
//
//      @Override
//      public void onSuccess(SendGreetingResult result) {
//        // take the result from the server and notify client interested
//        // components
//        eventBus.fireEvent(new GreetingSentEvent(result.getName(), result
//            .getMessage()));
//      }
//
//    });
//  }
//
//  @Override
//  protected void onBind() {
//    // 'display' is a final global field containing the Display passed into the
//    // constructor.
//    display.getSend().addClickHandler(new ClickHandler() {
//      public void onClick(final ClickEvent event) {
//        doSend();
//      }
//    });
//  }
//
//  @Override
//  protected void onUnbind() {
//    // Add unbind functionality here for more complex presenters.
//  }
//
//  @Override
//  protected void onRevealDisplay() {
//    // Nothing to do. This is more useful in UI which may be buried
//    // in a tab bar, tree, etc.
//  }
//
//  private static final String PARAM_NAME = "name";
//  
//  @Override
//  public void prepareFromRequest(PlaceRequest request) {
//    super.prepareFromRequest(request);
//    final String name = request.getParameter( PARAM_NAME, null );
//    if (name != null) {
//      getDisplay().getName().setValue(name);
//    } 
//  }
//
//  @Override
//  public PlaceRequest prepareRequest(PlaceRequest request) {
//    request = super.prepareRequest(request);
//    String name = getDisplay().getName().getValue();
//    if( name.length() == 0 ) return request;
//    return request.with( PARAM_NAME, name );
//  }  
//  
//}
