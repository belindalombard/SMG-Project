import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class registrationView {

    public registrationView(){
        JFrame window = new JFrame("Sell My Goods: Registration");
        window.setMinimumSize(new Dimension(700, 350));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocation(300, 300);

        JPanel grd = new JPanel(new GridLayout(4, 2));
        JPanel flow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50));

        JPanel buttonLayout = new JPanel();
        buttonLayout.setLayout(new BoxLayout(buttonLayout, BoxLayout.LINE_AXIS));

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        JTextField nameField = new JTextField(25);

        JLabel emailAddressLabel = new JLabel("Email Address:");
        emailAddressLabel.setHorizontalAlignment(JLabel.CENTER);
        JTextField emailField = new JPasswordField(25);
        emailAddressLabel.setLabelFor(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setHorizontalAlignment(JLabel.CENTER);
        JTextField passwordField = new JPasswordField(25);
        passwordLabel.setLabelFor(passwordField);

        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setHorizontalAlignment(JLabel.CENTER);
        JTextField locationField = new JPasswordField(25);
        locationLabel.setLabelFor(locationField);

        Checkbox seller = new Checkbox("Seller", true);
        Checkbox customer = new Checkbox("Customer");
        JPanel filters = new JPanel(new FlowLayout());
        filters.add(seller);
        filters.add(customer);

        customer.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(customer.getState() == true){
                    seller.setState(false);
                }
                else{
                    seller.setState(true);
                }

            }
        });

        seller.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(seller.getState() == true){
                    customer.setState(false);
                }
                else{
                    customer.setState(true);
                }

            }
        });

        //login button
        JButton createAccount = new JButton("Create Account");
        createAccount.setHorizontalAlignment(SwingConstants.CENTER);
        createAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homeView toHomeView = new homeView();
                window.setVisible(false);
            }
        });

        //registration button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login login = new login();
                window.setVisible(false);
            }
        });

        grd.add(nameLabel);
        grd.add(nameField);
        grd.add(locationLabel);
        grd.add(locationField);
        grd.add(emailAddressLabel);
        grd.add(emailField);
        grd.add(passwordLabel);
        grd.add(passwordField);

        buttonLayout.add(loginButton);
        buttonLayout.add(Box.createHorizontalGlue());
        buttonLayout.add(createAccount);
        window.add(buttonLayout, BorderLayout.SOUTH);

        window.add(filters, BorderLayout.CENTER);

        flow.add(grd);
        window.add(flow, BorderLayout.NORTH);

        grd.setBackground(new Color(118,215,196));
        flow.setBackground(new Color(118,215,196));
        buttonLayout.setBackground(new Color(118,215,196));
        filters.setBackground(new Color(118,215,196));
        window.setVisible(true);
    }
}
