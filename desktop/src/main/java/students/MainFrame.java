package students;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.border.Border;

public class MainFrame extends JFrame implements ActionListener {
    public MainFrame() throws IOException {
        setTitle("Students " + Configuration.getConfiguration().getAppVersion().toString());
        setBounds(300, 90, 480, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        Container c = getContentPane();
        c.setLayout(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(10, 5, 445, 550);
        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Users list");
        mainPanel.setBorder(titled);
        mainPanel.setLayout(null);
        c.add(mainPanel);

        JList<String> stud_list = new JList<String>();
        stud_list.setBounds(15, 25, 200, 510);
        stud_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        stud_list.setVisibleRowCount(20);
        Border etched_sl = BorderFactory.createEtchedBorder();
        stud_list.setBorder(etched_sl);
        mainPanel.add(stud_list);

        JButton button_add_user = new JButton("Add user");
        button_add_user.setActionCommand("Add_user");
        button_add_user.addActionListener(this);
        button_add_user.setBounds(225, 25, 200, 25);
        button_add_user.setEnabled(false);
        mainPanel.add(button_add_user);

        JButton button_remove_user = new JButton("Remove user");
        button_remove_user.setActionCommand("Remove_user");
        button_remove_user.addActionListener(this);
        button_remove_user.setBounds(225, 55, 200, 25);
        button_remove_user.setEnabled(false);
        mainPanel.add(button_remove_user);

        JButton button_edit_user = new JButton("Edit user roles");
        button_edit_user.setActionCommand("Edit_user_roles");
        button_edit_user.addActionListener(this);
        button_edit_user.setBounds(225, 85, 200, 25);
        button_edit_user.setEnabled(false);
        mainPanel.add(button_edit_user);

        JButton button_logout = new JButton("Logout");
        button_logout.setActionCommand("Logout");
        button_logout.addActionListener(this);
        button_logout.setEnabled(false);
        button_logout.setBounds(225, 115, 200, 25);
        mainPanel.add(button_logout);

        LoginFrame login_frame = new LoginFrame(this);


        String current_username = Configuration.getConfiguration().getUsername();
        String current_hostname = Configuration.getConfiguration().getHostname();
        System.out.println("LoginFrame: " + current_username + " " + current_hostname);
        if (current_username == null || current_hostname == null
                || current_hostname.length() == 0 || current_username.length() == 0 ) {
            System.exit(0);
        }

        button_logout.setEnabled(true);

        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == null) {
            System.out.println("Main window: " + e.getActionCommand().toString());
            return;
        }
    }

}
