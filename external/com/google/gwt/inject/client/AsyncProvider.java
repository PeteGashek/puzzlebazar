// Copyright 2009 Google Inc. All Rights Reserved.

package com.google.gwt.inject.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 */
public interface AsyncProvider<T> {
  void get(AsyncCallback<T> callback);

}