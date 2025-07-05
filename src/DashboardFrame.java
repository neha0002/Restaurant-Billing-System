import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.*;

public class DashboardFrame extends JFrame {

    private JLabel welcomeLabel, timeLabel;
    private JTextArea todoArea;
    private String username;

    public DashboardFrame(String username) {
        this.username = username;
        setTitle("KASTURI Restaurant");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.LIGHT_GRAY);

        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Menu");
        menu.setIcon(loadIcon("menu.png", 16, 16));
        JMenuItem manageMenuItem = new JMenuItem("Manage Menu");
        manageMenuItem.addActionListener(e -> new ManageMenuFrame().setVisible(true));
        menu.add(manageMenuItem);

        JMenu optionsMenu = new JMenu("Options");
        JMenuItem refreshItem = new JMenuItem("Refresh Dashboard");
        refreshItem.addActionListener(e -> {
            dispose();
            new DashboardFrame(username).setVisible(true);
        });
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        optionsMenu.add(refreshItem);
        optionsMenu.add(exitItem);

        JMenu aboutMenu = new JMenu("About us");
        JMenuItem aboutItem = new JMenuItem("About Kasturi");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "KASTURI Restaurant Billing System\n" +
            "Developed as a restaurant management solution.\n" +
            "Features: Billing, Reservation, Staff, Orders, Menu Management.\n" +
            "Developed in Java with MySQL backend.\n\n" +
            "© 2025 Kasturi Systems", "About Us", JOptionPane.INFORMATION_MESSAGE));
        aboutMenu.add(aboutItem);

        menuBar.add(menu);
        menuBar.add(optionsMenu);
        menuBar.add(aboutMenu);

        setJMenuBar(menuBar);

       
        JPanel sidebar = new JPanel(null);
        sidebar.setBounds(0, 0, 160, 540);
        sidebar.setBackground(new Color(55, 55, 55));
        add(sidebar);

        String[] sideOptions = {"Dashboard", "To do", "FAQ", "Logout", "Staff"};
        String[] iconFiles = {"dashboard.png", "todo.png", "faq.png", "logout.png", "staff.png"};
        int y = 50;

        for (int i = 0; i < sideOptions.length; i++) {
            String text = sideOptions[i];
            JButton btn = new JButton(text, loadIcon(iconFiles[i], 20, 20));
            btn.setBounds(20, y, 120, 30);
            btn.setBackground(Color.DARK_GRAY);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);

            if (text.equals("Logout")) {
                btn.setBackground(new Color(255, 204, 204));
                btn.addActionListener(e -> {
                    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        dispose();
                        new LoginFrame().setVisible(true);
                    }
                });
            } else {
                btn.addActionListener(e -> {
                    switch (text) {
                        case "Dashboard":
                            dispose();
                            new DashboardFrame(username).setVisible(true);
                            break;
                        case "To do":
                            JOptionPane.showMessageDialog(this,
                                "- Click 'Reservation' to view table reservations\n" +
                                "- You can manage pending tasks from the 'To do' section on the right");
                            break;
                        case "FAQ":
                            JOptionPane.showMessageDialog(this,
                                "Q1: How to create a bill?\n" +
                                "A: Click on the Billing button in the center and fill in the details.\n\n" +
                                "Q2: How to manage menu items?\n" +
                                "A: Use the Menu > Manage Menu option from the top bar.\n\n" +
                                "Q3: How to check order history?\n" +
                                "A: Click the Order History button.\n\n" +
                                "Q4: How to reserve a table?\n" +
                                "A: Use the Reservation section in the center of dashboard.",
                                "FAQ - Help", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        case "Staff":
                            new ManageStaff().setVisible(true);
                            break;
                    }
                });
            }

            sidebar.add(btn);
            y += 45;
        }

       
        welcomeLabel = new JLabel("Welcome, " + username);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        welcomeLabel.setBounds(750, 10, 200, 20);
        add(welcomeLabel);

        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        timeLabel.setBounds(750, 30, 150, 20);
        add(timeLabel);
        updateClock();

        JLabel dateLabel = new JLabel("Date : " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        dateLabel.setBounds(750, 50, 150, 20);
        add(dateLabel);

       
        JButton billingBtn = createIconButton("Billing", "billing.png", 220, 100);
        billingBtn.addActionListener(e -> new BillingFrame().setVisible(true));
        add(billingBtn);

        JButton orderHistoryBtn = createIconButton("Order History", "orderhistory.png", 420, 100);
        orderHistoryBtn.addActionListener(e -> new OrderHistoryFrame().setVisible(true));
        add(orderHistoryBtn);

        JButton adminBtn = createIconButton("Admin", "admin.png", 220, 200);
        adminBtn.addActionListener(e -> new AdminFrame().setVisible(true));
        add(adminBtn);

        JButton reservationBtn = createIconButton("Reservation", "reservation.png", 420, 200);
        reservationBtn.addActionListener(e -> new ManageReservation().setVisible(true));
        add(reservationBtn);

        JPanel todoPanel = new JPanel(null);
        todoPanel.setBackground(Color.GRAY);
        todoPanel.setBounds(620, 100, 280, 300);
        todoPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(todoPanel);

        JLabel todoLabel = new JLabel("To do");
        todoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        todoLabel.setBounds(10, 5, 100, 20);
        todoPanel.add(todoLabel);

        JButton clearBtn = new JButton("Clear");
        clearBtn.setBounds(220, 5, 50, 20);
        clearBtn.setMargin(new Insets(0, 0, 0, 0));
        todoPanel.add(clearBtn);

        todoArea = new JTextArea("Reserve Table for Mr x");
        JScrollPane scrollPane = new JScrollPane(todoArea);
        scrollPane.setBounds(10, 30, 260, 250);
        todoPanel.add(scrollPane);

        clearBtn.addActionListener(e -> todoArea.setText(""));
    }

    private JButton createIconButton(String text, String iconPath, int x, int y) {
        JButton button = new JButton(text, loadIcon(iconPath, 32, 32));
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setBounds(x, y, 160, 70);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        return button;
    }

    private ImageIcon loadIcon(String path, int width, int height) {
        ImageIcon original = new ImageIcon(path);
        Image scaled = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    private void updateClock() {
        Timer timer = new Timer(1000, e -> {
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
            timeLabel.setText(time);
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardFrame("admin").setVisible(true));
    }
}
