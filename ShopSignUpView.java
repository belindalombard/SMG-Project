import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ShopSignUpView {

    public ShopSignUpView(JFrame previousWindowFrame, JButton backButton, JButton createAccountButton, Checkbox buyer, Checkbox seller, seller sellerObj){
        //Frame
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
                store shop = new store(shopNameField.getText()+sellerObj.getContactNumber(),shopNameField.getText(), sellerObj.getResidentialAdr(),shopDescriptionField.getText(), null, bankAccNumberField.getText(),bankNameField.getText(),bankBranchCodeField.getText(),deliveryMethodField.getSelectedItem().toString());
                addSeller(sellerObj, shop);
                JOptionPane.showMessageDialog(null, "Shop successfully registered!");
                window.dispose();
                previousWindowFrame.dispose();
                FoldersView foldersView = new FoldersView();
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
	//Set connection to database
	DatabaseAccess db = new DatabaseAccess();
	db.AddSellerAndShop(sellerObj.getName(), sellerObj.getEmail(), sellerObj.getContactNumber(), sellerObj.getPassword(), sellerObj.getSellerID(), sellerObj.getResidentialAdr(), shop.getStoreName(), shop.getAccountNumber(), shop.getBankName(), shop.getBranch(), shop.getDelivery(), shop.getStoreDescription());
	db.CloseConnection();



    }
}
