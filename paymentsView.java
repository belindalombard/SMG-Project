import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class paymentsView {

    JButton backButton;

    public paymentsView(){
        JFrame window = new JFrame("Sell My Goods: Payments");
        window.setMinimumSize(new Dimension(700, 500));
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

        flowLayoutPanel.add(backButton);
        backButtonLayout.add(flowLayoutPanel, BorderLayout.WEST);
        window.add(backButtonLayout);

        window.setVisible(true);
    }
}
