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
        window.setMinimumSize(new Dimension(500, 350));
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
        shopNameLabel.setBounds(120, 60, 150, 50);
        JTextField shopNameField = new JTextField(10);
        shopNameField.setBounds(250, 72, 150, 27);

        JLabel bankAccNumberLabel = new JLabel("Bank Account Number : ");
        bankAccNumberLabel.setBounds(120, 110, 150, 50);
        JTextField bankAccNumberField = new JTextField(10);
        bankAccNumberField.setBounds(250, 122, 150, 27);

        JLabel bankNameLabel = new JLabel("Bank Name : ");
        bankNameLabel.setBounds(120, 160, 150, 50);
        JTextField bankNameField = new JTextField(10);
        bankNameField.setBounds(250, 172, 150, 27);

        JLabel bankBranchCodeLabel = new JLabel("Bank Branch code : ");
        bankBranchCodeLabel.setBounds(120, 210, 150, 50);
        JTextField bankBranchCodeField = new JTextField(10);
        bankBranchCodeField.setBounds(250, 222, 150, 27);

        JLabel deliveryMethodLabel = new JLabel("Delivery Method : ");
        deliveryMethodLabel.setBounds(120, 260, 150, 50);
        String choiceList [] = {"Choose", "Collection", "Delivery", "Both"};
        JComboBox deliveryMethodField = new JComboBox<String>(choiceList);
        deliveryMethodField.setBounds(250, 272, 150, 27);

        JLabel shopDescriptionLabel = new JLabel("Shop Description: ");
        shopDescriptionLabel.setBounds(120, 310, 150, 50);
        JTextField shopDescriptionField = new JTextField(10);
        shopDescriptionField.setBounds(250, 322, 150, 27);

        JButton registerShopButton = new JButton("Confirm");
        registerShopButton.setBounds(185, 400, 150, 27);
        registerShopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                store shop = new store(shopNameField.getText()+sellerObj.getContactNumber(),shopNameField.getText(), sellerObj.getResidentialAdr(),shopDescriptionField.getText(), null, bankAccNumberField.getText(),bankNameField.getText(),bankBranchCodeField.getText());
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
        //database method to add both the shop and the seller to the database :Belinda
    }
}
