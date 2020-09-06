import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductView {

    int quantityOfItems = 1;
    int availableStockItems = 160;
    double productPriceAmount = 270.00;
    double quantityTotalPrice = productPriceAmount;
    JLabel totalAmount;

    public ProductView(JFrame previousWindowFrame){
        //Frame
        JFrame window = new JFrame("Sell My Goods: Home");
        window.setMinimumSize(new Dimension(800, 600));
        window.setLocation(300, 150);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        //

        JPanel topButtonLayout = new JPanel();
        topButtonLayout.setLayout(new BoxLayout(topButtonLayout, BoxLayout.LINE_AXIS));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousWindowFrame.setVisible(true);
                window.setVisible(false);
            }
        });


        JLabel productImage = new JLabel(new ImageIcon("/Users/lindazungu/Desktop/Sell My Goods/src/sampleImage.png"));
        productImage.setBounds(80, 100, 250, 250);

        JLabel productName = new JLabel();
        productName.setText("Product Name");
        productName.setFont(new Font(null, Font.BOLD, 20));
        productName.setBounds(400, 100, 150, 50);

        JLabel productPrice = new JLabel();
        productPrice.setText("Product Price : R"+ quantityOfItems*productPriceAmount);
        productPrice.setBounds(400, 150, 150, 50);

        JTextArea productDetailsLabel = new JTextArea();
        productDetailsLabel.setText("Product Details :\nHello World, this product is so \ncool, like really cool! \nIt does everything");
        productDetailsLabel.setBounds(400, 200, 350, 85);
        productDetailsLabel.setBackground(window.getBackground());
        productDetailsLabel.setEditable(false);

        JLabel availableStock = new JLabel("Available Stock : "+availableStockItems);
        availableStock.setBounds(400, 270, 150, 50);

        JTextField quantityField = new JTextField(10);
        quantityField.setBounds(440, 320, 70, 27);
        quantityField.setText("1");
        quantityField.setHorizontalAlignment(SwingConstants.CENTER);
        quantityField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Add more edge cases
                quantityOfItems = Integer.parseInt(quantityField.getText());
                System.out.println(quantityOfItems*productPriceAmount);
                totalAmount.setText("Total : R"+ quantityOfItems*productPriceAmount);
            }
        });

        totalAmount = new JLabel("Total : R"+ quantityTotalPrice);
        totalAmount.setBounds(400, 360, 150, 25);

        JButton addItemButton = new JButton("+");
        addItemButton.setBounds(390, 321, 50, 25);
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(quantityField.getText().isEmpty()){
                    quantityOfItems = 1;
                    quantityField.setText("" + quantityOfItems);
                }
                else if(Integer.parseInt(quantityField.getText()) < availableStockItems){
                    quantityOfItems = Integer.parseInt(quantityField.getText())+1;
                    quantityTotalPrice += productPriceAmount;
                    quantityField.setText(""+quantityOfItems);
                    totalAmount.setText("Total : R"+ quantityOfItems*productPriceAmount);
                }
                else{
                    quantityOfItems = availableStockItems;
                    quantityField.setText(""+quantityOfItems);
                }
            }
        });

        JButton subtractItemButton = new JButton("-");
        subtractItemButton.setBounds(510, 321, 50, 25);
        subtractItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(quantityField.getText().isEmpty()){
                    quantityOfItems = 1;
                    quantityField.setText("" + quantityOfItems);
                }
                else if (Integer.parseInt(quantityField.getText()) > availableStockItems) {
                    quantityOfItems = availableStockItems;
                    quantityField.setText("" + quantityOfItems);
                }
                else if (Integer.parseInt(quantityField.getText()) > 1) {
                    quantityOfItems = Integer.parseInt(quantityField.getText()) - 1;
                    quantityTotalPrice -= productPriceAmount;
                    quantityField.setText("" + quantityOfItems);
                    totalAmount.setText("Total : R"+ quantityOfItems*productPriceAmount);
                }
            }
        });

        JButton payButton = new JButton("Pay");
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(quantityField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Error: Invalid quantity of items");
                }
                else if(Integer.parseInt(quantityField.getText()) < 1){
                    JOptionPane.showMessageDialog(null, "Entry unacceptable");
                }
                else if(Integer.parseInt(quantityField.getText()) > availableStockItems){
                    JOptionPane.showMessageDialog(null, "Too many more than available stock items requested");
                }
                else if(Integer.parseInt(quantityField.getText()) <= availableStockItems){
                    PaymentView paymentView = new PaymentView(window);
                    window.setVisible(false);
                }

            }
        });

        topButtonLayout.add(backButton);
        topButtonLayout.add(Box.createHorizontalGlue());
        topButtonLayout.add(payButton);

        window.add(topButtonLayout, BorderLayout.NORTH);
        window.add(productName);
        window.add(productPrice);
        window.add(productDetailsLabel);
        window.add(availableStock);
        window.add(addItemButton);
        window.add(quantityField);
        window.add(subtractItemButton);
        window.add(totalAmount);
        window.add(productImage);

        window.add(new JLabel());
        window.setVisible(true);
    }
}
