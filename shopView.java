import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class shopView {

    JButton backHomeButton, paymentsViewButton;
    JPanel backButtonLayout, paymentsButtonLayout, productExample;
    Font font = new Font("Serif",Font.BOLD,20);

    public shopView(){
        JFrame window = new JFrame("Sell My Goods: Shop View");
        window.setMinimumSize(new Dimension(700, 500));
        window.setLocation(300, 200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        JPanel flowLayoutPanel = new JPanel(new FlowLayout());
        backButtonLayout = new JPanel(new BorderLayout());
        paymentsButtonLayout = new JPanel(new BorderLayout());

        JPanel buttonLayout = new JPanel();
        buttonLayout.setLayout(new BoxLayout(buttonLayout, BoxLayout.LINE_AXIS));

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

        productExample = new JPanel(new FlowLayout(FlowLayout.CENTER,20,30));
        JLabel image = new JLabel("IMAGE OF PRODUCT");
        JButton contact_seller = new JButton("Contact Seller");
        contact_seller.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChatRoom chatRoom = new ChatRoom();
                window.setVisible(false);
            }
        });

	
	
        JTextArea information = new JTextArea(5,20);
        information.append("PRODUCT NAME\n");
        information.append("PRICE: Rxxx\n");
        information.append("LOCATION: xxxx\n");
        information.append("\n");
        information.append("Quantity Left: xxx\n");
        information.setFont(font);
        information.setEditable(false);
        image.setFont(font);
        image.setPreferredSize(new Dimension(200,200));
        image.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        image.setBackground(Color.GREEN);

        productExample.add(image);
        productExample.add(information);
        productExample.add(contact_seller);

//        flowLayoutPanel.add(backHomeButton);
//        flowLayoutPanel.add(paymentsViewButton);
//        backButtonLayout.add(flowLayoutPanel, BorderLayout.CENTER);
//        paymentsButtonLayout.add(flowLayoutPanel, BorderLayout.CENTER);
//        window.add(backButtonLayout,BorderLayout.NORTH);
        buttonLayout.add(backHomeButton);
        buttonLayout.add(Box.createHorizontalGlue());
        buttonLayout.add(paymentsViewButton);

        window.add(buttonLayout,BorderLayout.NORTH);

        window.add(productExample,BorderLayout.CENTER);
        backButtonLayout.setBackground(new Color(118,215,196));
        window.setBackground(new Color(118,215,196));
        productExample.setBackground(new Color(118,215,196));
        buttonLayout.setBackground(new Color(118,240,186));
	    window.setVisible(true);
	

    }
}
