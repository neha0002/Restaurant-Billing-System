import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PrintBill extends JFrame {

    public PrintBill(DefaultTableModel tableModel, String refNo, String billNo, double subtotal, double vat, double serviceCharge, double discount, double grandTotal, String issuedBy) {
        setTitle("Bill Output - KASTURI RESTAURANT");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel logo = new JLabel(new ImageIcon("logo.png")); 
        logo.setBounds(150, 10, 100, 100);
        add(logo);

        JLabel nameLabel = new JLabel("KASTURI RESTAURANT", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setBounds(50, 110, 300, 20);
        add(nameLabel);

        JLabel addressLabel = new JLabel("XYZ Street, Lake Gardens, Kolkata", SwingConstants.CENTER);
        addressLabel.setBounds(50, 130, 300, 20);
        add(addressLabel);

        JLabel telLabel = new JLabel("Tel: 012345678", SwingConstants.CENTER);
        telLabel.setBounds(50, 150, 300, 20);
        add(telLabel);

        JLabel vatLabel = new JLabel("VAT Registration No.: 109876543210", SwingConstants.CENTER);
        vatLabel.setBounds(50, 170, 300, 20);
        add(vatLabel);

        JLabel dateLabel = new JLabel("Date: " + new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
        dateLabel.setBounds(30, 200, 200, 20);
        add(dateLabel);

        JLabel refNoLabel = new JLabel("Ref No: " + refNo);
        refNoLabel.setBounds(250, 200, 100, 20);
        add(refNoLabel);

        JLabel billNoLabel = new JLabel("Bill No: " + billNo);
        billNoLabel.setBounds(250, 220, 100, 20);
        add(billNoLabel);

        
        String[] cols = {"Item", "Category", "Qty", "Rate", "Amount"};
        JTable table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(30, 250, 320, 100);
        add(scroll);

        int y = 360;

        JLabel subLabel = new JLabel("Sub Total: " + String.format("%.2f", subtotal), SwingConstants.RIGHT);
        subLabel.setBounds(150, y, 200, 20);
        add(subLabel);

        JLabel vatAmtLabel = new JLabel("VAT: " + String.format("%.2f", vat), SwingConstants.RIGHT);
        vatAmtLabel.setBounds(150, y += 20, 200, 20);
        add(vatAmtLabel);

        JLabel serviceAmtLabel = new JLabel("Service Charge: " + String.format("%.2f", serviceCharge), SwingConstants.RIGHT);
        serviceAmtLabel.setBounds(150, y += 20, 200, 20);
        add(serviceAmtLabel);

        JLabel discountLabel = new JLabel("Discount: " + String.format("%.2f", discount), SwingConstants.RIGHT);
        discountLabel.setBounds(150, y += 20, 200, 20);
        add(discountLabel);

        JLabel grandTotalLabel = new JLabel("Grand Total: " + String.format("%.2f", grandTotal), SwingConstants.RIGHT);
        grandTotalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        grandTotalLabel.setBounds(150, y += 20, 200, 20);
        add(grandTotalLabel);

        JLabel issuedLabel = new JLabel("Issued By: " + issuedBy, SwingConstants.RIGHT);
        issuedLabel.setBounds(150, y += 30, 200, 20);
        add(issuedLabel);

        JLabel thankYouLabel = new JLabel("Thank you for visiting.", SwingConstants.CENTER);
        thankYouLabel.setFont(new Font("Arial", Font.BOLD, 14));
        thankYouLabel.setBounds(50, y += 30, 300, 30);
        add(thankYouLabel);
    }

    public static void main(String[] args) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Item", "Category", "Qty", "Rate", "Amount"}, 0);
        model.addRow(new Object[]{"Veg Chowmein", "Chowmein", 3, 55, 165});
        model.addRow(new Object[]{"Veg Momo", "Momo", 3, 50, 150});
        new PrintBill(model, "REF12", "65706", 315, 40.95, 6.3, 0, 362.25, "admin").setVisible(true);
    }
}
