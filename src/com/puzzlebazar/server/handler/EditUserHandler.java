package com.puzzlebazar.server.handler;


import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import net.sf.jsr107cache.Cache;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.philbeaudoin.platform.dispatch.server.ActionHandler;
import com.philbeaudoin.platform.dispatch.server.ExecutionContext;
import com.philbeaudoin.platform.dispatch.shared.ActionException;
import com.puzzlebazar.server.currentuser.CurrentUserManager;
import com.puzzlebazar.shared.UserNotFoundException;
import com.puzzlebazar.shared.action.EditUser;
import com.puzzlebazar.shared.action.NoResult;
import com.puzzlebazar.shared.model.User;

public class EditUserHandler implements ActionHandler<EditUser, NoResult> {

  private final CurrentUserManager currentUserManager;
  private final PersistenceManagerFactory persistenceManagerFactory;
  private final Provider<Cache> cache;

  @Inject
  public EditUserHandler(
      final CurrentUserManager currentUserManager,
      final PersistenceManagerFactory persistenceManagerFactory,
      final Provider<Cache> cache ) {
    this.currentUserManager = currentUserManager;
    this.persistenceManagerFactory = persistenceManagerFactory;
    this.cache = cache;
  }

  @Override
  public NoResult execute(
      final EditUser action,
      final ExecutionContext context ) throws ActionException {
    // TODO Validate that the current user has the required privileges!
    User user = action.getUser();
    PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
    try {
      User datastoreUser = persistenceManager.getObjectById( User.class, user.getKey() );
      action.setPreviousUser( new User(datastoreUser) );
      datastoreUser.editFrom( user );
      persistenceManager.makePersistent( datastoreUser );
      cache.get().remove( CurrentUserManager.USER_TOKEN + datastoreUser.getKey() );
    }
    catch( JDOObjectNotFoundException exception ) {
      throw new UserNotFoundException();
    }
    finally {
      persistenceManager.close();
    }
    return null;
  }

  @Override
  public void undo(
      final EditUser action,
      final NoResult result,
      final ExecutionContext context) throws ActionException {    
    // TODO Validate that the current user has the required privileges!
    User user = action.getPreviousUser();
    PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
    try {
      User datastoreUser = persistenceManager.getObjectById( User.class, user.getKey() );
      datastoreUser.editFrom( user );
      persistenceManager.makePersistent( datastoreUser );
      cache.get().remove( CurrentUserManager.USER_TOKEN + datastoreUser.getKey() );
    }
    catch( JDOObjectNotFoundException exception ) {
      throw new UserNotFoundException();
    }
    finally {
      persistenceManager.close();
    }
  }

  @Override
  public Class<EditUser> getActionType() {
    return EditUser.class;
  }
}
