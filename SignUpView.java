import javafx.scene.control.CheckBox;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SignUpView {

    JFrame window;
    JLabel nameLabel, idLabel, locationLabel, phoneNumberLabel, emailAddressLabel, passwordLabel, confirmPasswordLabel;
    JTextField nameField, idField, phoneNumberField, emailAddressField, passwordField, confirmPasswordField;
    JComboBox locationField;

    public SignUpView(){}
    public SignUpView(JFrame previousWindowFrame){
        //Frame
        window = new JFrame("Sell My Goods: Sign Up");
        window.setMinimumSize(new Dimension(800, 500));
//        window.setLayout(null);
        window.setLocation(300, 200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //

        JPanel topLayout = new JPanel();
        topLayout.setLayout(new BoxLayout(topLayout, BoxLayout.LINE_AXIS));

        nameLabel = new JLabel("Name : ");
        nameLabel.setBounds(270, 100, 50, 27);
        nameField = new JTextField(10);
        nameField.setBounds(420, 100, 150, 27);

        idLabel = new JLabel("ID Number : ");
        idLabel.setBounds(270, 130, 150, 27);
        idField = new JTextField(10);
        idField.setBounds(420, 130, 150, 27);

        locationLabel = new JLabel("Location : ");
        locationLabel.setBounds(270, 160, 150, 27);
        String choiceList [] = {"Choose", "Location1", "Location2"};
        locationField = new JComboBox<String>(choiceList);
        locationField.setBounds(420, 160, 150, 27);

        phoneNumberLabel = new JLabel("Cellphone Number : ");
        phoneNumberLabel.setBounds(270, 190, 150, 27);
        phoneNumberField = new JTextField(10);
        phoneNumberField.setBounds(420, 190, 150, 27);

        emailAddressLabel = new JLabel("Email Address : ");
        emailAddressLabel.setBounds(270, 220, 150, 27);
        emailAddressField = new JTextField(10);
        emailAddressField.setBounds(420, 220, 150, 27);

        passwordLabel = new JLabel("Password : ");
        passwordLabel.setBounds(270, 250, 150, 27);
        passwordField = new JPasswordField(10);
        passwordField.setBounds(420, 250, 150, 27);

        confirmPasswordLabel = new JLabel("Confirm Password : ");
        confirmPasswordLabel.setBounds(270, 280, 150, 27);
        confirmPasswordField = new JPasswordField(10);
        confirmPasswordField.setBounds(420, 280, 150, 27);

        Checkbox seller = new Checkbox("Seller", true);
        seller.setBounds(310, 310,100,50);

        Checkbox buyer = new Checkbox("Customer");
        buyer.setBounds(420, 310,150,50);


        seller.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(seller.getState() == true){
                    buyer.setState(false);
                }
                else{
                    buyer.setState(true);
                }
            }
        });

        buyer.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(buyer.getState() == true){
                    seller.setState(false);
                }
                else{
                    seller.setState(true);
                }
            }
        });

        JButton backToLoginButton = new JButton("Back");
        backToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousWindowFrame.setVisible(true);
                window.setVisible(false);
            }
        });

        JButton createAccountButton = new JButton("Sign Up");
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(seller.getState() == true){
                    //Go to further shop registrationView
                    System.out.println("Going to further reg");
                }
                else{
                    HomeView homeView = new HomeView(window);
                    window.setVisible(false);
                }
            }
        });

        //Adding components in the window
        window.add(nameLabel);
        window.add(nameField);
        window.add(idLabel);
        window.add(idField);
        window.add(locationLabel);
        window.add(locationField);
        window.add(phoneNumberLabel);
        window.add(phoneNumberField);
        window.add(emailAddressLabel);
        window.add(emailAddressField);
        window.add(passwordLabel);
        window.add(passwordField);
        window.add(confirmPasswordLabel);
        window.add(confirmPasswordField);

        window.add(seller);
        window.add(buyer);
        window.add(new JLabel());

        topLayout.add(backToLoginButton);
        topLayout.add(Box.createHorizontalGlue());
        topLayout.add(createAccountButton);
        window.add(topLayout, BorderLayout.NORTH);

        window.setResizable(false);
        window.setVisible(true);
    }
}
