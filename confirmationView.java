
import javax.swing.*;
import java.awt.*;

public class confirmationView {

    public confirmationView(){
        JOptionPane.showMessageDialog(null, "Your order has been placed successfuly!");
        homeView homeView = new homeView();
    }
}