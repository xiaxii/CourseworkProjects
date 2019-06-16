import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Boundary Class <br>
 * Sub-page of Report: Weekly Report
 * @version 3.3
 * @author SE Group 101 (Taizhou QING, Haoran CUI, Jiayi PANG, Xi XIA, Yuanrong SHAO, Xi CUI)
 */
public class ReportWeekly implements ActionListener {

    JFrame frame;
    JLabel label, label1;
    JButton button;

    public ManageReport manageRepo;
    public void weeklyReportGUI(String reports) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        label = new JLabel();
        label.setText("Your weekly ManageReport is:");
        label.setBounds(280, 0, 800, 20);
        frame.getContentPane().add(label);
        label.setFont(new Font("Courier", Font.BOLD, 20));

        label1 = new JLabel();
        label1.setText(reports);
        label1.setBounds(80, 30, 790, 280);
        frame.getContentPane().add(label1);
        label1.setFont(new Font("Courier", Font.BOLD, 20));

        button = new JButton("back");
        button.setBounds(280, 300, 200, 60);
        button.addActionListener(this);
        frame.getContentPane().add(button);
        button.setFont(new Font("Courier", Font.BOLD, 20));

        frame.setSize(800, 400);
        frame.setVisible(true);
    }

    /**
     * Reaction to button: back
     * @param event button is pressed
     */
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == button) {
            frame.dispose();
            manageRepo = new ManageReport();
            manageRepo.reportGUI();
        }
    }
}
