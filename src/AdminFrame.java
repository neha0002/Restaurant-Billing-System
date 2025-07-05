import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class AdminFrame extends JFrame {

    private JTextField usernameField, passwordField;
    private JTextArea reportArea;
    private JPasswordField oldPasswordField, newpasswordField;
    private String username = "admin";
    private boolean flag = false;
    private ConnectionFactory db = new ConnectionFactory();

    public AdminFrame() {
        setTitle("KASTURI Admin Panel");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.LIGHT_GRAY);

        JLabel heading = new JLabel("Admin Panel", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        heading.setBounds(200, 10, 200, 30);
        add(heading);

        JLabel userLabel = new JLabel("New Admin Username:");
        userLabel.setBounds(50, 60, 150, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(200, 60, 150, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("New Admin Password:");
        passLabel.setBounds(50, 100, 150, 25);
        add(passLabel);

        passwordField = new JTextField();
        passwordField.setBounds(200, 100, 150, 25);
        add(passwordField);

        JButton addAdminBtn = new JButton("Add Admin");
        addAdminBtn.setBounds(370, 60, 150, 30);
        add(addAdminBtn);

        JButton deleteAdminBtn = new JButton("Delete Admin");
        deleteAdminBtn.setBounds(370, 100, 150, 30);
        add(deleteAdminBtn);

        JButton viewReportBtn = new JButton("View Sales Report");
        viewReportBtn.setBounds(50, 150, 200, 30);
        add(viewReportBtn);

        JButton changePasswordBtn = new JButton("Change Password");
        changePasswordBtn.setBounds(300, 150, 200, 30);
        add(changePasswordBtn);

        oldPasswordField = new JPasswordField();
        oldPasswordField.setBounds(200, 200, 150, 25);
        add(oldPasswordField);

        JLabel oldPassLabel = new JLabel("Old Password:");
        oldPassLabel.setBounds(50, 200, 150, 25);
        add(oldPassLabel);

        newpasswordField = new JPasswordField();
        newpasswordField.setBounds(200, 240, 150, 25);
        add(newpasswordField);

        JLabel newPassLabel = new JLabel("New Password:");
        newPassLabel.setBounds(50, 240, 150, 25);
        add(newPassLabel);

        JButton closeBtn = new JButton("Close");
        closeBtn.setBounds(400, 500, 100, 30);
        closeBtn.addActionListener(e -> dispose());
        add(closeBtn);

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setBounds(50, 280, 500, 200);
        add(scrollPane);

        addAdminBtn.addActionListener(e -> addAdmin());
        deleteAdminBtn.addActionListener(e -> deleteAdmin());
        viewReportBtn.addActionListener(e -> viewSalesReport());
        changePasswordBtn.addActionListener(e -> changePassword());
    }

    private void changePassword() {
        String currentPassword = new String(oldPasswordField.getPassword());
        String newPassword = new String(newpasswordField.getPassword());
        try {
            if (db.checkLoginToChangePassword(username, currentPassword)) {
                int result = db.changePassword(newPassword);
                if (result == 1) {
                    JOptionPane.showMessageDialog(this, "Password Changed. Please login again.");
                    dispose();
                    flag = true;
                    new DashboardFrame(username).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Password Not Changed...Error!!!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Current password mismatch");
                oldPasswordField.requestFocus();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addAdmin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both fields.");
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Kasturi_rbs", "root", "MySQL2002@")) {
            String sql = "INSERT INTO admin_users (username, password) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Admin added successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding admin.");
        }
    }

    private void deleteAdmin() {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a username to delete.");
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Kasturi_rbs", "root", "MySQL2002@")) {
            String sql = "DELETE FROM admin_users WHERE username = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Admin deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Admin not found.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting admin.");
        }
    }

    private void viewSalesReport() {
        reportArea.setText("");
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Kasturi_rbs", "root", "MySQL2002@")) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM order_history ORDER BY order_date DESC");

            while (rs.next()) {
                String line = "Bill No: " + rs.getString("bill_no") +
                              ", Item: " + rs.getString("item") +
                              ", Qty: " + rs.getInt("qty") +
                              ", Amount: Rs." + rs.getDouble("amount") +
                              ", Date: " + rs.getString("order_date") + "\n";
                reportArea.append(line);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            reportArea.setText("Failed to load sales report.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminFrame().setVisible(true));
    }
}


class ConnectionFactory {
    public boolean checkLoginToChangePassword(String user, String pass) {
        return "admin".equals(user) && "admin123".equals(pass); 
    }

    public int changePassword(String newPassword) {
        System.out.println("Password changed to: " + newPassword);
        return 1; 
    }
}
