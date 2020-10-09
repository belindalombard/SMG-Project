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

    public AdminView(){
        //Frame
        JFrame window = new JFrame("Sell My Goods: Admin");
        window.setMinimumSize(new Dimension(600, 500));
        window.setLocation(450, 250);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        //

        JPanel buttonsBoxLayout = new JPanel();
        buttonsBoxLayout.setLayout(new BoxLayout(buttonsBoxLayout, BoxLayout.LINE_AXIS));

        DefaultListModel userAccounts = db.getAccountList();
        JList userAccountsList = new JList(userAccounts);
        ArrayList<Object> allUsers = db.getAllUsers();
        ArrayList<buyer> buyers = new ArrayList<>();
        ArrayList<seller> sellers = new ArrayList<>();
//        Dictionary sellers = new Hashtable();
        for(int i = 0; i < allUsers.size(); i++){
            if(allUsers.get(i).getClass().getName().equals("buyer")){
                buyers.add((buyer) allUsers.get(i));
            }
            else{
                sellers.add((seller)allUsers.get(i));
//                sellers.put(i, allUsers.get(i));
            }
        }
//        for(int i = 0; i < buyers.size(); i++){
//            System.out.println(buyers.get(i));
//            System.out.println(buyers.get(i).getName());
//        }
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(userAccountsList);
        scrollPane.setBorder(new EmptyBorder(0,0,0,0));
        userAccountsList.setLayoutOrientation(JList.VERTICAL);
        userAccountsList.setFixedCellHeight(60);


        userAccountsList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedFromSellers = 0;
                if(!userAccounts.isEmpty()){
                    for(int i = 0; i < sellers.size(); i++){
                        if(sellers.get(i).equals(allUsers.get(userAccountsList.getSelectedIndex()))){
                            selectedFromSellers = i;
                        }
                    }
                    String selectedClass = allUsers.get(userAccountsList.getSelectedIndex()).getClass().getName();
                    AccountDetailsView accountDetailsView = new AccountDetailsView(window, userAccountsList.getSelectedIndex(),
                            userAccounts, sellers, buyers, selectedClass, selectedFromSellers);
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

        JButton addAccountButton = new JButton("Add");
        addAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Add user account
                SignUpView x = new SignUpView(window);
            }
        });

        JButton editAccountButton = new JButton("Update");
        editAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Edit a user's account
                
            }
        });

        JButton removeAccountButton = new JButton("Remove");
        removeAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //remove user account
                for(int i = 0; i < buyers.size(); i++){
                    if(buyers.get(i).equals(allUsers.get(userAccountsList.getSelectedIndex()))){
                        buyers.remove(i);
                    }
                }

                for(int i = 0; i < sellers.size(); i++){
                    if(sellers.get(i).equals(allUsers.get(userAccountsList.getSelectedIndex()))){
                        sellers.remove(i);
                    }
                }
                userAccounts.remove(userAccountsList.getSelectedIndex());

                //Remove from database: Belinda
            }
        });

        buttonsBoxLayout.add(addAccountButton);
        buttonsBoxLayout.add(Box.createHorizontalGlue());
        buttonsBoxLayout.add(editAccountButton);
        buttonsBoxLayout.add(Box.createHorizontalGlue());
        buttonsBoxLayout.add(removeAccountButton);

        window.add(scrollPane);
        window.add(buttonsBoxLayout, BorderLayout.SOUTH);
        window.setVisible(true);
        db.CloseConnection();
    }

    public static void main(String [] args){
        AdminView x = new AdminView();
    }
}
