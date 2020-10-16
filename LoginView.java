import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.AttributedCharacterIterator;
import java.util.Arrays;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.border.*;
import java.util.Map;
import java.util.concurrent.Flow;

public class LoginView {
    JFrame window;
    DatabaseAccess db = new DatabaseAccess(window);
    JLabel appName, nameLabel, passwordLabel;
    JPanel unknownAccountFlow, boxLayout, flowSignUpLayout;
    public LoginView(){
        //Frame
        window = new JFrame("Sell My Goods: Login");
        window.setMinimumSize(new Dimension(500, 250));
        window.setLocation(450, 200);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //
        // Fields

        Checkbox dark = new Checkbox("Dark Mode", false);
        dark.setBounds(200, 160,100,20);
        dark.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(dark.getState() == true){
                    appName.setForeground(Color.LIGHT_GRAY);
                    window.getContentPane().setBackground(new Color(0x1F201F));

                    dark.setBackground(new Color(0x1F201F));
                    dark.setForeground(Color.white);

                    nameLabel.setForeground(Color.WHITE);
                    passwordLabel.setForeground(Color.WHITE);

                    unknownAccountFlow.setBackground(new Color(0x1F201F));
                    boxLayout.setBackground(new Color(0x1F201F));
                    flowSignUpLayout.setBackground(new Color(0x1F201F));
                }
                else{
                    appName.setForeground(Color.GRAY);
                    window.getContentPane().setBackground(window.getBackground());

                    dark.setBackground(window.getBackground());
                    dark.setForeground(Color.BLACK);

                    nameLabel.setForeground(Color.BLACK);
                    passwordLabel.setForeground(Color.BLACK);

                    unknownAccountFlow.setBackground(window.getBackground());
                    boxLayout.setBackground(window.getBackground());
                    flowSignUpLayout.setBackground(window.getBackground());
                }

            }
        });

        appName = new JLabel("S-M-G");
        appName.setBounds(200, 10, 200, 50);
        appName.setFont(new Font(null, Font.BOLD, 25));


        nameLabel = new JLabel("Email : ");
        nameLabel.setBounds(150, 80, 150, 27);

        JTextField emailField = new JTextField(10);
        emailField.setBounds(230, 80, 150, 22);
        emailField.setBorder(new BevelBorder(2));

        passwordLabel = new JLabel("Password : ");
        passwordLabel.setBounds(150, 120, 150, 27);

        JPasswordField passwordField = new JPasswordField(10);
        passwordField.setBounds(230, 120, 150, 22);
        passwordField.setBorder(new BevelBorder(2));
        //
        //Layouts
        unknownAccountFlow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        unknownAccountFlow.setVisible(false);

        boxLayout = new JPanel();
        boxLayout.setLayout(new BoxLayout(boxLayout, BoxLayout.LINE_AXIS));
        flowSignUpLayout = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
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
               if (verify((emailField.getText()).toLowerCase(),new String(passwordField.getPassword()))) {
                   int sb = SellerOrBuyer(emailField.getText().toLowerCase());
                   if (sb==1) { //Person loggin in is a Seller.
                       // Get seller details
                       int sellerID = db.getUserCode((emailField.getText()).toLowerCase(), "seller"); //get the seller's code so that the correct paramaters can be sent through to the next view.
                       folderView = new FoldersView(sellerID, dark.getState());
                       window.setVisible(false);
                   }
                   else { //Person login in is a buyer.
                       buyer buyerobj = db.getBuyer((emailField.getText()).toLowerCase());
                       // buyerobj.setUserEmail(emailField.getText());
                       homeView = new HomeView(window, buyerobj, dark.getState());
                       window.setVisible(false);
                   }
               }
               else if(emailField.getText().equals("admin@admin.com") && ((String.valueOf(passwordField.getPassword())).equals("CoolAdmin"))){
                   AdminView adminView = new AdminView(window, dark.getState());
                   window.setVisible(false);
               }
               else {
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
                SignUpView toSignUpView = new SignUpView(window, dark.getState());
                window.setVisible(false);
            }
        });
        JLabel noAccountYetLabel = new JLabel("Don't have an account yet? ");
        noAccountYetLabel.setForeground(new Color(0x9A9B9C));
//        unknownAccountFlow.setBackground(Color.ORANGE);

        window.add(dark);
        window.add(appName);
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
    private Boolean verify(String username, String password) {
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
        int seller_buyer = db.IsBuyerOrSeller(email.toLowerCase());
        return seller_buyer;
    }

}
