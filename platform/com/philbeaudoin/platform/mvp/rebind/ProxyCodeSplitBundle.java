package com.philbeaudoin.platform.mvp.rebind;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import com.philbeaudoin.platform.mvp.client.ProviderBundle;

@Target(ElementType.TYPE)
public @interface ProxyCodeSplitBundle {
  Class<? extends ProviderBundle> bundleClass();
  int id();
}
