//import javafx.scene.control.CheckBox;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher; 
import java.util.regex.Pattern;

public class SignUpView {
    DatabaseAccess db;
    JFrame window;
    JLabel nameLabel, idLabel, locationLabel, phoneNumberLabel, emailAddressLabel, passwordLabel, confirmPasswordLabel;
    JTextField nameField, idField, phoneNumberField, emailAddressField, passwordField, confirmPasswordField;
    JComboBox locationField;

    JButton backToLoginButton, createAccountButton;
    Checkbox seller, buyer;

    public SignUpView(){}
    public SignUpView(JFrame previousWindowFrame){
	db=new DatabaseAccess();
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
        idField = new JTextField(13); //ID Number is max 13 chars
        idField.setBounds(420, 130, 150, 27);

        locationLabel = new JLabel("Location : ");
        locationLabel.setBounds(270, 160, 150, 27);
	
        //Get list of districts from the db.
        String[] choiceList = db.GetAllDistricts();
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

        seller = new Checkbox("Seller", true);
        seller.setBounds(310, 310,100,50);

        buyer = new Checkbox("Customer");
        buyer.setBounds(420, 310,150,50);


        seller.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(seller.getState() == true){
                    buyer.setState(false);
                }
                else{
                    seller.setState(true);
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
	  	    buyer.setState(true);
                }
            }
        });

        backToLoginButton = new JButton("Back");
        backToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousWindowFrame.setVisible(true);
                window.setVisible(false);
            }
        });

        createAccountButton = new JButton("Sign Up");
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String val = Validate(nameField.getText(), idField.getText(), phoneNumberField.getText(), emailAddressField.getText(), passwordField.getText(), confirmPasswordField.getText()); //validate entered fields.
                if (val.equals("yes")){
                    db.CloseConnection();
                    if(seller.getState() == true){
                        disable();
                        seller sellerObj = new seller(idField.getText(), locationField.getSelectedItem().toString(), nameField.getText(), encrypt(passwordField.getText()), emailAddressField.getText(), phoneNumberField.getText(),false);
                        ShopSignUpView shopSignUpView = new ShopSignUpView(window, previousWindowFrame, backToLoginButton, createAccountButton, buyer, seller,sellerObj);
                    }
                    else{
                        try{
                            buyer buyerobj = new buyer(idField.getText(), locationField.getSelectedItem().toString(), nameField.getText(), encrypt(passwordField.getText()), emailAddressField.getText(), phoneNumberField.getText(), false);
                            ConfirmCustomerSignUpView confirmCustomerSignUpView = new ConfirmCustomerSignUpView(window, previousWindowFrame, buyerobj);
                            window.setVisible(false);
                            db.CloseConnection();
                        }
                        catch (Exception k){}
                    }
                }
                else{
                    JOptionPane x = new JOptionPane();
                    x.showMessageDialog(null, val, "Warning", JOptionPane.WARNING_MESSAGE);
                    x.setFocusable(true);
                }
            }
        });

        JButton adminButton = new JButton("Admin");
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminLoginView adminLoginView = new AdminLoginView();
                window.setVisible(false);
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
        topLayout.add(adminButton);
        topLayout.add(Box.createHorizontalGlue());
        topLayout.add(createAccountButton);

        window.add(topLayout, BorderLayout.NORTH);

        window.setResizable(false);
        window.setVisible(true);
    }

    public void disable(){
        backToLoginButton.setEnabled(false);
        createAccountButton.setEnabled(false);

        seller.setEnabled(false);
        buyer.setEnabled(false);
    }
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
        } catch (NoSuchAlgorithmException ex){} 

        return null;

    }

    //Returns either 1. a message indicating what needs to change or 2. "yes" 
    private String Validate(String name, String id, String cellno, String email, String Password, String cPassword){ 
	String vname = validateName(name);
	if (!vname.equals("yes"))
		return vname;

	String vid = validateID(id);	
	if (!vid.equals("yes"))
		return vid;
	
	String vcell = validateCellNo(cellno);
	if (!vcell.equals("yes"))
		return vcell;	
	
	String vemail = validateEmailAddress(email);
	if (!vemail.equals("yes"))
		return vemail;	


	String vpass = validatePassword(Password, cPassword);
	if (!vpass.equals("yes"))
		return vpass;


	return "yes";
    }


    //Validates the Name field. Returns either 1. a message indicating what needs to change or 2. "yes" 
    private String validateName(String name) {
	if (name.equals(""))
	    return "Please enter your name";
	Pattern p = Pattern.compile( "[0-9]" );
    	Matcher m = p.matcher(name);

    	if  (m.find())
		return "Name can not contain numbers";

	return "yes";

    }
    
    //Validates the ID field. Returns either 1. a message indicating what needs to change or 2. "yes" 
    private String validateID(String id){
	if (id.equals("")) 
		return "Please fill in your national ID number";
	if (!(id.length()==13))
		return "Your ID is not the correct length";
	if (!id.matches("[0-9]+"))
		return "Your ID can only contain numbers";

	//Check uniqueness of ID Number. 
	int a = db.IDExists(id);
	if ((a==0)||(a==1)) //The ID Number is already in the seller or buyer table.
		return "There is an account already linked to your ID number.";
	if (a==-2) //The connection failed
		return "There was a connection error - please check your connection before attempting to Sign up.";
	return "yes";
    }
 
    //Validates the Phone Number field. Returns either 1. a message indicating what needs to change or 2. "yes" 
    private String validateCellNo(String cell){
	if (cell.equals("")) 
		return "Please fill in a valid South African Cell Phone Number";
	if (!(cell.length()==10))
		return "Your Cell Phone number is not the correct length";
	if (!cell.matches("[0-9]+"))
		return "Your Cell Phone number can only contain numbers";

	//Check uniqueness of cell 
	int a = db.cellExists(cell);
	if ((a==0)||(a==1)) //The number is already in the seller or buyer table.
		return "There is an account already linked to this Phone Number.";
	return "yes";
    }

 
    //Validates the Email Address field. Returns either 1. a message indicating what needs to change or 2. "yes" 
    private String validateEmailAddress(String email){
	//get more validation methods or something to check that email address is valid!!!!
	if (email.equals("")) 
		return "Please fill in a valid Email Address";
		
	
	//Check pattern of email.
        Pattern patternCheck = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher emailMatcher = patternCheck.matcher(email);

        if(!emailMatcher.matches()){
        	return("The email provided is invalid");
        } 

	//Check uniqueness of email. 
	int a = db.IsBuyerOrSeller(email);
	if ((a==0)||(a==1)) //The Email is already in the seller or buyer table.
		return "There is an account already linked to this Email Address.";
	return "yes";
    }

     //Validates password. Returns a message indicating what needs to change, or yes if everything is already correct. 
     private String validatePassword(String password, String cPassword){
	if (!cPassword.equals(password))
		return "The passwords must match.";

	if (password.length()<6)
		return "Your password must be at least 6 characters long";
	
    	return "yes";
     }
}

