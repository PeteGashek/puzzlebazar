package com.puzzlebazar.client.ui;

/**
 * Copyright 2010 Philippe Beaudoin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.LayoutPanel;

/**
 * <b>Important!</b> This layout panel must be added inside a {@link LayoutPanel}.
 * <p />
 * This class is a layout panel that maintains a user-specified aspect ratio 
 * (width/height). It does so by resizing itself whenever its parent presenter is 
 * resized. One of the axis (horizontal or vertical) always takes up the entire 
 * parent, while the other axis is adjusted to respect the desired aspect ratio.
 * Also, the panel is centered in its parent along this second axis.
 * 
 * @author Philippe Beaudoin
 */
public class AspectRatioLayoutPanel extends OverflowLayoutPanel {

  private int border = 0;           // Allocated border space in pixel
  private float aspectRatio = 1.0f; // This is the desired aspect ratio (width / height)

  /**
   * Sets the desired border of the layout panel, in pixels. 
   * Space for this border will always be allocated, and the inside
   * area will respect the aspect ratio.
   * <pre>
   *   +----------------------------------------------------+
   *   |   border (in pixel)                                |
   *   |  +----------------------------------------------+  |
   *   |  |  This inside area                            |  |
   *   |  |  respects aspect ratio                       |  |
   *   |  |                                              |  |
   *   |  |                                              |  |
   *   |  |                                              |  |
   *   |  +----------------------------------------------+  |
   *   |                                                    |
   *   +----------------------------------------------------+
   * </pre>
   * 
   * @param border The desired border, in pixels. 
   */
  public void setBorder(int border) {
    this.border = border;
    if( isAttached() )
      onResize();
  }

  /**
   * Access the border size, in pixels. See {@link #setBorder(int)} for details.
   * 
   * @return The border size, in pixels.
   */
  public int getBorder() {
    return border;
  }
  
  /**
   * Sets the desired aspect ration (width divided by height) for this
   * layout panel.
   * 
   * @param aspectRatio The desired aspect ratio.
   */
  public void setAspectRatio( float aspectRatio ) {
    assert aspectRatio > 0 : "Aspect ratio must be positive and non-zero!";
    this.aspectRatio = aspectRatio;
    if( isAttached() )
      onResize();    
  }
  
  @Override
  public void onLoad() {
    super.onLoad();
    assert (getParent() instanceof LayoutPanel) : "AspectRatioLayoutPanel must be inserted in a LayoutPanel!";
    DeferredCommand.addCommand( new Command() {
      @Override
      public void execute() {
        onResize();        
      }
    } );
  }

  @Override
  public void onResize() {
    LayoutPanel parent = (LayoutPanel)getParent();

    // Compute width and height available for inside area
    int parentWidth  = parent.getOffsetWidth();
    int parentHeight = parent.getOffsetHeight();
    int availableWidth  = parentWidth - 2*border; 
    int availableHeight = parentHeight - 2*border;
    
    if( availableWidth > 0 && availableHeight > 0 ) {
      float parentRatio = (float)availableWidth/(float)availableHeight;
      if( parentRatio < aspectRatio ) {
        // availableWidth/availableHeight < desiredWidth/desiredHeight
        // Set desiredWidth = availableWidth  ==>  availableHeight > desiredHeight (good!)
        // Then desiredHeight = availableWidth / aspectRatio
        int desiredHeight = (int)(availableWidth / aspectRatio);
        parent.setWidgetLeftRight(this, 0, Unit.PX, 0, Unit.PX);
        parent.setWidgetTopHeight(this, (parentHeight - desiredHeight)/2 - border, Unit.PX, 
            desiredHeight + 2*border, Unit.PX);
      }
      else {
        // availableWidth/availableHeight > desiredWidth/desiredHeight
        // Set desiredHeight = availableHeight  ==>  availableWidth > desiredWidth (good!)
        // Then desiredWidth = availableHeight * aspectRatio     
        int desiredWidth = (int)(availableHeight * aspectRatio);
        parent.setWidgetTopBottom(this, 0, Unit.PX, 0, Unit.PX);
        parent.setWidgetLeftWidth(this, (parentWidth - desiredWidth)/2 - border, Unit.PX, 
            desiredWidth + 2*border, Unit.PX);      
      }
    }
    else {
      // Not enough room for inside area, border takes up everything.      
      if( parentWidth < parentHeight ) {
        // parentWidth is smaller, allocate parentWidth x parentWidth
        parent.setWidgetLeftRight(this, 0, Unit.PX, 0, Unit.PX);
        parent.setWidgetTopHeight(this, (parentHeight - parentWidth)/2, Unit.PX, parentWidth, Unit.PX);        
      }
      else {
        // parentHeight is smaller, allocate parentHeight x parentHeight
        parent.setWidgetTopBottom(this, 0, Unit.PX, 0, Unit.PX);
        parent.setWidgetLeftWidth(this, (parentWidth - parentHeight)/2, Unit.PX, parentHeight, Unit.PX);        
      }
    }
    super.onResize();
  }


}
