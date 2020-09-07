import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ShopSignUpView {

    public ShopSignUpView(JFrame previousWindowFrame, JButton backButton, JButton createAccountButton, Checkbox buyer, Checkbox seller){
        //Frame
        JFrame window = new JFrame("Sell My Goods: Home");
        window.setMinimumSize(new Dimension(500, 350));
        window.setLocation(450, 250);
        window.setResizable(false);
        window.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {
                backButton.setEnabled(true);
                createAccountButton.setEnabled(true);
                buyer.setEnabled(true);
                seller.setEnabled(true);
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
        //

        window.setVisible(true);
    }
}
