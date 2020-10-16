import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

public class HomeView extends SignUpView{
    buyer buyerobj;
    JList shopList;
    int clickedItemIndex;
    String [] shops;
    String [] selecetedShops;
    JScrollPane scrollPane;
    JFrame window;
    ArrayList<String> tempShops;
    DatabaseAccess db = new DatabaseAccess();
    seller selected_seller;
    ArrayList<seller> sellers = new ArrayList<seller>();
    ArrayList<store>  returnedShops= new ArrayList<>();
    JComboBox searchCriteriaComboBox;
    String selectedCriteria , searchedWord ;
    JButton inboxButton;
    JPanel topSearchBarLayout;

    public HomeView(JFrame previousWindowFrame, buyer buyerobj, boolean isDarkMode){
        this.buyerobj = buyerobj;
	    selected_seller =null;
	    //Frame
        window = new JFrame("Sell My Goods: Stores");
        window.setMinimumSize(new Dimension(800, 600));
        window.setLocation(300, 150);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        //

        topSearchBarLayout = new JPanel(new FlowLayout()){
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
//        topSearchBarLayout.setBackground(Color.black);//////
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginView loginView = new LoginView();
                window.dispose();
            }
        });
        //get shops in area from the database.
        //ArrayList<seller> sellers = new ArrayList<seller>();
        sellers = db.getSellersAtDistrict(buyerobj.getResidentialAdr());

        shops = new String[sellers.size()];
        
//	System.out.println(sellers.size()); //trace statement
	
	// Shops List
        for(int i = 0; i < shops.length; i++){
            store shop = db.getShopFromSeller(sellers.get(i));
	    shops[i]=shop.getStoreName();
        }
        shopList = new JList(shops);
        shopList.setBackground(isDarkMode ? new Color(0x222425) : new Color(0xFFFFFF));
        shopList.setForeground(isDarkMode ? Color.white : Color.BLACK);

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(shopList);
        scrollPane.setBorder(new EmptyBorder(0,0,0,0));
        shopList.setLayoutOrientation(JList.VERTICAL);
        shopList.setFixedCellHeight(60);
        shopList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
		    selected_seller = sellers.get(shopList.getSelectedIndex());
                    ShopView shopView = new ShopView(window, shopList.getSelectedIndex(), shops, buyerobj,  selected_seller, db, isDarkMode);
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
        String choiceList [] = {"Shop", "Area", "Product"};
        searchCriteriaComboBox = new JComboBox<String>(choiceList);
        searchCriteriaComboBox.setBounds(270, 252, 150, 27);

        //getting shops based on search criteria
        selectedCriteria = searchCriteriaComboBox.getSelectedItem().toString();


        JTextField searchBar = new JTextField(25);
        searchBar.setBorder(new BevelBorder(2));
        searchBar.setText("Search for shops around you");
        searchBar.setForeground(Color.GRAY);
        searchBar.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(searchBar.getText().equals("Search for shops around you")){
                    searchBar.setText("");
                    searchBar.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(searchBar.getText().isEmpty()){
                    searchBar.setText("Search for shops around you");
                    searchBar.setForeground(Color.GRAY);
                }
            }
        });
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempShops = new ArrayList<>();
                selectedCriteria = searchCriteriaComboBox.getSelectedItem().toString();
                System.out.println(selectedCriteria);
                searchedWord = searchBar.getText();
                selecetedShops = new String[1000];

                if(searchedWord.equals("") || searchedWord.equals("Search for shops around you")){
                    searchAllShops(isDarkMode);
                }
                else{
                    if (selectedCriteria.equals("Area"))
                    {
                        populateList("district", isDarkMode);
                    }
                    else if (selectedCriteria.equals("Product"))
                    {
                       populateList("product", isDarkMode);
                    }

                    else if (selectedCriteria.equals("Shop"))
                    {
                        for(int i = 0; i < shops.length; i++){
                            if((shops[i].toLowerCase()).contains(searchedWord.toLowerCase())){
                                tempShops.add(shops[i]);
                            }
                        }
                        filterShops(isDarkMode);
                    }


                }
                window.repaint();
            }
        });
        inboxButton = new JButton("Inbox");
        inboxButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                InboxView inboxView = new InboxView(buyerobj.getEmail(),window, isDarkMode);
                window.setVisible(false);
            }
        });


        topSearchBarLayout.add(logoutButton);
        topSearchBarLayout.add(inboxButton);
        topSearchBarLayout.add(searchBar);
        topSearchBarLayout.add(searchCriteriaComboBox);
        topSearchBarLayout.add(searchButton);
        window.add(topSearchBarLayout, BorderLayout.NORTH);
        window.add(scrollPane);
        window.setVisible(true);
    }

    private void searchAllShops(boolean isDarkMode){
        shopList = new JList(shops);
        shopList.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        shopList.setForeground(isDarkMode ? Color.white : Color.BLACK);
        scrollPane.setViewportView(shopList);
        shopList.setLayoutOrientation(JList.VERTICAL);
        shopList.setFixedCellHeight(60);
        shopList.addMouseListener(new MouseListener() {
            @Override

            public void mouseClicked(MouseEvent e) {
                selected_seller = sellers.get(shopList.getSelectedIndex());
                ShopView shopView = new ShopView(window, shopList.getSelectedIndex(), shops, buyerobj, selected_seller, db, isDarkMode);
                window.setVisible(false);
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
    }

    private void filterShops(boolean isDarkMode){
        Object [] refinedTempShops = tempShops.toArray();

        shopList = new JList(refinedTempShops);
        shopList.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        shopList.setForeground(isDarkMode ? Color.white : Color.BLACK);
        scrollPane.setViewportView(shopList);
        shopList.setLayoutOrientation(JList.VERTICAL);
        shopList.setFixedCellHeight(60);
        shopList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ArrayList<seller> sellersByShop=new ArrayList<>();
                for (int i=0;i<tempShops.size();i++)
                {
                    sellersByShop.add(db.getSellerByShopName (tempShops.get(i)));
                }

                selected_seller = sellersByShop.get(shopList.getSelectedIndex());
                for(int i = 0; i < shops.length; i++){
                    if(refinedTempShops.length != 0 && refinedTempShops[shopList.getSelectedIndex()].equals(shops[i])){
                        clickedItemIndex = i;
                    }
                }
                if(refinedTempShops.length != 0){
                    ShopView shopView = new ShopView(window, clickedItemIndex, shops, buyerobj, selected_seller, db, isDarkMode);
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
    }
    public ArrayList<store> getShops()
    {
        ArrayList<store> shops = new ArrayList<>();
        return shops;
    }
    private void populateList (String table, boolean isDarkMode)
    {
        returnedShops = db.searchShops(searchedWord,table);
        for(int i=0;i<returnedShops.size();i++)
        {
            tempShops.add(returnedShops.get(i).getStoreName());
        }
        filterShops(isDarkMode);
    }
}
