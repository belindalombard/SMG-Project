import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;

public class LoginView {
    public LoginView(){
        //Frame
        JFrame window = new JFrame("Sell My Goods: Login");
        window.setMinimumSize(new Dimension(600, 300));
        window.setLocation(450, 200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //

        //Layouts
        JPanel userNameFlow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 80));
        JPanel passwordFlow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        JPanel flowsGrid = new JPanel(new GridLayout(2, 1));
        JPanel boxLayout = new JPanel();
        boxLayout.setLayout(new BoxLayout(boxLayout, BoxLayout.LINE_AXIS));
        JPanel flowSignUpLayout = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        //

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomeView homeView = new HomeView(window);
                window.setVisible(false);
            }
        });

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignUpView toSignUpView = new SignUpView(window);
                window.setVisible(false);
            }
        });
        JLabel noAccountYetLabel = new JLabel("Don't have an account yet? ");
        noAccountYetLabel.setForeground(new Color(0x9A9B9C));

        JLabel nameLabel = new JLabel("Username : ");
        JTextField nameField = new JTextField(10);
        JLabel passwordLabel = new JLabel("Password : ");
        JPasswordField passwordField = new JPasswordField(10);

        userNameFlow.add(nameLabel);
        userNameFlow.add(nameField);
        passwordFlow.add(passwordLabel);
        passwordFlow.add(passwordField);

        flowsGrid.add(userNameFlow);
        flowsGrid.add(passwordFlow);

        flowSignUpLayout.add(noAccountYetLabel);
        flowSignUpLayout.add(signUpButton);

        boxLayout.add(loginButton);
        boxLayout.add(Box.createHorizontalGlue());
        boxLayout.add(flowSignUpLayout);

        window.add(boxLayout, BorderLayout.SOUTH);
        window.add(flowsGrid, BorderLayout.CENTER);
        window.setResizable(false);
        window.setVisible(true);
    }

    public static void main(String [] args){
        LoginView x = new LoginView();
    }
}
