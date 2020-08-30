import javax.swing.*;
import java.awt.*;

public class confirmationView {

    public confirmationView(){
        int res = JOptionPane.showConfirmDialog(null, "Your order has been successful!", "Order Confirmation",
                JOptionPane.DEFAULT_OPTION,
                 JOptionPane.PLAIN_MESSAGE, null);
        if(res == JOptionPane.OK_OPTION){
            System.out.println("Hello, we are done!");
            homeView toHomeView = new homeView();
        }
    }


}
