import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class shopView {

    JButton backHomeButton, paymentsViewButton;
    JPanel backButtonLayout, paymentsButtonLayout;

    public shopView(){
        JFrame window = new JFrame("Sell My Goods: Shop View");
        window.setMinimumSize(new Dimension(700, 500));
        window.setLocation(300, 200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel flowLayoutPanel = new JPanel(new FlowLayout());
        backButtonLayout = new JPanel(new BorderLayout());
        paymentsButtonLayout = new JPanel(new BorderLayout());

        backHomeButton = new JButton("<-Back");

        backHomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                homeView home = new homeView();
                window.setVisible(false);
            }
        });

        paymentsViewButton = new JButton("Next -> Pay");
        paymentsViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paymentsView paymentsView = new paymentsView();
                window.setVisible(false);
            }
        });


        flowLayoutPanel.add(backHomeButton);
        flowLayoutPanel.add(paymentsViewButton);
        backButtonLayout.add(flowLayoutPanel, BorderLayout.CENTER);
        paymentsButtonLayout.add(flowLayoutPanel, BorderLayout.CENTER);
        window.add(backButtonLayout);
        window.add(paymentsButtonLayout);

        window.setVisible(true);


    }
}
