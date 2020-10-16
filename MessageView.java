//import javafx.geometry.Orientation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MessageView {
    JButton backButton;
    JTextArea messageText = new JTextArea();
    JPanel panel;
    JScrollPane scrollPane;
    DatabaseAccess db;
    public MessageView(JFrame previousWindowFrame, ArrayList<message> listOfMessages,int selectedMessage, DefaultListModel messages, JButton previousBackButton, JList messagesList, seller seller, boolean isDarkMode)
    {
        db = new DatabaseAccess();
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
        window.getContentPane().setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());

        previousBackButton.setEnabled(false);
        messagesList.setEnabled(false);
        previousWindowFrame.setFocusable(false);

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

        JPanel bottomButtonLayout = new JPanel(){
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
        bottomButtonLayout.setLayout(new BoxLayout(bottomButtonLayout, BoxLayout.LINE_AXIS));

        panel = new JPanel(){
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
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        message thisMessage = listOfMessages.get(selectedMessage);
        JTextField sellerMessageField = new JTextField(10);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
                JTextArea area = new JTextArea();
                area.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
                area.setLineWrap(true);
                area.setWrapStyleWord(true);
                area.setText("\n"+(new Time(System.currentTimeMillis()))+"\n"+thisMessage.getMessage()+"\n"+seller.getName()+ ": "+ sellerMessageField.getText());
                area.setEditable(false);
                area.setMargin(new Insets(10, 10, 10, 10));
//                area.setBackground(window.getBackground());
                area.setForeground(isDarkMode ? Color.white : Color.BLACK);

                JTextArea sentTimestamp = new JTextArea((new Time(System.currentTimeMillis()))+"");
                sentTimestamp.setLineWrap(true);
                sentTimestamp.setWrapStyleWord(true);
                sentTimestamp.setEditable(false);
                sentTimestamp.setMargin(new Insets(10, 10, 10, 10));
//                sentTimestamp.setBackground(window.getBackground());
                sentTimestamp.setForeground(isDarkMode ? Color.white : Color.BLACK);

                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
                panel.add(sentTimestamp);
                panel.add(area);
                panel.revalidate();
                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
                db.updateMessage(seller.getEmail(),thisMessage.getBuyerEmail(),area.getText(),"buyer");
                JOptionPane.showMessageDialog(null, "Message succesfully sent!");
            }
        });
        // message messages.getElementAt(selectedMessage)).toString()



        JTextArea timeReceivedLabel = new JTextArea(""+thisMessage.getTimeStamp().toString().substring(0, 10)+"\n"+thisMessage.getTimeStamp().toString().substring(11, 16));
        timeReceivedLabel.setMargin(new Insets(10, 10, 10, 10));
//        timeReceivedLabel.setBackground(window.getBackground());
        timeReceivedLabel.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        timeReceivedLabel.setEditable(false);
//        timeReceivedLabel.setBounds(70, 270, 200, 200);

        messageText.setText(thisMessage.getMessage());
        messageText.setEditable(false);
        messageText.setLineWrap(true);
        messageText.setWrapStyleWord(true);
        messageText.setMargin(new Insets(10, 10, 10, 10));
//        messageText.setBackground(window.getBackground());
//        messageText.setBounds(5, 300, 200, 200);
        messageText.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());

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
        scrollPane.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());


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
    public MessageView(JFrame previousWindowFrame, ArrayList<message> listOfMessages,int selectedMessage, DefaultListModel messages, JButton previousBackButton, JList messagesList, buyer buyer, boolean isDarkMode)
    {
        db = new DatabaseAccess();
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
        window.getContentPane().setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());

        previousBackButton.setEnabled(false);
        messagesList.setEnabled(false);
        previousWindowFrame.setFocusable(false);

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

        JPanel bottomButtonLayout = new JPanel(){
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
        bottomButtonLayout.setLayout(new BoxLayout(bottomButtonLayout, BoxLayout.LINE_AXIS));

        panel = new JPanel(){
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
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        message thisMessage = listOfMessages.get(selectedMessage);
        JTextField buyerMessageField = new JTextField(10);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
                JTextArea area = new JTextArea();
                area.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
                area.setLineWrap(true);
                area.setWrapStyleWord(true);
                area.setText("\n"+(new Time(System.currentTimeMillis()))+"\n"+thisMessage.getMessage()+"\n"+buyer.getName()+ ": "+ buyerMessageField.getText());
                area.setEditable(false);
                area.setMargin(new Insets(10, 10, 10, 10));
//                area.setBackground(window.getBackground());
                area.setForeground(isDarkMode ? Color.white : Color.BLACK);

                JTextArea sentTimestamp = new JTextArea(new Time(System.currentTimeMillis())+"");
                sentTimestamp.setLineWrap(true);
                sentTimestamp.setWrapStyleWord(true);
                sentTimestamp.setEditable(false);
                sentTimestamp.setMargin(new Insets(10, 10, 10, 10));
//                sentTimestamp.setBackground(window.getBackground());
                sentTimestamp.setForeground(isDarkMode ? Color.white : Color.BLACK);

                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
                panel.add(sentTimestamp);
                panel.add(area);
                panel.revalidate();
                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
                db.updateMessage(thisMessage.getSellerEmail(),buyer.getEmail(),area.getText(),"buyer");
                JOptionPane.showMessageDialog(null, "Message succesfully sent!");

            }
        });
        // message messages.getElementAt(selectedMessage)).toString()



        JTextArea timeReceivedLabel = new JTextArea(""+thisMessage.getTimeStamp().toString().substring(0, 10)+"\n"+thisMessage.getTimeStamp().toString().substring(11, 16));
        timeReceivedLabel.setMargin(new Insets(10, 10, 10, 10));
//        timeReceivedLabel.setBackground(window.getBackground());
        timeReceivedLabel.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        timeReceivedLabel.setEditable(false);
//        timeReceivedLabel.setBounds(70, 270, 200, 200);

        messageText.setText(thisMessage.getMessage());
        messageText.setEditable(false);
        messageText.setLineWrap(true);
        messageText.setWrapStyleWord(true);
        messageText.setMargin(new Insets(10, 10, 10, 10));
//        messageText.setBackground(window.getBackground());
        messageText.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
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
        scrollPane.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());


        topButtonLayout.add(backButton);

        bottomButtonLayout.add(buyerMessageField);
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
