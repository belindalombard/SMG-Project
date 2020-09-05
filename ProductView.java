import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ProductView {

    int quantityOfItems = 1;

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
        productName.setBounds(350, 100, 150, 50);

        JLabel productPrice = new JLabel();
        productPrice.setText("Product Price : R270.00");
        productPrice.setBounds(350, 150, 150, 50);

        JTextArea productDetailsLabel = new JTextArea();
        productDetailsLabel.setText("Product Details :\nHello World, this product is so \ncool, like really cool! \nIt does everything");
        productDetailsLabel.setBounds(350, 200, 400, 85);
        productDetailsLabel.setBackground(window.getBackground());

        JLabel availableStock = new JLabel("Available Stock : 10");
        availableStock.setBounds(350, 270, 150, 50);

        JTextField quantityField = new JTextField(10);
        quantityField.setBounds(390, 320, 70, 27);
//        quantityField.setText(""+quantityOfItems);
        quantityField.setText("1");
        quantityField.setHorizontalAlignment(SwingConstants.CENTER);

        JButton addItemButton = new JButton("+");
        addItemButton.setBounds(340, 321, 50, 25);
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quantityOfItems = Integer.parseInt(quantityField.getText())+1;
                quantityField.setText(""+quantityOfItems);
            }
        });

        JButton subtractItemButton = new JButton("-");
        subtractItemButton.setBounds(460, 321, 50, 25);
        subtractItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quantityOfItems = Integer.parseInt(quantityField.getText())-1;
                quantityField.setText(""+quantityOfItems);
            }
        });


        topButtonLayout.add(backButton);

        window.add(topButtonLayout, BorderLayout.NORTH);
        window.add(productName);
        window.add(productPrice);
        window.add(productDetailsLabel);
        window.add(availableStock);
        window.add(addItemButton);
        window.add(quantityField);
        window.add(subtractItemButton);
        window.add(productImage);

        window.add(new JLabel());
        window.setVisible(true);
    }

//    public static void main(String [] args){
//        ProductView x = new ProductView(new JFrame());
//    }
}
