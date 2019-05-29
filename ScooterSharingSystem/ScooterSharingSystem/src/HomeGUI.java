import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Boundary Class: Home Page
 * <p>providing entries to 4 sub-pages: <br>
 * 1. Management <br>
 * 2. Station A <br>
 * 3. Station B <br>
 * 4. Station C <br>
 * @version 3.3
 * @author SE Group 101 (Taizhou QING, Haoran CUI, Jiayi PANG, Xi XIA, Yuanrong SHAO, Xi CUI)
 */
public class HomeGUI {

	JFrame frame = new JFrame();
	public List<StationGUI> stations ;
	public ManageGUI manage;

	public static void main(String[] args){
		HomeGUI gui = new HomeGUI();
		gui.homeSetUp();
	}

	/**
	 * Set up the Home Page GUI
	 */
	public void homeSetUp() {

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);

		JLabel label = new JLabel();
		label.setText("SCOOTER SHARING SYSTEM");
		label.setBounds( 280, 0, 800, 50);
		frame.getContentPane().add(label);
		label.setFont(new Font("Courier",Font.BOLD,20));

		JLabel label1= new JLabel();
		label1.setText("Please choose the function you need!");
		label1.setBounds( 230, 50, 800, 50);
		frame.getContentPane().add(label1);
		label1.setFont(new Font("Courier",Font.PLAIN,20));

		JButton button1 = new JButton("Management");
		JButton button2 = new JButton("Station  A");
		JButton button3 = new JButton("Station  B");
		JButton button4 = new JButton("Station  C");

		frame.getContentPane().add(button1);
		frame.getContentPane().add(button2);
		frame.getContentPane().add(button3);
		frame.getContentPane().add(button4);


		button1.setFont(new Font("Courier",Font.BOLD,20));
		button2.setFont(new Font("Courier",Font.BOLD,20));
		button3.setFont(new Font("Courier",Font.BOLD,20));
		button4.setFont(new Font("Courier",Font.BOLD,20));

		button1.setBounds(310, 150, 180, 80);
		button2.setBounds(310, 250, 180, 80);
		button3.setBounds(310, 350, 180, 80);
		button4.setBounds(310, 450, 180, 80);

		button1.addActionListener(new buttonListener1());
		button2.addActionListener(new buttonListener2());
		button3.addActionListener(new buttonListener3());
		button4.addActionListener(new buttonListener4());

		frame.setSize(800, 600);
		frame.setVisible(true);
	}


	/**
	 * Go to Management Page
	 */
	class buttonListener1 implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			//	frame.dispose();
			manage = new ManageGUI();
			manage.manageGUI();
		}

	}

	/**
	 * Go to StationA Page
	 */
	class buttonListener2 implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			//	frame.dispose();
			StationGUI stationA = new StationGUI("A");
			stationA.bulidGui();
			stations.add(stationA);
		}

	}

	/**
	 * Go to StationB Page
	 */
	class buttonListener3 implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			//	frame.dispose();
			StationGUI stationB = new StationGUI("B");
			stationB.bulidGui();
			stations.add(stationB);
		}

	}

	/**
	 * Go to StationC Page
	 */
	class buttonListener4 implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			//frame.dispose();
			StationGUI stationC = new StationGUI("C");
			stationC.bulidGui();
			stations.add(stationC);
		}

	}
}
