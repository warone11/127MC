import com.warone11.auth.service.SessionService;

public class TestAuth3 {
    public static void main(String[] args) {
        SessionService sessionService= new SessionService();
        try {
            System.out.println(sessionService.VerifySession("xLLEkmhHPXfd9/d/tVsBXvNBJl4jMEkc3j3q3ozPFf8="));
        } catch (Exception e) {
            System.out.println("Error: "+e);
        }
        
    }
}
