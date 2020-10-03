import javax.swing.*;
import javax.swing.text.html.Option;

public class ConfirmCustomerSignUpView {

    public ConfirmCustomerSignUpView(JFrame previousWindowFrame){
        int dialog = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", JOptionPane.YES_NO_OPTION);

        if(dialog == JOptionPane.YES_OPTION){
            HomeView homeView = new HomeView(previousWindowFrame);
        }
        else{
            SignUpView view = new SignUpView();
            view.window.setVisible(true);
        }

    }
}
