import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class paymentsView {

    JButton backButton, submitOrderPaymentButton;

    public paymentsView(){
        JFrame window = new JFrame("Sell My Goods: Payments");
        window.setMinimumSize(new Dimension(700, 500));
        window.setLocation(300, 200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        JPanel flowLayoutPanel = new JPanel(new FlowLayout());
//        JPanel backButtonLayout = new JPanel(new BorderLayout());
//
//        JPanel flowLayoutPanelPay = new JPanel(new FlowLayout());
//        JPanel payButtonLayout = new JPanel(new BorderLayout());
        JPanel buttonLayout = new JPanel();
        buttonLayout.setLayout(new BoxLayout(buttonLayout, BoxLayout.LINE_AXIS));

        backButton = new JButton("<-Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shopView shopView = new shopView();
                window.setVisible(false);
            }
        });

        submitOrderPaymentButton = new JButton("Submit Order");
        submitOrderPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmationView toConfirmationView = new confirmationView();
                window.setVisible(false);
            }
        });

        buttonLayout.add(backButton);
        buttonLayout.add(Box.createHorizontalGlue());
        buttonLayout.add(submitOrderPaymentButton);

        window.add(buttonLayout, BorderLayout.NORTH);


//        backButtonLayout.add(backButton, BorderLayout.WEST);
//        backButtonLayout.add(submitOrderPaymentButton, BorderLayout.EAST);
//        window.add(backButtonLayout);
//        flowLayoutPanel.add(backButton);
//        flowLayoutPanelPay.add(submitOrderPaymentButton);
//        backButtonLayout.add(flowLayoutPanel, BorderLayout.WEST);
//        payButtonLayout.add(flowLayoutPanelPay, BorderLayout.EAST);

//        window.add(submitOrderPaymentButton);
//        window.add(backButtonLayout);
////        window.add(payButtonLayout);

        window.setVisible(true);
    }
}
