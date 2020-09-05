import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ProductView {
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
        productDetailsLabel.setBounds(350, 200, 400, 100);
        productDetailsLabel.setBackground(window.getBackground());
        
        topButtonLayout.add(backButton);

        window.add(topButtonLayout, BorderLayout.NORTH);
        window.add(productName);
        window.add(productPrice);
        window.add(productDetailsLabel);
        window.add(productImage);

        window.add(new JLabel());
        window.setVisible(true);
    }
}
