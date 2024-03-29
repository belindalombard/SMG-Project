import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class  ChatRoomView {

    JTextArea messageBoard;
    JScrollPane scrollPane;

    public ChatRoomView(JFrame previousWindowFrame, JButton back, JButton pay, JButton contactSeller,seller seller, buyer buyer, product product, boolean isDarkMode){
        DatabaseAccess db = new DatabaseAccess();
        //Frame
        JFrame window = new JFrame("Sell My Goods: ChatRoom");
        window.setMinimumSize(new Dimension(300, 500));
        window.setLocation(500, 150);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {
                previousWindowFrame.setFocusable(true);
                back.setEnabled(true);
                pay.setEnabled(true);
                contactSeller.setEnabled(true);
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
        window.setResizable(false);
        //

        JPanel topLabelLayout = new JPanel(new FlowLayout());
        JLabel userInstruction = new JLabel("Type your message below");
        topLabelLayout.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        userInstruction.setForeground(Color.GRAY);

        JPanel bottomButtonLayout = new JPanel(new FlowLayout());
        bottomButtonLayout.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        JButton sendMessageButton = new JButton("Send");
        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!messageBoard.getText().isEmpty()){
                    back.setEnabled(true);
                    pay.setEnabled(true);
                    contactSeller.setEnabled(true);
                    previousWindowFrame.setFocusable(true);
                    //message messageSent = new message();
		    System.out.println(buyer.getEmail());
                    db.sendMessage(seller.getEmail(),buyer.getEmail(),messageBoard.getText(),"buyer");
                    JOptionPane.showMessageDialog(null, "Message succesfully sent!");
                    window.setVisible(false);
                }
            }
        });

        messageBoard = new JTextArea();
        messageBoard.setLineWrap(true);
        messageBoard.setMargin(new Insets(10, 10, 10, 10));
        messageBoard.setWrapStyleWord(true);
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(messageBoard);
        scrollPane.setBorder(new EmptyBorder(0,0,0,0));
        messageBoard.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());


        topLabelLayout.add(userInstruction);
        bottomButtonLayout.add(sendMessageButton);


        window.add(scrollPane, BorderLayout.CENTER);
        window.add(topLabelLayout, BorderLayout.NORTH);
        window.add(bottomButtonLayout, BorderLayout.SOUTH);
        window.setVisible(true);

    }

}
