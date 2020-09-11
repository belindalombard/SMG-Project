import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ProductFolderView {

    JTextField productName;
    Checkbox show, hide;

    public ProductFolderView(JFrame previousWindowFrame, int selectedFolder, DefaultListModel folders, DefaultListModel hiddenFolders){
        //Frame
        JFrame window = new JFrame("Sell My Goods: "+folders.getElementAt(selectedFolder));
        window.setMinimumSize(new Dimension(800, 500));
        window.setLocation(300, 200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        //

        JPanel topButtonLayout = new JPanel();
        topButtonLayout.setLayout(new BoxLayout(topButtonLayout, BoxLayout.LINE_AXIS));

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousWindowFrame.setVisible(true);
                window.setVisible(false);
            }
        });

        JButton saveProductButton = new JButton("Save");
        saveProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                folders.setElementAt(productName.getText(), selectedFolder);
                if(hide.getState()){
                    hiddenFolders.addElement(folders.getElementAt(selectedFolder));
                    System.out.println(hiddenFolders.size());
                    /*hiddenFolders is going to be used in
                    the database in order to know what to hide from the user.*/

//                    folders.removeElementAt(selectedFolder);
                }

                previousWindowFrame.setVisible(true);
                window.setVisible(false);
            }
        });

        show = new Checkbox("Show", true);
        show.setBounds(340, 50, 100, 20);
        show.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(hide.getState()){
                    hide.setState(false);
                }
                else if(show.getState() == false && hide.getState() == false){
                    show.setState(true);
                }
            }
        });

        hide = new Checkbox("Hide");
        hide.setBounds(440, 50, 100, 20);
        hide.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(show.getState()){
                    show.setState(false);
                }
                else if(show.getState() == false && hide.getState() == false){
                    hide.setState(true);
                }
            }
        });

        JLabel productImage = new JLabel(new ImageIcon("/Users/lindazungu/Desktop/Sell My Goods/src/sampleImage.png"));
        productImage.setBounds(80, 100, 250, 250);

        JLabel productNameLabel = new JLabel("Product Name : ");
        productNameLabel.setBounds(400, 100, 150, 50);

        productName = new JTextField();
        productName.setText((String) folders.getElementAt(selectedFolder));
        productName.setFont(new Font(null, Font.BOLD, 20));
        productName.setBounds(510, 100, 260, 50);
        productName.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    folders.setElementAt(productName.getText(), selectedFolder);
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });

        JLabel productPrice = new JLabel();
        productPrice.setText("Product Price : R");
        productPrice.setBounds(400, 150, 150, 50);

        JTextField productPriceField = new JTextField(10);
        productPriceField.setBounds(510, 163, 150, 27);

        JLabel productDetailsLabel = new JLabel("Product Details : ");
        productDetailsLabel.setBounds(400, 200, 150, 20);

        JTextArea productDetailsTextArea = new JTextArea();
        productDetailsTextArea.setBounds(515, 200, 250, 85);
        productDetailsTextArea.setLineWrap(true);

        JLabel stockAvailableLabel = new JLabel("Stock Available : ");
        stockAvailableLabel.setBounds(400, 300, 150, 24);

        JTextField stockAvailableField = new JTextField(10);
        stockAvailableField.setBounds(510, 300, 150, 27);

        topButtonLayout.add(backButton);
        topButtonLayout.add(Box.createHorizontalGlue());
        topButtonLayout.add(saveProductButton);

        window.add(show);
        window.add(hide);
        window.add(productImage);
        window.add(productNameLabel);
        window.add(productName);
        window.add(productPrice);
        window.add(productPriceField);
        window.add(productDetailsLabel);
        window.add(productDetailsTextArea);
        window.add(stockAvailableLabel);
        window.add(stockAvailableField);
        window.add(new JLabel());

        window.add(topButtonLayout, BorderLayout.NORTH);
        window.setVisible(true);
    }
}
