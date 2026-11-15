package com.warone11.auth.service;

import java.util.Optional;
import java.util.UUID;

import com.warone11.auth.exceptions.InternalLoginException;
import com.warone11.auth.exceptions.InvalidCredentialsException;
import com.warone11.auth.exceptions.InvalidTokenException;
import com.warone11.auth.exceptions.RevokedTokenException;
import com.warone11.auth.security.PasswordHasher;
import com.warone11.auth.security.TokenGenerator;
import com.warone11.auth.security.TokenHasher;
import com.warone11.database.RepositoryProvider;
import com.warone11.database.models.AccessToken;
import com.warone11.database.models.SessionToken;
import com.warone11.database.models.User;
import com.warone11.database.utils.SQLModifiers.ComparingColumns;

public class SessionService {
    protected PasswordHasher passwordHasher= new PasswordHasher();
    private final String fakeHash= passwordHasher.hash("VOJFSZJF");
    /**
     * Logs in a user using a AccesToken and returns a session token <br>
     * WARNING: The returned value is a secret credential.
     * Never persist or log the raw token. It must only be transmitted to the client through a secure mechanism.
     * @param token the token to use for login
     * @return the session token <br>
     * @throws InvalidTokenException if the token is invalid
     * @throws RevokedTokenException if the token is revoked
     * @throws InternalLoginException if an internal error occurs in the database
     */
    
    public String login(String token) throws InvalidTokenException, RevokedTokenException{
        if(token==null) throw new IllegalArgumentException("token cannot be null");

        String hash=TokenHasher.hash(token);

        var accTokenRepo=RepositoryProvider.accessTokenRepository;
        //get the acess token from the database where the hash is equal to the hash of the token
        Optional<AccessToken> result = accTokenRepo.selectFirst(ComparingColumns.of("hash", ComparingColumns.Comparator.EQUALS, hash));
        //failed to get the value from the database
        if(result==null)        throw new InternalLoginException();
        //no value was found in the database
        if(result.isEmpty())    throw new InvalidTokenException();
        //get the acess token (the value was found)
        AccessToken accToken=result.get();

        if(!accToken.isActive()||accToken.checkExpiration()) throw new RevokedTokenException();
        //create a session and return its insecure token value
        return createSession(accToken.getUserUUID(), "test");
    }
    /**
     * Creates a session for a user
     * @param userUUID the user UUID
     * @param name the name of the session
     * @return the session token
     */
    protected String createSession(UUID userUUID, String name){
        if(userUUID==null) throw new IllegalArgumentException("userUUID cannot be null");
        var sessionRepo=RepositoryProvider.sessionTokenRepository;

        //generate a session token and the hash
        String sessionToken=TokenGenerator.generate();
        var sessionTokenHash=TokenHasher.hash(sessionToken);
        //create and DO with the hash
        var sessionTokenDO= new SessionToken(UUID.randomUUID(),name,sessionTokenHash,null,null,null,true,userUUID);
        //if the session token was inserted into the database, return the secret session token
        if(sessionRepo.insert(sessionTokenDO)){
            return sessionToken;
        }
        else{
            //otherwise throw an exception due to an error with the database 
            throw new InternalLoginException();
        }
        
    }
    /**
     * Logs in a user using a username and password and returns a session token <br>
     * WARNING: The returned value is a secret credential.
     * Never persist or log the raw token. It must only be transmitted to the client through a secure mechanism.
     * @param username username of the user
     * @param password password of the user
     * @return the session token <br>
     * @throws InvalidCredentialsException if the credentials are invalid
     * @throws InternalLoginException if an internal error occurs in the database
     */
    public String login(String username, String password) throws InvalidCredentialsException{
        if(username==null || username.isEmpty()) throw new IllegalArgumentException("invalid username");
        if(password==null || password.isEmpty()) throw new IllegalArgumentException("invalid password");
        var userRepo = RepositoryProvider.userRepository;
        // gets the user where the username column is equal to the username
        Optional<User> result = userRepo.selectFirst(ComparingColumns.of("username", ComparingColumns.Comparator.EQUALS, username));
        // internal error in the database
        if(result==null)        throw new InternalLoginException();
        // no user was found
        if(result.isEmpty()){
            passwordHasher.verify(password, fakeHash);
            throw new InvalidCredentialsException();
        }    
        // get the user (user was found)
        var user=result.get();
        // gets the password hash
        String passwordHash = user.getPasswordHash();
        // if the user has no password set (only token login configured)
        if(passwordHash==null){
            passwordHasher.verify(password, fakeHash);
            throw new InvalidCredentialsException();
        } 
        if(passwordHasher.verify(password, passwordHash)){
            // create a session and return its insecure token value
            return createSession(user.getUuid(), "test");
        }
        else{
            // otherwise throw an exception due to invalid credentials
            throw new InvalidCredentialsException();
        }


    }
    
}
