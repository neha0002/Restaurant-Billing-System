import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/Kasturi_rbs", 
                "root", 
                "MySQL2002@"
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkLoginToChangePassword(String username, String currentPassword, String type) {
        
        return username.equals("admin") && currentPassword.equals("admin123");
    }

    public int changePassword(String newPassword, String type) {
        
        return 1; 
    }

    public void changePasswordEmp(String newPassword) {
        
        System.out.println("Password updated to: " + newPassword);
    }
}
