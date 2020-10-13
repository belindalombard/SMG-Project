import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class ShopView {
    public ShopView(JFrame previousWindowFrame, int selectedShop, String [] shops, buyer buyerobj, seller s, DatabaseAccess db){
        //Frame
        JFrame window = new JFrame("Sell My Goods: "+shops[selectedShop]);
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

	ArrayList<product> product_list = new ArrayList<product>();
	int code = db.getUserCode(s.getEmail(), "seller"); //get seller code of the shop that wants to be visited now. 
	product_list = db.getProductsFromSeller(code); //get list of products by a specific seller.
	
        String [] products = new String[product_list.size()];
        for(int i = 0; i < product_list.size(); i++){
            products[i] = product_list.get(i).getProductName();
        }

        JList productList = new JList(products);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(productList);
        scrollPane.setBorder(new EmptyBorder(0,0,0,0));
        productList.setLayoutOrientation(JList.VERTICAL);
        productList.setFixedCellHeight(60);

        productList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    ProductView productView = new ProductView(window, productList.getSelectedIndex(), products, buyerobj); //getProductsFromSeller
                    window.setVisible(false);
                }
            }
            @Override
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) { }
            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
        });

        topButtonLayout.add(backButton);

        window.add(topButtonLayout, BorderLayout.NORTH);
        window.add(scrollPane);
        window.setVisible(true);
    }
}
