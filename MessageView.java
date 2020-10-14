import javafx.geometry.Orientation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MessageView {
    JButton backButton;
    JTextArea messageText = new JTextArea();
    JPanel panel;
    JScrollPane scrollPane;
    public MessageView(JFrame previousWindowFrame, ArrayList<message> listOfMessages,int selectedMessage, DefaultListModel messages, JButton previousBackButton, JList messagesList)
    {
        JFrame window = new JFrame("Sell My Goods: Inbox");
        window.setMinimumSize(new Dimension(350, 500));
        window.setLocation(600, 200);
        window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        window.setResizable(false);
        window.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {
                previousWindowFrame.setFocusable(true);
                previousBackButton.setEnabled(true);
                messagesList.setEnabled(true);
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

        previousBackButton.setEnabled(false);
        messagesList.setEnabled(false);
        previousWindowFrame.setFocusable(false);

        JPanel topButtonLayout = new JPanel();
        topButtonLayout.setLayout(new BoxLayout(topButtonLayout, BoxLayout.LINE_AXIS));

        JPanel bottomButtonLayout = new JPanel();
        bottomButtonLayout.setLayout(new BoxLayout(bottomButtonLayout, BoxLayout.LINE_AXIS));

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField sellerMessageField = new JTextField(10);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
                JTextArea area = new JTextArea();
                area.setLineWrap(true);
                area.setWrapStyleWord(true);
                area.setText("Seller: "+ sellerMessageField.getText());
                area.setEditable(false);
                area.setMargin(new Insets(10, 10, 10, 10));
                area.setBackground(window.getBackground());

                JTextArea sentTimestamp = new JTextArea("Timestamp here for seller");
                sentTimestamp.setLineWrap(true);
                sentTimestamp.setWrapStyleWord(true);
                sentTimestamp.setEditable(false);
                sentTimestamp.setMargin(new Insets(10, 10, 10, 10));
                sentTimestamp.setBackground(window.getBackground());
                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
                panel.add(sentTimestamp);
                panel.add(area);
                panel.revalidate();
                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());

            }
        });
        // message messages.getElementAt(selectedMessage)).toString()

        message thisMessage = listOfMessages.get(selectedMessage);

        JTextArea timeReceivedLabel = new JTextArea(""+thisMessage.getTimeStamp().toString().substring(0, 10)+"\n"+thisMessage.getTimeStamp().toString().substring(11, 16));
        timeReceivedLabel.setMargin(new Insets(10, 10, 10, 10));
        timeReceivedLabel.setBackground(window.getBackground());
        timeReceivedLabel.setEditable(false);
//        timeReceivedLabel.setBounds(70, 270, 200, 200);

        messageText.setText("Buyer Name: "+thisMessage.getMessage());
        messageText.setEditable(false);
        messageText.setLineWrap(true);
        messageText.setWrapStyleWord(true);
        messageText.setMargin(new Insets(10, 10, 10, 10));
        messageText.setBackground(window.getBackground());
//        messageText.setBounds(5, 300, 200, 200);

        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messagesList.setEnabled(true);
                previousBackButton.setEnabled(true);
                previousWindowFrame.setFocusable(false);
                window.setVisible(false);
            }
        });

        panel.add(timeReceivedLabel);
        panel.add(messageText);

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(panel);
        scrollPane.setBorder(new EmptyBorder(0,0,0,0));
        scrollPane.createVerticalScrollBar();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        topButtonLayout.add(backButton);

        bottomButtonLayout.add(sellerMessageField);
        bottomButtonLayout.add(sendButton);

        window.add(topButtonLayout, BorderLayout.NORTH);
        window.add(scrollPane, BorderLayout.CENTER);
        window.add(bottomButtonLayout, BorderLayout.SOUTH);
//        window.add(scrollPane);
//        window.add(timeReceivedLabel);
//        window.add(new JLabel());
        window.setVisible(true);
        window.setFocusable(true);
    }
}
