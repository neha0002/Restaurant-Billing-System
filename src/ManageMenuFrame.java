import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ManageMenuFrame extends JFrame {

    private JTextField idField, priceField, nameField;
    private JComboBox<String> categoryBox;
    private JTable table;
    private DefaultTableModel model;

    public ManageMenuFrame() {
        setTitle("Manage Menu - Kasturi Restaurant");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.LIGHT_GRAY);

        
        JPanel sidebar = new JPanel(null);
        sidebar.setBounds(0, 0, 160, 540);
        sidebar.setBackground(new Color(55, 55, 55));
        add(sidebar);

        String[] sideOptions = {"Dashboard", "To do", "FAQ", "Logout", "Staff"};
        String[] iconPaths = {
            "dashboard.png", "todo.png", "faq.png", "logout.png", "staff.png"
        };

        int y = 50;
        for (int i = 0; i < sideOptions.length; i++) {
            JButton btn = new JButton(sideOptions[i], resizeIcon(new ImageIcon(iconPaths[i]), 24, 24));
            btn.setBounds(10, y, 140, 40);
            btn.setBackground(Color.DARK_GRAY);
            btn.setForeground(Color.WHITE);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setFocusPainted(false);
            sidebar.add(btn);
            y += 50;
        }

        
        JLabel idLabel = new JLabel("Item ID:");
        idLabel.setBounds(180, 30, 80, 25);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(260, 30, 150, 25);
        idField.setEditable(false);
        add(idField);

        JLabel catLabel = new JLabel("Category:");
        catLabel.setBounds(180, 65, 80, 25);
        add(catLabel);

        categoryBox = new JComboBox<>();
        categoryBox.setBounds(260, 65, 150, 25);
        add(categoryBox);

        JLabel nameLabel = new JLabel("Item Name:");
        nameLabel.setBounds(180, 100, 80, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(260, 100, 150, 25);
        add(nameField);

        JLabel priceLabel = new JLabel("Item Price:");
        priceLabel.setBounds(180, 135, 80, 25);
        add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(260, 135, 150, 25);
        add(priceField);

        
        JButton addBtn = new JButton("Add");
        addBtn.setBounds(180, 180, 70, 30);
        add(addBtn);

        JButton editBtn = new JButton("Edit");
        editBtn.setBounds(260, 180, 70, 30);
        add(editBtn);

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(340, 180, 80, 30);
        add(deleteBtn);

        String[] cols = {"Item ID", "Item Name", "Category", "Item Price"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(430, 30, 480, 400);
        add(scrollPane);

        
        loadCategories();
        loadMenuData();
        generateNextId();

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                idField.setText(model.getValueAt(row, 0).toString());
                nameField.setText(model.getValueAt(row, 1).toString());
                categoryBox.setSelectedItem(model.getValueAt(row, 2).toString());
                priceField.setText(model.getValueAt(row, 3).toString());
            }
        });

        addBtn.addActionListener(e -> addItem());
        editBtn.addActionListener(e -> editItem());
        deleteBtn.addActionListener(e -> deleteItem());
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resized = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resized);
    }

    private void loadCategories() {
        String[] categories = {"Momo", "Chowmein", "Pizza", "Pasta", "Snacks", "Bread", "Breakfast", "Starter",
                "Main Course", "Drinks", "Chinese", "South Indian", "Rice", "Vegan", "Desserts"};
        for (String cat : categories) categoryBox.addItem(cat);
    }

    private void loadMenuData() {
        model.setRowCount(0);
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Kasturi_rbs", "root", "MySQL2002@")) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM menu");
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("item_id"),
                        rs.getString("item_name"),
                        rs.getString("category"),
                        rs.getDouble("item_price")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateNextId() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Kasturi_rbs", "root", "MySQL2002@")) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(item_id) FROM menu");
            if (rs.next()) {
                idField.setText(String.valueOf(rs.getInt(1) + 1));
            } else {
                idField.setText("1");
            }
        } catch (Exception e) {
            idField.setText("1");
        }
    }

    private void addItem() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Kasturi_rbs", "root", "MySQL2002@")) {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String category = (String) categoryBox.getSelectedItem();
            double price = Double.parseDouble(priceField.getText());

            PreparedStatement check = con.prepareStatement("SELECT item_id FROM menu WHERE item_id=?");
            check.setInt(1, id);
            ResultSet rs = check.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Item ID already exists.");
                return;
            }

            PreparedStatement ps = con.prepareStatement("INSERT INTO menu (item_id, item_name, category, item_price) VALUES (?, ?, ?, ?)");
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, category);
            ps.setDouble(4, price);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Item added!");
            loadMenuData();
            generateNextId();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding item.");
        }
    }

    private void editItem() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Kasturi_rbs", "root", "MySQL2002@")) {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String category = (String) categoryBox.getSelectedItem();
            double price = Double.parseDouble(priceField.getText());

            PreparedStatement ps = con.prepareStatement("UPDATE menu SET item_name=?, category=?, item_price=? WHERE item_id=?");
            ps.setString(1, name);
            ps.setString(2, category);
            ps.setDouble(3, price);
            ps.setInt(4, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Item updated!");
            loadMenuData();
            generateNextId();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating item.");
        }
    }

    private void deleteItem() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Kasturi_rbs", "root", "MySQL2002@")) {
            int id = Integer.parseInt(idField.getText());
            PreparedStatement ps = con.prepareStatement("DELETE FROM menu WHERE item_id=?");
            ps.setInt(1, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Item deleted!");
            loadMenuData();
            generateNextId();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting item.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManageMenuFrame().setVisible(true));
    }
}
