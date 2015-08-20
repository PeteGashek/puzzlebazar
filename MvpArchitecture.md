**IMPORTANT NOTE!** This documentation is slightly outdated, PuzzleBazar now uses much simpler automatic proxies. It will be updated when moving to [gwt-platform](http://code.google.com/p/gwt-platform/).

# Introduction #

A recent [Google IO talk](http://code.google.com/events/io/2009/sessions/GoogleWebToolkitBestPractices.html) introduced a number of good practices that could lead to better GWT applications. In particular, Ray Ryan proposes to use the Model-View-Presenter (MVP) architecture, dependency injection through GIN/Guice, a Command pattern for client-server communication, a consistent and centralized history management mechanism, and decoupling through the use of an event bus. PuzzleBazar uses all these, in addition to using a declarative UI through UIBinder, GWT internationalization tools (i18n), client bundles and code splitting. The server-side is implemented using Goggle AppEngine for Java.

If you're building a project from the ground up and you wish to integrate these technologies, then PuzzleBazar might be a good example project to look at. This page is partly written with that in mind. Please [use the Google Group](http://groups.google.com/group/puzzlebazar-dev) to let me know if you find this page useful, or if you would like to have PuzzleBazar core classes packaged in a reusable jar.

# Background #

PuzzleBazar MVP architecture is strongly inspired by the excellent [gwt-dispatch](http://code.google.com/p/gwt-dispatch/) and [gwt-presenter](http://code.google.com/p/gwt-presenter/) written by David Peterson and David Chandler. The classes you will find under `com.philbeaudoin.gwt` almost all originate from there.

For the moment, the `com.philbeaudoin.gwt.dispatch` package is almost an exact replica of gwt-dispatch's head revision. The minor differences were introduced mainly to get rid of compilation warnings. Also, the spring dependencies were removed, as PuzzleBazar doesn't depend on it.

The `com.philbeaudoin.gwt.presenter` package started off from the replace branch of gwt-presenter, and you will find that a lot of the code is similar. However, some major architectural and naming changes were introduced. This is done in order to better fit the requirements of this project.

This document is not meant to be an introduction to the MVP architecture. For this, check out, among other things, the [Google IO talk](http://code.google.com/events/io/2009/sessions/GoogleWebToolkitBestPractices.html).

# Major differences with gwt-presenter #

For those who are familiar with gwt-presenter, here is a quick look at the major differences between this package and PuzzleBazar's presenter implementation. If don't know anything about gwt-presenter, feel free to skip this section.

In gwt-presenter, places are loosely coupled with presenters. In PuzzleBazar, places are replaced with proxies. Each presenter has one (and only one) proxy. Some of these proxies are places and can be attached to history tokens.

The gwt-presenter package relies on the event bus for communicating between presenters and places. In PuzzleBazar, presenter are injected with their proxies and vice-versa (through `Provider<?>`), and they communicate by invoking one another's methods. The event bus is still used for inter-presenter or inter-proxy communication.

The gwt-presenter declares generic events (for example `PresenterRevealedEvent`) and handlers are expected to do the filtering based on the event content. PuzzleBazar describes non-typed events that can be initialized with their specific `Type<?>` so that filtering is done by the event bus itself (for example see `SetContentEvent`).

# MVP + Proxies #

The classical MVP architecture describes the inter-relationships of three key players: models, views and presenters. The architecture used here introduces a fourth one: proxies.

`Presenter` (and its implementation, `PresenterImpl`) is the base class from which all presenters in the application are derived. These derived classes are instantiated as singleton using GIN.

Each presenter is associated with two other singleton: a class derived from `PresenterDisplay` (the presenter's View), and a class derived from `PresenterProxy` (the presenter's proxy). Therefore there exists a one-to-one relationship between presenter, view and proxy.

Presenters, proxies and views participate in dependency injection using GIN. Check out `AbstractPresenterModule` and `PuzzlebazarClientModule` to see how this is done. Presenters are injected with their view and their proxies. Proxies are eager singletons and are injected with a `Provider<?>` to their presenter. This means that proxies are instantiated as soon as the application starts, but this does not cause providers to be instantiated. Also, a view is instantiated as soon as its presenter is.

_Note_: If dependency injection, `Provider<?>` or eager singletons mean nothing to you, you should probably read (see [Guice's documentation](http://code.google.com/p/google-guice/wiki/ProviderBindings)).

## Why Proxies? ##

At first, the tight relationship between presenters and proxies might make the latter seems unnecessary. The problem of presenters, however, is that they can grow to become quite large and complex. Without proxies, it would be necessary to load and instantiate all the presenters as soon as the application starts.

Proxies, on the other hand, are lightweight classes whose sole goal is to listen for relevant events that might require their presenter to spring to life. As such, they can be loaded and instantiated from the start with minimal cost.

## History Management ##

One of the event that proxies need listen to is the `PlaceRequestEvent` which is fired whenever a change in the history token is detected. This can occur if the user manually changes the URL or clicks an hyperlink. `ProxyPlace` is the base class of all proxies that are interested in such events.

`ProxyPlace` declares one string as their history token: the unique name that can be used to access this presenter.

# Presenter-View-Proxy Communication #

The three classes within a presenter-view-proxy triplet are tightly coupled and communicate through method invocation. More precisely:
  * Presenters can call methods on their views.
  * Presenters can call methods on their proxies.
  * Proxies can call method on their presenters, but doing so triggers the presenter's instantiation, so it should only be done when clearly needed.
  * Proxies never invoke views methods directly.
  * Views don't invoke presenters or proxies methods.

# Parent-Child Communication #

To reduce coupling (and spaghettification) presenters should not be injected with any proxy other than the one with which it has a one-to-one relationship. Also, it should never be injected with another presenter. The same holds for proxies.

However, parent and child often have to communicate. In PuzzleBazar, this communication is performed by firing events on the event bus.

## Revealing Parent Presenters ##

One case in which a presenter needs to communicate with its parent occurs when the child presenter has been requested to reveal itself on the screen. In this case, the child presenter will need to ask its parent presenter to add it at the right location within the DOM.

Remember, however, that presenter's instantiation is delayed as much as possible. It means that a child presenter can be revealed without its parent being instantiated. This means the parent will not be listening to the event bus and will miss the child's request to be added to the DOM.

How can this be solved? Simply by having the parent presenter's proxy listen for the event! As soon as the proxy gets the message, it instantiates its parent (through `Provider#get()`) and passes the request along.

Note also that `ProxyPlace` will let the `PlaceManager` know whenever its presenter is revealed. That is because these presenters are associated with history tokens. Therefore, if it is revealed through a direct method call, the URL must be updated to show to correct token, hence the request to `PlaceManager`.

## One event type versus many ##

One way to use the event bus to communicate from child to parent would be to define a single generic event type. Parent proxies would all handle this event and would examine it to see if they are concerned. Doing this has a number of drawbacks however. One of which is performance: all the proxies are activated every time a presenter is revealed. Another problem is that it's not obvious how parent proxies should identify if they are concerned or not.

Another approach is to define one specific event class (derived from `GwtEvent<?>`) for each parent proxy. This, however, could quickly lead to an undesirable explosion of event classes.

To overcome all these problems, PuzzleBazar uses one non-typed event class `SetContentEvent`. As opposed to typical event classes, however, `SetContentEvent` is not attached to a specific event type (derived from `GwtEvent.Type<?>`). Instead, it can be attached to an arbitrary type upon construction. Parent proxies are in turn responsible for creating one or more static event `Type` to which they can listen and that children can fire. For an example, take a look at `SplitMainProxy` (the parent declaring `TYPE_SetCenterContent`) and `UserSettingsPresenter` (the child, using this type).

# Tab containers #

Some presenters are displayed within tab panels. To do this, Puzzle Bazar uses presenters and proxies derived from `TabContainerPresenter`, `TabContainerProxy` and `TabContentProxy`.

Whenever a `TabContainerPresenter` is instantiated, it fires its specific version of the non-typed event `RequestTabsEvent`. Child proxies interested to be displayed in this tab panel handle this event and request to be added. Note that this _does not_ require the child proxy to instantiate its presenter.

# Code splitting #

[GWT documentation](http://code.google.com/webtoolkit/doc/latest/DevGuideCodeSplitting.html) makes code splitting look deceptively simple. The truth is that in a real scenario it can be quite hard to use efficiently. Code splitting will only work well when used to wrap pieces of code that are both significant and isolated.

Fortunately for us, presenters have both of these characteristics, making them very good candidates for code splitting:
  * They are very well isolated since they can only be instantiated through their `Proxy` ;
  * They are often significant since they contain the logic for exchanging information between the view, the business objects and the server ;
  * Moreover, they are the sole entry-point to their view. Code splitting the presenter will therefore code split the view!

## Code splitting means cut and paste ##

Using code splitting typically requires a lot of cut and paste. This is because the GWT compiler introduces one split point for every _occurence_ of `GWT.runAsync` in your code. It means that trying to be clever by hiding the call to `GWT.runAsync` in some utility class fails: it leads to a single (and usually very small) code split.

## How to split? ##

Now that we know _what_ we want to split out, we have to decide _how_ to do it. One possible way is to wrap `Presenter` in an [Async Provider pattern](http://code.google.com/webtoolkit/doc/latest/DevGuideCodeSplitting.html#patterns). Better yet, we could get rid of the cut and paste with the elegant [deferred binding solution](http://jcheng.wordpress.com/2010/02/16/elegant-code-splitting-with-gwt/) of Joe Cheng. Wrapping a presenter in this way can hide most of its methods behind a splitting point, but not its constructor. Unfortunately, since the view is injected in the constructor, it means that most of the view's code will not sit behind the splitting point.

## Code splitting and GIN ##

The real problem is that, since we use dependency injection, GIN is responsible for instantiating most of our objects. So if we want to make sure the constructor of `MyPresenter` is behind a split point, GIN has to do some of its magic within a call to `GWT.runAsync`.

Unfortunately, there is no way to do that besides playing within GIN's innards. Although such a feature might get added in a future version of GIN we don't have to wait! Fazal Azim has in fact proposed [a patch](http://code.google.com/p/google-gin/issues/detail?id=61) that adds this very useful feature in the form of an `AsyncProvider<T>`. The patched GIN is available with PuzzleBazar in the `external` folder.

## Efficient code splitting made easy ##

With this patched GIN and an MVP architecture, efficient code splitting becomes really easy to do. First take a look at `Callback` and `CallbackProvider`:
```
public interface Callback<T> {
  void execute(T parameter);
}

public interface CallbackProvider<T> {
 void get(Callback<T> callback);
}
```

A `CallbackProvider` makes it possible to wrap either an `AsyncProvider` or a `Provider`, letting you choose to use code splitting on a case-by-case basis. For example, if you don't want to code split a given presenter you would use `DirectProvider`:
```
public class DirectProvider<T> implements CallbackProvider<T> {

  private final Provider<T> provider;
  
  public DirectProvider( Provider<T> provider ) {
    this.provider = provider;
  }
  
  public void get(Callback<T> callback) {
    callback.execute( provider.get() );
  }
}
```

Thanks to the GIN patch, you can also write a `CodeSplitProvider` in this way:

```
public class CodeSplitProvider<T> implements CallbackProvider<T> {
  
  private final AsyncProvider<T> provider;
  
  public CodeSplitProvider( AsyncProvider<T> provider ) {
    this.provider = provider;
  }
  
  public void get(final Callback<T> callback) {
    provider.get( new AsyncCallback<T>(){
      @Override
      public void onFailure(Throwable caught) {
        Window.alert( "Asynchronous code loading failed!" );
      }

      @Override
      public void onSuccess(T parameter) {
        callback.execute(parameter);
      }} );
  }

}
```

But where do you use these `CallbackProvider`s exactly? Within the only access point to your presenter, that is, its proxy. More specifically in the proxy's constructor. For example, say you want to put `MainPagePresenter` behind a split point, here is how its proxy's constructor could look like:

```
  @Inject
  public MainPageProxy(
      final EventBus eventBus, 
      final PlaceManager placeManager,
      final AsyncProvider<MainPagePresenter> presenter {
    super(
      eventBus, 
      placeManager, 
      new CodeSplitProvider<AdminGeneralPresenter>(presenter));
  }
```

## Too many split points? ##

Now that you have to choose whether or not to split presenters, one possible option would be to split them all! This could help considerably reduce the size of your initial download. (Although it might prove somewhat useless to use code splitting on presenters that are revealed in every possible start-up scenarios.)

Having too many split points can be a problem, however. Suppose you have designed a nice but heavy `TabPanelWidget` that you use in `TabAPresenter` and `TabBPresenter`. You'd like the code of `TabPanelWidget` to sit behind a split point that is loaded only when one of these presenters is revealed. Code splitting both presenters will not achieve this, however. That is because the code of `TabPanelWidget` sits behind _two different_ split points. This kind of non-exclusive code is placed by GWT in the _left over fragment_, which can end-up being a large melting-pot of unrelated code fragments.

If you want to explicitly specify that both presenters should sit behind the same split point, you can use a provider bundle pattern. The idea is to gather a well-defined set of providers within a single class and to inject an `AsyncProvider<>` to this class. The base class for this pattern looks like this:

```
public class ProviderBundle {

  // ... (see below)

  protected final Provider<?> providers[] ;

  public ProviderBundle( int bundleSize ) {
    providers = new Provider<?>[bundleSize];
  }
  public Provider<?> get(int providerId) {
    return providers[providerId];
  }

}
```

Then you can define your own bundles, for the previous example this would be:

```
public class TabPresenterBundle extends ProviderBundle {

  public final static int ID_TabAPresenter = 0;
  public final static int ID_TabBPresenter = 1;
  public final static int BUNDLE_SIZE = 2;
  
  @Inject
  TabPresenterBundle(
      final Provider<TabAPresenter> tabAPresenter,
      final Provider<TabBPresenter> tabBPresenter) {
    super( BUNDLE_SIZE );
    providers[ID_TabAPresenter] = tabAPresenter;
    providers[ID_TabBPresenter] = tabBPresenter;
  }  
}
```

Make sure you bind this bundle as a singleton in your GIN module. Finally, we need a `CallbackProvider` that can work with `ProviderBundle`s:

```
public class ProviderBundle {
  
  public static class CodeSplit<T, B extends ProviderBundle> 
  implements CallbackProvider<T> {
    
    private final AsyncProvider<B> bundleProvider;
    private final int providerId;
    
    public CodeSplit( 
        AsyncProvider<B> bundleProvider, 
        int providerId ) {
      this.bundleProvider = bundleProvider;
      this.providerId = providerId;
    }
    
    public void get(final Callback<T> callback) {
      bundleProvider.get( new AsyncCallback<B>(){
        @Override
        public void onFailure(Throwable caught) {
          Window.alert( "Asynchronous code loading failed!" );
        }
  
        @SuppressWarnings("unchecked")
        @Override
        public void onSuccess(B providerBundle) {
          callback.execute(((Provider<T>)providerBundle.get(providerId)).get());
        }
      } );
    }
  
  }

  // ... (ProviderBundle implementation, see above)

}
```

Telling the proxies to use the bundle is then simply a question of using this `CallbackProvider`:

```
  @Inject
  public TabAProxy(
      final EventBus eventBus, 
      final AsyncProvider<TabPresenterBundle> presenterBundle,
      final Translations translations) {
    super(
        eventBus,  
        new ProviderBundle.CodeSplit<AdminPresenter,TabPresenterBundle>(
            presenterBundle,
            TabbedPresenterBundle.ID_TabAPresenter) );
  }
```