import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
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
    String selectedCriteria;

    public HomeView(JFrame previousWindowFrame, buyer buyerobj){
        this.buyerobj = buyerobj;
	selected_seller =null;	
	//Frame
        window = new JFrame("Sell My Goods: Home");
        window.setMinimumSize(new Dimension(800, 600));
        window.setLocation(300, 150);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        //

        JPanel topSearchBarLayout = new JPanel(new FlowLayout());
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
                    ShopView shopView = new ShopView(window, shopList.getSelectedIndex(), shops, buyerobj,  selected_seller, db);
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
        String choiceList [] = {"Shop", "Area", "Delivery Method", "Product"};
        searchCriteriaComboBox = new JComboBox<String>(choiceList);
        searchCriteriaComboBox.setBounds(270, 252, 150, 27);

        //getting shops based on search criteria
        selectedCriteria = searchCriteriaComboBox.getSelectedItem().toString();


        JTextField searchBar = new JTextField(25);
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempShops = new ArrayList<>();
                selectedCriteria = searchCriteriaComboBox.getSelectedItem().toString();
                System.out.println(selectedCriteria);
                String searchedWord = searchBar.getText();
                selecetedShops = new String[1000];

                if(searchedWord.equals("")){
                    searchAllShops();
                }
                else{
                    if (selectedCriteria.equals("Area"))
                    {
                        returnedShops = db.searchShops(searchedWord,"district");
                        for(int i=0;i<returnedShops.size();i++)
                        {
                            tempShops.add(returnedShops.get(i).getStoreName());
                        }

                        /*for(int i = 0; i < selecetedShops.length; i++){
                            if((returnedShops.get(i).toLowerCase()).contains(searchedWord.toLowerCase())){
                                tempShops.add(selecetedShops[i]);
                            }
                        }*/
                        filterShops();
                    }
                    else if (selectedCriteria.equals("Product"))
                    {}
                    else if (selectedCriteria.equals("Delivery Method"))
                    {}
                    else if (selectedCriteria.equals("Shop"))
                    {
                        for(int i = 0; i < shops.length; i++){
                            if((shops[i].toLowerCase()).contains(searchedWord.toLowerCase())){
                                tempShops.add(shops[i]);
                            }
                        }
                        filterShops();
                    }


                }
                window.repaint();
            }
        });





        topSearchBarLayout.add(logoutButton);
        topSearchBarLayout.add(searchBar);
        topSearchBarLayout.add(searchCriteriaComboBox);
        topSearchBarLayout.add(searchButton);
        window.add(topSearchBarLayout, BorderLayout.NORTH);
        window.add(scrollPane);
        window.setVisible(true);
    }

    private void searchAllShops(){
        shopList = new JList(shops);
        scrollPane.setViewportView(shopList);
        shopList.setLayoutOrientation(JList.VERTICAL);
        shopList.setFixedCellHeight(60);
        shopList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ShopView shopView = new ShopView(window, shopList.getSelectedIndex(), shops, buyerobj, selected_seller, db);
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

    private void filterShops(){
        Object [] refinedTempShops = tempShops.toArray();

        shopList = new JList(refinedTempShops);
        scrollPane.setViewportView(shopList);
        shopList.setLayoutOrientation(JList.VERTICAL);
        shopList.setFixedCellHeight(60);
        shopList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for(int i = 0; i < shops.length; i++){
                    if(refinedTempShops.length != 0 && refinedTempShops[shopList.getSelectedIndex()].equals(shops[i])){
                        clickedItemIndex = i;
                    }
                }
                if(refinedTempShops.length != 0){
                    ShopView shopView = new ShopView(window, clickedItemIndex, shops, buyerobj, selected_seller, db);
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
}
