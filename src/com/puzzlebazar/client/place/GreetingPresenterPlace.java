package com.puzzlebazar.client.place;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.gin.ProvidedPresenterPlace;
import com.philbeaudoin.gwt.presenter.client.place.PlaceRequest;
import com.puzzlebazar.client.presenter.GreetingPresenter;

public class GreetingPresenterPlace extends ProvidedPresenterPlace<GreetingPresenter> {

  private static final String NAME = "name";
  
  @Inject
  public GreetingPresenterPlace(Provider<GreetingPresenter> presenter) {
    super(presenter);
  }

  @Override
  public String getName() {
    return "Greeting";
  }

  @Override
  protected void preparePresenter(PlaceRequest request, GreetingPresenter presenter) {
    final String name = request.getParameter( NAME, null);
    if (name != null) {
      presenter.getDisplay().getName().setValue(name);
    } 
  }

  @Override
  protected PlaceRequest prepareRequest( PlaceRequest request, GreetingPresenter presenter ) {
    String name = presenter.getDisplay().getName().getValue();
    if( name.length() == 0 ) return request;
    return request.with( NAME, name );
  }  
  
}
