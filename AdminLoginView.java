import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLoginView {
    public AdminLoginView(JFrame previousWindowFrame, boolean isDarkMode){
        //Frame
        JFrame window = new JFrame("Sell My Goods: Admin Login");
        window.setMinimumSize(new Dimension(500, 250));
        window.setLocation(450, 200);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        //

        JPanel boxLayout = new JPanel();
        boxLayout.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        boxLayout.setLayout(new BoxLayout(boxLayout, BoxLayout.LINE_AXIS));

        JPanel backBoxLayout = new JPanel();
        backBoxLayout.setBackground(isDarkMode ? new Color(0x222425) : window.getBackground());
        backBoxLayout.setLayout(new BoxLayout(backBoxLayout, BoxLayout.LINE_AXIS));
        // Fields
        JLabel nameLabel = new JLabel("Email : ");
        nameLabel.setBounds(150, 60, 150, 27);
        nameLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JTextField emailField = new JTextField(10);
        emailField.setBounds(230, 60, 150, 27);
        JLabel passwordLabel = new JLabel("Password : ");
        passwordLabel.setBounds(150, 100, 150, 27);
        passwordLabel.setForeground(isDarkMode ? Color.white : Color.BLACK);
        JPasswordField passwordField = new JPasswordField(10);
        passwordField.setBounds(230, 100, 150, 27);
        //

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(emailField.getText().equals("admin@admin.com") && ((String.valueOf(passwordField.getPassword())).equals("CoolAdmin"))){
                    AdminView adminView = new AdminView(window, isDarkMode);
                    window.setVisible(false);
                }
                else{
                    System.out.println("Error");
                }
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousWindowFrame.setVisible(true);
                window.setVisible(false);
            }
        });

        window.add(nameLabel);
        window.add(emailField);
        window.add(passwordLabel);
        window.add(passwordField);

        boxLayout.add(Box.createHorizontalGlue());
        boxLayout.add(loginButton);
        boxLayout.add(Box.createHorizontalGlue());

        backBoxLayout.add(backButton);
        backBoxLayout.add(Box.createHorizontalGlue());

        window.add(backBoxLayout, BorderLayout.NORTH);
        window.add(boxLayout, BorderLayout.SOUTH);

        window.add(new JLabel());

        window.setVisible(true);
    }
}
