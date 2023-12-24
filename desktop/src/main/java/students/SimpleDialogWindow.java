package students;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import javax.swing.border.Border;

enum Result { OK, YES, NO, CANCEL }
public class SimpleDialogWindow extends JDialog implements ActionListener {
    private Result result;
    //private JFrame parentWindow;
    public SimpleDialogWindow(JFrame parentWindow, String title, String message, boolean ok_button, boolean cancel_button, boolean yes_button, boolean no_button) {
        super(parentWindow, true);
        //this.parentWindow = parentWindow;
        setTitle(title);
        setBounds(300, 300, 480, 165);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        Container c = getContentPane();
        c.setLayout(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(10, 5, 445, 110);
        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, title);
        mainPanel.setBorder(titled);
        mainPanel.setLayout(null);
        c.add(mainPanel);

        JLabel label = new JLabel(message);
        label.setBounds(15, 25, 350, 25);
        mainPanel.add(label);

        if (ok_button) {
            JButton button_ok = new JButton("OK");
            button_ok.setActionCommand(Result.OK.toString());
            button_ok.addActionListener(this);
            button_ok.setBounds(20, 65, 200, 25);
            mainPanel.add(button_ok);
        }

        if (cancel_button) {
            JButton button_cancel = new JButton("Cancel");
            button_cancel.setActionCommand(Result.CANCEL.toString());
            button_cancel.addActionListener(this);
            button_cancel.setBounds(225, 65, 200, 25);
            mainPanel.add(button_cancel);
        }

        if (yes_button) {
            JButton button_yes = new JButton("Yes");
            button_yes.setActionCommand(Result.YES.toString());
            button_yes.addActionListener(this);
            button_yes.setBounds(20, 65, 200, 25);
            mainPanel.add(button_yes);
        }

        if (no_button) {
            JButton button_no = new JButton("No");
            button_no.setActionCommand(Result.NO.toString());
            button_no.addActionListener(this);
            button_no.setBounds(225, 65, 200, 25);
            mainPanel.add(button_no);
        }

        setVisible(true);
    }


    public Result getResult() {
        return result;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(Result.OK.toString())) {
            result = Result.OK;
        } else if (e.getActionCommand().equals(Result.YES.toString())) {
            result = Result.YES;
        } else if (e.getActionCommand().equals(Result.NO.toString())) {
            result = Result.NO;
        } else if (e.getActionCommand().equals(Result.CANCEL.toString())) {
            result = Result.CANCEL;
        }
        dispose();
    }

}
