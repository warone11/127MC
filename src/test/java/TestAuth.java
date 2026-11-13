
import com.warone11.auth.security.PasswordHasher;

public class TestAuth {
    public static void main(String[] args) {
        PasswordHasher passwordHasher = new PasswordHasher();
        String[] passwords = new String[50];
        for(int i=0;i<passwords.length;i++){
            passwords[i]=generateRandomPassword();
        }
        int hashingTime=0;
        int verifyTime=0;
        for(String password:passwords){
            long start = System.nanoTime();
            String hashedPassword = passwordHasher.hash(password);
            long hashCompleted = System.nanoTime();
            passwordHasher.verify(password, hashedPassword);
            long verifyCompleted = System.nanoTime();
            hashingTime+=(hashCompleted-start)/1000000l;
            verifyTime+=(verifyCompleted-hashCompleted)/1000000l;
            System.out.println("Create " + (hashCompleted - start)/1000000l + " ms\tVerify"+(verifyCompleted-hashCompleted)/1000000l+" ms");
        }
        hashingTime/=passwords.length;
        verifyTime/=passwords.length;
        System.out.println("Create avg " + hashingTime + " ms Verify avg "+verifyTime+" ms");
    }
    public static String generateRandomPassword(){
        int size= (int)(Math.random()*15)+5;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(size);
        for(int i=0;i<size;i++){
            sb.append(chars.charAt((int)(Math.random()*chars.length())));
        }
        return sb.toString();
    }
}
