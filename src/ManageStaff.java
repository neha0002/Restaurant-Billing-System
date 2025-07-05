import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ManageStaff extends JFrame {

    private JTextField idField, nameField, contactField, addressField, salaryField, usernameField, passwordField;
    private JRadioButton maleRadio, femaleRadio;
    private JTable staffTable;
    private DefaultTableModel tableModel;

    public ManageStaff() {
        setTitle("Staff Information");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.LIGHT_GRAY);

        JLabel titleLabel = new JLabel("Staff Information", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(400, 10, 300, 30);
        add(titleLabel);

        JLabel idLabel = new JLabel("Id :");
        idLabel.setBounds(50, 60, 100, 25);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(150, 60, 200, 25);
        idField.setEditable(false);
        add(idField);

        JLabel nameLabel = new JLabel("Name :");
        nameLabel.setBounds(50, 100, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 100, 200, 25);
        add(nameField);

        JLabel genderLabel = new JLabel("Gender :");
        genderLabel.setBounds(50, 140, 100, 25);
        add(genderLabel);

        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        maleRadio.setBounds(150, 140, 70, 25);
        femaleRadio.setBounds(230, 140, 80, 25);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        add(maleRadio);
        add(femaleRadio);

        JLabel contactLabel = new JLabel("Contact :");
        contactLabel.setBounds(50, 180, 100, 25);
        add(contactLabel);

        contactField = new JTextField();
        contactField.setBounds(150, 180, 200, 25);
        add(contactField);

        JLabel addressLabel = new JLabel("Address :");
        addressLabel.setBounds(50, 220, 100, 25);
        add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(150, 220, 200, 25);
        add(addressField);

        JLabel salaryLabel = new JLabel("Salary :");
        salaryLabel.setBounds(50, 260, 100, 25);
        add(salaryLabel);

        salaryField = new JTextField();
        salaryField.setBounds(150, 260, 200, 25);
        add(salaryField);

        JLabel usernameLabel = new JLabel("Username :");
        usernameLabel.setBounds(50, 300, 100, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 300, 200, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password :");
        passwordLabel.setBounds(50, 340, 100, 25);
        add(passwordLabel);

        passwordField = new JTextField();
        passwordField.setBounds(150, 340, 200, 25);
        add(passwordField);

        JButton addButton = new JButton("Add Employee");
        addButton.setBounds(50, 390, 140, 30);
        add(addButton);

        JButton editButton = new JButton("Edit Employee");
        editButton.setBounds(200, 390, 140, 30);
        add(editButton);

        JButton deleteButton = new JButton("Delete Employee");
        deleteButton.setBounds(125, 430, 140, 30);
        add(deleteButton);

        // Table setup
        String[] columnNames = {"ID", "Name", "Gender", "Contact", "Address", "Salary", "Username", "Password"};
        tableModel = new DefaultTableModel(columnNames, 0);
        staffTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(staffTable);
        scrollPane.setBounds(370, 60, 700, 400);
        add(scrollPane);

        loadStaffData();

        staffTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = staffTable.getSelectedRow();
                idField.setText(tableModel.getValueAt(row, 0).toString());
                nameField.setText(tableModel.getValueAt(row, 1).toString());
                String gender = tableModel.getValueAt(row, 2).toString();
                if (gender.equalsIgnoreCase("Male")) maleRadio.setSelected(true);
                else femaleRadio.setSelected(true);
                contactField.setText(tableModel.getValueAt(row, 3).toString());
                addressField.setText(tableModel.getValueAt(row, 4).toString());
                salaryField.setText(tableModel.getValueAt(row, 5).toString());
                usernameField.setText(tableModel.getValueAt(row, 6).toString());
                passwordField.setText(tableModel.getValueAt(row, 7).toString());
            }
        });

        addButton.addActionListener(e -> addEmployee());
        editButton.addActionListener(e -> editEmployee());
        deleteButton.addActionListener(e -> deleteEmployee());
    }

    private void loadStaffData() {
        tableModel.setRowCount(0);
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Kasturi_rbs", "root", "MySQL2002@")) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM staff");
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("contact"),
                        rs.getString("address"),
                        rs.getString("salary"),
                        rs.getString("username"),
                        rs.getString("password")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading staff data:\n" + e.getMessage());
        }
    }

    private void addEmployee() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Kasturi_rbs", "root", "MySQL2002@")) {
            String gender = maleRadio.isSelected() ? "Male" : "Female";
            PreparedStatement ps = con.prepareStatement("INSERT INTO staff (name, gender, contact, address, salary, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, nameField.getText());
            ps.setString(2, gender);
            ps.setString(3, contactField.getText());
            ps.setString(4, addressField.getText());
            ps.setString(5, salaryField.getText());
            ps.setString(6, usernameField.getText());
            ps.setString(7, passwordField.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "One Record Inserted.");
            clearFields();
            loadStaffData();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding employee:\n" + e.getMessage());
        }
    }

    private void editEmployee() {
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to edit.");
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Kasturi_rbs", "root", "MySQL2002@")) {
            String gender = maleRadio.isSelected() ? "Male" : "Female";
            PreparedStatement ps = con.prepareStatement("UPDATE staff SET name=?, gender=?, contact=?, address=?, salary=?, username=?, password=? WHERE id=?");
            ps.setString(1, nameField.getText());
            ps.setString(2, gender);
            ps.setString(3, contactField.getText());
            ps.setString(4, addressField.getText());
            ps.setString(5, salaryField.getText());
            ps.setString(6, usernameField.getText());
            ps.setString(7, passwordField.getText());
            ps.setInt(8, Integer.parseInt(idField.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "One Record Updated.");
            clearFields();
            loadStaffData();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating employee:\n" + e.getMessage());
        }
    }

    private void deleteEmployee() {
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to delete.");
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Kasturi_rbs", "root", "MySQL2002@")) {
            int id = Integer.parseInt(idField.getText());
            PreparedStatement ps = con.prepareStatement("DELETE FROM staff WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "One Record Deleted.");
            clearFields();
            loadStaffData();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting employee:\n" + e.getMessage());
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        contactField.setText("");
        addressField.setText("");
        salaryField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        maleRadio.setSelected(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManageStaff().setVisible(true));
    }
}
