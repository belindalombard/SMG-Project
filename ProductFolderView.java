import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ProductFolderView {

    JTextField productName;
    Checkbox show, hide;

    public ProductFolderView(JFrame previousWindowFrame, int selectedFolder, DefaultListModel folders, DefaultListModel hiddenFolders, ArrayList productsList){
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

	    boolean product_hide = ((product)productsList.get(selectedFolder)).getHide();
        show = new Checkbox("Show",!product_hide);
        
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

        hide = new Checkbox("Hide", product_hide);
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

        JButton uploadImageButton = new JButton();
        uploadImageButton.setBounds(80, 100, 250, 250);
        uploadImageButton.setOpaque(false);
        uploadImageButton.setContentAreaFilled(false);
        uploadImageButton.setBorderPainted(false);
        uploadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button clicked!");
            }
        });
        uploadImageButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) {
                uploadImageButton.setBorderPainted(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                uploadImageButton.setBorderPainted(false);
            }
        });
        JLabel productImage = new JLabel(new ImageIcon("/Users/lindazungu/Desktop/Sell My Goods/src/sampleImage.png"));
        productImage.setBounds(80, 100, 250, 250);

        JLabel productNameLabel = new JLabel("Product Name : ");
        productNameLabel.setBounds(400, 100, 150, 50);

        productName = new JTextField();
        productName.setText((String) folders.getElementAt(selectedFolder));
        productName.setFont(new Font(null, Font.BOLD, 20));
        productName.setBounds(525, 100, 260, 50);
        productName.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    folders.setElementAt(productName.getText(), selectedFolder);
                    ((product)productsList.get(selectedFolder)).setProductName(productName.getText());
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });

        JLabel productPrice = new JLabel();
        productPrice.setText("Product Price : R");
        productPrice.setBounds(400, 150, 150, 50);

        JTextField productPriceField = new JTextField(10);
        productPriceField.setBounds(525, 163, 150, 27);
        productPriceField.setText(""+((product)productsList.get(selectedFolder)).getProductPrice());

        JLabel productDetailsLabel = new JLabel("Product Details : ");
        productDetailsLabel.setBounds(400, 200, 150, 20);

        JTextArea productDetailsTextArea = new JTextArea();
        productDetailsTextArea.setBounds(525, 200, 250, 85);
        productDetailsTextArea.setLineWrap(true);
        productDetailsTextArea.setWrapStyleWord(true);
        productDetailsTextArea.setText(((product)productsList.get(selectedFolder)).getProductDescription());

        JLabel stockAvailableLabel = new JLabel("Stock Available : ");
        stockAvailableLabel.setBounds(400, 300, 150, 24);

        JTextField stockAvailableField = new JTextField(10);
        stockAvailableField.setBounds(525, 300, 150, 27);
        stockAvailableField.setText(""+((product)productsList.get(selectedFolder)).getProductQty());

        JButton saveProductButton = new JButton("Save");
        saveProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String productName, String productDescription, int productQty, double productPrice, boolean visible
                folders.setElementAt(productName.getText(), selectedFolder);
                ((product)productsList.get(selectedFolder)).setProductName(productName.getText());
                ((product)productsList.get(selectedFolder)).setProductQty(Integer.parseInt(stockAvailableField.getText()));
                ((product)productsList.get(selectedFolder)).setProductPrice(Double.parseDouble(productPriceField.getText()));
                ((product)productsList.get(selectedFolder)).setProductDescription(productDetailsTextArea.getText());
                ((product)productsList.get(selectedFolder)).setHide(hide.getState());
		
		        if(hide.getState()){
		            hiddenFolders.addElement(folders.getElementAt(selectedFolder));
		            System.out.println(hiddenFolders.size());
		            /*hiddenFolders is going to be used in
                    the database in order to know what to hide from the user.*/

//                  folders.removeElementAt(selectedFolder);
		        }

		        previousWindowFrame.setVisible(true);
		        window.setVisible(false);
            }
        });

        topButtonLayout.add(backButton);
        topButtonLayout.add(Box.createHorizontalGlue());
        topButtonLayout.add(saveProductButton);

        window.add(show);
        window.add(hide);
        window.add(productImage);
        window.add(uploadImageButton);
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
