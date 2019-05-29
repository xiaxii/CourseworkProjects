import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Boundary Class<br>
 * Station GUI <br>
 * <p>Containing: <br>
 *     1. 8 slots <br>
 *     2. 2 functions: Pick Up and Return <br>
 * @version 3.3
 * @author SE Group 101 (Taizhou QING, Haoran CUI, Jiayi PANG, Xi XIA, Yuanrong SHAO, Xi CUI)
 */
public class StationGUI {

    private String stationNameAll;

    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JLabel label2 = new JLabel();
    JButton[] button = new JButton[10];
    JTextField textFieldID = new JTextField();

    StationGUI(String name) {
        stationNameAll = name;
    }

    public Station thisStation = new Station();

    //set a flag
    public volatile boolean exit = true;

    /**
     * Set up Station GUI
     */
    public void bulidGui() {

        thisStation.setStationName(stationNameAll);
        JFrame frame = new JFrame(stationNameAll);


        jPanel1.setLayout(new GridLayout(2, 1));
        jPanel2.setLayout(new GridLayout(2, 4));
        jPanel3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        jPanel1.setSize(800, 200);
        jPanel2.setSize(800, 200);
        jPanel3.setSize(800, 100);
        jPanel4.setSize(800, 100);


        label2.setSize(800, 100);


        JLabel label = new JLabel("Please have your id card scanned " +
                "for picking up or returning a scooter.", JLabel.CENTER);
        label.setSize(800, 150);
        label.setFont(new Font("Courier", Font.PLAIN, 16));

        JLabel label1 = new JLabel("Welcome", JLabel.CENTER);
        label1.setSize(800, 50);
        label1.setFont(new Font("Courier", Font.BOLD, 20));


        thisStation.setScooterNum(StationControl.getCurrentNum(stationNameAll));

        for (int i = 0; i < thisStation.getScooterNum(); i++) {
            button[i] = new JButton("scooter");
            button[i].setSize(150, 80);
        }

        for (int i = thisStation.getScooterNum(); i < 8; i++) {
            button[i] = new JButton("Empty");
            button[i].setSize(150, 80);
            button[i].setForeground(Color.red);
        }


        for (int i = 0; i < 8; i++) {
            button[i].setFont(new Font("Courier", Font.BOLD, 25));
            jPanel2.add(button[i]);

        }


        button[8] = new JButton("Pick up");
        button[9] = new JButton("Return");

        button[8].setPreferredSize(new Dimension(150, 80));
        button[9].setPreferredSize(new Dimension(150, 80));


        textFieldID.setPreferredSize(new Dimension(300, 80));


        jPanel1.add(label1);
        jPanel1.add(label);

        jPanel3.add(textFieldID);
        jPanel3.add(button[8]);
        jPanel3.add(button[9]);

        jPanel4.add(label2);

        frame.setSize(800, 600);
        frame.setLayout(new GridLayout(4, 1));


        frame.add(jPanel1);
        frame.add(jPanel2);
        frame.add(jPanel3);
        frame.add(jPanel4);

        frame.setVisible(true);

        //add actionlistener for button "pick up"
        button[8].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textFieldID.getText().equals("")){
                    label2.setText("Please enter your ID.");
                }else{
                    int temp = StationControl.getCurrentNum(stationNameAll);
                    if (temp == 0) {
                        label2.setText("There is no enough scooter");
                    } else
                        switch (StationControl.readID(textFieldID.getText(), 1)) {
                        case 1:
                            Thread thread = new Thread(new PickUp());
                            thread.start();
                            break;
                        case 2:
                            label2.setText("You haven't resisted");
                            break;
                        case 3:
                            label2.setText("You haven't paid the fine");
                            break;
                        case 4:
                            label2.setText("You haven't returned a scooter");
                            break;
                        default:
                            break;
                        }
                }
            }
        });

        //add actionlistener for button "return"
        button[9].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textFieldID.getText().equals("")){
                    label2.setText("Please enter your ID.");
                }else{
                    int temp = StationControl.getCurrentNum(stationNameAll);
                    if (temp == 8)
                        label2.setText("There is no enough slot");
                    else
                        switch (StationControl.readID(textFieldID.getText(), 2)) {
                        case 1:
                            Thread thread = new Thread(new Return());
                            thread.start();
                            break;
                        case 2:
                            label2.setText("You haven't resisted");
                            break;
                        case 5:
                            label2.setText("Please pick up first");
                            break;
                        default:
                            break;
                        }
                }
            }
        });
    }


    /**
     * Reaction to PickUp button
     */
    class PickUp implements Runnable {
        @Override
        public void run() {
            exit = true;
            long time1;
            long time2;
            long d1;
            long d2 = 0;

            time1 = System.currentTimeMillis();
            PickupOperation pickupS = new PickupOperation();
            button[thisStation.getScooterNum() - 1].addActionListener(pickupS);
            while (exit) {
                time2 = System.currentTimeMillis();
                d1 = time2 - time1;
                if (d1 % 1000 == 0 & d2 != d1) {
                    label2.setText("Pick the scooter at slot in " + (60 - d2 / 1000 - 1) + " seconds");
                    if ((60 - d2 / 1000) % 2 == 0)
                        button[thisStation.getScooterNum() - 1].setForeground(Color.red);
                    else
                        button[thisStation.getScooterNum() - 1].setForeground(Color.black);
                    d2 = d1;
                }
                if (d1 > 60000) {
                    Thread thread = new Thread(new Fail());
                    thread.start();
                    break;
                }
            }
            button[thisStation.getScooterNum() - 1].removeActionListener(pickupS);
        }
    }

    /**
     * Feedback when out of time
     */
    class Fail implements Runnable {

        @Override
        public void run() {
            label2.setText("Out of time, Operate again");
            long time1 = System.currentTimeMillis();
            long time2;
            while (true) {
                time2 = System.currentTimeMillis();
                if (time2 - time1 > 3000)
                    break;
            }
            label2.setText("");
            textFieldID.setText("");
        }
    }

    /**
     * Reaction to Return button
     */
    class Return implements Runnable {

        @Override
        public void run() {
            exit = true;
            long time1;
            long time2;
            long d1;
            long d2 = 0;
            ReturnOperation returns = new ReturnOperation();
            button[thisStation.getScooterNum()].addActionListener(returns);
            time1 = System.currentTimeMillis();
            while (exit) {
                time2 = System.currentTimeMillis();
                d1 = time2 - time1;
                if (d1 % 1000 == 0 & d2 != d1) {
                    label2.setText("Return the scooter at slot in " + (60 - d2 / 1000 - 1) + " seconds");
                    if ((60 - d2 / 1000) % 2 == 0)
                        button[thisStation.getScooterNum()].setForeground(Color.black);
                    else
                        button[thisStation.getScooterNum()].setForeground(Color.red);
                    d2 = d1;

                }
                if (d1 > 60000) {
                    Thread thread = new Thread(new Fail());
                    thread.start();
                    break;
                }
            }

            button[thisStation.getScooterNum()].removeActionListener(returns);
        }

    }

    /**
     * Blink when returning
     */
    class ReturnOperation implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            exit = false;
            button[thisStation.getScooterNum()].setText("scooter");
            button[thisStation.getScooterNum()].setForeground(Color.black);
            Thread thread = new Thread(new Returninfo());
            thread.start();
            StationControl.returnScooter(textFieldID.getText(), stationNameAll);
            thisStation.setScooterNum(thisStation.getScooterNum() + 1);
        }
    }

    /**
     * Feedback of successful Return
     */
    class Returninfo implements Runnable {

        @Override
        public void run() {
            label2.setText("You have returned successfully");
            label2.setForeground(Color.red);
            long time1 = System.currentTimeMillis();
            long time2;
            while (true) {
                time2 = System.currentTimeMillis();
                if (time2 - time1 > 3100)
                    break;
            }
            label2.setText("");
            textFieldID.setText("");

        }
    }

    /**
     * Feedback of successful PickUp
     */
    class PickUpinfo implements Runnable {

        @Override
        public void run() {
            label2.setText("You have picked up successfully");
            long time1 = System.currentTimeMillis();
            long time2;
            while (true) {
                time2 = System.currentTimeMillis();
                if (time2 - time1 > 2000)
                    break;
            }
            label2.setText("");
            textFieldID.setText("");

        }
    }

    /**
     * Blink when picking up
     */
    class PickupOperation implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            exit = false;
            button[thisStation.getScooterNum() - 1].setText("Empty");
            button[thisStation.getScooterNum() - 1].setForeground(Color.red);
            Thread thread = new Thread(new PickUpinfo());
            thread.start();
            StationControl.pickUpScooter(textFieldID.getText(), stationNameAll);
            thisStation.setScooterNum(thisStation.getScooterNum() - 1);
        }
    }
}

