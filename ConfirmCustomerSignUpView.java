import javax.swing.*;
import javax.swing.text.html.Option;

public class ConfirmCustomerSignUpView {

    public ConfirmCustomerSignUpView(JFrame previousWindowFrame, buyer buyerobj){
        int dialog = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", JOptionPane.YES_NO_OPTION);

        if(dialog == JOptionPane.YES_OPTION){
            //Attempts to add buyer to database :) 
	   DatabaseAccess db = new DatabaseAccess();
	   HomeView homeView; 
	   if (db.AddBuyer(buyerobj.getName(), buyerobj.getEmail(), buyerobj.getContactNumber(), buyerobj.getPassword(), buyerobj.getResidentialAdr(), buyerobj.getBuyerID()))
		homeView = new HomeView(previousWindowFrame);
	   else 
		goBack();  
        }
	else		
		goBack();
    }
	
    public void goBack() {
	    SignUpView view = new SignUpView();
	    view.window.setVisible(true);
    }
}
