package com.puzzlebazar.server.guice;

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


import com.philbeaudoin.gwtp.dispatch.server.guice.ActionHandlerModule;

import com.puzzlebazar.server.handler.EditUserActionHandler;
import com.puzzlebazar.server.handler.LogoutActionHandler;
import com.puzzlebazar.server.handler.GetCurrentUserActionHandler;
import com.puzzlebazar.shared.action.EditUser;
import com.puzzlebazar.shared.action.Logout;
import com.puzzlebazar.shared.action.GetCurrentUser;

/**
 * Module which binds the handlers and configurations
 *
 */
public class ServerModule extends ActionHandlerModule {

  @Override
  protected void configureHandlers() {
    bindHandler(Logout.class, LogoutActionHandler.class);
    bindHandler(GetCurrentUser.class, GetCurrentUserActionHandler.class);
    bindHandler(EditUser.class, EditUserActionHandler.class);
  }
}
