import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Boundary Class <br>
 * Management sub-page: Registration <br>
 * Enter QMID, Full Name and Mail Address to register <br>
 * @version 3.3
 * @version 3.3
 * @author SE Group 101 (Taizhou QING, Haoran CUI, Jiayi PANG, Xi XIA, Yuanrong SHAO, Xi CUI)
 */
public class ManageRegistration implements ActionListener {

    JFrame frame;
    JLabel label, label1, label2, label3, label4;
    JTextField text, text1, text2;
    JButton button1, button2;

    public ManageGUI manage;

    /**
     * Set up Registration GUI
     */
    public void registrationGUI() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        label = new JLabel();
        label.setText(" New User Registration");
        label.setBounds(280, 0, 800, 50);
        frame.getContentPane().add(label);
        label.setFont(new Font("Courier", Font.BOLD, 20));

        label1 = new JLabel();
        label1.setText("Please enter your information below.");
        label1.setBounds(230, 50, 800, 50);
        frame.getContentPane().add(label1);
        label1.setFont(new Font("Courier", Font.PLAIN, 20));

        label2 = new JLabel("QM ID");
        label2.setBounds(100, 100, 800, 50);
        frame.getContentPane().add(label2);
        label2.setFont(new Font("Courier", Font.BOLD, 20));

        text = new JTextField();
        frame.getContentPane().add(text);
        text.setBounds(100, 140, 500, 50);

        label3 = new JLabel("FULL NAME");
        label3.setBounds(100, 210, 800, 50);
        frame.getContentPane().add(label3);
        label3.setFont(new Font("Courier", Font.BOLD, 20));

        text1 = new JTextField();
        frame.getContentPane().add(text1);
        text1.setBounds(100, 250, 500, 50);

        label4 = new JLabel("MAIL ADDRESS");
        label4.setBounds(100, 320, 800, 50);
        frame.getContentPane().add(label4);
        label4.setFont(new Font("Courier", Font.BOLD, 20));

        text2 = new JTextField();
        frame.getContentPane().add(text2);
        text2.setBounds(100, 360, 500, 50);

        button1 = new JButton("Register");
        button1.setBounds(400, 420, 200, 60);
        button1.addActionListener(this);
        frame.getContentPane().add(button1);
        button1.setFont(new Font("Courier", Font.BOLD, 20));

        button2 = new JButton("Back");
        button2.setBounds(100, 420, 200, 60);
        button2.addActionListener(this);
        frame.getContentPane().add(button2);
        button2.setFont(new Font("Courier", Font.BOLD, 20));

        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    /**
     * Reaction to buttons: <br>
     *<p>  1. Register <br>
     *     2. Back <br>
     * @param event button is pressed
     */
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == button1) {
            if(text1.getText().equals("") || text.getText().equals("") || text.getText().equals("")) {
                label1.setText("Please fill in all the blanks");
            }
            else {
                User user = new User(text1.getText(), text.getText(), text2.getText());
                if(!UserControl.checkIDFormat(text.getText()) && UserControl.checkEmail(text2.getText())){
                    label1.setText("The QM ID doesn't satisfied the requirement");
                }
                else if (UserControl.checkIDFormat(text.getText()) && !UserControl.checkEmail(text2.getText())){
                    label1.setText("The Email doesn't satisfied the requirement");
                }
                else if(!UserControl.checkIDFormat(text.getText()) && !UserControl.checkEmail(text2.getText())){
                    label1.setText("The QM ID and Email doesn't satisfied the requirement");
                }
                else if (UserControl.checkRegisterInfo(user.getQmID())) {
                    label1.setText("The QM ID have already registered.");
                } else {
                    label1.setText("You haven't registered, we are going to register for you.");
                    String content = UserControl.register(user.getName(), user.getQmID(), user.getMailAddress());
                    label1.setText(content);
                }
            }
        } else if (event.getSource() == button2) {
            frame.dispose();
            manage = new ManageGUI();
            manage.manageGUI();
        }
    }

}