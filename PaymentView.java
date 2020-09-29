import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentView {
    public PaymentView(JFrame previousWindowFrame, JButton payButton, JButton preBackButton, JTextField quantityField, JButton addButton, JButton subtractButton, JButton chatButton){
        //Frame
        JFrame window = new JFrame("Sell My Goods: Payment");
        window.setMinimumSize(new Dimension(400, 400));
        window.setLocation(600, 150);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        //

        JPanel topButtonLayout = new JPanel();
        topButtonLayout.setLayout(new BoxLayout(topButtonLayout, BoxLayout.LINE_AXIS));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousWindowFrame.setVisible(true);
                window.setVisible(false);
                payButton.setEnabled(true);
                preBackButton.setEnabled(true);
                quantityField.setEditable(true);
                addButton.setEnabled(true);
                subtractButton.setEnabled(true);
                chatButton.setEnabled(true);
            }
        });

        JButton paymentButton = new JButton("Confirm Payment");
        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfirmationView confirmationView = new ConfirmationView();
                window.setVisible(false);
                previousWindowFrame.setVisible(false);
            }
        });

//        JLabel itemBoughtLabel = new JLabel("Item : ");
//        itemBoughtLabel.setBounds(150, 100, 150, 27);

        topButtonLayout.add(backButton);
        topButtonLayout.add(Box.createHorizontalGlue());
        topButtonLayout.add(paymentButton);

//        window.add(itemBoughtLabel);
        window.add(new JLabel());
        window.add(topButtonLayout, BorderLayout.NORTH);
        window.setVisible(true);
    }
}
