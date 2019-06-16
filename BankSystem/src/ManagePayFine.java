import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Boundary Class <br>
 * Management sub-page: pay for fine <br>
 * Enter the ID to pay
 * @version 3.3
 * @author SE Group 101 (Taizhou QING, Haoran CUI, Jiayi PANG, Xi XIA, Yuanrong SHAO, Xi CUI)
 */
public class ManagePayFine implements ActionListener {
    JFrame frame;
    JLabel label1;
    JTextField text;
    JButton button, button2;

    public ManageGUI manage;

    /**
     * Set up the payForFine GUI
     */
    public void payGUI() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel label = new JLabel();
        label.setText("Pay for fine");
        label.setBounds(320, 0, 800, 50);
        frame.getContentPane().add(label);
        label.setFont(new Font("Courier", Font.BOLD, 20));

        label1 = new JLabel();
        label1.setText("Please enter your id below");
        label1.setBounds(130, 60, 800, 50);
        frame.getContentPane().add(label1);
        label1.setFont(new Font("Courier", Font.PLAIN, 20));

        text = new JTextField();
        frame.getContentPane().add(text);
        text.setBounds(130, 130, 500, 50);

        button = new JButton("Pay");
        button.setBounds(430, 190, 200, 60);
        button.addActionListener(this);
        frame.getContentPane().add(button);
        button.setFont(new Font("Courier", Font.BOLD, 20));

        button2 = new JButton("Back");
        button2.setBounds(130, 190, 200, 60);

        button2.addActionListener(this);
        frame.getContentPane().add(button2);
        button2.setFont(new Font("Courier", Font.BOLD, 20));

        frame.setSize(800, 400);
        frame.setVisible(true);
    }

    /**
     * Reaction to button "pay for fine"
     * @param event button is pressed
     */
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == button) {
            String ID = text.getText();
            User user = new User(ID);
            if (UserControl.checkRegisterInfo(user.getQmID())) {
                String result = UserControl.payFine(user);
                label1.setText(result);
            } else
                label1.setText("You haven't registered or you provided a invalid ID.");
        } else if (event.getSource() == button2) {
            frame.dispose();
            manage = new ManageGUI();
            manage.manageGUI();
        }
    }
}
