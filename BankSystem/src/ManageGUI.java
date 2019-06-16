import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Boundary Class: Management page  <br>
 * <p>providing 3 main functions: <br>
 *     1. Manage Registration <br>
 *     2. Manage Report <br>
 *     3. Manage Fine <br>
 * @version 3.3
 * @author SE Group 101 (Taizhou QING, Haoran CUI, Jiayi PANG, Xi XIA, Yuanrong SHAO, Xi CUI)
 */
public class ManageGUI implements ActionListener{

	JFrame frame;
	JLabel label, label1;
	JButton button1, button2, button3;

	public ManageRegistration registration;
	public ManageReport report;
	public ManagePayFine payFine;

	/**
	 * Set up the Management GUI
	 */
	public void manageGUI(){
		frame = new JFrame();

		frame.setLayout(null);

		label = new JLabel();
		label.setText("Management System");
		label.setBounds( 280, 0, 800, 50);
		frame.getContentPane().add(label);
		label.setFont(new Font("Courier",Font.BOLD,20));

		label1= new JLabel();
		label1.setText("Please choose the service you need!");
		label1.setBounds( 230, 50, 800, 50);
		frame.getContentPane().add(label1);
		label1.setFont(new Font("Courier",Font.PLAIN,20));

		button1 = new JButton("Registration");
		button2 = new JButton("Usage Report");
		button3 = new JButton("Pay for fine");
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		frame.getContentPane().add(button1);
		frame.getContentPane().add(button2);
		frame.getContentPane().add(button3);
		button1.setFont(new Font("Courier",Font.BOLD,20));
		button2.setFont(new Font("Courier",Font.BOLD,20));
		button3.setFont(new Font("Courier",Font.BOLD,20));
		button1.setBounds(300, 150, 200, 80);
		button2.setBounds(300, 270, 200, 80);
		button3.setBounds(300, 390, 200, 80);

		frame.setSize(800, 600);
		frame.setVisible(true);
	}

	/**
	 * Reaction to buttons <br>
	 * <p> 1. Registration <br>
	 *     2. Report <br>
	 *     3. Pay for fine <br>
	 * @param event pressed button
	 */
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == button1) {
			frame.dispose();
			registration = new ManageRegistration();
			registration.registrationGUI();
		}
		else if (event.getSource() == button2) {
			frame.dispose();
			report = new ManageReport();
			report.reportGUI();
		}
		else if (event.getSource() == button3) {
			frame.dispose();
			payFine = new ManagePayFine();
			payFine.payGUI();
		}
	}
}
