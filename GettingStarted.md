**NOTE!** This page might be deprecated. If you see any error please mention them to `philippe.beaudoin@gmail.com`.

# Introduction #

PuzzleBazar is built around the model-view-presenter architecture and strives to integrate many technologies of interest, such as code splitting, use of UIBinder, internationalization, etc. As such, it can be a useful example to help you understand the challenges of these technologies within an MVP architecture. It can even be used as a starting point for your own project. To help you achieve this, this document describes how to compile PuzzleBazar locally.

Be aware, however, that the core code is still fluctuating frequently. A large scale project might be better off using stable releases of the framework available via [GWTP](http://code.google.com/p/gwt-platform/) (gwt-platform).

# Setting up your environment #

PuzzleBazar is developed under Eclipse and this document focuses on how to get and compile the project within this environment. If you successfully compiled it under different conditions, please let me know and I will add specific instructions to this page.

  1. [Download](http://www.eclipse.org/downloads/) your favorite flavour of Eclipse for Java.
  1. Install [Google Plugin for Eclipse](http://code.google.com/eclipse/). The easiest way is through Eclipse's menu `Help --> Install New Software`, the update site is `http://dl.google.com/eclipse/plugin/3.5`.
  1. The previous step should have installed [GWT](http://code.google.com/webtoolkit/) and [Google App Engine SDK](http://code.google.com/appengine/downloads.html). If not, download them manually and unpack them in any directory.
  1. The project is typically meant to compile against the latest version of GWT. (As of 03-26-10 it compiled against GWT 2.0.3 and App Engine SDK 1.3.2)
  1. If you want to compile and use the Python scripts, you can use the excellent [PyDev](http://pydev.org/). The update site is `http://pydev.org/updates`. You will also need [Python 2.1](http://www.python.org/) or newer.

# The patched gin #

PuzzleBazar uses a patched version of Google Gin that includes the excellent `AsyncProvider`. The required `patchedgin.jar` is included in PuzzleBazar, but if you want to use it in your own project, you can download it from [GWTP](http://code.google.com/p/gwt-platform/downloads/list). If you want to know more about that patch read the related [google-gin issue](http://code.google.com/p/google-gin/issues/detail?id=61).

# Getting GWTP #

In order to run PuzzleBazar you will have to get your copy of [gwt-platform](http://code.google.com/p/gwt-platform/). To do so, simply clone the public mercurial repository at `http://gwt-platform.googlecode.com/hg/`. You can place this directly in your eclipse workspace directory.

**Important!** Now that you have GWTP, you need to let Eclipse know where to find it. To do this:
  * Go to Window --> Preferences --> General --> Workspace --> Linked Resources.
  * Check _Enable linked resources_.
  * Add a new variable:
    * _Name:_ `gwtp`
    * _Location:_ The directory where you checked out GWTP

# Getting PuzzleBazar #

The code for PuzzleBazar can be obtained from the public mercurial repository at `http://puzzlebazar.googlecode.com/hg/`, place it into your Eclipse workspace directory. Once you have the code, you can simply import the project in Eclipse. Go to File --> Import --> General --> Existing Projects into Workspace. Then indicate the PuzzleBazar repository.

# Setting up the GWT and AppEngine SDK JAR #

The Mercurial repository doesn't include the `jar` files required for GWT and AppEngine SDKs. Fortunately, these can be very easily installed with the Google Plugin for Eclipse. To do this, find the `Problems` view in Eclipse (it's usually in a tab on the bottom part of your screen). Open the `Warnings` section and click on ones about the GWT or AppEngine SDK JAR. Hit CTRL-1 to access the quick-fix menu and select `Synchronize <WAR>/WEB-INFO/lib with SDK libraries`.

# Running the project #

To run the project, click on the PuzzleBazar folder in Eclipse and simply select Run --> Run As --> Web Application.

# Troubleshooting #

First, make sure you follow the instructions above. In particular, make sure you defined the `gwtp` linked resource and that you synchronized the GWT and AppEngine SDK JAR. Other problems you might run into:
  * If you have an exclamation mark beside the `gwtp` folder in Eclipse, make sure you correctly defined the `gwtp` linked resource. If you did, then close the project (right-click --> Close Project) and reopen it (double-click).
  * If you get an error message like `The class "..." is not persistable.` when loading the page, this is usually due to the DataNucleus Enhancer failing to to its job. Open the erroneous class (i.e. `com.puzzlebazar.server.model.Session`), add a space somewhere and save. This will cause the DataNucleus Enhancer to run and should solve your problem.

# Understanding the code #

PuzzleBazar's core comes from the GWTP project. In the PuzzleBazar Eclipse project, you will see this code under the `gwtp` directory. This is probably where you should look first.

The `com.gwtplatform.dispatch` package is a slightly modified version of the excellent [gwt-dispatch](http://code.google.com/p/gwt-dispatch/). It contains the base classes for the Command pattern use to communicate with the server.

The `com.gwtplatform.mvp` package contains the base classes for the MVP architecture. It started off from [gwt-presenter](http://code.google.com/p/gwt-presenter/) but changed a lot since. To better understand it and its motivation, read MvpArchitecture.

The `mergelocale` python script facilitates internationalization with UIBinder and is described in UiBinderInternationalisation.

The project is actively discussed on the [Google Group](http://groups.google.com/group/puzzlebazar-dev), join us there!

# Contributing #

If you like the idea of this project and would like to become a contributor, send an email to philippe.beaudoin@gmail.com.