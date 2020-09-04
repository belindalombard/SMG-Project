import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class HomeView extends SignUpView{
    public HomeView(JFrame previousWindowFrame){
        //Frame
        JFrame window = new JFrame("Sell My Goods: Home");
        window.setMinimumSize(new Dimension(800, 600));
        window.setLocation(300, 150);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        //

        JPanel topSearchBarLayout = new JPanel(new FlowLayout());
        JTextField searchBar = new JTextField(25);
        JButton searchButton = new JButton("Search");

        String [] shops = new String[100];
        for(int i = 0; i < 100; i++){
            shops[i] = "Shop "+i;
        }
        JList shopList = new JList(shops);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(shopList);
        shopList.setLayoutOrientation(JList.VERTICAL);
        shopList.setFixedCellHeight(60);
        shopList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
            }
        });

        topSearchBarLayout.add(searchBar);
        topSearchBarLayout.add(searchButton);
        window.add(topSearchBarLayout, BorderLayout.NORTH);
        window.add(scrollPane);
        window.setVisible(true);
    }
}
