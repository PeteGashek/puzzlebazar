package com.philbeaudoin.gwtp.mvp.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.Widget;

public abstract class PresenterWidgetImpl<V extends View>
    extends HandlerContainerImpl implements PresenterWidget {

  /**
   * The {@link EventBus} for the application.
   */
  protected final EventBus eventBus;

  /**
   * This map makes it possible to keep a list of all the active children
   * in every slot managed by this PresenterWidget. A slot is identified by an opaque
   * object. A single slot can have many children.
   */
  private final Map< Object, List<PresenterWidget> > activeChildren =
    new HashMap< Object, List<PresenterWidget> >();

  protected boolean visible = false;
  
  /**
   * The view for the presenter.
   */
  protected final V view;
  

  public PresenterWidgetImpl(
      EventBus eventBus, 
      V view) {
    super();
    this.view = view;
    this.eventBus = eventBus;
  }

  @Override
  public final V getView() {
    return view;
  }

  @Override
  public final boolean isVisible() {
    return visible;
  }
  
  @Override
  public final void reveal() {
    if( visible )
      return;
    visible = true;
    onReveal();
    revealChildren();
  } 

  @Override
  public final void notifyHide() {
    if( !visible )
      return;
    visible = false;    
    notifyHideChildren();
    onHide();
  }

  @Override
  public void setContent( Object slot, PresenterWidget content ) {
    if( content == null ) {
      // Assumes the user wants to clear the slot content.
      clearContent( slot );
    }
    List<PresenterWidget> slotChildren = activeChildren.get( slot );
    if( slotChildren != null ) {
      if( slotChildren.size() == 1 && slotChildren.get(0) == content )
        return;
      for( PresenterWidget activeChild : slotChildren )
        activeChild.notifyHide();
      slotChildren.clear();
      slotChildren.add( content );
    }
    else {
      slotChildren = new ArrayList<PresenterWidget>(1);
      slotChildren.add( content );
      activeChildren.put( slot, slotChildren );
    }
    getView().setContent( slot, content.getWidget() );
    content.reveal();
  }

  @Override
  public void addContent( Object slot, PresenterWidget content ) {
    if( content == null ) {
      return;
    }
    List<PresenterWidget> slotChildren = activeChildren.get( slot );
    if( slotChildren != null ) {
      slotChildren.add( content );
    }
    else {
      slotChildren = new ArrayList<PresenterWidget>(1);
      slotChildren.add( content );
      activeChildren.put( slot, slotChildren );
    }
    getView().addContent( slot, content.getWidget() );
    content.reveal();
  }

  @Override
  public void clearContent( Object slot ) {
    List<PresenterWidget> slotChildren = activeChildren.get( slot );
    if( slotChildren != null ) {
      for( PresenterWidget activeChild : slotChildren )
        activeChild.notifyHide();
      slotChildren.clear();
    }
    getView().clearContent( slot );
  }
  
  @Override
  public Widget getWidget() {
    return getView().asWidget();
  }
  
  /**
   * <b>Important:</b> Make sure you call your superclass {@link #onReveal()}.
   * <p />
   * This method will be called whenever the presenter is revealed. Override
   * it to perform any action (such as refreshing content) that needs
   * to be done when the presenter is revealed.
   */
  protected void onReveal() {}

  /**
   * <b>Important:</b> Make sure you call your superclass {@link #onHide()}.
   * <p />
   * Override this method to perform any clean-up operations. For example,
   * objects created directly or indirectly during the call to
   * {@link #onReveal()} should be disposed of in this methods.
   */
  protected void onHide() {}


  /**
   * TODO This should probably be called notifyRevealChildren()
   * 
   * Notifies all the children that they are being revealed. This happens when
   * this presenter goes from invisible to visible.
   */
  protected final void revealChildren() {
    for (List<PresenterWidget> slotChildren : activeChildren.values())
      for( PresenterWidget activeChild : slotChildren )
        activeChild.reveal();    
  }

  /**
   * Notifies all the children that they are being hidden. This happens when
   * this presenter goes from visible to invisible.
   */
  protected final void notifyHideChildren() {
    for (List<PresenterWidget> slotChildren : activeChildren.values())
      for( PresenterWidget activeChild : slotChildren )
        activeChild.notifyHide();    
  }
  

}