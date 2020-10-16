import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.*;
import java.io.File;
import java.io.FileInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream ;
import javax.imageio.ImageIO;

public class ProductFolderView {

    JTextField productName;
    Checkbox show, hide;
    JLabel showUploadInstruction, productImage, addInstruction;
    String photo_path = "";
    int selectedFolder;
    ArrayList productsList;

    public ProductFolderView(JFrame previousWindowFrame, int selectedFolder, DefaultListModel folders, DefaultListModel hiddenFolders, ArrayList productsList, boolean isDarkMode){
        //Frame
	this.selectedFolder=selectedFolder;
	this.productsList=productsList;
        JFrame window = new JFrame("Sell My Goods: "+folders.getElementAt(selectedFolder));
        window.setMinimumSize(new Dimension(800, 500));
        window.setLocation(300, 200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.getContentPane().setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        //

	    product current_product = ((product)productsList.get(selectedFolder));

        JPanel topButtonLayout = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0,
                        isDarkMode ? getBackground().darker().darker().gray : getBackground().darker(), 0, getHeight(),
                        isDarkMode ? getBackground().darker().darker().darkGray : getBackground().brighter().brighter().brighter().brighter());
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        topButtonLayout.setLayout(new BoxLayout(topButtonLayout, BoxLayout.LINE_AXIS));

        showUploadInstruction = new JLabel("^ Upload Image ^");
        showUploadInstruction.setBounds(150, 350, 250, 27);
        showUploadInstruction.setForeground(isDarkMode ? Color.white : Color.BLACK);
        showUploadInstruction.setVisible(false);

        addInstruction = new JLabel("Add Image");
        addInstruction.setBounds(175, 200, 100,27);
        addInstruction.setForeground(new Color(0x9A9B9C));
        addInstruction.setVisible(false);

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
	    show.setForeground(isDarkMode ? Color.white : Color.BLACK);
        
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
        hide.setForeground(isDarkMode ? Color.white : Color.BLACK);
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

		//Code to add photo to the database.
                JFileChooser fileChooser = new JFileChooser("C:\\", FileSystemView.getFileSystemView());
            	fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "tif", "gif", "bmp", "jpeg"));
                System.out.println("Button clicked!");
                int val = fileChooser.showOpenDialog(topButtonLayout);
                if (val==JFileChooser.APPROVE_OPTION) {
                    String fileName = fileChooser.getSelectedFile().getName();
                    //System.out.println(fileName);
                    String extension = fileName.substring(fileName.lastIndexOf("."));
                    if (extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".png")
                            || extension.equalsIgnoreCase(".bmp") || extension.equalsIgnoreCase(".tif")
                            || extension.equalsIgnoreCase(".gif") || extension.equalsIgnoreCase(".jpeg")) {
                        photo_path=fileChooser.getSelectedFile().getPath();
                        byte[] arr = image_to_bytea(photo_path);
                        BufferedImage buff = bytea_to_image(arr);
                        productImage.setIcon(new ImageIcon(buff));
                        current_product.setPhoto(arr);
                    }
                }
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
                productImage.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
                showUploadInstruction.setVisible(true);
                addInstruction.setVisible(false);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                productImage.setBorder(new EtchedBorder(EtchedBorder.RAISED));
                showUploadInstruction.setVisible(false);
                addInstruction.setVisible(true);
            }
        });
        if ((current_product.getProductImage()!=null)){
            productImage = new JLabel(new ImageIcon(bytea_to_image(current_product.getProductImage())));
            addInstruction.setVisible(false);
        }

        else
            productImage = new JLabel();
            productImage.setBounds(80, 100, 250, 250);
            productImage.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
            addInstruction.setVisible(true);

        JLabel productNameLabel = new JLabel("Product Name : ");
        productNameLabel.setBounds(400, 100, 150, 50);
        productNameLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);

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
        productPrice.setForeground(isDarkMode ? Color.white : Color.BLACK);

        JTextField productPriceField = new JTextField(10);
        productPriceField.setBounds(525, 163, 150, 27);
        productPriceField.setText(""+((product)productsList.get(selectedFolder)).getProductPrice());

        JLabel productDetailsLabel = new JLabel("Product Details : ");
        productDetailsLabel.setBounds(400, 200, 150, 20);
        productDetailsLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);

        JTextArea productDetailsTextArea = new JTextArea();
        productDetailsTextArea.setBounds(525, 200, 250, 85);
        productDetailsTextArea.setLineWrap(true);
        productDetailsTextArea.setWrapStyleWord(true);
        productDetailsTextArea.setText(((product)productsList.get(selectedFolder)).getProductDescription());

        JLabel stockAvailableLabel = new JLabel("Stock Available : ");
        stockAvailableLabel.setBounds(400, 300, 150, 24);
        stockAvailableLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);

        JTextField stockAvailableField = new JTextField(10);
        stockAvailableField.setBounds(525, 300, 150, 27);
        stockAvailableField.setText(""+((product)productsList.get(selectedFolder)).getProductQty());

        JButton saveProductButton = new JButton("Save");
        saveProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
		String val = validate_shop_info(productName.getText(), stockAvailableField.getText(), productPriceField.getText());
		if (val.equals("done")){
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
		else {
   		    JOptionPane x = new JOptionPane();
                    x.showMessageDialog(null, val, "Warning", JOptionPane.WARNING_MESSAGE);
                    x.setFocusable(true);
		}

	    }
        });

        topButtonLayout.add(backButton);
        topButtonLayout.add(Box.createHorizontalGlue());
        topButtonLayout.add(saveProductButton);

        window.add(show);
        window.add(hide);
        window.add(productImage);
        window.add(uploadImageButton);
        window.add(showUploadInstruction);
        window.add(addInstruction);
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


   public byte[] image_to_bytea(String file_path){
        try {
                File file = new File(file_path);
                FileInputStream fs = new FileInputStream(file);
                long bytes = file.length();
                byte[] bytea = new byte[(int)bytes];

                fs.read(bytea, 0, (int) bytes);
                return bytea;
        }
        catch (Exception e){
                e.printStackTrace();
        }
        return null;

    }

    public BufferedImage bytea_to_image(byte[] bytes){
	try {
        	ByteArrayInputStream b = new ByteArrayInputStream(bytes);
		BufferedImage image = ImageIO.read(b);
		return image;
    	} catch (Exception e){
		e.printStackTrace();
	}
    return null;
    }	

    /**
     * Validates shop informtion. Returns message indicate what error is. 
     */    
    public String validate_shop_info(String shop_name, String qty, String price) {	
	if (!validate_product_name(shop_name)) {
		return "Please enter a valid shop name" ;
	}
	if (!validate_product_qty(qty)){
		return "Please enter a valid integer for the quantity";
	}
	if (!validate_product_price(price)){
		return "Please enter a valid product price";
	}
	DatabaseAccess db = new DatabaseAccess();
	if (!(((product)productsList.get(selectedFolder)).getProductName().equals(shop_name))&&(db.productExists(shop_name)))
		return "Please enter a unique shop name";
       db.CloseConnection();	
	return "done";
    }
    
    /**
     * Validates product name. Returns boolean. 
     */    
    public boolean validate_product_name(String product_name) {
	if (product_name.replace(" ","").equals("")) 
		return false;

	return true;
    }
    
    /**
     * Validates product qty. Returns boolean. 
     */    

    public boolean validate_product_qty(String qty){
	if (!qty.matches("\\d+"))
		return false;
	return true;
	
    }

    /**
     * Validates product price. Returns boolean. 
     */     
    public boolean validate_product_price(String price){
	try
	{
  		Double.parseDouble(price);
		return true;
	}
	catch(NumberFormatException e)
	{
		return false;
	}
    }
}	
