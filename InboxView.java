import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

public class InboxView {
    JButton backButton;
    ArrayList<message> listOfMessages = new ArrayList<message>();
    DatabaseAccess db = new DatabaseAccess();
    public InboxView( String buyerEmail,JFrame previousWindowFrame, boolean isDarkMode)
    {
        JFrame window = new JFrame("Sell My Goods: Inbox");
        window.setMinimumSize(new Dimension(800, 500));
        window.setLocation(300, 200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        //
        DefaultListModel messages = new DefaultListModel();


        listOfMessages= db.getMessagesByBuyer(buyerEmail);
        Iterator i = listOfMessages.iterator();

        while (i.hasNext()){
            message next = (message) i.next();
            //System.out.println(next.getBuyerEmail());
            messages.addElement((next.getBuyerEmail()));
        }

        JList messageList = new JList(messages);
        messageList.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        messageList.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(messageList);
        scrollPane.setBorder(new EmptyBorder(0,0,0,0));
        messageList.setLayoutOrientation(JList.VERTICAL);
        messageList.setFixedCellHeight(60);

        messageList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

                if(messages.size() != 0){
                    if(mouseEvent.getClickCount() == 2){
                        MessageView messageView = new MessageView(window, listOfMessages, messageList.getSelectedIndex(), messages, backButton, messageList, db.getBuyer(buyerEmail), isDarkMode);
//
//                        window.setVisible(false);
                    }
                }

            }


            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });

        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousWindowFrame.setVisible(true);
                window.setVisible(false);
            }
        });
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

        topButtonLayout.add(backButton);
        window.add(scrollPane);
        window.add(topButtonLayout, BorderLayout.NORTH);
        window.setVisible(true);

    }
    public InboxView(JFrame previousWindowFrame, String sellerEmail, boolean isDarkMode)
    {
        JFrame window = new JFrame("Sell My Goods: Inbox");
        window.setMinimumSize(new Dimension(800, 500));
        window.setLocation(300, 200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        //
        DefaultListModel messages = new DefaultListModel();


        listOfMessages= db.getMessagesBySeller(sellerEmail);
        Iterator i = listOfMessages.iterator();

        while (i.hasNext()){
            message next = (message) i.next();
	    //System.out.println(next.getBuyerEmail());
            messages.addElement((next.getSellerEmail()));
	}

        JList messageList = new JList(messages);
        messageList.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        messageList.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(messageList);
        scrollPane.setBorder(new EmptyBorder(0,0,0,0));
        messageList.setLayoutOrientation(JList.VERTICAL);
        messageList.setFixedCellHeight(60);

        messageList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

                if(messages.size() != 0){
                    if(mouseEvent.getClickCount() == 2){
                        MessageView messageView = new MessageView(window, listOfMessages, messageList.getSelectedIndex(), messages, backButton, messageList,db.getSeller(sellerEmail), isDarkMode);
//                        window.setVisible(false);
                    }
                }

            }


            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
 
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousWindowFrame.setVisible(true);
                window.setVisible(false);
            }
        });
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

        topButtonLayout.add(backButton);
        window.add(scrollPane);
        window.add(topButtonLayout, BorderLayout.NORTH);
        window.setVisible(true);

    }

}

