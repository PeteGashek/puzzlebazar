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
