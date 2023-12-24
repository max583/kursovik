package students;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.border.Border;

public class LoginFrame extends JDialog implements ActionListener {

    private MainFrame mainFrame;
    JTextField field_username = null;
    JPasswordField field_password = null;
    JTextField field_host = null;
    public LoginFrame(MainFrame mainFrame)  throws IOException {

        super(mainFrame, true);
        this.mainFrame = mainFrame;
        setTitle("Login");
        setBounds(300, 300, 480, 235);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //setResizable(false);
        //setVisible(true);

        Container c = getContentPane();
        c.setLayout(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(10, 5, 445, 180);
        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Login");
        mainPanel.setBorder(titled);
        mainPanel.setLayout(null);
        c.add(mainPanel);

        JLabel label_username = new JLabel("Username");
        label_username.setBounds(15, 25, 100, 25);
        field_username = new JTextField();
        field_username.setBounds(115, 25, 300, 25);
        String username = Configuration.getConfiguration().getUsername();
        if (username == null) {
            username = "admin";
        }
        field_username.setText(username);
        mainPanel.add(label_username);
        mainPanel.add(field_username);

        JLabel label_password = new JLabel("Password");
        label_password.setBounds(15, 55, 100, 25);
        field_password = new JPasswordField();
        field_password.setBounds(115, 55, 300, 25);
        mainPanel.add(label_password);
        mainPanel.add(field_password);

        JLabel host = new JLabel("Backend host");
        host.setBounds(15, 85, 100, 25);
        field_host = new JTextField();
        field_host.setBounds(115, 85, 300, 25);
        String hostname = Configuration.getConfiguration().getHostname();
        if (hostname == null) {
            hostname = "localhost:8081";
        }
        field_host.setText(hostname);
        mainPanel.add(host);
        mainPanel.add(field_host);

        JButton button_login = new JButton("Login");
        button_login.setActionCommand("Login");
        button_login.addActionListener(this);
        button_login.setBounds(20, 125, 200, 25);
        button_login.setEnabled(true);
        mainPanel.add(button_login);

        setVisible(true);
    }
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == null) {
            System.out.println("Login window: " + e.getActionCommand().toString());
            return;
        }
        if (e.getActionCommand().equals("Login")) {
            String username = this.field_username.getText();
            String password = this.field_password.getText();
            String hostname = this.field_host.getText();
            boolean result = false;
            try {
                result = RestApiClient.getClient().checkLogin(username, password, hostname);
            } catch (Exception ex) {
                SimpleDialogWindow dw = new SimpleDialogWindow( mainFrame, "Connection error",
                        ex.getMessage(), true, false, false, false);
                return;
            }
            if (result) {
                Configuration.getConfiguration().setUsername(username);
                Configuration.getConfiguration().setHostname(hostname);
                setModal(true);
                this.dispose();
            }
            else {
                SimpleDialogWindow dw = new SimpleDialogWindow( mainFrame, "Connection error",
                        "Wrong username or password. Try again.", true, false, false, false);
            }
        }
        if (e.getActionCommand().equals("Cancel")) {
            setModal(true);
            this.dispose();
        }
    }
}
