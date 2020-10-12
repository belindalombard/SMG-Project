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
import javax.swing.border.LineBorder;
import java.util.concurrent.Flow;

public class LoginView {
    DatabaseAccess db = new DatabaseAccess();
    public LoginView(){
        //Frame
        JFrame window = new JFrame("Sell My Goods: Login");
        window.setMinimumSize(new Dimension(500, 250));
        window.setLocation(450, 200);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //
        // Fields
        JLabel nameLabel = new JLabel("Email : ");
        nameLabel.setBounds(150, 60, 150, 27);
        JTextField emailField = new JTextField(10);
        emailField.setBounds(230, 60, 150, 27);
        JLabel passwordLabel = new JLabel("Password : ");
        passwordLabel.setBounds(150, 100, 150, 27);
        JPasswordField passwordField = new JPasswordField(10);
        passwordField.setBounds(230, 100, 150, 27);
        //
        //Layouts
        JPanel unknownAccountFlow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        unknownAccountFlow.setVisible(false);

        JPanel boxLayout = new JPanel();
        boxLayout.setLayout(new BoxLayout(boxLayout, BoxLayout.LINE_AXIS));
        JPanel flowSignUpLayout = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        //

        JLabel accountDoesNotExistLabel = new JLabel("Invalid Login Details");

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(new String(passwordField.getPassword()));
                // Verify Login credentials
	 
               FoldersView folderView;
               HomeView homeView;
               if (verify(emailField.getText(),new String(passwordField.getPassword()))) {
                       int sb = SellerOrBuyer(emailField.getText());
               	       if (sb==1)
               	       { //Person loggin in is a Seller.
               	           // Get seller details
                 		int sellerID = db.getUserCode(emailField.getText(), "seller"); //get the seller's code so that the correct paramaters can be sent through to the next view.  
		   		        folderView = new FoldersView(sellerID);
               	       }
	                   else
	                   { //Person login in is a buyer.
	                       buyer buyer = new buyer();
	                       // Get buyer details;
	                       buyer.setUserEmail(emailField.getText());
                           homeView = new HomeView(window, buyer);
                           window.setVisible(false);
                       }
               }
               else
               {
                   unknownAccountFlow.setVisible(true);
                   accountDoesNotExistLabel.setForeground(Color.RED);

                   emailField.setBorder(new LineBorder(Color.red,1));
                   passwordField.setBorder(new LineBorder(Color.red,1));
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
//        unknownAccountFlow.setBackground(Color.ORANGE);


        window.add(nameLabel);
        window.add(emailField);
        window.add(passwordLabel);
        window.add(passwordField);
        window.add(new JLabel());

        flowSignUpLayout.add(noAccountYetLabel);
        flowSignUpLayout.add(signUpButton);

        boxLayout.add(loginButton);
        boxLayout.add(Box.createHorizontalGlue());
        boxLayout.add(flowSignUpLayout);
        unknownAccountFlow.add(accountDoesNotExistLabel);

        window.add(unknownAccountFlow, BorderLayout.NORTH);
        window.add(boxLayout, BorderLayout.SOUTH);
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
        exists = db.Login(username, encPassword);
	//System.out.println(encPassword); //Trace statement
	
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

    //Method to determine whether person signing in is a seller or a buyer.
    private int SellerOrBuyer(String email) 
    {
        int seller_buyer = db.IsBuyerOrSeller(email);
        return seller_buyer;
    }

}
