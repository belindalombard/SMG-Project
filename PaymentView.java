import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class PaymentView { 
    DatabaseAccess db = new DatabaseAccess();
    JTextField cardNumberField, cvvNumField, expiryDateField;
    boolean done;
    JFrame loading;
    String productName;


    public PaymentView(JFrame previousWindowFrame, JButton payButton, JButton preBackButton, JTextField quantityField, JButton addButton, JButton subtractButton, JButton chatButton, String productName, JLabel totalAmount, buyer buyerobj, boolean isDarkMode){
        //Frame
        JFrame window = new JFrame("Sell My Goods: Payment");
        window.setMinimumSize(new Dimension(400, 550));
        window.setLocation(500, 150);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.getContentPane().setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        //

        JPanel topButtonLayout = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0,
                        isDarkMode ? getBackground().darker().darker().gray : getBackground().darker(), 0, getHeight(),
                        isDarkMode ? getBackground().darker().darker().darkGray : getBackground().brighter().brighter().brighter().brighter());
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        topButtonLayout.setLayout(new BoxLayout(topButtonLayout, BoxLayout.LINE_AXIS));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousWindowFrame.setVisible(true);
                window.setVisible(false);
                payButton.setEnabled(true);
                preBackButton.setEnabled(true);
                quantityField.setEditable(true);
                addButton.setEnabled(true);
                subtractButton.setEnabled(true);
                chatButton.setEnabled(true);
            }
        });

        JButton paymentButton = new JButton("Confirm Payment");
        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!validateAllFields(cvvNumField.getText(), expiryDateField.getText(), cardNumberField.getText()).equals("yes")){
                    JOptionPane.showMessageDialog(null, validateAllFields(cvvNumField.getText(), expiryDateField.getText(), cardNumberField.getText()), "WARNING", JOptionPane.WARNING_MESSAGE);
                }
                else{
         	    db.updateQuantity(productName, Integer.parseInt(quantityField.getText())); 
	            expiryDateField.setText(expiryDateField.getText().substring(0, 2)+"/"+expiryDateField.getText().substring(2, 4));
                    ConfirmationView confirmationView = new ConfirmationView(buyerobj, isDarkMode);
                    window.setVisible(false);
                    previousWindowFrame.setVisible(false);
                }
            }
        });

        JLabel paymentHeading = new JLabel("PAYMENT");
        paymentHeading.setBounds(20, 30, 200, 50);
        paymentHeading.setFont(new Font(null, Font.BOLD, 25));
        paymentHeading.setForeground(Color.GRAY);

        JLabel itemBoughtLabel = new JLabel("Item : "+productName);
        itemBoughtLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        itemBoughtLabel.setBounds(130, 100, 150, 27);

        JLabel quantityBoughtLabel = new JLabel("Number of Items : "+quantityField.getText());
        quantityBoughtLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        quantityBoughtLabel.setBounds(130, 150, 150, 27);

        JLabel totalAmountSpentLabel = new JLabel(totalAmount.getText());
        totalAmountSpentLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        totalAmountSpentLabel.setBounds(130, 200, 150, 27);

        JLabel paymentDetailsInst = new JLabel("Fill in your payment details");
        paymentDetailsInst.setBounds(115, 280, 200, 27);
        paymentDetailsInst.setForeground(Color.GRAY);

        JLabel cardNumberLabel = new JLabel("Card Number : ");
        cardNumberLabel.setBounds(60, 320, 180, 50);
        cardNumberLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        cardNumberField = new JTextField(10);
        cardNumberField.setBounds(200, 332, 150, 22);
        cardNumberField.setBorder(new BevelBorder(2));

        JLabel expiryDateLabel = new JLabel("Expiry Date[mmyy] : ");
        expiryDateLabel.setBounds(60, 370, 150, 50);
        expiryDateLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        expiryDateField = new JTextField(10);
        expiryDateField.setBounds(200, 382, 150, 22);
        expiryDateField.setBorder(new BevelBorder(2));

        JLabel cvvNumLabel = new JLabel("CVV Number : ");
        cvvNumLabel.setBounds(60, 420, 150, 50);
        cvvNumLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        cvvNumField = new JTextField(10);
        cvvNumField.setBounds(200, 432, 150, 22);
        cvvNumField.setBorder(new BevelBorder(2));

        topButtonLayout.add(backButton);
        topButtonLayout.add(Box.createHorizontalGlue());
        topButtonLayout.add(paymentButton);

        window.add(paymentHeading);
        window.add(itemBoughtLabel);
        window.add(quantityBoughtLabel);
        window.add(totalAmountSpentLabel);
        window.add(paymentDetailsInst);
        window.add(expiryDateLabel);
        window.add(expiryDateField);
        window.add(cardNumberLabel);
        window.add(cardNumberField);
        window.add(cvvNumLabel);
        window.add(cvvNumField);
        window.add(new JLabel());
        window.add(topButtonLayout, BorderLayout.NORTH);
        window.setVisible(true);
    }

    private String validateAllFields(String cvvNum, String expiry, String cardNum){
        String validateCard = validateCardNumber(cardNum);
        if(!validateCard.equals("yes")){
            return validateCard;
        }

        String validateDate = validateExpiryDate(expiry);
        if(!validateDate.equals("yes")){
            return validateDate;
        }

        String validateCVV = validateCVVNum(cvvNum);
        if(!validateCVV.equals("yes")){
            return validateCVV;
        }

        return "yes";
    }

    private String validateCVVNum(String cvvNum){
        if(cvvNum.length() == 3){
            if(!cvvNum.matches("[0-9]+")){
                return "Only digits required";
            }
        }
        else{
            return "Your CVV number is not the correct length";
        }
        return "yes";
    }

    private String validateCardNumber(String cardNum){
        if(cardNum.length() == 16){
            if(!cardNum.matches("[0-9]+")){
                return "Only digits required";
            }
        }
        else{
            return "Your card number is not the correct length";
        }
        return "yes";
    }

    private String validateExpiryDate(String expiryDate){
        if(expiryDate.length() == 4){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
            simpleDateFormat.setLenient(false);
            try{
                Date expiry = simpleDateFormat.parse(Integer.parseInt(expiryDate.substring(0, 2)) + "/" + Integer.parseInt(expiryDate.substring(2, 4)));
                boolean expired = expiry.before(new Date());
                if(expired){
                    return "Sorry, the presented card has expired";
                }
            }catch (Exception e){
                return "This date is incorrect";
            }
        }
        else{
            return "Your expiry date is not the required length";
        }
        return "yes";
    }
}
