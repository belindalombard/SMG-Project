import javax.swing.*;
import javax.swing.text.FlowView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;

public class login {

    JButton loginButton, registrationButton;
    JPanel area;
    JPasswordField passwordField;
    JTextField usernameField;
    JLabel usernameLabel;
    JLabel passwordLabel;
    public login()
    {
        //window
        JFrame window = new JFrame("Sell My Goods: Login");
        window.setMinimumSize(new Dimension(700, 300));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocation(300, 300);

        JPanel grd = new JPanel(new GridLayout(3, 2));
        JPanel flow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 100));

        JPanel buttonLayout = new JPanel();
        buttonLayout.setLayout(new BoxLayout(buttonLayout, BoxLayout.LINE_AXIS));

        usernameLabel = new JLabel("Email Address:");
        usernameLabel.setHorizontalAlignment(JLabel.CENTER);
        usernameField = new JTextField(25);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setHorizontalAlignment(JLabel.CENTER);
        passwordField = new JPasswordField(25);
        passwordLabel.setLabelFor(passwordField);

        //login button
        loginButton = new JButton("Login");
        loginButton.setHorizontalAlignment(SwingConstants.CENTER);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homeView toHomeView = new homeView();
                window.setVisible(false);
            }
        });

        //registration button
        registrationButton = new JButton("Sign Up");
        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrationView registrationView = new registrationView();
                window.setVisible(false);
            }
        });

        grd.add(usernameLabel);
        grd.add(usernameField);
        grd.add(passwordLabel);
        grd.add(passwordField);

        buttonLayout.add(registrationButton);
        buttonLayout.add(Box.createHorizontalGlue());
        buttonLayout.add(loginButton);
        window.add(buttonLayout, BorderLayout.SOUTH);

        flow.add(grd);
        window.add(flow, BorderLayout.CENTER);

        flow.setBackground(new Color(118,215,196));
        buttonLayout.setBackground(new Color(118,215,196));
        grd.setBackground(new Color(118,215,196));

        window.setVisible(true);
    }

    public static void main(String[] args) {

        login start = new login();
    }
}
