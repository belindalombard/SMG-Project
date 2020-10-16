import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;

public class AddFolderView {
    public AddFolderView(JButton addFolderButton, JButton removeFolderButton, DefaultListModel folders, ArrayList productsList, boolean isDarkMode){
        //Frame
        JFrame window = new JFrame("Sell My Goods: Add Item");
        window.setMinimumSize(new Dimension(300, 120));
        window.setLocation(550, 300);
        window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        window.setResizable(false);
        window.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {
                addFolderButton.setEnabled(true);
                removeFolderButton.setEnabled(true);
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
        window.getContentPane().setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        //

        JPanel bottomButtonLayout = new JPanel(new FlowLayout());
        bottomButtonLayout.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());

        JPanel topButtonLayout = new JPanel(new FlowLayout());
        topButtonLayout.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        JLabel folderNameLabel = new JLabel("Item Name");
        folderNameLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);

        JTextField folderNameField = new JTextField();
        JButton createFolder = new JButton("Create Item");
        createFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialog = JOptionPane.showConfirmDialog(null, "Are you sure you want to add this item: "+folderNameField.getText(),"Confirmation", JOptionPane.YES_NO_OPTION);
                if(dialog == JOptionPane.YES_OPTION){
                    addFolderButton.setEnabled(true);
                    removeFolderButton.setEnabled(true);
                    //String productName, String productDescription, int productQty, double productPrice, boolean visible
                    product newProduct = new product(folderNameField.getText(),"",0,0,false);
                    folders.addElement(newProduct.getProductName());
                    productsList.add(newProduct);

                    window.setVisible(false);
                }
            }
        });

        topButtonLayout.add(folderNameLabel);
        bottomButtonLayout.add(createFolder);

        window.add(folderNameField);
        window.add(topButtonLayout, BorderLayout.NORTH);
        window.add(bottomButtonLayout, BorderLayout.SOUTH);

        window.setVisible(true);
    }
}
