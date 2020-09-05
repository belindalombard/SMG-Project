import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShopView {
    public ShopView(JFrame previousWindowFrame, int selectedShop, String [] shops){
        //Frame
        JFrame window = new JFrame("Sell My Goods: Shop");
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

        JLabel productsLabel = new JLabel();
        productsLabel.setText(shops[selectedShop]);
        productsLabel.setBounds(10, 20, 150, 100);
        productsLabel.setFont(new Font(null, Font.BOLD, 30));

        String [] products = new String[100];
        for(int i = 0; i < 100; i++){
            products[i] = "Hello "+i;
        }

        JList productList = new JList(products);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(productList);
        productList.setLayoutOrientation(JList.VERTICAL);
        productList.setFixedCellHeight(60);

        topButtonLayout.add(backButton);
        topButtonLayout.add(productsLabel);
        topButtonLayout.add(Box.createHorizontalGlue());


//        window.add(new JLabel());
        window.add(topButtonLayout, BorderLayout.NORTH);
//        window.add(productsLabel);
        window.add(scrollPane);
        window.setVisible(true);
    }
}
