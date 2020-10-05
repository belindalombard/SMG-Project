import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class FoldersView {

    JButton addFolderButton, removeFolderButton;
    DefaultListModel hiddenFolders;

    public FoldersView(int sellercode){
        //Frame
        JFrame window = new JFrame("Sell My Goods: Folders");
        window.setMinimumSize(new Dimension(800, 500));
        window.setLocation(300, 200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        //

        hiddenFolders = new DefaultListModel();
        DefaultListModel folders = new DefaultListModel();
        ArrayList<product> productsList = new ArrayList<>();//to store all the products of the shop
        JList foldersList = new JList(folders);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(foldersList);
        foldersList.setLayoutOrientation(JList.VERTICAL);
        foldersList.setFixedCellHeight(60);

        foldersList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(folders.size() != 0){
                    ProductFolderView productFolderView = new ProductFolderView(window, foldersList.getSelectedIndex(), folders, hiddenFolders, productsList);
                    window.setVisible(false);
                }
            }
            @Override
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) { }
            @Override
            public void mouseEntered(MouseEvent e) { }
            @Override
            public void mouseExited(MouseEvent e) { }
        });

        JPanel topLayout = new JPanel();
        topLayout.setLayout(new BoxLayout(topLayout, BoxLayout.LINE_AXIS));

        addFolderButton = new JButton("+");
        addFolderButton.setBounds(710, 0, 50, 27);
        addFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disable();
                AddFolderView addFolderView = new AddFolderView(addFolderButton, removeFolderButton, folders, productsList);
            }
        });
        removeFolderButton = new JButton("-");
        removeFolderButton.setBounds(750, 0, 50, 27);
        removeFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(foldersList.isSelectedIndex(foldersList.getSelectedIndex())){
                    productsList.remove(foldersList.getSelectedIndex());
                    folders.remove(foldersList.getSelectedIndex());
                }
            }
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
		AddProductsToDatabase(sellercode, productsList);  
                LoginView loginView = new LoginView();
                window.dispose();
            }
        });

        topLayout.add(logoutButton);

        window.add(addFolderButton);
        window.add(removeFolderButton);
        window.add(new JLabel());
        window.add(topLayout, BorderLayout.NORTH);
        window.add(scrollPane);

        window.setVisible(true);
    }

    public void disable(){
        addFolderButton.setEnabled(false);
        removeFolderButton.setEnabled(false);
    }
//Add products to the database that is contained in the productList arraylist
    public boolean AddProductsToDatabase(int sellerCode, ArrayList<product> productsList){
	    return false;


    }
}
