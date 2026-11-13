

import java.sql.Connection;

import com.warone11.database.DatabaseManager;
import com.warone11.database.models.Repository;
import com.warone11.database.models.Server;

public class TestDB {
    public static void main(String[] args) {
        
        Connection con = DatabaseManager.getConnection();
        // final Repository<User> userRepository = new Repository<>(User.PREFAB, con, User.TABLE_NAME, User.COLUMN_ARRAY);
        // User testUser=new User(UUID.randomUUID(), "test", null, null);
        // userRepository.insert(testUser);
        // final Repository<SessionToken> sessionTokenRepository = new Repository<>(SessionToken.PREFAB, con, SessionToken.TABLE_NAME, SessionToken.COLUMN_ARRAY);
        // sessionTokenRepository.insert(new SessionToken(UUID.randomUUID(), "test","test", null, null, null, testUser.getUuid()));
        // System.out.println(sessionTokenRepository.selectAll());
        // Repository<Role> roleRepository = new Repository<>(Role.PREFAB, con, Role.TABLE_NAME, Role.COLUMN_ARRAY);
        // Role role =  roleRepository.select(Pager.of(1, 1)).get(0);
        // System.out.println(role);
        Repository<Server> serverRepository = new Repository<>(Server.PREFAB, con, Server.TABLE_NAME, Server.COLUMN_ARRAY);
        // Server server = new Server(UUID.randomUUID(), "test", Server.ServerType.paper, null, false);
        // serverRepository.insert(server);
        System.out.println(serverRepository.selectAll());
        // Repository<UserServerRole> userServerRoleRepository = new Repository<>(UserServerRole.PREFAB, con, UserServerRole.TABLE_NAME, UserServerRole.COLUMN_ARRAY);
        // userServerRoleRepository.insert(new UserServerRole(testUser.getUuid(),server.getUuid(), role.getUuid()));
        // System.out.println(userServerRoleRepository.selectAll());
        // Repository<ServerAction> serverActionRepository = new Repository<>(ServerAction.PREFAB, con, ServerAction.TABLE_NAME, ServerAction.COLUMN_ARRAY);
        // serverActionRepository.insert(new ServerAction(UUID.randomUUID(), "test", null, testUser.getUuid(), server.getUuid(), ServerAction.ActionType.create));
        // System.out.println(serverActionRepository.selectAll());
        
    }
}
