import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.concurrent.Flow;

public class LoginView {
    public LoginView(){
        //Frame
        JFrame window = new JFrame("Sell My Goods: Login");
        window.setMinimumSize(new Dimension(500, 300));
        window.setLocation(450, 200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //
        // Fields
        JLabel nameLabel = new JLabel("Username : ");
        JTextField nameField = new JTextField(10);
        JLabel passwordLabel = new JLabel("Password : ");
        JPasswordField passwordField = new JPasswordField(10);
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
                //System.out.println(new String(passwordField.getPassword()));
                // Verify Login credentials
               if (verify(nameField.getText(),new String(passwordField.getPassword()))) {
                   HomeView homeView = new HomeView(window);
                   window.setVisible(false);
               }
               else
               {
                   // Error management :Pop up or red text, Linda
               }

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

    // Lookup user in the database
    private Boolean verify(String username, String password)
    {
        boolean exists = false;
        String encPassword = encrypt(password);
        // lookUp method waiting for the database design :Belinda
        return exists;
    }
    //Method for encrypting user password
    private String encrypt(String pass)
    {

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] passBytes = pass.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer encPassword = new StringBuffer();
            for(int i=0;i<digested.length;i++){
                encPassword.append(Integer.toHexString(0xff & digested[i]));
            }
            return encPassword.toString();
        } catch (NoSuchAlgorithmException ex) {

        }
        return null;




    }
}
