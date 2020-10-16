import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class ShopView {
	DatabaseAccess db;
    public ShopView(JFrame previousWindowFrame, int selectedShop, String [] shops, buyer buyerobj, seller s, DatabaseAccess db, boolean isDarkMode){
        this.db=db;
	    
	    //Frame
        JFrame window = new JFrame("Sell My Goods: "+shops[selectedShop]);
        window.setMinimumSize(new Dimension(800, 600));
        window.setLocation(300, 150);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
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
//        topButtonLayout.setBackground(new Color(0xFFFFFF));/**/

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
        product_list = db.getVisibleProductsFromSeller(code); //get list of products by a specific seller.
	
        String [] products = new String[product_list.size()];
        for(int i = 0; i < product_list.size(); i++){
            products[i] = product_list.get(i).getProductName();
        }

        JList productList = new JList(products);
        productList.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        productList.setForeground(isDarkMode ? Color.white : Color.BLACK);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(productList);
        scrollPane.setBorder(new EmptyBorder(0,0,0,0));
        productList.setLayoutOrientation(JList.VERTICAL);
        productList.setFixedCellHeight(60);

        productList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    ProductView productView = new ProductView(window, productList.getSelectedIndex(), products, buyerobj,s,db, shops[selectedShop], isDarkMode); //getProductsFromSeller
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
