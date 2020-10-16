import javax.swing.*;

public class ConfirmationView {
    public ConfirmationView(buyer b, boolean isDarkMode){
        JOptionPane.showMessageDialog(null, "Your order has been placed successfully!");
        HomeView homeView = new HomeView(new JFrame(),b, isDarkMode);
    }
}
