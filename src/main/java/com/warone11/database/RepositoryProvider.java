package com.warone11.database;

import com.warone11.database.models.AccessToken;
import com.warone11.database.models.Repository;
import com.warone11.database.models.Role;
import com.warone11.database.models.Server;
import com.warone11.database.models.ServerAction;
import com.warone11.database.models.SessionToken;
import com.warone11.database.models.User;
import com.warone11.database.models.UserServerRole;

public class RepositoryProvider {
    public static final Repository<User> userRepository = new Repository<>(User.PREFAB, DatabaseManager.getConnection(), User.TABLE_NAME, User.COLUMN_ARRAY);
    public static final Repository<SessionToken> sessionTokenRepository = new Repository<>(SessionToken.PREFAB, DatabaseManager.getConnection(), SessionToken.TABLE_NAME, SessionToken.COLUMN_ARRAY);
    public static final Repository<Role> roleRepository = new Repository<>(Role.PREFAB, DatabaseManager.getConnection(), Role.TABLE_NAME, Role.COLUMN_ARRAY);
    public static final Repository<Server> serverRepository = new Repository<>(Server.PREFAB, DatabaseManager.getConnection(), Server.TABLE_NAME, Server.COLUMN_ARRAY);
    public static final Repository<UserServerRole> userServerRoleRepository = new Repository<>(UserServerRole.PREFAB, DatabaseManager.getConnection(), UserServerRole.TABLE_NAME, UserServerRole.COLUMN_ARRAY);
    public static final Repository<ServerAction> serverActionRepository = new Repository<>(ServerAction.PREFAB, DatabaseManager.getConnection(), ServerAction.TABLE_NAME, ServerAction.COLUMN_ARRAY);
    public static final Repository<AccessToken> accessTokenRepository = new Repository<>(AccessToken.PREFAB, DatabaseManager.getConnection(), AccessToken.TABLE_NAME, AccessToken.COLUMN_ARRAY);
    
}
