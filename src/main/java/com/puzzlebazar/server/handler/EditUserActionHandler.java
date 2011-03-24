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

package com.puzzlebazar.server.handler;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.puzzlebazar.server.model.UserDAO;
import com.puzzlebazar.shared.action.EditUserAction;
import com.puzzlebazar.shared.action.NoResult;

/**
 * @author Philippe Beaudoin
 */
public class EditUserActionHandler implements ActionHandler<EditUserAction, NoResult> {

  private final Provider<UserDAO> userDAO;

  @Inject
  public EditUserActionHandler(
      final Provider<UserDAO> userDAO) {
    this.userDAO = userDAO;
  }

  @Override
  public NoResult execute(
      final EditUserAction action,
      final ExecutionContext context) throws ActionException {
    userDAO.get().modifyUser(action.getUser());
    return null;
  }

  @Override
  public void undo(
      final EditUserAction action,
      final NoResult result,
      final ExecutionContext context) throws ActionException {
    userDAO.get().modifyUser(action.getPreviousUser());
  }

  @Override
  public Class<EditUserAction> getActionType() {
    return EditUserAction.class;
  }
}
