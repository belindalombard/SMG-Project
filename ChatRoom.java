import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ChatRoom {


    public ChatRoom() {
        JFrame window = new JFrame("Sell My Goods: Chat Room");
        window.setMinimumSize(new Dimension(700, 600));
        window.setLocation(300, 200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel components = new JPanel(new BorderLayout());

        JPanel navigation = new JPanel();
        navigation.setLayout(new BoxLayout(navigation, BoxLayout.LINE_AXIS));

        JButton back = new JButton("Back");
        navigation.add(back);
        navigation.add(Box.createHorizontalGlue());
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shopView home = new shopView();
                window.setVisible(false);
            }
        });

        JLabel instruction = new JLabel("Type your message to xxxxx below");
        TextArea message = new TextArea(20,50);
        JButton sendMessage = new JButton("Send Message to Seller");
        sendMessage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Message sent successfully");
                homeView homeView = new homeView();
                window.setVisible(false);

            }
        });

        JPanel messageStuff = new JPanel(new FlowLayout());
        messageStuff.add(instruction);
        messageStuff.add(message);
        messageStuff.add(sendMessage);

        components.add(navigation,BorderLayout.NORTH);
        components.add(messageStuff,BorderLayout.CENTER);
        components.setBackground(new Color(118,215,196));
        messageStuff.setBackground(new Color(118,215,196));
        navigation.setBackground(new Color(118,240,196));

        window.add(components);
        window.setVisible(true);

    }
}

