import javax.swing.*;
import javax.swing.text.html.Option;

public class ConfirmCustomerSignUpView {

	AdminView x;

	public ConfirmCustomerSignUpView(JFrame previousWindowFrame, JFrame adminWindow, buyer buyerobj){
        int dialog = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", JOptionPane.YES_NO_OPTION);

        if(dialog == JOptionPane.YES_OPTION){
            //Attempts to add buyer to database :) 
		   	DatabaseAccess db = new DatabaseAccess();
			x = new AdminView(previousWindowFrame,true);
			x.window.setVisible(false);

		   	HomeView homeView;
		   	if (db.AddBuyer(buyerobj.getName(), (buyerobj.getEmail()).toLowerCase(), buyerobj.getContactNumber(), buyerobj.getPassword(), buyerobj.getResidentialAdr(), buyerobj.getBuyerID()))
		   		if(adminWindow.getTitle().equals("Sell My Goods: Admin")){
					x.refreshList();
					adminWindow.dispose();
					x.window.setVisible(true);
		   		}
		   		else{
					homeView = new HomeView(previousWindowFrame, buyerobj, true);
				}
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
