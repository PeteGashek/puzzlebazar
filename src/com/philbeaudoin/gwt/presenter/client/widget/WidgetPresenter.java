package com.philbeaudoin.gwt.presenter.client.widget;

import com.google.gwt.user.client.ui.Widget;

import com.philbeaudoin.gwt.presenter.client.BasicPresenter;
import com.philbeaudoin.gwt.presenter.client.EventBus;

/**
 * Abstract super-class for {@link BasicPresenter}s that work with GWT
 * {@link Widget}s via {@link WidgetDisplay}s.
 * 
 * @author David Peterson
 * 
 * @param <D>
 *            The {@link WidgetDisplay} type.
 */
public abstract class WidgetPresenter<D extends WidgetDisplay> extends BasicPresenter<D> {
    public WidgetPresenter( D display, EventBus eventBus ) {
        super( display, eventBus );
    }
}
