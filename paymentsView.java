import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class paymentsView {

    JButton backButton, payButton;

    public paymentsView(){
        JFrame window = new JFrame("Sell My Goods: Payments");
        window.setMinimumSize(new Dimension(700, 600));
        window.setLocation(300, 200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel flowLayoutPanel = new JPanel(new FlowLayout());
        JPanel backButtonLayout = new JPanel(new BorderLayout());
        backButton = new JButton("<-Back");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shopView shopView = new shopView();
                window.setVisible(false);
            }
        });

        JLabel apimessage = new JLabel("Payment API/platform will be used here");

        //Button to pay (obviously not so simple when we're implementing this)
        payButton = new JButton("Pay");
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmationView confirmationView = new confirmationView();
                window.setVisible(false);
            }
        });


        apimessage.setPreferredSize(new Dimension(10,10));

        flowLayoutPanel.add(backButton);
        flowLayoutPanel.add(payButton);
        backButtonLayout.add(flowLayoutPanel, BorderLayout.WEST);
        backButtonLayout.add(apimessage, BorderLayout.CENTER);
        apimessage.setBorder(BorderFactory.createLineBorder(Color.black));

        window.add(backButtonLayout);
        window.setBackground(new Color(118,240,196));
        flowLayoutPanel.setBackground(new Color(118,240,196));
        backButtonLayout.setBackground(new Color(118,240,196));
        window.setVisible(true);

    }
}