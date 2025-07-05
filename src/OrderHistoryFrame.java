import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OrderHistoryFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public OrderHistoryFrame() {
        setTitle("Order History - KASTURI RESTAURANT");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Order History", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{
                "Order ID", "Ref No", "Bill No", "Item", "Qty", "Rate", "Amount", "Date", "Table No", "Issued By"
        }, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadOrderHistory();
    }

    private void loadOrderHistory() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS order_history ("
                + "order_id INT AUTO_INCREMENT PRIMARY KEY,"
                + "ref_no VARCHAR(50),"
                + "bill_no VARCHAR(50),"
                + "item VARCHAR(100),"
                + "qty INT,"
                + "rate DOUBLE,"
                + "amount DOUBLE,"
                + "order_date DATE,"
                + "table_no INT,"
                + "issued_by VARCHAR(50)"
                + ")";

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/Kasturi_rbs", "root", "MySQL2002@")) {

            Statement stmt = con.createStatement();
            stmt.executeUpdate(createTableSQL);

            ResultSet rs = stmt.executeQuery("SELECT * FROM order_history");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                model.addRow(new Object[]{
                        rs.getInt("order_id"),
                        rs.getString("ref_no"),
                        rs.getString("bill_no"),
                        rs.getString("item"),
                        rs.getInt("qty"),
                        rs.getDouble("rate"),
                        rs.getDouble("amount"),
                        rs.getDate("order_date"),
                        rs.getInt("table_no"),
                        rs.getString("issued_by")
                });
            }

            if (!hasData) {
                JOptionPane.showMessageDialog(this, "No order history found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OrderHistoryFrame().setVisible(true));
    }
}
