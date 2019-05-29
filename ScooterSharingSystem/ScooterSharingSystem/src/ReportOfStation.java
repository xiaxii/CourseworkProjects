import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Boundary Class <br>
 * Sub-page of Report: Station report
 * @version 3.3
 * @author SE Group 101 (Taizhou QING, Haoran CUI, Jiayi PANG, Xi XIA, Yuanrong SHAO, Xi CUI)
 */
public class ReportOfStation implements ActionListener {

    public String pathname_parent = "src/File/";

    JFrame frame;
    JLabel label, label1;
    JButton button;
    public ManageReport manageRepo;

    /**
     * Set up the StationReport GUI
     */
    public void stationreportGUI() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        label = new JLabel();
        label.setText("Station Report is:");
        label.setBounds(280, 0, 800, 20);
        frame.getContentPane().add(label);
        label.setFont(new Font("Courier", Font.BOLD, 20));

        String total_reports = null;
        try {
            String pathname = pathname_parent + "numOfScooters.txt";
            File filename = new File(pathname);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = null;
            line = br.readLine();
            while (line != null) {
                String[] stationInfo = line.split(",");

                if (total_reports == null) {
                    total_reports = "<html>" + "\tstationOpp" + "\tNumber of usable scooters" + "<br/>";
                    total_reports = total_reports + "Station " + stationInfo[0] + ": " + stationInfo[1] + " scooters. <br/>";
                } else {
                    total_reports = total_reports + "Station " + stationInfo[0] + ": " + stationInfo[1] + " scooters. <br/>";

                }
                line = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        total_reports = total_reports + "</html>";

        label1 = new JLabel();
        label1.setText(total_reports);
        label1.setBounds(80, 30, 600, 280);
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
     * Reaction to the button: Back
     * @param event buttons is pressed
     */
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == button) {
            frame.dispose();
            manageRepo = new ManageReport();
            manageRepo.reportGUI();
        }
    }
}
