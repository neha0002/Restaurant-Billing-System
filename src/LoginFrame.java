import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JRadioButton adminRadio;
    private JRadioButton userRadio;
    private JButton loginButton, cancelButton;

    public LoginFrame() {
        setTitle("Login - Kasturi Restaurant");
        setSize(450, 400);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        
        JLabel logoLabel = new JLabel();
        try {
            ImageIcon logoIcon = new ImageIcon("logo.png"); // logo.png should be in project root
            Image scaledLogo = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledLogo));
        } catch (Exception e) {
            logoLabel.setText("Logo");
        }
        logoLabel.setBounds(170, 10, 100, 100);
        add(logoLabel);

        JLabel title = new JLabel("Kasturi Restaurant");
        title.setFont(new Font("Serif", Font.BOLD, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(120, 110, 200, 30);
        add(title);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(80, 150, 100, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(180, 150, 180, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(80, 185, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(180, 185, 180, 25);
        add(passwordField);

        JLabel userTypeLabel = new JLabel("User Type:");
        userTypeLabel.setBounds(80, 220, 100, 25);
        add(userTypeLabel);

        adminRadio = new JRadioButton("Admin");
        adminRadio.setBounds(180, 220, 80, 25);
        userRadio = new JRadioButton("User");
        userRadio.setBounds(260, 220, 80, 25);
        ButtonGroup group = new ButtonGroup();
        group.add(adminRadio);
        group.add(userRadio);
        add(adminRadio);
        add(userRadio);

        loginButton = new JButton("Login");
        loginButton.setBounds(120, 270, 80, 30);
        add(loginButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(220, 270, 80, 30);
        add(cancelButton);

        loginButton.addActionListener(e -> authenticate());
        cancelButton.addActionListener(e -> System.exit(0));
    }

    private void authenticate() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String userType = adminRadio.isSelected() ? "Admin" : userRadio.isSelected() ? "User" : "";

        if (username.isEmpty() || password.isEmpty() || userType.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/Kasturi_rbs", "root", "MySQL2002@"
            );

            String query = "SELECT * FROM login WHERE username=? AND password=? AND usertype=?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, userType);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful");
                dispose();
                new DashboardFrame(username).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid login credentials.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
