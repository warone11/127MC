import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.UUID;

import com.warone11.auth.security.PasswordHasher;
import com.warone11.auth.security.TokenGenerator;
import com.warone11.auth.security.TokenHasher;
import com.warone11.auth.service.SessionService;
import com.warone11.database.RepositoryProvider;
import com.warone11.database.models.AccessToken;
import com.warone11.database.models.User;

public class TestAuth2 {
    public static void main(String[] args) {
        var userRepo= RepositoryProvider.userRepository;
        var hasher = new PasswordHasher();
        var user =new User(UUID.randomUUID(), "username",hasher.hash("password") , null);
        userRepo.insert(user);
        var accessTokenRepo = RepositoryProvider.accessTokenRepository;
        String accessToken= TokenGenerator.generate();
        accessTokenRepo.insert(new AccessToken(UUID.randomUUID(), "test", TokenHasher.hash(accessToken),user.getUuid() , null, null, true));
        var sessionService= new SessionService();
        //password incorrect
        try {
            String tokenDeSession=sessionService.login("username", "invalidPassword");
            System.out.println("Inicio sesion da este token de sesion: "+tokenDeSession);
            System.out.println("El hash del token de sesion es: "+TokenHasher.hash(tokenDeSession));
        } catch (Exception e) {
            System.out.println("Error: "+e);
        }
        //password correct
        try {
            String tokenDeSession=sessionService.login("username", "password");
            System.out.println("Inicio sesion da este token de sesion: "+tokenDeSession);
            System.out.println("El hash del token de sesion es: "+TokenHasher.hash(tokenDeSession));
        } catch (Exception e) {
            System.out.println("Error: "+e);
        }
        //incorrect token
        try{
            String tokenDeSession=sessionService.login(TokenGenerator.generate());
            System.out.println("Inicio de sesion da este token de sesion: "+tokenDeSession);
            System.out.println("El hash del token de sesion es: "+TokenHasher.hash(tokenDeSession));
        }catch(Exception e){
            System.out.println("Error: "+e);
        }
        //correct token
        try{
            String tokenDeSession=sessionService.login(accessToken);
            System.out.println("Inicio de sesion da este token de sesion: "+tokenDeSession);
            System.out.println("El hash del token de sesion es: "+TokenHasher.hash(tokenDeSession));
        }catch(Exception e){
            System.out.println("Error: "+e);
        }
        var sessionRepo = RepositoryProvider.sessionTokenRepository;
        System.out.println(sessionRepo.selectAll());
        System.out.println();
    }
}
