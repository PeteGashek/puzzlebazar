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
import com.puzzlebazar.shared.action.LogoutAction;
import com.puzzlebazar.shared.action.NoResult;


public class LogoutActionHandler implements ActionHandler<LogoutAction, NoResult> {
  
  private final Provider<UserDAO> userDao;

  @Inject
  public LogoutActionHandler(
      final Provider<UserDAO> userDao ) {
    this.userDao = userDao;
  }

  @Override
  public NoResult execute(final LogoutAction action,
      final ExecutionContext context) throws ActionException {
    try {
      userDao.get().logoutSessionUser();
      return null;
    }
    catch (Exception cause) {
      throw new ActionException(cause);
    }
  }

  @Override
  public void undo(final LogoutAction action,
      final NoResult result,
      final ExecutionContext context) throws ActionException {
    // Nothing to do here
  }

  @Override
  public Class<LogoutAction> getActionType() {
    return LogoutAction.class;
  }
}
