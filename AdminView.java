import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminView {
    public AdminView(){
        //Frame
        JFrame window = new JFrame("Sell My Goods: Admin");
        window.setMinimumSize(new Dimension(600, 500));
        window.setLocation(450, 250);
        window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
        userAccountsList.setLayoutOrientation(JList.VERTICAL);
        userAccountsList.setFixedCellHeight(60);

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
