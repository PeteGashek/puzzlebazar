package com.puzzlebazar.server.model;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.helper.DAOBase;
import com.puzzlebazar.shared.ObjectNotFoundException;
import com.puzzlebazar.shared.model.InvalidEditException;
import com.puzzlebazar.shared.model.User;
import com.puzzlebazar.shared.model.UserImpl;


/**
 * The class responsible of managing cache and datastore
 * storage of {@link UserImplServer} objects.
 * 
 * @author Philippe Beaudoin
 */
public class UserDAO extends DAOBase {

  static {
    ObjectifyService.register(UserImpl.class);
    ObjectifyService.register(EmailToEmail.class);
  }

  private static final Object ADMINISTRATOR_EMAIL = "philippe.beaudoin@gmail.com";

  @Inject
  public UserDAO() {
  }

  /**
   * Access the user given a user key.
   * 
   * @param id The id for the user to get.
   * @return The {@link User} obtained from the datastore, or {@code null} if none found.
   */
  public User getUser(
      final long id) {
    return getUser( new Key<UserImpl>(UserImpl.class, id) );
  }

  /**
   * Access the user given a user key.
   * 
   * @param key The key for the user to get.
   * @return The {@link User} obtained from the datastore, or {@code null} if none found.
   */
  public User getUser(Key<UserImpl> key) {   

    UserImpl user = null;
    try {
      user = ofy().get( key );
    }
    catch( NotFoundException e ) {
      user = null;
    }
    
    return updateAdministrator(user);
  }

  /**
   * Modify the information of a specific {@link User}.
   * 
   * @param updatedUser The updated information on the {@link User}.
   * @throws ObjectNotFoundException If the object is not found.
   * @throws InvalidEditException If the user cannot be edited because a non-editable field doesn't match.
   */
  public void modifyUser(
      final User updatedUser) throws ObjectNotFoundException, InvalidEditException {

    // TODO Validate that the current user has the required privileges!
    long userId = updatedUser.getId();

    // TODO Get rid of ObjectifyService, inject ObjectifyFactory instead
    Objectify ofyTxn = ObjectifyService.beginTransaction();
    try {
      UserImpl user = ofyTxn.get( UserImpl.class, userId );      
      user.editFrom( updatedUser );
      ofyTxn.put( user );
      ofyTxn.getTxn().commit();
    }
    catch( NotFoundException e ) {
      throw new ObjectNotFoundException( "User not found, can't modify. Id = " + userId );
    }
    finally {
      if(ofyTxn.getTxn().isActive())
        ofyTxn.getTxn().rollback();
    }      

  }

  /**
   * Access the user given an email, or create it if not found.
   * 
   * @param emailQuery The email of the user to get or create.
   * @param locale The locale to use for this user, if it needs to be created.
   * @return The {@link User} obtained from the given email.
   */
  public User getUserByEmailOrCreate(
      final String emailQuery,
      final String locale ) {

    if( emailQuery == null )
      return null;

    // TODO Get rid of ObjectifyService, inject ObjectifyFactory instead
    Objectify ofyTxn = ObjectifyService.beginTransaction();

    UserImpl user = null;
    int retry = 0;
    while( user == null && retry < 5 ) {
      try {
        EmailToEmail emailToUser = ofyTxn.get( EmailToEmail.class, emailQuery );
        ofyTxn.getTxn().commit();
        user = ofy().get( emailToUser.getUserKey() );
      }
      catch( NotFoundException e ) {
        user = new UserImpl( emailQuery );
        user.setLocale( locale );
        ofy().put( user );
        EmailToEmail emailToUser = new EmailToEmail( emailQuery, user.createKey() );
        ofyTxn.put( emailToUser );
        ofyTxn.getTxn().commit();
      }
      finally {
        if(ofyTxn.getTxn().isActive()) {
          ofyTxn.getTxn().rollback();
          if( user != null )
            ofy().delete( user );
        }
      }      
    }
    
    assert user != null : "Could not fetch user";

    return updateAdministrator(user);
  }

  /**
   * Makes sure the passed {@link User} administrator field is set to 
   * the right value.
   * 
   * @param user The user on which to update the administrator field. Can be {@code null}.
   * @return The user with the valid administrator field, or {@code null} id {@code null} was passed in.
   */
  private User updateAdministrator(UserImpl user) {
    if( user == null )
      return null;
    if( user.getEmail().equals(ADMINISTRATOR_EMAIL) )
      user.setAdministrator(true);
    else
      user.setAdministrator(false);
    return user;
  }

}
