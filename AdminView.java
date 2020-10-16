import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class AdminView {
    DatabaseAccess db = new DatabaseAccess();
    DefaultListModel userAccounts;
    JList userAccountsList;
    JFrame window;
    public AdminView(JFrame previousWindowFrame, boolean isDarkMode){
        //Frame
        window = new JFrame("Sell My Goods: Admin");
        window.setMinimumSize(new Dimension(600, 500));
        window.setLocation(450, 250);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        //

        JPanel buttonsBoxLayout = new JPanel();
        buttonsBoxLayout.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        buttonsBoxLayout.setLayout(new BoxLayout(buttonsBoxLayout, BoxLayout.LINE_AXIS));

        JPanel boxLayout = new JPanel();
        boxLayout.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        boxLayout.setLayout(new BoxLayout(boxLayout, BoxLayout.LINE_AXIS));

        userAccounts = db.getAccountList();
        userAccountsList = new JList(userAccounts);
        userAccountsList.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        userAccountsList.setForeground(isDarkMode ? Color.white : Color.BLACK);
        ArrayList<Object> allUsers = db.getAllUsers();
        ArrayList<buyer> buyers = new ArrayList<>();
        ArrayList<seller> sellers = new ArrayList<>();
        for(int i = 0; i < allUsers.size(); i++){
            if(allUsers.get(i).getClass().getName().equals("buyer")){
                buyers.add((buyer) allUsers.get(i));
            }
            else{
                sellers.add((seller)allUsers.get(i));
            }
        }
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(userAccountsList);
        scrollPane.setBorder(new EmptyBorder(0,0,0,0));
        userAccountsList.setLayoutOrientation(JList.VERTICAL);
        userAccountsList.setFixedCellHeight(60);


        userAccountsList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    int selectedFromSellers = -1;
		    int selectedFromBuyers = -1;
		    String selectedClass="buyer" ; //just sets default value.
                    if(!userAccounts.isEmpty()){
			int index = userAccountsList.getSelectedIndex(); //Gets index of currently selected item in overall list.
			selectedClass = allUsers.get(index).getClass().getName(); //Is current item a seller or a buyer?
			if (selectedClass.equals("seller")) 
				selectedFromSellers=index-buyers.size(); //Index of seller is the total number of items - the size of the buyers since buyers comes before sellers
		    	else 
				selectedFromBuyers=index;

			//Shows detail of item selected at the moment.
                        AccountDetailsView accountDetailsView = new AccountDetailsView(window, userAccountsList.getSelectedIndex(), userAccounts, sellers, buyers, selectedClass, selectedFromSellers, userAccountsList, allUsers, selectedFromBuyers, isDarkMode);
                        window.setVisible(false);
                    }
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

        JButton addAccountButton = new JButton("Add");
        addAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Add user account
                SignUpView x = new SignUpView(window, isDarkMode);
            }
        });

        JButton editAccountButton = new JButton("Update");
        editAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Edit a user's account
//                refreshList();
            }
        });

        JButton backButton = new JButton("Logout");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginView loginView = new LoginView();
                window.dispose();
            }
        });


        buttonsBoxLayout.add(addAccountButton);
        buttonsBoxLayout.add(Box.createHorizontalGlue());
        buttonsBoxLayout.add(editAccountButton);

        boxLayout.add(backButton);

        window.add(scrollPane);
        window.add(boxLayout, BorderLayout.NORTH);
        window.add(buttonsBoxLayout, BorderLayout.SOUTH);
        window.setVisible(true);
//        db.CloseConnection();
    }

//    public static void main(String [] args){
//        AdminView x = new AdminView();
//    }

    public void refreshList(){
        userAccounts = db.getAccountList();
        userAccountsList.setModel(userAccounts);
    }
}
