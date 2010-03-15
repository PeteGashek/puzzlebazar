package com.puzzlebazar.server.currentuser;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dyuproject.openid.OpenIdUser;
import com.dyuproject.openid.RelyingParty;
import com.dyuproject.openid.ext.AxSchemaExtension;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class OpenIdServlet extends HttpServlet
{

  /**
   * 
   */
  private static final long serialVersionUID = 6361607086182086663L;

  private final CurrentUserManager userSessionManager;
  
  @Inject
  public OpenIdServlet( CurrentUserManager userSessionManager ) {
    this.userSessionManager = userSessionManager;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws IOException, ServletException
  {
    OpenIdUser user = (OpenIdUser)request.getAttribute(OpenIdUser.ATTR_NAME);
    userSessionManager.set( user );    
  }

  @Override
  public void init() throws ServletException {
    super.init();

    // One-time initialization of RelyingParty.
    RelyingParty.getInstance()
    .addListener(new AxSchemaExtension()
        .addExchange("email")
        .addExchange("country")
        .addExchange("language")
    );    
  }
  

}
