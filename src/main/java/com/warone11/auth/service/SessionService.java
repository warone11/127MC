package com.warone11.auth.service;

import java.util.UUID;

import com.warone11.auth.Exceptions.InternalLoginException;
import com.warone11.auth.Exceptions.InvalidTokenException;
import com.warone11.auth.Exceptions.RevokedTokenException;
import com.warone11.auth.security.TokenGenerator;
import com.warone11.auth.security.TokenHasher;
import com.warone11.database.RepositoryProvider;
import com.warone11.database.models.SessionToken;
import com.warone11.database.utils.SQLModifiers.ComparingColumns;

public class SessionService {
    public String login(String token) throws InvalidTokenException, RevokedTokenException{
        if(token==null) throw new IllegalArgumentException("token cannot be null");
        String hash=TokenHasher.hash(token);
        var accTokenRepo=RepositoryProvider.accessTokenRepository;
        var sessionRepo=RepositoryProvider.sessionTokenRepository;

        if(!accTokenRepo.exist(ComparingColumns.of("hash", ComparingColumns.Comparator.EQUALS, hash)))
        throw new InvalidTokenException();
        var accToken=accTokenRepo.select(ComparingColumns.of("hash", ComparingColumns.Comparator.EQUALS, hash)).get(0);

        if(!accToken.isActive()||accToken.checkExpiration()) throw new RevokedTokenException();
        //valid token, creating session token
        String sessionToken=TokenGenerator.generate();
        var sessionTokenHash=TokenHasher.hash(sessionToken);
        var sessionTokenDO= new SessionToken(UUID.randomUUID(),"test",sessionTokenHash,null,null,null,true,accToken.getUserUUID());
        if(sessionRepo.insert(sessionTokenDO)){
            return sessionToken;
        }
        else{
            throw new InternalLoginException();
        }
    }
}
