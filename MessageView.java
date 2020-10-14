import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MessageView {
    JButton backButton;
    JLabel messageText;
    public MessageView(JFrame previousWindowFrame,int selectedMessage, DefaultListModel messages)
    {
        JFrame window = new JFrame("Sell My Goods: Inbox");
        window.setMinimumSize(new Dimension(800, 500));
        window.setLocation(300, 200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        messageText.setText(((message)messages.getElementAt(selectedMessage)).getMessage());
        messageText.setBounds(400, 300, 200, 200);
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousWindowFrame.setVisible(true);
                window.setVisible(false);
            }
        });
        JPanel topButtonLayout = new JPanel();
        topButtonLayout.setLayout(new BoxLayout(topButtonLayout, BoxLayout.LINE_AXIS));

        topButtonLayout.add(backButton);

        window.add(topButtonLayout, BorderLayout.NORTH);
        window.add(messageText);
        window.setVisible(true);
    }
}