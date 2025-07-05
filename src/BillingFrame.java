import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BillingFrame extends JFrame {

    private JTextField refNoField, billNoField, rateField, qtyField, subTotalField, vatField, serviceField, grandTotalField,
            paidField, returnField, discountField, issuedByField;
    private JComboBox<String> categoryBox, itemBox, tableBox, paymentModeBox;
    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel refNoteLabel;

    public BillingFrame() {
        setTitle("KASTURI RESTAURANT - Billing");
        setSize(600, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JButton newBillBtn = new JButton("New Bill");
        newBillBtn.setBounds(10, 10, 100, 25);
        add(newBillBtn);

        JButton cancelBtn = new JButton("Cancel Bill");
        cancelBtn.setBounds(120, 10, 100, 25);
        add(cancelBtn);

        JButton closeBtn = new JButton("Close");
        closeBtn.setBounds(230, 10, 100, 25);
        closeBtn.addActionListener(e -> dispose());
        add(closeBtn);

        JLabel dateLabel = new JLabel("Date : " + new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
        dateLabel.setBounds(400, 10, 180, 25);
        add(dateLabel);

        JLabel refNoLabel = new JLabel("Ref No:");
        refNoLabel.setBounds(10, 50, 80, 25);
        add(refNoLabel);

        refNoField = new JTextField("REF13");
        refNoField.setBounds(100, 50, 150, 25);
        add(refNoField);

        refNoteLabel = new JLabel("*Increment by 1, the used Ref No is REF12");
        refNoteLabel.setBounds(260, 50, 300, 25);
        add(refNoteLabel);

        JLabel billNoLabel = new JLabel("Bill No:");
        billNoLabel.setBounds(10, 80, 80, 25);
        add(billNoLabel);

        billNoField = new JTextField(generateBillNo());
        billNoField.setBounds(100, 80, 150, 25);
        add(billNoField);

        JLabel catLabel = new JLabel("Category:");
        catLabel.setBounds(260, 80, 80, 25);
        add(catLabel);

        categoryBox = new JComboBox<>();
        categoryBox.setBounds(340, 80, 150, 25);
        add(categoryBox);

        JLabel itemLabel = new JLabel("Item:");
        itemLabel.setBounds(10, 110, 80, 25);
        add(itemLabel);

        itemBox = new JComboBox<>();
        itemBox.setBounds(100, 110, 150, 25);
        add(itemBox);

        JLabel tableLabel = new JLabel("Table No:");
        tableLabel.setBounds(260, 110, 80, 25);
        add(tableLabel);

        tableBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5","6"});
        tableBox.setBounds(340, 110, 150, 25);
        add(tableBox);

        JLabel qtyLabel = new JLabel("Qty:");
        qtyLabel.setBounds(10, 140, 80, 25);
        add(qtyLabel);

        qtyField = new JTextField();
        qtyField.setBounds(100, 140, 150, 25);
        add(qtyField);

        JLabel rateLabel = new JLabel("Rate:");
        rateLabel.setBounds(260, 140, 80, 25);
        add(rateLabel);

        rateField = new JTextField();
        rateField.setBounds(340, 140, 150, 25);
        rateField.setEditable(false);
        add(rateField);

        JButton addBtn = new JButton("Add");
        addBtn.setBounds(100, 170, 80, 25);
        add(addBtn);

        JButton clearBtn = new JButton("Clear");
        clearBtn.setBounds(200, 170, 80, 25);
        add(clearBtn);

        tableModel = new DefaultTableModel(new String[]{"Item", "Category", "Qty", "Rate", "Amount"}, 0);
        table = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBounds(10, 210, 560, 100);
        add(tableScroll);

        JLabel subTotalLabel = new JLabel("Sub Total :");
        subTotalLabel.setBounds(10, 320, 100, 25);
        add(subTotalLabel);

        subTotalField = new JTextField();
        subTotalField.setBounds(110, 320, 100, 25);
        subTotalField.setEditable(false);
        add(subTotalField);

        JLabel vatLabel = new JLabel("VAT :");
        vatLabel.setBounds(230, 320, 80, 25);
        add(vatLabel);

        vatField = new JTextField("13");
        vatField.setBounds(270, 320, 80, 25);
        add(vatField);

        JLabel serviceLabel = new JLabel("Service Charge :");
        serviceLabel.setBounds(360, 320, 120, 25);
        add(serviceLabel);

        serviceField = new JTextField("2");
        serviceField.setBounds(480, 320, 80, 25);
        add(serviceField);

        JLabel grandTotalLabel = new JLabel("Grand Total :");
        grandTotalLabel.setBounds(10, 350, 100, 25);
        add(grandTotalLabel);

        grandTotalField = new JTextField();
        grandTotalField.setBounds(110, 350, 100, 25);
        grandTotalField.setEditable(false);
        add(grandTotalField);

        JLabel paymentLabel = new JLabel("Payment Mode:");
        paymentLabel.setBounds(230, 350, 100, 25);
        add(paymentLabel);

        paymentModeBox = new JComboBox<>(new String[]{"Cash on Delivery", "Card", "UPI"});
        paymentModeBox.setBounds(330, 350, 150, 25);
        add(paymentModeBox);

        JLabel paidLabel = new JLabel("Paid Amount :");
        paidLabel.setBounds(10, 380, 100, 25);
        add(paidLabel);

        paidField = new JTextField();
        paidField.setBounds(110, 380, 100, 25);
        paidField.setEditable(false);
        add(paidField);

        JLabel returnLabel = new JLabel("Return Amount :");
        returnLabel.setBounds(230, 380, 120, 25);
        add(returnLabel);

        returnField = new JTextField();
        returnField.setBounds(350, 380, 100, 25);
        returnField.setEditable(false);
        add(returnField);

        JLabel discountLabel = new JLabel("Discount :");
        discountLabel.setBounds(10, 410, 100, 25);
        add(discountLabel);

        discountField = new JTextField("0.00");
        discountField.setBounds(110, 410, 100, 25);
        add(discountField);

        JLabel issuedLabel = new JLabel("Issued By :");
        issuedLabel.setBounds(230, 410, 100, 25);
        add(issuedLabel);

        issuedByField = new JTextField("user");
        issuedByField.setBounds(330, 410, 100, 25);
        add(issuedByField);

        JButton printBtn = new JButton("Print Bill");
        printBtn.setBounds(200, 450, 200, 30);
        add(printBtn);

        loadCategories();
        categoryBox.addActionListener(e -> loadItemsByCategory());
        itemBox.addActionListener(e -> updateRateField());
        addBtn.addActionListener(e -> addItemToTable());
        printBtn.addActionListener(e -> calculateBill());

        newBillBtn.addActionListener(e -> {
            tableModel.setRowCount(0);
            qtyField.setText("");
            rateField.setText("");
            subTotalField.setText("");
            grandTotalField.setText("");
            vatField.setText("13");
            serviceField.setText("2");
            discountField.setText("0.00");
            issuedByField.setText("user");
            refNoField.setText(incrementRefNo(refNoField.getText()));
            paidField.setText("");
            returnField.setText("");
            billNoField.setText(generateBillNo());
        });

        cancelBtn.addActionListener(e -> {
            tableModel.setRowCount(0);
            JOptionPane.showMessageDialog(this, "Bill has been cancelled.");
        });
    }

    private String generateBillNo() {
        return String.valueOf(new Random().nextInt(90000) + 10000);
    }

    private void loadCategories() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Kasturi_rbs", "root", "MySQL2002@")) {
            ResultSet rs = con.createStatement().executeQuery("SELECT DISTINCT category FROM menu");
            while (rs.next()) categoryBox.addItem(rs.getString(1));
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void loadItemsByCategory() {
        itemBox.removeAllItems();
        String cat = (String) categoryBox.getSelectedItem();
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Kasturi_rbs", "root", "MySQL2002@")) {
            PreparedStatement ps = con.prepareStatement("SELECT item_name FROM menu WHERE category = ?");
            ps.setString(1, cat);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) itemBox.addItem(rs.getString(1));
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void updateRateField() {
        String item = (String) itemBox.getSelectedItem();
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Kasturi_rbs", "root", "MySQL2002@")) {
            PreparedStatement ps = con.prepareStatement("SELECT item_price FROM menu WHERE item_name = ?");
            ps.setString(1, item);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) rateField.setText(String.valueOf(rs.getDouble(1)));
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void addItemToTable() {
        try {
            String cat = (String) categoryBox.getSelectedItem();
            String item = (String) itemBox.getSelectedItem();
            int qty = Integer.parseInt(qtyField.getText());
            double rate = Double.parseDouble(rateField.getText());
            double amt = qty * rate;
            tableModel.addRow(new Object[]{item, cat, qty, rate, amt});
            qtyField.setText("");
            rateField.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please enter valid quantity and rate");
        }
    }

    private void calculateBill() {
        try {
            double subtotal = 0;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                subtotal += (double) tableModel.getValueAt(i, 4);
            }
            subTotalField.setText(String.valueOf(subtotal));

            double vat = Double.parseDouble(vatField.getText());
            double service = Double.parseDouble(serviceField.getText());
            double discount = Double.parseDouble(discountField.getText());

            double vatAmt = subtotal * vat / 100;
            double serviceAmt = subtotal * service / 100;
            double discountAmt = subtotal * discount / 100;

            double grand = subtotal + vatAmt + serviceAmt - discountAmt;
            grandTotalField.setText(String.format("%.2f", grand));

            paidField.setText(String.format("%.2f", grand));
            returnField.setText("0.00");

            new PrintBill(tableModel, refNoField.getText(), billNoField.getText(), subtotal, vatAmt, serviceAmt, discountAmt, grand, issuedByField.getText()).setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Calculation error.");
        }
    }

    private String incrementRefNo(String current) {
        try {
            int number = Integer.parseInt(current.replaceAll("[^0-9]", "")) + 1;
            return "REF" + number;
        } catch (Exception e) {
            return "REF1";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BillingFrame().setVisible(true));
    }
}
