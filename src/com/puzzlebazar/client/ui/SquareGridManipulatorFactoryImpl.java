package com.puzzlebazar.client.ui;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.puzzlebazar.client.util.SquareGridConverterImpl;
import com.puzzlebazar.shared.util.SquareGridValidatorImpl;

public class SquareGridManipulatorFactoryImpl implements
    SquareGridManipulatorFactory {  

  @Inject
  SquareGridManipulatorFactoryImpl() {
  }
  
  @Override
  public SquareGridManipulator create(
      SquareGridLayoutPanel gridPanel,
      Widget uiWidget) {
    return new SquareGridManipulatorImpl(
        new SquareGridValidatorImpl(gridPanel),
        new SquareGridConverterImpl(gridPanel, uiWidget),
        gridPanel,
        uiWidget);
  }

}
