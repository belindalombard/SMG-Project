import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ShopSignUpView {
    DatabaseAccess db; 
    public ShopSignUpView(JFrame previousWindowFrame, JFrame adminWindow, JButton backButton, JButton createAccountButton, Checkbox buyer, Checkbox seller, seller sellerObj){
        //Frame
        db = new DatabaseAccess();
        System.out.println(adminWindow.getTitle());
        JFrame window = new JFrame("Sell My Goods: Shop Registration");
        window.setMinimumSize(new Dimension(500, 480));
        window.setLocation(450, 250);
        window.setResizable(false);
        window.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {
                backButton.setEnabled(true);
                createAccountButton.setEnabled(true);
                buyer.setEnabled(true);
                seller.setEnabled(true);
            }
            @Override
            public void windowClosed(WindowEvent e) {}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        //

        JLabel shopNameLabel = new JLabel("Shop Name : ");
        shopNameLabel.setBounds(110, 40, 150, 50);
        JTextField shopNameField = new JTextField(10);
        shopNameField.setBounds(270, 52, 150, 27);

        JLabel bankAccNumberLabel = new JLabel("Bank Account Number : ");
        bankAccNumberLabel.setBounds(110, 90, 180, 50);
        JTextField bankAccNumberField = new JTextField(10);
        bankAccNumberField.setBounds(270, 102, 150, 27);

        JLabel bankNameLabel = new JLabel("Bank Name : ");
        bankNameLabel.setBounds(110, 140, 150, 50);
        JTextField bankNameField = new JTextField(10);
        bankNameField.setBounds(270, 152, 150, 27);

        JLabel bankBranchCodeLabel = new JLabel("Bank Branch code : ");
        bankBranchCodeLabel.setBounds(110, 190, 150, 50);
        JTextField bankBranchCodeField = new JTextField(10);
        bankBranchCodeField.setBounds(270, 202, 150, 27);

        JLabel deliveryMethodLabel = new JLabel("Delivery Method : ");
        deliveryMethodLabel.setBounds(110, 240, 150, 50);
        String choiceList [] = {"Choose", "Collection", "Delivery", "Collection & Delivery"};
        JComboBox deliveryMethodField = new JComboBox<String>(choiceList);
        deliveryMethodField.setBounds(270, 252, 150, 27);

        JLabel shopDescriptionLabel = new JLabel("Shop Description: ");
        shopDescriptionLabel.setBounds(110, 290, 150, 50);
        JTextField shopDescriptionField = new JTextField(10);
        shopDescriptionField.setBounds(270, 302, 150, 27);

        JButton registerShopButton = new JButton("Confirm");
        registerShopButton.setBounds(185, 380, 150, 27);
        registerShopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String val = validate(bankAccNumberField.getText(), bankBranchCodeField.getText(), shopNameField.getText(), shopDescriptionField.getText(), bankNameField.getText());
                if(val.equals("yes")/*!shopNameField.getText().equals("") && !bankNameField.getText().equals("")
                        && !bankAccNumberField.getText().equals("") && !bankBranchCodeField.getText().equals("")
                        && !shopDescriptionField.getText().equals("")*/){
                    if(deliveryMethodField.getSelectedIndex() != 0){
                        store shop = new store(shopNameField.getText()+sellerObj.getContactNumber(),shopNameField.getText(), sellerObj.getResidentialAdr(),shopDescriptionField.getText(), null, bankAccNumberField.getText(),bankNameField.getText(),bankBranchCodeField.getText(),deliveryMethodField.getSelectedItem().toString());
                        addSeller(sellerObj, shop);
			            int sellerID = db.getUserCode(sellerObj.getEmail(),"seller");
			            db.CloseConnection();
                        JOptionPane.showMessageDialog(null, "Shop successfully registered!");
                        window.dispose();
                        previousWindowFrame.dispose();
                        if(adminWindow.getTitle().equals("Sell My Goods: Admin")){ }
                        else{
                            FoldersView foldersView = new FoldersView(sellerID);
                        }
                    }
                    else{
                        JOptionPane x = new JOptionPane();
                        x.showMessageDialog(null, "Choose a method of delivery", "Caution", JOptionPane.WARNING_MESSAGE);
                    }
                }
                else{
                    JOptionPane x = new JOptionPane();
                    x.showMessageDialog(null, /*"Fill in all fields"*/ val, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        window.add(shopNameLabel);
        window.add(shopNameField);
        window.add(bankNameLabel);
        window.add(bankNameField);
        window.add(bankAccNumberLabel);
        window.add(bankAccNumberField);
        window.add(bankBranchCodeLabel);
        window.add(bankBranchCodeField);
        window.add(deliveryMethodLabel);
        window.add(deliveryMethodField);
        window.add(shopDescriptionLabel);
        window.add(shopDescriptionField);
        window.add(registerShopButton);
        window.add(new JLabel());

        window.setVisible(true);
    }
    private void addSeller(seller sellerObj, store shop)
    {
        db.AddSellerAndShop(sellerObj.getName(), sellerObj.getEmail(), sellerObj.getContactNumber(), sellerObj.getPassword(), sellerObj.getSellerID(), sellerObj.getResidentialAdr(), shop.getStoreName(), shop.getAccountNumber(), shop.getBankName(), shop.getBranch(), shop.getDelivery(), shop.getStoreDescription());
    }

    private String validate(String bankAccNum, String bankBranchCode, String shopName, String description, String bankName){
        String vbankAccNum = validateBankAccountNum(bankAccNum);
        if(!vbankAccNum.equals("yes")){
            return vbankAccNum;
        }

        String vbankBranchCode = validateBankBranchCode(bankBranchCode);
        if(!vbankBranchCode.equals("yes")){
            return vbankBranchCode;
        }

	String vShopName = validateShopName(shopName);
	if (!vShopName.equals("yes")){
	    return vShopName;
	}

	if (description.length()<0)
	    return "Please enter a shop description of at least ten characters";

	if (bankName.equals(""))
	    return "Please enter a bank name";

        return("yes");
    }

    private String validateBankAccountNum(String bankAccNum){
        if(bankAccNum.length()<8 &&  bankAccNum.length()>11){
            return("Your bank account number is not the correct length");
        }
        else if (!bankAccNum.matches("[0-9]+")) {
            return "Invalid bank account number. Digits required.";
        }
        return("yes");
    }

    private String validateBankBranchCode(String bankBranchCode){
        if(bankBranchCode.length() != 6){
            return("Your BANK BRANCH CODE number is not the correct length");
        }
        else if(!bankBranchCode.matches("[0-9]+")){
            return("Invalid BANK BRANCH CODE number. Digits required.");
        }
        return("yes");
    }

    //Validates shopName. Returns either message or "yes". 
    private String validateShopName(String shopName){
        if(shopName.equals("")){
            return("Please enter a shop name");
        }

	//Check for uniqueness.
	if (db.shopExists(shopName)){
	    return("That shop's name already exists. Please enter a new name");
    	}	
        return("yes");
    }



}
