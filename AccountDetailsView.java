import javafx.scene.text.Text;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountDetailsView {

    JFrame window;
//    DatabaseAccess db = new DatabaseAccess();
    public AccountDetailsView(JFrame previousWindowFrame, int selectedAccount, DefaultListModel userAccounts){
        //Frame
        window = new JFrame(""+userAccounts.getElementAt(selectedAccount));
        window.setMinimumSize(new Dimension(600, 500));
        window.setLocation(450, 250);
        window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        window.setResizable(false);
        //

        JPanel backBoxLayout = new JPanel();
        backBoxLayout.setLayout(new BoxLayout(backBoxLayout, BoxLayout.LINE_AXIS));

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousWindowFrame.setVisible(true);
                window.setVisible(false);
            }
        });

//      if(buyer){
//          showBuyerDetails();
//      }
//      else{
//          showSellerDetails()
//      }
        showSellerDetails(); //Remove after working with the above

        backBoxLayout.add(backButton);

        window.add(backBoxLayout, BorderLayout.NORTH);
        window.setVisible(true);
    }

    private void showBuyerDetails(){
        JLabel nameLabel = new JLabel("Name : ");
        nameLabel.setBounds(150, 60, 50, 27);
        JLabel userName = new JLabel("Linda");
        userName.setBounds(320, 60, 150, 27);

        JLabel idLabel = new JLabel("ID Number : ");
        idLabel.setBounds(150, 90, 150, 27);
        JLabel userID = new JLabel("1234567890123");
        userID.setBounds(320, 90, 150, 27);

        JLabel locationLabel = new JLabel("Location : ");
        locationLabel.setBounds(150, 120, 150, 27);
        JLabel userLocation = new JLabel("Johannesburg");
        userLocation.setBounds(320, 120, 150, 27);

        JLabel phoneNumberLabel = new JLabel("Cellphone Number : ");
        phoneNumberLabel.setBounds(150, 150, 150, 27);
        JLabel userPhoneNumber = new JLabel("1234567890");
        userPhoneNumber.setBounds(320, 150, 150, 27);

        JLabel emailAddressLabel = new JLabel("Email Address : ");
        emailAddressLabel.setBounds(150, 180, 150, 27);
        JLabel userEmailAddress = new JLabel("a@a.com");
        userEmailAddress.setBounds(320, 180, 150, 27);

        JLabel passwordLabel = new JLabel("Password : ");
        passwordLabel.setBounds(150, 210, 150, 27);
        JLabel userPassword = new JLabel("123456");
        userPassword.setBounds(320, 210, 150, 27);

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
        window.add(new JLabel());
    }

    private void showShopDetails(){
        JLabel shopNameLabel = new JLabel("Shop Name : ");
        shopNameLabel.setBounds(150, 240, 150, 50);
        JLabel actualShopName = new JLabel("The Name");
        actualShopName.setBounds(320, 240, 150, 50);

        JLabel bankAccNumberLabel = new JLabel("Bank Account Number : ");
        bankAccNumberLabel.setBounds(150, 270, 180, 50);
        JLabel actualBankAccNum = new JLabel("12345678901");
        actualBankAccNum.setBounds(320, 270, 150, 50);

        JLabel bankNameLabel = new JLabel("Bank Name : ");
        bankNameLabel.setBounds(150, 300, 150, 50);
        JLabel actualBankName = new JLabel("Standard Bank");
        actualBankName.setBounds(320, 300, 150, 50);

        JLabel bankBranchCodeLabel = new JLabel("Bank Branch code : ");
        bankBranchCodeLabel.setBounds(150, 330, 150, 50);
        JLabel actualBankBranchCode = new JLabel("123123");
        actualBankBranchCode.setBounds(320, 330, 150, 50);

        JLabel deliveryMethodLabel = new JLabel("Delivery Method : ");
        deliveryMethodLabel.setBounds(150, 360, 150, 50);
        JLabel actualDeliveryMethod = new JLabel("Delivery & Collection");
        actualDeliveryMethod.setBounds(320, 360, 150, 50);


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

    private void showSellerDetails(){
        showBuyerDetails();
        showShopDetails();
    }
}
