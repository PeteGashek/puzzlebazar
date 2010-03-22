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
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JPackage;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.platform.mvp.client.CodeSplitBundleProvider;
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
  private static final String baseGinjectorClassName = Ginjector.class.getCanonicalName();
  private static final String typeClassName = Type.class.getCanonicalName();
  private static final String revealContentHandlerClassName = RevealContentHandler.class.getCanonicalName();
  private static final String providerClassName = Provider.class.getCanonicalName();
  private static final String asyncProviderClassName = AsyncProvider.class.getCanonicalName();

  @Override
  public String generate(TreeLogger logger, GeneratorContext ctx, String requestedClass)
  throws UnableToCompleteException {

    TypeOracle oracle = ctx.getTypeOracle();
    JClassType proxyInterface = oracle.findType(requestedClass);

    if (proxyInterface == null) {
      logger.log(TreeLogger.ERROR, "Unable to find metadata for type '"
          + requestedClass + "'", null);

      throw new UnableToCompleteException();
    }

    // If it's not an interface it's a custom user-made proxy class. Don't use generator.
    if( proxyInterface.isInterface() == null )
      return null;

    ProxyStandard proxyStandardAnnotation = proxyInterface.getAnnotation( ProxyStandard.class );
    ProxyCodeSplit proxyCodeSplitAnnotation = proxyInterface.getAnnotation( ProxyCodeSplit.class );
    ProxyCodeSplitBundle proxyCodeSplitBundleAnnotation = proxyInterface.getAnnotation( ProxyCodeSplitBundle.class );

    int nbNonNullTags = 0;
    if( proxyStandardAnnotation != null ) nbNonNullTags++;
    if( proxyCodeSplitAnnotation != null ) nbNonNullTags++;
    if( proxyCodeSplitBundleAnnotation != null ) nbNonNullTags++;

    // If there is no proxy tag, don't use generator.
    if( nbNonNullTags == 0 )
      return null;

    String gingectorClassName = null;
    try {
      gingectorClassName = ctx.getPropertyOracle().getConfigurationProperty("gin.ginjector").getValues().get(0);
    } catch (BadPropertyValueException e) {
      logger.log(TreeLogger.ERROR, "The required configuration property 'gin.ginjector' was not found.", e);
      throw new UnableToCompleteException();
    }
    JClassType baseGinjectorClass = oracle.findType(baseGinjectorClassName);
    JClassType ginjectorClass = oracle.findType(gingectorClassName);
    if( ginjectorClass == null || !ginjectorClass.isAssignableTo(baseGinjectorClass) ) {
      logger.log(TreeLogger.ERROR, "The configuration property 'gin.ginjector' is '"+gingectorClassName+"' " +
          " which doesn't identify a type inheriting from 'Ginjector'.", null);      
      throw new UnableToCompleteException();
    }

    // Make sure this proxy lies within a presenter
    JClassType basePresenterClass = oracle.findType( basePresenterClassName );
    JClassType presenterClass = proxyInterface.getEnclosingType();
    if( presenterClass == null || 
        !presenterClass.isAssignableTo( basePresenterClass ) ) {
      logger.log(TreeLogger.ERROR, "Proxy must be enclosed in a class derived from '"
          + basePresenterClassName + "'", null);

      throw new UnableToCompleteException();
    }    
    String presenterClassName = presenterClass.getName();

    // Watch out for more than one proxy tag
    if( nbNonNullTags > 1 ) {
      logger.log(TreeLogger.ERROR, "Proxy for '" + presenterClassName + "' has more than one @Proxy annotation.", null);
      throw new UnableToCompleteException();
    }    

    JPackage interfacePackage = proxyInterface.getPackage();
    String packageName = interfacePackage == null ? "" : interfacePackage.getName();
    String implClassName = presenterClassName + proxyInterface.getSimpleSourceName() + "Impl";
    String generatedClassName = packageName + "." + implClassName;

    PrintWriter printWriter = ctx.tryCreate(logger, packageName, implClassName);
    if (printWriter == null) {
      // We've already created it, so nothing to do
    } else {
      ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(
          packageName, implClassName);

      JClassType typeClass = oracle.findType( typeClassName );
      JClassType revealContentHandlerClass = oracle.findType( revealContentHandlerClassName );
      JClassType providerClass = oracle.findType( providerClassName );
      JClassType asyncProviderClass = oracle.findType( asyncProviderClassName );
      
      // TODO cleanup imports
      composerFactory.addImport(GWT.class.getCanonicalName());  // Obsolete?
      composerFactory.addImport(Inject.class.getCanonicalName());  // Obsolete?
      composerFactory.addImport(Provider.class.getCanonicalName());
      composerFactory.addImport(AsyncProvider.class.getCanonicalName());
      composerFactory.addImport(EventBus.class.getCanonicalName());
      composerFactory.addImport(StandardProvider.class.getCanonicalName());
      composerFactory.addImport(CodeSplitProvider.class.getCanonicalName());
      composerFactory.addImport(CodeSplitBundleProvider.class.getCanonicalName());
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
      writer.println( gingectorClassName + " injector = (" + gingectorClassName + ")ginjector;"  );
      writer.println( "EventBus eventBus = injector.getEventBus();"  );
      writer.println( "ProxyFailureHandler failureHandler = injector.getProxyFailureHandler();" );
      
      if( proxyCodeSplitAnnotation == null && proxyCodeSplitBundleAnnotation == null ) {
        // StandardProvider
        
        // Find the appropriate get method in the Ginjector
        String methodName = null;
        for( JMethod method : ginjectorClass.getMethods() ) {
          JParameterizedType returnType = method.getReturnType().isParameterized();
          if( method.getParameters().length == 0 &&
              returnType != null && 
              returnType.isAssignableTo( providerClass ) &&
              returnType.getTypeArgs()[0].isAssignableTo(presenterClass) ) {
            methodName = method.getName();
            break;
          }
        }
        if( methodName == null ) {
          logger.log(TreeLogger.ERROR, "The Ginjector '"+ gingectorClassName + 
              "' does not have a get() method returning 'Provider<"+presenterClassName+
              ">'. This is required when using @" + ProxyStandard.class.getSimpleName() + ".", null);      
          throw new UnableToCompleteException();
        }
        
        writer.println( "presenter = new StandardProvider<" + presenterClassName + ">( injector." +
            methodName + "() );" );
      }
      else if( proxyCodeSplitAnnotation != null ) {
        // CodeSplitProvider        
        
        // Find the appropriate get method in the Ginjector
        String methodName = null;
        for( JMethod method : ginjectorClass.getMethods() ) {
          JParameterizedType returnType = method.getReturnType().isParameterized();
          if( method.getParameters().length == 0 &&
              returnType != null && 
              returnType.isAssignableTo( asyncProviderClass ) &&
              returnType.getTypeArgs()[0].isAssignableTo(presenterClass) ) {
            methodName = method.getName();
            break;
          }
        }
        if( methodName == null ) {
          logger.log(TreeLogger.ERROR, "The Ginjector '"+ gingectorClassName + 
              "' does not have a get() method returning 'AsyncProvider<"+presenterClassName+
              ">'. This is required when using @" + ProxyCodeSplit.class.getSimpleName() + ".", null);      
          throw new UnableToCompleteException();
        }
        
        writer.println( "presenter = new CodeSplitProvider<" + presenterClassName + ">( injector." +
            methodName + "() );" );
      }
      else {
        // CodeSplitBundleProvider

        String bundleClassName = proxyCodeSplitBundleAnnotation.bundleClass().getCanonicalName();
        JClassType bundleClass = oracle.findType(bundleClassName);

        if( bundleClass == null ) {
          logger.log(TreeLogger.ERROR, "Cannot find the bundle class '" + bundleClassName + 
              ", used with @" + ProxyCodeSplitBundle.class.getSimpleName() +
              " on presenter '", null);      
          throw new UnableToCompleteException();
        }
        
        // Find the appropriate get method in the Ginjector
        String methodName = null;
        for( JMethod method : ginjectorClass.getMethods() ) {
          JParameterizedType returnType = method.getReturnType().isParameterized();
          if( method.getParameters().length == 0 &&
              returnType != null && 
              returnType.isAssignableTo( asyncProviderClass ) &&
              returnType.getTypeArgs()[0].isAssignableTo(bundleClass) ) {
            methodName = method.getName();
            break;
          }
        }
        if( methodName == null ) {
          logger.log(TreeLogger.ERROR, "The Ginjector '"+ gingectorClassName + 
              "' does not have a get() method returning 'AsyncProvider<"+bundleClassName+
              ">'. This is required when using @" + ProxyCodeSplitBundle.class.getSimpleName() + 
              " on presenter '" + presenterClassName + "'.", null);      
          throw new UnableToCompleteException();
        }
        
        writer.println( "presenter = new CodeSplitBundleProvider<" + presenterClassName + ", " +
            bundleClassName + ">( injector." + methodName + "(), " + proxyCodeSplitBundleAnnotation.id() + ");" );
      }
      
      writer.println( "revealContentHandler = new RevealContentHandler<" + presenterClassName + ">( failureHandler, this );" );      
      for( JField field : presenterClass.getFields() ) {
        ContentSlot annotation = field.getAnnotation( ContentSlot.class );
        JParameterizedType parameterizedType = field.getType().isParameterized();
        if( annotation != null ) {
          if( !field.isStatic() || 
              parameterizedType == null || 
              !parameterizedType.isAssignableTo( typeClass ) ||
              !parameterizedType.getTypeArgs()[0].isAssignableTo(revealContentHandlerClass) )
            logger.log(TreeLogger.WARN, "Found the annotation @" + ContentSlot.class.getSimpleName() + " on the invalid field '"+presenterClassName+"."+field.getName()+
                "'. Field must be static and its type must be Type<RevealContentHandler<?>>. Skipping this field.", null);      
          else
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
