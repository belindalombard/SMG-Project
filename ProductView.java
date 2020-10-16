import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream ;
import javax.imageio.ImageIO;



public class ProductView {

    int quantityOfItems = 1;
    int availableStockItems;
    double productPriceAmount;
    double quantityTotalPrice = productPriceAmount;
    JLabel totalAmount;
    String shopLocation;
    JButton contactSellerButton;
    product current_product;

    public ProductView(JFrame previousWindowFrame, int selectedProduct, String [] products, buyer buyerobj, seller s,DatabaseAccess db, String nameOfShop, boolean isDarkMode){
        //Frame
        JFrame window = new JFrame("Sell My Goods: "+products[selectedProduct]);
        window.setMinimumSize(new Dimension(800, 600));
        window.setLocation(300, 150);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.getContentPane().setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        //

        JPanel topButtonLayout = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0,
                        isDarkMode ? getBackground().darker().darker().gray : getBackground().darker(), 0, getHeight(),
                        isDarkMode ? getBackground().darker().darker().darkGray : getBackground().brighter().brighter().brighter().brighter());
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        topButtonLayout.setLayout(new BoxLayout(topButtonLayout, BoxLayout.LINE_AXIS));

        JLabel shopNameLabel = new JLabel(nameOfShop);
        shopNameLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        shopNameLabel.setBounds(30, 30, 770, 50);
        shopNameLabel.setFont(new Font(null, Font.BOLD, 25));
        shopNameLabel.setForeground(Color.GRAY);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousWindowFrame.setVisible(true);
                window.setVisible(false);
            }
        });
        current_product = db.getProductFromName(products[selectedProduct]);

        JLabel productImage;
        if (current_product.getProductImage()!=null)
                productImage = new JLabel(new ImageIcon(bytea_to_image(current_product.getProductImage())));
        else
            productImage = new JLabel(new ImageIcon());
        productImage.setBounds(80, 100, 250, 250);
        productImage.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

        JLabel productName = new JLabel();
        productName.setForeground(isDarkMode ? Color.white : Color.BLACK);
        productName.setText(products[selectedProduct]);
        productName.setFont(new Font(null, Font.BOLD, 20));
        productName.setBounds(400, 100, 150, 50);

        productPriceAmount=current_product.getProductPrice();
        availableStockItems=current_product.getProductQty();

        JLabel productPrice = new JLabel();
        productPrice.setForeground(isDarkMode ? Color.white : Color.BLACK);
        productPrice.setText("Product Price : R"+ String.format("%.2f",Math.round(quantityOfItems*productPriceAmount*100.0)/100.0));
        productPrice.setBounds(400, 150, 350, 50);

        JTextArea productDetailsLabel = new JTextArea();
        productDetailsLabel.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        productDetailsLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        productDetailsLabel.setText("Product Details :\n"+ current_product.getProductDescription());
        productDetailsLabel.setBounds(400, 200, 350, 85);
//        productDetailsLabel.setBackground(window.getBackground());
        productDetailsLabel.setEditable(false);
        productDetailsLabel.setLineWrap(true);
        productDetailsLabel.setWrapStyleWord(true);

        shopLocation = db.getUserDistrict(s.getSellerID(), "seller"); //Set the location of the shop.
        JLabel locationLabel = new JLabel("Location : "+shopLocation);
        locationLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        locationLabel.setBounds(400, 275, 300, 50);

        JLabel availableStock = new JLabel("Available Stock : "+availableStockItems);
        availableStock.setForeground(isDarkMode ? Color.white : Color.BLACK);
        availableStock.setBounds(400, 305, 150, 50);

        JTextField quantityField = new JTextField(10);
        quantityField.setBounds(440, 355, 70, 25);
        quantityField.setText("0");
        quantityField.setHorizontalAlignment(SwingConstants.CENTER);
        quantityField.setBorder(new BevelBorder(2));
        quantityField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(quantityField.getText().isEmpty()){
                        quantityOfItems = 0;
                        quantityField.setText(""+quantityOfItems);
                        totalAmount.setText("Total : R"+ String.format("%.2f",Math.round(quantityOfItems*productPriceAmount*100.0)/100.0));
                    }
                    else if(Integer.parseInt(quantityField.getText()) > availableStockItems || Integer.parseInt(quantityField.getText()) < 0){
                        quantityOfItems = 0;
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
        totalAmount.setForeground(isDarkMode ? Color.white : Color.BLACK);
        totalAmount.setBounds(400, 395, 150, 25);

        JButton addItemButton = new JButton("+");
        addItemButton.setBounds(390, 356, 50, 25);
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(quantityField.getText().isEmpty()){
                        quantityOfItems = 0;
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
                        quantityOfItems = 0;
                        quantityField.setText("" + quantityOfItems);
                    } else if (Integer.parseInt(quantityField.getText()) > availableStockItems) {
                        quantityOfItems = availableStockItems;
                        quantityField.setText("" + quantityOfItems);
                    } else if (Integer.parseInt(quantityField.getText()) > 0) {
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
                        PaymentView paymentView = new PaymentView(window, payButton, backButton, quantityField, addItemButton, subtractItemButton, contactSellerButton, products[selectedProduct], totalAmount,buyerobj, isDarkMode);
//                        window.setVisible(false);
                        payButton.setEnabled(false);
                        backButton.setEnabled(false);
                        quantityField.setEditable(false);
                        addItemButton.setEnabled(false);
                        subtractItemButton.setEnabled(false);
                        contactSellerButton.setEnabled(false);
                    }
                }
                catch (Exception k) {
                    JOptionPane.showMessageDialog(null, "Error! That is not a valid entry. Numbers only are allowed");
                }
            }
        });

        JPanel bottomButtonLayout = new JPanel(new FlowLayout());
        bottomButtonLayout.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        bottomButtonLayout.setForeground(isDarkMode ? Color.white : Color.BLACK);
        contactSellerButton = new JButton("Contact Seller");
        contactSellerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChatRoomView chatRoomView = new ChatRoomView(window, backButton, payButton,contactSellerButton,s, buyerobj, current_product);
//                window.setVisible(false);
                contactSellerButton.setEnabled(false);
                backButton.setEnabled(false);
                payButton.setEnabled(false);
            }
        });

        bottomButtonLayout.add(contactSellerButton);
        topButtonLayout.add(backButton);
        topButtonLayout.add(Box.createHorizontalGlue());
        topButtonLayout.add(payButton);

        window.add(topButtonLayout, BorderLayout.NORTH);
        window.add(bottomButtonLayout, BorderLayout.SOUTH);
        window.add(shopNameLabel);
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



    /** 
     * Convert a byte array to an image obj
     */
    public BufferedImage bytea_to_image(byte[] bytes){
        try {
                ByteArrayInputStream b = new ByteArrayInputStream(bytes);
                BufferedImage image = ImageIO.read(b);
                return image;
        } catch (Exception e){
                e.printStackTrace();
        }
    return null;
    }



}
