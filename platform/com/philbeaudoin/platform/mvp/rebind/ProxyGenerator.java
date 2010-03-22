package com.philbeaudoin.platform.mvp.rebind;

import java.io.PrintWriter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JPackage;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.platform.mvp.client.CodeSplitProvider;
import com.philbeaudoin.platform.mvp.client.DelayedBind;
import com.philbeaudoin.platform.mvp.client.DelayedBindRegistry;
import com.philbeaudoin.platform.mvp.client.EventBus;
import com.philbeaudoin.platform.mvp.client.Presenter;
import com.philbeaudoin.platform.mvp.client.StandardProvider;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyFailureHandler;
import com.philbeaudoin.platform.mvp.client.proxy.ProxyImpl;
import com.philbeaudoin.platform.mvp.client.proxy.RevealContentEvent;
import com.philbeaudoin.platform.mvp.client.proxy.RevealContentHandler;

public class ProxyGenerator extends Generator {

  private static final String basePresenterClassName = Presenter.class.getCanonicalName();
  private static final String ginjectorClassName = Ginjector.class.getCanonicalName();

  @Override
  public String generate(TreeLogger logger, GeneratorContext ctx, String requestedClass)
  throws UnableToCompleteException {

    JClassType proxyInterface = ctx.getTypeOracle().findType(requestedClass);
    
    if (proxyInterface == null) {
      logger.log(TreeLogger.ERROR, "Unable to find metadata for type '"
          + requestedClass + "'", null);

      throw new UnableToCompleteException();
    }
    
    String customGingectorClassName = null;
    try {
      customGingectorClassName = ctx.getPropertyOracle().getConfigurationProperty("gin.ginjector").getValues().get(0);
    } catch (BadPropertyValueException e) {
      logger.log(TreeLogger.ERROR, "The required configuration property 'gin.ginjector' was not found.", e);
      throw new UnableToCompleteException();
    }
    JClassType ginjectorClass = ctx.getTypeOracle().findType(ginjectorClassName);
    JClassType customGinjectorClass = ctx.getTypeOracle().findType(customGingectorClassName);
    if( customGinjectorClass == null || !customGinjectorClass.isAssignableTo(ginjectorClass) ) {
      logger.log(TreeLogger.ERROR, "The configuration property 'gin.ginjector' is '"+customGingectorClassName+"' " +
      		" which doesn't identify a type inheriting from 'Ginjector'.", null);      
      throw new UnableToCompleteException();
    }

    // Make sure this proxy lies within a presenter
    JClassType basePresenterClass = ctx.getTypeOracle().findType( basePresenterClassName );
    JClassType presenterClass = proxyInterface.getEnclosingType();
    if( presenterClass == null || 
        !presenterClass.isAssignableTo( basePresenterClass ) ) {
      logger.log(TreeLogger.ERROR, "Proxy must be enclosed in a class derived from '"
          + basePresenterClassName + "'", null);

      throw new UnableToCompleteException();
    }    
    String presenterClassName = presenterClass.getName();

    // This is the Injector we use for the Generator internally,
    // it has nothing to do with user code.   
    JPackage interfacePackage = proxyInterface.getPackage();
    String packageName = interfacePackage == null ? "" : interfacePackage.getName();
    String implClassName = presenterClassName + proxyInterface.getSimpleSourceName() + "Impl";
    String generatedClassName = packageName + "." + implClassName;


    UseCodeSplit codeSplitAnnotation = proxyInterface.getAnnotation( UseCodeSplit.class );
    UseCodeSplitBundle codeSplitBundleAnnotation = proxyInterface.getAnnotation( UseCodeSplitBundle.class );

    if( codeSplitAnnotation != null && codeSplitBundleAnnotation != null ) {
      logger.log(TreeLogger.ERROR, "Proxy cannot have both annotations '" +
          codeSplitAnnotation.getClass().getSimpleName() + "' and '" +
          codeSplitBundleAnnotation.getClass().getSimpleName()  + "'", null);
      throw new UnableToCompleteException();
    }    

    PrintWriter printWriter = ctx.tryCreate(logger, packageName, implClassName);
    if (printWriter == null) {
      // We've already created it, so nothing to do
    } else {
      ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(
          packageName, implClassName);
      
      // TODO cleanup imports
      composerFactory.addImport(GWT.class.getCanonicalName());  // Obsolete?
      composerFactory.addImport(Inject.class.getCanonicalName());  // Obsolete?
      composerFactory.addImport(Provider.class.getCanonicalName());
      composerFactory.addImport(AsyncProvider.class.getCanonicalName());
      composerFactory.addImport(EventBus.class.getCanonicalName());
      composerFactory.addImport(StandardProvider.class.getCanonicalName());
      composerFactory.addImport(CodeSplitProvider.class.getCanonicalName());
      composerFactory.addImport(ProxyFailureHandler.class.getCanonicalName());
      composerFactory.addImport(ProxyImpl.class.getCanonicalName());
      composerFactory.addImport(RevealContentHandler.class.getCanonicalName());
      composerFactory.addImport(DelayedBind.class.getCanonicalName());
      composerFactory.addImport(DelayedBindRegistry.class.getCanonicalName());
      composerFactory.addImport(Ginjector.class.getCanonicalName());
      composerFactory.addImport(RevealContentEvent.class.getCanonicalName());  // Obsolete?
      
      // Sets interfaces an superclass
      composerFactory.addImplementedInterface(
          proxyInterface.getParameterizedQualifiedSourceName());
      composerFactory.addImplementedInterface(
          DelayedBind.class.getCanonicalName());
      composerFactory.setSuperclass(ProxyImpl.class.getCanonicalName()+"<"+presenterClassName+">" );

      SourceWriter writer = composerFactory.createSourceWriter(ctx, printWriter);

      writer.println();
      writer.println( "private RevealContentHandler<"+presenterClassName+"> revealContentHandler = null;");
      writer.println();
      writer.println( "public " +  implClassName + "() {");
      writer.indent();
      writer.println( "DelayedBindRegistry.register(this);" );
      writer.outdent();
      writer.println( "}" );
      writer.println();
      writer.println( "@Override");
      writer.println( "public void delayedBind( Ginjector ginjector ) {");
      writer.indent();
      writer.println( customGingectorClassName + " injector = (" + customGingectorClassName + ")ginjector;"  );
      writer.println( "EventBus eventBus = injector.getEventBus();"  );
      writer.println( "ProxyFailureHandler failureHandler = injector.getProxyFailureHandler();" );
      if( codeSplitAnnotation == null && codeSplitBundleAnnotation == null ) {
        // StandardProvider
        writer.println( "presenter = new StandardProvider<" + presenterClassName + ">( injector.get" +
            presenterClassName + "() );" );
      }
      else if( codeSplitAnnotation != null ) {
        // CodeSplitProvider
        writer.println( "presenter = new CodeSplitProvider<" + presenterClassName + ">( injector.get" +
            presenterClassName + "() );" );
      }
      else {
        // CodeSplitBundleProvider        
        String bundleClassName = codeSplitBundleAnnotation.bundleClass().getCanonicalName();
        writer.println( "AsyncProvider<" + bundleClassName + "> presenterBundle ) {");
        writer.outdent(); 
        writer.println( "presenter = new CodeSplitBundleProvider<" + presenterClassName + ", " +
            bundleClassName + ">( injector.get" + bundleClassName + "(), " + codeSplitBundleAnnotation.id() + ");" );
      }
      writer.println( "revealContentHandler = new RevealContentHandler<" + presenterClassName + ">( failureHandler, this );" );      
      for( JField field : presenterClass.getFields() ) {
        ContentSlot annotation = field.getAnnotation( ContentSlot.class );
        if( field.isStatic() && // TODO field.getType().isGenericType()... &&
            annotation != null ) {          
          writer.println( "eventBus.addHandler( " +
              presenterClassName + "." + field.getName() + 
              ", revealContentHandler );" );
        }
      }
      
      writer.outdent();
      writer.println( "}" );

      writer.commit(logger);


    }

    return generatedClassName;    
  }
}
