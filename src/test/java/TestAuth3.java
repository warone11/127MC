import com.warone11.auth.service.SessionService;

public class TestAuth3 {
    public static void main(String[] args) {
        SessionService sessionService= new SessionService();
        try {
            System.out.println(sessionService.VerifySession("mKy3kEf630zhXfnwtAkbiGNHr0YHNkHMR76LiF/JseE="));
        } catch (Exception e) {
            System.out.println("Error: "+e);
        }
        
    }
}
