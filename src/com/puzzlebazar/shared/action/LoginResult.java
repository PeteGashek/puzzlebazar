package com.puzzlebazar.shared.action;

import com.puzzlebazar.shared.model.LoginInfo;

import com.philbeaudoin.gwt.dispatch.shared.Result;

public class LoginResult implements Result {

 private static final long serialVersionUID = 7917449246674223581L;

 private LoginInfo loginInfo;


 @SuppressWarnings("unused")
 private LoginResult() {
 }

 public LoginResult(final LoginInfo loginInfo) {
  this.loginInfo = loginInfo;
 }

 public LoginInfo getLoginInfo() {
  return loginInfo;
 }

}
