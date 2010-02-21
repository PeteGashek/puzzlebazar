package com.philbeaudoin.gwt.presenter.client;

import com.google.gwt.user.client.ui.Widget;
import com.philbeaudoin.gwt.presenter.client.place.PlaceRequest;

public interface Presenter {

    /**
     * Called when the presenter is initialised. This is called before any other
     * methods. Any event handlers and other setup should be done here rather
     * than in the constructor.
     */
    void bind();

    /**
     * Called after the presenter and display have been finished with for the
     * moment.
     */
    void unbind();

    /**
     * Returns true if the presenter is currently in a 'bound' state. That is,
     * the {@link #bind()} method has completed and {@link #unbind()} has not
     * been called.
     *
     * @return <code>true</code> if bound.
     */
    boolean isBound();

    /**
     * Returns the {@link Display} for the current presenter.
     *
     * @return The display.
     */
    Display getDisplay();
    
    /**
     * Requests the presenter to reveal the display on screen. 
     * Composite presenters should forward this call to their child presenters.
     * The presenter is also responsible of inserting itself in the DOM, often
     * by firing an appropriate event.
     */
    void revealDisplay();
    
    /**
     * This method is called when a {@link Presenter} should prepare itself
     * based on a {@link PlaceRequest}. The presenter should extract
     * any parameters it needs from the request. A presenter should override
     * this method if it handles custom parameters, but it should call
     * the parent's <code>prepareFromRequest</code> method.
     *
     * @param request   The request.
     */
    public void prepareFromRequest( PlaceRequest request );

    /**
     * This method is called when creating a {@link PlaceRequest} for this
     * {@link Presenter}. The presenter should add all the required parameters to the 
     * request.
     * <p/>
     * <p/>
     * If nothing is to be done, simply return the <code>request</code>
     * unchanged. Otherwise, call {@link PlaceRequest#with(String, String)} to
     * add parameters. Eg:
     * <p/>
     * <pre>
     * return request.with( &quot;id&quot;, getId() );
     * </pre>
     * <p/>
     * A presenter should override this method if it handles custom parameters, but
     * it should call the parent's <code>prepareRequest</code> method.
     * 
     * @param request   The current request.
     * @return The prepared place request.
     */
    public PlaceRequest prepareRequest( PlaceRequest request );    

    
    /**
     * Makes it possible to access the {@link Widget} object associated with that presenter.
     * 
     * @return The Widget associated with that presenter.
     */
    public Widget getWidget();
    
}