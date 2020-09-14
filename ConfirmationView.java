import javax.swing.*;

public class ConfirmationView {
    public ConfirmationView(){
        JOptionPane.showMessageDialog(null, "Your order has been placed successfully!");
        HomeView homeView = new HomeView(new JFrame());
    }
}
