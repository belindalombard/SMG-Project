import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountDetailsView {
//    DatabaseAccess db = new DatabaseAccess();
    public AccountDetailsView(JFrame previousWindowFrame, int selectedAccount, DefaultListModel userAccounts){
        //Frame
        JFrame window = new JFrame(""+userAccounts.getElementAt(selectedAccount));
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

        backBoxLayout.add(backButton);

        window.add(showAccDetails());
        window.add(backBoxLayout, BorderLayout.NORTH);
        window.setVisible(true);
    }

    private Component showAccDetails(){
        JTextArea detailsArea = new JTextArea("Hello\nCool");
//        detailsArea.setEditable(false);
//        detailsArea.setEnabled(false);
        detailsArea.setWrapStyleWord(true);
        detailsArea.setLineWrap(true);
        detailsArea.setMargin(new Insets(40, 60, 20, 120));
        JScrollPane scrollPane = new JScrollPane(detailsArea);
        scrollPane.setBorder(new EmptyBorder(0,0,0,0));



        return scrollPane;
    }
}
