import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Boundary Class <br>
 * Management sub-page: Report  <br>
 * <p>2 types of report: <br>
 *     1. Weekly Report <br>
 *     2. Station Report <br>
 * @version 3.3
 * @author SE Group 101 (Taizhou QING, Haoran CUI, Jiayi PANG, Xi XIA, Yuanrong SHAO, Xi CUI)
 */
public class ManageReport implements ActionListener {

    JFrame frame;
    JLabel label, label1, label2, label3, label4;
    JButton button, button1, button2;
    JTextField text;

    public ManageGUI manage;
    public ReportOfStation stationRepo;
    public ReportWeekly weeklyRepo;

    /**
     * Set up the Report GUI
     */
    public void reportGUI() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        label = new JLabel();
        label.setText("USAGE REPORT");
        label.setBounds(330, 0, 800, 50);
        frame.getContentPane().add(label);
        label.setFont(new Font("Courier", Font.BOLD, 20));

        label1 = new JLabel();
        label1.setText("Weekly Report");
        label1.setBounds(130, 60, 800, 50);
        frame.getContentPane().add(label1);
        label1.setFont(new Font("Courier", Font.BOLD, 20));

        label2 = new JLabel();
        label2.setText("Please enter the id below.");
        label2.setBounds(130, 90, 800, 50);
        frame.getContentPane().add(label2);
        label2.setFont(new Font("Courier", Font.PLAIN, 20));

        text = new JTextField();
        frame.getContentPane().add(text);
        text.setBounds(130, 130, 500, 50);

        button = new JButton("Weekly Report!");
        button.setBounds(130, 190, 200, 60);
        button.addActionListener(this);
        frame.getContentPane().add(button);
        button.setFont(new Font("Courier", Font.BOLD, 20));

        label3 = new JLabel();
        label3.setText("Stock Report");
        label3.setBounds(130, 280, 800, 50);
        frame.getContentPane().add(label3);
        label3.setFont(new Font("Courier", Font.BOLD, 20));

        label4 = new JLabel();
        label4.setText("Press if you want to check the stock status.");
        label4.setBounds(130, 310, 800, 50);
        frame.getContentPane().add(label4);
        label4.setFont(new Font("Courier", Font.PLAIN, 20));

        button1 = new JButton("Station Report");
        button1.setBounds(130, 360, 200, 60);
        button1.addActionListener(this);
        frame.getContentPane().add(button1);
        button1.setFont(new Font("Courier", Font.BOLD, 20));

        button2 = new JButton("Back");
        button2.setBounds(130, 450, 200, 60);
        button2.addActionListener(this);
        frame.getContentPane().add(button2);
        button2.setFont(new Font("Courier", Font.BOLD, 20));

        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    /**
     * Reaction to buttons: <br>
     * <p> 1. Weekly report <br>
     *     2. Station report <br>
     *     3. Back <br>
     * @param event button is pressed
     */
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == button) {
            frame.dispose();
            String weeklyTotalRepo = UserControl.weeklyReportOf1User(text.getText());
            weeklyRepo = new ReportWeekly();
            weeklyRepo.weeklyReportGUI(weeklyTotalRepo);
        } else if (event.getSource() == button1) {
            frame.dispose();
            stationRepo = new ReportOfStation();
            stationRepo.stationreportGUI();
        } else if (event.getSource() == button2) {
            frame.dispose();
            manage = new ManageGUI();
            manage.manageGUI();
        }
    }

}