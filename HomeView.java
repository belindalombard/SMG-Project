import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class HomeView extends SignUpView{

    JList shopList;
    int clickedItemIndex;
    String [] shops;
    JScrollPane scrollPane;
    JFrame window;
    ArrayList<String> tempShops;

    public HomeView(JFrame previousWindowFrame){
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

        shops = new String[100];
        for(int i = 0; i < shops.length; i++){
            shops[i] = "Shop Name "+i;
        }
        shopList = new JList(shops);
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(shopList);
        shopList.setLayoutOrientation(JList.VERTICAL);
        shopList.setFixedCellHeight(60);
        shopList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ShopView shopView = new ShopView(window, shopList.getSelectedIndex(), shops);
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

        JTextField searchBar = new JTextField(25);
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempShops = new ArrayList<>();
                String searchedWord = searchBar.getText();

                if(searchedWord.equals("")){
                    searchAllShops();
                }
                else{
                    for(int i = 0; i < shops.length; i++){
                        if((shops[i].toLowerCase()).contains(searchedWord.toLowerCase())){
                            tempShops.add(shops[i]);
                        }
                    }
                    filterShops();
                }
                window.repaint();
            }
        });

        topSearchBarLayout.add(logoutButton);
        topSearchBarLayout.add(searchBar);
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
                ShopView shopView = new ShopView(window, shopList.getSelectedIndex(), shops);
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
                    ShopView shopView = new ShopView(window, clickedItemIndex, shops);
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
}
