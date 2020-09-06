import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatRoomView {
    public ChatRoomView(JFrame previousWindowFrame){
        //Frame
        JFrame window = new JFrame("Sell My Goods: ChatRoom");
        window.setMinimumSize(new Dimension(300, 500));
        window.setLocation(500, 150);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setResizable(false);
        //

        JPanel topLabelLayout = new JPanel(new FlowLayout());
        JLabel userInstruction = new JLabel("Type your message below");

        JPanel bottomButtonLayout = new JPanel(new FlowLayout());
        JButton sendMessageButton = new JButton("Send");
        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JTextArea messageBoard = new JTextArea();
        messageBoard.setLineWrap(true);
        messageBoard.setMargin(new Insets(10, 10, 10, 10));
        messageBoard.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(messageBoard);


        topLabelLayout.add(userInstruction);
        bottomButtonLayout.add(sendMessageButton);


        window.add(scrollPane, BorderLayout.CENTER);
        window.add(topLabelLayout, BorderLayout.NORTH);
        window.add(bottomButtonLayout, BorderLayout.SOUTH);
        window.setVisible(true);
    }
}
