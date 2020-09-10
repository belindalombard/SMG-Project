import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ShopSignUpView {

    public ShopSignUpView(JFrame previousWindowFrame, JButton backButton, JButton createAccountButton, Checkbox buyer, Checkbox seller){
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

        JLabel bankDetailsLabel = new JLabel("Bank Account : ");
        bankDetailsLabel.setBounds(120, 110, 150, 50);
        JTextField bankDetailsField = new JTextField(10);
        bankDetailsField.setBounds(250, 122, 150, 27);

        JLabel deliveryMethodLabel = new JLabel("Delivery Method : ");
        deliveryMethodLabel.setBounds(120, 160, 150, 50);
        JTextField deliveryMethodField = new JTextField(10);
        deliveryMethodField.setBounds(250, 172, 150, 27);

        JButton registerShopButton = new JButton("Confirm");
        registerShopButton.setBounds(185, 250, 150, 27);
        registerShopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Shop successfully registered!");
                window.dispose();
                previousWindowFrame.dispose();
                FoldersView foldersView = new FoldersView();
            }
        });

        window.add(shopNameLabel);
        window.add(shopNameField);
        window.add(bankDetailsLabel);
        window.add(bankDetailsField);
        window.add(deliveryMethodLabel);
        window.add(deliveryMethodField);
        window.add(registerShopButton);
        window.add(new JLabel());

        window.setVisible(true);
    }
}
