package com.philbeaudoin.gwt.presenter.client.gin;

import com.google.inject.Provider;
import com.philbeaudoin.gwt.presenter.client.Presenter;
import com.philbeaudoin.gwt.presenter.client.place.PresenterPlace;

/**
 * A place that represents a {@link PresenterWithPlace}, constructed by using a {@link Provider}.
 * This is the recommended method for creating places when using GIN, since it will help
 * avoid any circular dependencies when you have two Presenters that want to link to each
 * other. Implementing classes should simply inject a <code>Provider<X></code> rather than
 * the actual Presenter class directly, and pass it to the super() constructor.
 * <p/>
 * <p>Also, places should <b>not</b> be @Singleton values.</p>
 * <p/>
 * <p>Eg:
 * <p/>
 * <pre>
 * public class FooPlace extends ProvidedPresenterPlace<FooPresenter> {
 *      \@Inject
 *      public FooPlace( Provider<FooPresenter> presenter ) {
 *          super( presenter );
 *      }
 * }
 * </pre>
 *
 * @author David Peterson
 */
public abstract class ProvidedPresenterPlace<T extends Presenter> extends PresenterPlace<T> {
    private final Provider<T> presenter;

    public ProvidedPresenterPlace( Provider<T> presenter ) {
        this.presenter = presenter;
    }

    @Override
    public T getPresenter() {
        return presenter.get();
    }

}
