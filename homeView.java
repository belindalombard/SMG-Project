import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;

public class homeView{
    JFrame window;
    JTextField searchField;

    JPanel flowPanel, bPanel, filters, center_layout;
    JButton search, nextViewButton;
    JLabel searchLabel = new JLabel("");
    Border plainborder = BorderFactory.createLineBorder(Color.black);
    Font font = new Font("TimesRoman",Font.BOLD,25);
    Checkbox localbased,countrybased,shop,products;

    JButton searchButton;


    public homeView(){
        window = new JFrame("Sell My Goods: Home");
        window.setMinimumSize(new Dimension(700, 500));
        window.setLocation(300, 200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	
	
	    flowPanel = new JPanel(new FlowLayout());
        bPanel = new JPanel(new BorderLayout());

        //search field
        searchField = new JTextField(20);

        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //bPanel.add(searchLabel);
                searchLabel.setPreferredSize(new Dimension(600,400));
                searchLabel.setBorder(plainborder);
                searchLabel.setFont(font);
                searchLabel.setText("Search Results Will Appear Here");
                searchLabel.setHorizontalAlignment(JLabel.CENTER);
            }
        });

        //set check boxes for filters
        localbased = new Checkbox("Local-based", true);
        countrybased = new Checkbox("Country-based");
        shop = new Checkbox("Shop", true);
        products = new Checkbox("Filters");
        filters = new JPanel(new FlowLayout());
        filters.add(localbased);
        filters.add(countrybased);
        filters.add(shop);
        filters.add(products);


        //search button
//        searchButton = new JButton("Search");
        //


        //the next button
        nextViewButton = new JButton("Shop");
        nextViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shopView shopView = new shopView();
                window.setVisible(false);
            }
        });


        center_layout = new JPanel();
        flowPanel.add(searchField);
//        flowPanel.add(search);

        flowPanel.add(searchField);
        flowPanel.add(searchButton);


        bPanel.add(nextViewButton, BorderLayout.SOUTH);
        bPanel.add(flowPanel, BorderLayout.NORTH);
        center_layout.add(filters);
        center_layout.add(searchLabel);
        bPanel.add(center_layout, BorderLayout.CENTER);

        //Set Background Colors
        flowPanel.setBackground(new Color(118,215,196));
        window.setBackground(new Color(118,215,196));
        center_layout.setBackground(new Color(118,215,196));
        searchLabel.setBackground(new Color(209,242,235));
        filters.setBackground(new Color(119,215,196));


        window.add(bPanel);
        window.setVisible(true);

    }

}


