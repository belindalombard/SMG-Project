import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class shopView {

    JButton backHomeButton, paymentsViewButton;
    JPanel buttonLayout;

    public shopView(){
        JFrame window = new JFrame("Sell My Goods: Shop View");
        window.setMinimumSize(new Dimension(700, 500));
        window.setLocation(300, 200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        JPanel flowLayoutPanel = new JPanel(new FlowLayout());
//        backButtonLayout = new JPanel(new BorderLayout());
//        paymentsButtonLayout = new JPanel(new BorderLayout());

        buttonLayout = new JPanel();
        buttonLayout.setLayout(new BoxLayout(buttonLayout, BoxLayout.LINE_AXIS));


        backHomeButton = new JButton("<-Back");
        backHomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                homeView home = new homeView();
                window.setVisible(false);
            }
        });

        paymentsViewButton = new JButton("Pay");
        paymentsViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paymentsView paymentsView = new paymentsView();
                JOptionPane confirmationWindow = new JOptionPane("Your order has been successful");
                window.setVisible(false);
            }
        });

        buttonLayout.add(backHomeButton);
        buttonLayout.add(Box.createHorizontalGlue());
        buttonLayout.add(paymentsViewButton);
//        flowLayoutPanel.add(backHomeButton);
//        flowLayoutPanel.add(paymentsViewButton);
//        backButtonLayout.add(flowLayoutPanel, BorderLayout.CENTER);
//        paymentsButtonLayout.add(flowLayoutPanel, BorderLayout.CENTER);
//        window.add(backButtonLayout);
//        window.add(paymentsButtonLayout);
        window.add(buttonLayout, BorderLayout.NORTH);
        window.setVisible(true);


    }
}