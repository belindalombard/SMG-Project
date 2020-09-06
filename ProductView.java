import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class ProductView {

    int quantityOfItems = 1;
    int availableStockItems = 160;
    double productPriceAmount = 200.67;
    double quantityTotalPrice = productPriceAmount;
    JLabel totalAmount;
    String shopLocation;

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
        productPrice.setText("Product Price : R"+ String.format("%.2f",Math.round(quantityOfItems*productPriceAmount*100.0)/100.0));
        productPrice.setBounds(400, 150, 150, 50);

        JTextArea productDetailsLabel = new JTextArea();
        productDetailsLabel.setText("Product Details :\nHello World, this product is so cool, like really cool! It does everything...It does everything");
        productDetailsLabel.setBounds(400, 200, 350, 85);
        productDetailsLabel.setBackground(window.getBackground());
        productDetailsLabel.setEditable(false);
        productDetailsLabel.setLineWrap(true);
        productDetailsLabel.setWrapStyleWord(true);

        shopLocation = "Cape Town";
        JLabel locationLabel = new JLabel("Location : "+shopLocation);
        locationLabel.setBounds(400, 275, 300, 50);

        JLabel availableStock = new JLabel("Available Stock : "+availableStockItems);
        availableStock.setBounds(400, 305, 150, 50);

        JTextField quantityField = new JTextField(10);
        quantityField.setBounds(440, 355, 70, 27);
        quantityField.setText("1");
        quantityField.setHorizontalAlignment(SwingConstants.CENTER);
        quantityField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(quantityField.getText().isEmpty()){
                        quantityOfItems = 1;
                        quantityField.setText(""+quantityOfItems);
                        totalAmount.setText("Total : R"+ String.format("%.2f",Math.round(quantityOfItems*productPriceAmount*100.0)/100.0));
                    }
                    else if(Integer.parseInt(quantityField.getText()) > availableStockItems || Integer.parseInt(quantityField.getText()) < 1){
                        quantityOfItems = 1;
                        quantityField.setText(""+quantityOfItems);
                        totalAmount.setText("Total : R"+ String.format("%.2f",Math.round(quantityOfItems*productPriceAmount*100.0)/100.0));
                    }
                    else{
                        quantityOfItems = Integer.parseInt(quantityField.getText());
                        totalAmount.setText("Total : R"+ String.format("%.2f",Math.round(quantityOfItems*productPriceAmount*100.0)/100.0));
                    }
                }
                catch (Exception k){ }
            }
        });

        totalAmount = new JLabel("Total : R"+ quantityTotalPrice);
        totalAmount.setBounds(400, 395, 150, 25);

        JButton addItemButton = new JButton("+");
        addItemButton.setBounds(390, 356, 50, 25);
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(quantityField.getText().isEmpty()){
                        quantityOfItems = 1;
                        quantityField.setText("" + quantityOfItems);
                    }
                    else if(Integer.parseInt(quantityField.getText()) < availableStockItems){
                        quantityOfItems = Integer.parseInt(quantityField.getText())+1;
                        quantityTotalPrice += productPriceAmount;
                        quantityField.setText(""+quantityOfItems);
                        totalAmount.setText("Total : R"+ String.format("%.2f",Math.round(quantityOfItems*productPriceAmount*100.0)/100.0));
                    }
                    else{
                        quantityOfItems = availableStockItems;
                        quantityField.setText(""+quantityOfItems);
                    }
                }
                catch (Exception k){ }
            }
        });

        JButton subtractItemButton = new JButton("-");
        subtractItemButton.setBounds(510, 356, 50, 25);
        subtractItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (quantityField.getText().isEmpty()) {
                        quantityOfItems = 1;
                        quantityField.setText("" + quantityOfItems);
                    } else if (Integer.parseInt(quantityField.getText()) > availableStockItems) {
                        quantityOfItems = availableStockItems;
                        quantityField.setText("" + quantityOfItems);
                    } else if (Integer.parseInt(quantityField.getText()) > 1) {
                        quantityOfItems = Integer.parseInt(quantityField.getText()) - 1;
                        quantityTotalPrice -= productPriceAmount;
                        quantityField.setText("" + quantityOfItems);
                        totalAmount.setText("Total : R" + String.format("%.2f", Math.round(quantityOfItems * productPriceAmount * 100.0) / 100.0));
                    }
                }
                catch (Exception k){ }
            }
        });

        JButton payButton = new JButton("Pay");
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
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
                        quantityOfItems = Integer.parseInt(quantityField.getText());
                        totalAmount.setText("Total : R"+ String.format("%.2f",Math.round(quantityOfItems*productPriceAmount*100.0)/100.0));
                        PaymentView paymentView = new PaymentView(window);
                        window.setVisible(false);
                    }
                }
                catch (Exception k) {
                    JOptionPane.showMessageDialog(null, "Error! That is not a valid entry. Numbers only are allowed");
                }
            }
        });

        JPanel bottomButtonLayout = new JPanel(new FlowLayout());
        JButton contactSellerButton = new JButton("Contact Seller");
        contactSellerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChatRoomView chatRoomView = new ChatRoomView(window);
//                window.setVisible(false);
            }
        });

        bottomButtonLayout.add(contactSellerButton);
        topButtonLayout.add(backButton);
        topButtonLayout.add(Box.createHorizontalGlue());
        topButtonLayout.add(payButton);

        window.add(topButtonLayout, BorderLayout.NORTH);
        window.add(bottomButtonLayout, BorderLayout.SOUTH);
        window.add(productName);
        window.add(productPrice);
        window.add(productDetailsLabel);
        window.add(locationLabel);
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
