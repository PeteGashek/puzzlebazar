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

package com.puzzlebazar.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dyuproject.openid.Constants;
import com.dyuproject.openid.OpenIdServletFilter;
import com.dyuproject.openid.OpenIdUser;
import com.dyuproject.openid.RelyingParty;
import com.dyuproject.openid.YadisDiscovery;
import com.dyuproject.openid.ext.AxSchemaExtension;
import com.dyuproject.util.http.UrlEncodedParameterMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.puzzlebazar.server.model.Session;

@Singleton
public class OpenIdServlet extends HttpServlet
{
  /**
   * 
   */
  private static final long serialVersionUID = 6314103753523555658L;
  
  private static final String CLOSE_POPUP_URI = "/openid/ClosePopup.html";
  private final Session.Manager sessionManager;

  @Inject
  public OpenIdServlet( Session.Manager sessionManager ) {
    this.sessionManager = sessionManager;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws IOException, ServletException
  {
    doPost(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws IOException, ServletException
  {
    String provider = request.getParameter("provider");
    if(provider!=null)
    {
      // If the ui supplies a number of buttons for default providers. 
      // This will speed up the openid process by skipping discovery. 
      // The override is done by adding the OpenIdUser to the request attribute.
      if(provider.equals("google"))
      {
        OpenIdUser user = OpenIdUser.populate("https://www.google.com/accounts/o8/id", 
            YadisDiscovery.IDENTIFIER_SELECT,   
        "https://www.google.com/accounts/o8/ud");
        request.setAttribute(OpenIdUser.ATTR_NAME, user);

      }
      else if(provider.equals("yahoo"))
      {
        OpenIdUser user = OpenIdUser.populate("http://yahoo.com/", 
            YadisDiscovery.IDENTIFIER_SELECT, 
        "https://open.login.yahooapis.com/openid/op/auth");
        request.setAttribute(OpenIdUser.ATTR_NAME, user);
      }
    }    


    RelyingParty relyingParty = RelyingParty.getInstance();

    String errorMsg = OpenIdServletFilter.DEFAULT_ERROR_MSG;
    try
    {
      OpenIdUser user = relyingParty.discover(request);
      if(user==null)
      {                
        if(RelyingParty.isAuthResponse(request))
        {
          // authentication timeout                    
          response.sendRedirect(request.getRequestURI());
        }
        else
        {
          // set error msg if the openid_identifier is not resolved.
          if(request.getParameter(relyingParty.getIdentifierParameter())!=null)
            request.setAttribute(OpenIdServletFilter.ERROR_MSG_ATTR, errorMsg);

          // TODO: Simply close the window? Alert?
          request.getRequestDispatcher(CLOSE_POPUP_URI).forward(request, response);
        }
        return;
      }

      if(user.isAuthenticated())
      {
        // user already authenticated
        request.getRequestDispatcher(CLOSE_POPUP_URI).forward(request, response);
        return;
      }

      if(user.isAssociated() && RelyingParty.isAuthResponse(request))
      {
        // verify authentication
        if(relyingParty.verifyAuth(user, request, response))
        {
          // authenticated                    
          sessionManager.setUser( user );
          
          // redirect to home to remove the query params instead of doing:
          request.getRequestDispatcher(CLOSE_POPUP_URI).forward(request, response);
        }
        else
        {
          // failed verification

          // TODO: Simply close the window? Alert?
          request.getRequestDispatcher(CLOSE_POPUP_URI).forward(request, response);
        }
        return;
      }

      // associate and authenticate user
      StringBuffer url = request.getRequestURL();
      String trustRoot = url.substring(0, url.indexOf("/", 9));
      String realm = url.substring(0, url.lastIndexOf("/"));
      String returnTo = url.toString();            
      if(relyingParty.associateAndAuthenticate(user, request, response, trustRoot, realm, 
          returnTo))
      {
        return;
      }          
    }
    catch(UnknownHostException uhe)
    {
      System.err.println("not found");
      errorMsg = OpenIdServletFilter.ID_NOT_FOUND_MSG;
    }
    catch(FileNotFoundException fnfe)
    {
      System.err.println("could not be resolved");
      errorMsg = OpenIdServletFilter.DEFAULT_ERROR_MSG;
    }
    catch(Exception e)
    {
      e.printStackTrace();
      errorMsg = OpenIdServletFilter.DEFAULT_ERROR_MSG;
    }
    request.setAttribute(OpenIdServletFilter.ERROR_MSG_ATTR, errorMsg);

    // TODO: Simply close the window? Alert?
    request.getRequestDispatcher(CLOSE_POPUP_URI).forward(request, response);
  }

  @Override
  public void init() throws ServletException {
    super.init();

    // One-time initialization of RelyingParty.
    RelyingParty.getInstance()
    .addListener(new AxSchemaExtension()
    .addExchange("email")
    .addExchange("language")
    )
    .addListener(new RelyingParty.Listener()
    {
      @Override
      public void onDiscovery(OpenIdUser user, HttpServletRequest request)
      {
      }            

      @Override
      public void onPreAuthenticate(OpenIdUser user, HttpServletRequest request,
          UrlEncodedParameterMap params)
      {
        if("true".equals(request.getParameter("popup")))
        {                
          String returnTo = params.get(Constants.OPENID_TRUST_ROOT) + request.getRequestURI();                    
          params.put(Constants.OPENID_RETURN_TO, returnTo);
          params.put(Constants.OPENID_REALM, returnTo);                    
          params.put("openid.ns.ui", "http://specs.openid.net/extensions/ui/1.0");
          params.put("openid.ui.mode", "popup");
        }
      }            

      @Override
      public void onAuthenticate(OpenIdUser user, HttpServletRequest request)
      {
      }            

      @Override
      public void onAccess(OpenIdUser user, HttpServletRequest request)
      {
      }  
    });

  }


}
