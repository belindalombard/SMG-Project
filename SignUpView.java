import javafx.scene.control.CheckBox;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SignUpView {

    JFrame window;
    JLabel nameLabel, idLabel, locationLabel, phoneNumberLabel, emailAddressLabel, passwordLabel, confirmPasswordLabel;
    JTextField nameField, idField, phoneNumberField, emailAddressField, passwordField, confirmPasswordField;
    JComboBox locationField;

    public SignUpView(){
        //Frame
        window = new JFrame("Sell My Goods: Sign Up");
        window.setMinimumSize(new Dimension(800, 500));
        window.setLayout(null);
        window.setLocation(300, 200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //

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
        String [] locs = {"Hello", "World"};
        locationField = new JComboBox<String>(locs);
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
        seller.setBounds(330, 310,100,50);

        Checkbox buyer = new Checkbox("Customer");
        buyer.setBounds(440, 310,150,50);


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

        window.setResizable(false);
        window.setVisible(true);
    }
}
