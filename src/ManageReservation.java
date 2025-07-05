import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ManageReservation extends JFrame {

    private JTextField idField, tokenField, nameField, addressField, contactField;
    private JComboBox<String> tableCombo;
    private JDateChooser dateChooser;
    private JSpinner timeSpinner;
    private JTable table;
    private DefaultTableModel model;
    private JLabel reservedTokenLabel;
    private JButton reserveBtn, cancelBtn, checkBtn, assignedBtn;

    public ManageReservation() {
        setTitle("KASTURI Restaurant - Reservation");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.LIGHT_GRAY);

        JLabel welcomeLabel = new JLabel("Welcome, user");
        welcomeLabel.setBounds(950, 10, 200, 20);
        add(welcomeLabel);

        JPanel formPanel = new JPanel(null);
        formPanel.setBounds(20, 20, 400, 500);
        formPanel.setBackground(Color.LIGHT_GRAY);
        add(formPanel);

        JLabel idLabel = new JLabel("Id :");
        idLabel.setBounds(10, 10, 100, 25);
        formPanel.add(idLabel);

        idField = new JTextField();
        idField.setBounds(150, 10, 200, 25);
        idField.setEditable(false);
        formPanel.add(idField);

        JLabel tokenLabel = new JLabel("Token No :");
        tokenLabel.setBounds(10, 50, 100, 25);
        formPanel.add(tokenLabel);

        tokenField = new JTextField();
        tokenField.setBounds(150, 50, 200, 25);
        tokenField.setEditable(false);
        formPanel.add(tokenField);

        reservedTokenLabel = new JLabel("*Increment by 1, the used Token No is");
        reservedTokenLabel.setBounds(10, 80, 300, 25);
        formPanel.add(reservedTokenLabel);

        JLabel nameLabel = new JLabel("Customer Name:");
        nameLabel.setBounds(10, 110, 120, 25);
        formPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 110, 200, 25);
        formPanel.add(nameField);

        JLabel addressLabel = new JLabel("Customer Address:");
        addressLabel.setBounds(10, 150, 120, 25);
        formPanel.add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(150, 150, 200, 25);
        formPanel.add(addressField);

        JLabel contactLabel = new JLabel("Contact No:");
        contactLabel.setBounds(10, 190, 120, 25);
        formPanel.add(contactLabel);

        contactField = new JTextField();
        contactField.setBounds(150, 190, 200, 25);
        formPanel.add(contactField);

        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setBounds(10, 230, 120, 25);
        formPanel.add(dateLabel);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(150, 230, 200, 25);
        dateChooser.setDateFormatString("yyyy-MM-dd");
        formPanel.add(dateChooser);

        JLabel timeLabel = new JLabel("Time:");
        timeLabel.setBounds(10, 270, 120, 25);
        formPanel.add(timeLabel);

        timeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setBounds(150, 270, 200, 25);
        formPanel.add(timeSpinner);

        JLabel tableLabel = new JLabel("Table For:");
        tableLabel.setBounds(10, 310, 120, 25);
        formPanel.add(tableLabel);

        tableCombo = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6"});
        tableCombo.setBounds(150, 310, 200, 25);
        formPanel.add(tableCombo);

        JLabel reservedTokenStaticLabel = new JLabel("Reserved Token No:");
        reservedTokenStaticLabel.setBounds(10, 350, 130, 25);
        formPanel.add(reservedTokenStaticLabel);

        JTextField reservedTokenField = new JTextField("RSV");
        reservedTokenField.setBounds(150, 350, 200, 25);
        reservedTokenField.setEditable(false);
        formPanel.add(reservedTokenField);

        reserveBtn = new JButton("Reserve");
        reserveBtn.setBounds(10, 400, 100, 30);
        formPanel.add(reserveBtn);

        checkBtn = new JButton("Check Reservation");
        checkBtn.setBounds(120, 400, 150, 30);
        formPanel.add(checkBtn);

        assignedBtn = new JButton("Assigned");
        assignedBtn.setBounds(280, 400, 100, 30);
        formPanel.add(assignedBtn);

        cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(150, 450, 100, 30);
        formPanel.add(cancelBtn);

        model = new DefaultTableModel(new String[]{"Id", "Token No", "Customer Name", "Customer Address", "Contact No", "Date", "Time", "Table For"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(440, 50, 620, 450);
        add(scrollPane);

        reserveBtn.addActionListener(e -> reserve());
        cancelBtn.addActionListener(e -> cancelReservation());
        checkBtn.addActionListener(e -> loadTodaysReservations());
        assignedBtn.addActionListener(e -> markAsAssigned());

        loadReservations();
    }

    private void reserve() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Kasturi_rbs", "root", "MySQL2002@")) {
            String token = tokenField.getText().trim();
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();
            String contact = contactField.getText().trim();
            Date selectedDate = dateChooser.getDate();

            if (selectedDate == null || name.isEmpty() || address.isEmpty() || contact.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields before reserving.");
                return;
            }

            String date = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate);
            String time = new SimpleDateFormat("HH:mm").format((Date) timeSpinner.getValue());
            int tableFor = Integer.parseInt(tableCombo.getSelectedItem().toString());

            PreparedStatement ps = con.prepareStatement("INSERT INTO reservation(token, customer_name, customer_address, contact_no, res_date, res_time, table_no) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, token);
            ps.setString(2, name);
            ps.setString(3, address);
            ps.setString(4, contact);
            ps.setString(5, date);
            ps.setString(6, time);
            ps.setInt(7, tableFor);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Reservation Completed");
            clearFields();
            loadReservations();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add reservation: " + e.getMessage());
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        addressField.setText("");
        contactField.setText("");
        dateChooser.setDate(null);
        timeSpinner.setValue(new Date());
        tableCombo.setSelectedIndex(0);
        tokenField.setText("");
    }

    private void cancelReservation() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a reservation to cancel.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this reservation?", "Confirm Cancel", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        int id = (int) model.getValueAt(selectedRow, 0);

        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Kasturi_rbs", "root", "MySQL2002@")) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM reservation WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Reservation Cancelled Successfully.");
            loadReservations();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error while cancelling reservation.");
        }
    }

    private void markAsAssigned() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a reservation to assign.");
            return;
        }
        String token = model.getValueAt(selectedRow, 1).toString();
        JOptionPane.showMessageDialog(this, "Token " + token + " assigned.");
    }

    private void loadReservations() {
        model.setRowCount(0);
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Kasturi_rbs", "root", "MySQL2002@")) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM reservation");
            int maxTokenId = 0;

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("token"),
                        rs.getString("customer_name"),
                        rs.getString("customer_address"),
                        rs.getString("contact_no"),
                        rs.getString("res_date"),
                        rs.getString("res_time"),
                        rs.getString("table_no")
                });

                String token = rs.getString("token");
                if (token != null && token.startsWith("RSV")) {
                    try {
                        int tokenNum = Integer.parseInt(token.substring(3));
                        if (tokenNum > maxTokenId) maxTokenId = tokenNum;
                    } catch (Exception ignored) {}
                }
            }

            reservedTokenLabel.setText("*Increment by 1, the used Token No is RSV" + maxTokenId);
            tokenField.setText("RSV" + (maxTokenId + 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTodaysReservations() {
        model.setRowCount(0);
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Kasturi_rbs", "root", "MySQL2002@")) {
            String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reservation WHERE res_date = ?");
            ps.setString(1, today);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("token"),
                        rs.getString("customer_name"),
                        rs.getString("customer_address"),
                        rs.getString("contact_no"),
                        rs.getString("res_date"),
                        rs.getString("res_time"),
                        rs.getString("table_no")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load today's reservations.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManageReservation().setVisible(true));
    }
}
