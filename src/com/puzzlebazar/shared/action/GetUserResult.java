package com.puzzlebazar.shared.action;

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

import com.philbeaudoin.gwtp.dispatch.shared.Result;
import com.puzzlebazar.shared.model.User;

public class GetUserResult implements Result {

  private static final long serialVersionUID = -738456865589456638L;
  
  private User userInfo;

  @SuppressWarnings("unused")
  private GetUserResult() {
  }

  public GetUserResult(final User userInfo) {
    this.userInfo = userInfo;
  }

  public User getUser() {
    return userInfo;
  }

}
