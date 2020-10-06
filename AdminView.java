import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AdminView {
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

        DefaultListModel userAccounts = new DefaultListModel();
        for(int i = 0; i < 20; i++){
            userAccounts.addElement("Account " + i);
        }
        JList userAccountsList = new JList(userAccounts);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(userAccountsList);
        scrollPane.setBorder(new EmptyBorder(0,0,0,0));
        userAccountsList.setLayoutOrientation(JList.VERTICAL);
        userAccountsList.setFixedCellHeight(60);


        userAccountsList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!userAccounts.isEmpty()){
                    AccountDetailsView accountDetailsView = new AccountDetailsView(window, userAccountsList.getSelectedIndex(), userAccounts);
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
    }

    public static void main(String [] args){
        AdminView x = new AdminView();
    }
}
