import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SignUp {
    public static ArrayList<User> users = new ArrayList<>();

    public static void createSignUp() {
        JFrame signUpFrame = new JFrame("Sign Up Page");
        signUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signUpFrame.setSize(500, 200);
        signUpFrame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton signUpButton = new JButton("Sign Up");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(signUpButton);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                createUser(username, password);
                JOptionPane.showMessageDialog(signUpFrame, "Sign Up Successful!");
                signUpFrame.dispose();
            }
        });

        signUpFrame.add(panel, BorderLayout.CENTER);
        signUpFrame.setVisible(true);
    }

    public static void createUser(String username, String password) {
        users.add(new User(username, password));
    }
}