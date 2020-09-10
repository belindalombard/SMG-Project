import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FoldersView {

    JButton addFolderButton, removeFolderButton;

    public FoldersView(){
        //Frame
        JFrame window = new JFrame("Sell My Goods: Folders");
        window.setMinimumSize(new Dimension(800, 500));
        window.setLocation(300, 200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        //

        DefaultListModel folders = new DefaultListModel();
        JList foldersList = new JList(folders);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(foldersList);
        foldersList.setLayoutOrientation(JList.VERTICAL);
        foldersList.setFixedCellHeight(60);

        JPanel topLayout = new JPanel();
        topLayout.setLayout(new BoxLayout(topLayout, BoxLayout.LINE_AXIS));

        addFolderButton = new JButton("+");
        addFolderButton.setBounds(710, 0, 50, 27);
        addFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disable();
                AddFolderView addFolderView = new AddFolderView(addFolderButton, removeFolderButton, folders);
            }
        });
        removeFolderButton = new JButton("-");
        removeFolderButton.setBounds(750, 0, 50, 27);
        removeFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(foldersList.isSelectedIndex(foldersList.getSelectedIndex())){
                    folders.remove(foldersList.getSelectedIndex());
                }
            }
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
}
