//import javafx.scene.text.Text;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AccountDetailsView {

    JFrame window;
    DatabaseAccess db = new DatabaseAccess();
    public AccountDetailsView(JFrame previousWindowFrame, int selectedAccount, DefaultListModel userAccounts, ArrayList<seller> sellers, ArrayList<buyer> buyers, String nameOfClass, int selectedFromSellers, JList userAccountsList, ArrayList allUsers, int selectedFromBuyers, boolean isDarkMode){
        //Frame
        window = new JFrame(""+userAccounts.getElementAt(selectedAccount));
        window.setMinimumSize(new Dimension(600, 500));
        window.setLocation(450, 250);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.getContentPane().setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        //

        JPanel backBoxLayout = new JPanel();
        backBoxLayout.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        backBoxLayout.setLayout(new BoxLayout(backBoxLayout, BoxLayout.LINE_AXIS));

        JPanel boxLayout = new JPanel();
        boxLayout.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        boxLayout.setLayout(new BoxLayout(boxLayout, BoxLayout.LINE_AXIS));

        JButton removeAccountButton = new JButton("Remove");
        removeAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
		
		if (nameOfClass.equals("seller")){
	
			db.removeSellerAccount(sellers.get(selectedFromSellers).getSellerID());
			sellers.remove(selectedFromSellers);
           	
			userAccounts.remove(selectedAccount);
		       	previousWindowFrame.setVisible(true);
                        window.setVisible(false);				
		}
		else {
			db.removeBuyerAccount(buyers.get(selectedFromBuyers).getBuyerID());
			buyers.remove(selectedFromBuyers);
           	
			userAccounts.remove(selectedAccount);
		       	previousWindowFrame.setVisible(true);
                        window.setVisible(false);
		}		
		    
            }
        });

        JButton validateAccountButton = new JButton("Validate");
        validateAccountButton.addActionListener(new ActionListener() {
            @Override
	    /*
	     * Change validation in Database to true. 
	     */
            public void actionPerformed(ActionEvent e) {
		String _id;
		if (nameOfClass.equals("seller")) {
			_id = sellers.get(selectedFromSellers).getSellerID();
			((seller)sellers.get(selectedFromSellers)).setValidation(true);
		}
		else { 
			_id = buyers.get(selectedFromBuyers).getBuyerID();
			((buyer)buyers.get(selectedFromBuyers)).setValidation(true);
		}
		db.setValidation(_id, nameOfClass, true);
		
		previousWindowFrame.setVisible(true);
                window.setVisible(false);

	    }		
			
	
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousWindowFrame.setVisible(true);
                window.setVisible(false);
            }
        });

        if(nameOfClass.equals("buyer")){
            showBuyerDetails((buyer) buyers.get(selectedAccount), isDarkMode);
        }
        else{
            showSellerDetails((seller)sellers.get(selectedFromSellers),db.getShopFromSeller((seller)sellers.get(selectedFromSellers)), isDarkMode);
        }

        backBoxLayout.add(backButton);

        boxLayout.add(validateAccountButton);
        boxLayout.add(Box.createHorizontalGlue());
        boxLayout.add(removeAccountButton);

        window.add(backBoxLayout, BorderLayout.NORTH);
        window.add(boxLayout, BorderLayout.SOUTH);
        window.setVisible(true);
    }

    private void showBuyerDetails(buyer user, boolean isDarkMode){
        JLabel nameLabel = new JLabel("Name : ");
        nameLabel.setBounds(150, 60, 50, 27);
        nameLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JLabel userName = new JLabel(user.getName());
        userName.setBounds(320, 60, 150, 27);
        userName.setForeground(isDarkMode ? Color.white : Color.BLACK);

        JLabel idLabel = new JLabel("ID Number : ");
        idLabel.setBounds(150, 90, 150, 27);
        idLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JLabel userID = new JLabel(user.getBuyerID());
        userID.setBounds(320, 90, 150, 27);
        userID.setForeground(isDarkMode ? Color.white : Color.BLACK);

        JLabel locationLabel = new JLabel("Location : ");
        locationLabel.setBounds(150, 120, 150, 27);
        locationLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JLabel userLocation = new JLabel(db.getUserDistrict(user.getBuyerID(), "buyer"));
        userLocation.setBounds(320, 120, 150, 27);
        userLocation.setForeground(isDarkMode ? Color.white : Color.BLACK);

        JLabel phoneNumberLabel = new JLabel("Cellphone Number : ");
        phoneNumberLabel.setBounds(150, 150, 150, 27);
        phoneNumberLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JLabel userPhoneNumber = new JLabel(user.getContactNumber());
        userPhoneNumber.setBounds(320, 150, 150, 27);
        userPhoneNumber.setForeground(isDarkMode ? Color.white : Color.BLACK);

        JLabel emailAddressLabel = new JLabel("Email Address : ");
        emailAddressLabel.setBounds(150, 180, 150, 27);
        emailAddressLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JLabel userEmailAddress = new JLabel(user.getEmail());
        userEmailAddress.setBounds(320, 180, 150, 27);
        userEmailAddress.setForeground(isDarkMode ? Color.white : Color.BLACK);

        JLabel passwordLabel = new JLabel("Encrypted Password : ");
        passwordLabel.setBounds(150, 210, 150, 27);
        passwordLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JLabel userPassword = new JLabel(user.getPassword());
        userPassword.setBounds(320, 210, 150, 27);
        userPassword.setForeground(isDarkMode ? Color.white : Color.BLACK);

        JLabel validateLabel = new JLabel("Validation : ");
        validateLabel.setBounds(150, 240, 150, 27);
        validateLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JLabel validation = new JLabel(user.getValidation() ? "Validated" : "Not Validated");
        validation.setBounds(320, 240, 150, 27);
        validation.setForeground(isDarkMode ? Color.white : Color.BLACK);

        window.add(nameLabel);
        window.add(userName);

        window.add(idLabel);
        window.add(userID);

        window.add(locationLabel);
        window.add(userLocation);

        window.add(phoneNumberLabel);
        window.add(userPhoneNumber);

        window.add(emailAddressLabel);
        window.add(userEmailAddress);

        window.add(passwordLabel);
        window.add(userPassword);

        window.add(validateLabel);
        window.add(validation);
        window.add(new JLabel());
    }

    private void showShopDetails(store shop, boolean isDarkMode){
        JLabel shopNameLabel = new JLabel("Shop Name : ");
        shopNameLabel.setBounds(150, 270, 150, 50);
        shopNameLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JLabel actualShopName = new JLabel(shop.getStoreName());
        actualShopName.setBounds(320, 270, 150, 50);
        actualShopName.setForeground(isDarkMode ? Color.white : Color.BLACK);

        JLabel bankAccNumberLabel = new JLabel("Bank Account Number : ");
        bankAccNumberLabel.setBounds(150, 300, 180, 50);
        bankAccNumberLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JLabel actualBankAccNum = new JLabel(shop.getAccountNumber());
        actualBankAccNum.setBounds(320, 300, 150, 50);
        actualBankAccNum.setForeground(isDarkMode ? Color.white : Color.BLACK);

        JLabel bankNameLabel = new JLabel("Bank Name : ");
        bankNameLabel.setBounds(150, 330, 150, 50);
        bankNameLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JLabel actualBankName = new JLabel(shop.getBankName());
        actualBankName.setBounds(320, 330, 150, 50);
        actualBankName.setForeground(isDarkMode ? Color.white : Color.BLACK);

        JLabel bankBranchCodeLabel = new JLabel("Bank Branch code : ");
        bankBranchCodeLabel.setBounds(150, 360, 150, 50);
        bankBranchCodeLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JLabel actualBankBranchCode = new JLabel(shop.getBranch());
        actualBankBranchCode.setBounds(320, 360, 150, 50);
        actualBankBranchCode.setForeground(isDarkMode ? Color.white : Color.BLACK);

        JLabel deliveryMethodLabel = new JLabel("Delivery Method : ");
        deliveryMethodLabel.setBounds(150, 390, 150, 50);
        deliveryMethodLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JLabel actualDeliveryMethod = new JLabel(shop.getDelivery());
        actualDeliveryMethod.setBounds(320, 390, 150, 50);
        actualDeliveryMethod.setForeground(isDarkMode ? Color.white : Color.BLACK);


        window.add(shopNameLabel);
        window.add(actualShopName);

        window.add(bankAccNumberLabel);
        window.add(actualBankAccNum);

        window.add(bankNameLabel);
        window.add(actualBankName);

        window.add(bankBranchCodeLabel);
        window.add(actualBankBranchCode);

        window.add(deliveryMethodLabel);
        window.add(actualDeliveryMethod);
        window.add(new JLabel());
    }

    private void showSellerDetails(seller user, store x, boolean isDarkMode){
        JLabel nameLabel = new JLabel("Name : ");
        nameLabel.setBounds(150, 60, 50, 27);
        nameLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JLabel userName = new JLabel(user.getName());
        userName.setBounds(320, 60, 150, 27);
        userName.setForeground(isDarkMode ? Color.white : Color.BLACK);

        JLabel idLabel = new JLabel("ID Number : ");
        idLabel.setBounds(150, 90, 150, 27);
        idLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JLabel userID = new JLabel(user.getSellerID());
        userID.setBounds(320, 90, 150, 27);
        userID.setForeground(isDarkMode ? Color.white : Color.BLACK);

        JLabel locationLabel = new JLabel("Location : ");
        locationLabel.setBounds(150, 120, 150, 27);
        locationLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JLabel userLocation = new JLabel(db.getUserDistrict(user.getSellerID(), "seller"));
        userLocation.setBounds(320, 120, 150, 27);
        userLocation.setForeground(isDarkMode ? Color.white : Color.BLACK);

        JLabel phoneNumberLabel = new JLabel("Cellphone Number : ");
        phoneNumberLabel.setBounds(150, 150, 150, 27);
        phoneNumberLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JLabel userPhoneNumber = new JLabel(user.getContactNumber());
        userPhoneNumber.setBounds(320, 150, 150, 27);
        userPhoneNumber.setForeground(isDarkMode ? Color.white : Color.BLACK);

        JLabel emailAddressLabel = new JLabel("Email Address : ");
        emailAddressLabel.setBounds(150, 180, 150, 27);
        emailAddressLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JLabel userEmailAddress = new JLabel(user.getEmail());
        userEmailAddress.setBounds(320, 180, 150, 27);
        userEmailAddress.setForeground(isDarkMode ? Color.white : Color.BLACK);

        JLabel passwordLabel = new JLabel("Encrypted Password : ");
        passwordLabel.setBounds(150, 210, 150, 27);
        passwordLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JLabel userPassword = new JLabel(user.getPassword());
        userPassword.setBounds(320, 210, 150, 27);
        userPassword.setForeground(isDarkMode ? Color.white : Color.BLACK);

        JLabel validateLabel = new JLabel("Validation : ");
        validateLabel.setBounds(150, 240, 150, 27);
        validateLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JLabel validation = new JLabel(user.getValidation() ? "Validated" : "Not Validated");
        validation.setBounds(320, 240, 150, 27);
        validation.setForeground(isDarkMode ? Color.white : Color.BLACK);

        window.add(nameLabel);
        window.add(userName);

        window.add(idLabel);
        window.add(userID);

        window.add(locationLabel);
        window.add(userLocation);

        window.add(phoneNumberLabel);
        window.add(userPhoneNumber);

        window.add(emailAddressLabel);
        window.add(userEmailAddress);

        window.add(passwordLabel);
        window.add(userPassword);

        window.add(validateLabel);
        window.add(validation);
        window.add(new JLabel());

        showShopDetails(x, isDarkMode);
    }
}
