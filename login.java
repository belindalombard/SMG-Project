import javax.swing.*;
import javax.swing.text.FlowView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;

public class login {

    JButton loginButton;
    JPanel area;
    JPasswordField passwordField;
    JTextField usernameField;
    JLabel usernameLabel;
    JLabel passwordLabel;
    public login()
    {
        //window
        JFrame window = new JFrame("Sell My Goods: Login");
        window.setMinimumSize(new Dimension(800, 400));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocation(300, 300);

        JPanel grd = new JPanel(new GridLayout(3, 2));
        JPanel flow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 100));

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

        grd.add(usernameLabel);
        grd.add(usernameField);
        grd.add(passwordLabel);
        grd.add(passwordField);
        window.add(loginButton, BorderLayout.SOUTH);

        flow.add(grd);
        window.add(flow, BorderLayout.CENTER);

        window.setVisible(true);

    }

    public static void main(String[] args) {

        login start = new login();
    }
}
