/**
 * Title                 : MathTeacher.java<br>
 * Description           : This class gives a basic maths teacher program with a graphical user interface (GUI).<br>
 *
 * @author : Xi Xia
 * @QM_Student_Number : 161187917
 * @BUTP_Student_Number : 2016213482
 * @BUPT_Class_Number : 2016215117
 * @version : 1.0-- 2018.06.01
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MathTeacher {
    public final static JFrame parentFrame = new JFrame("Math Teacher");
    public static JPanel textPanel = new JPanel();
    public static JPanel feedbackPanel = new JPanel();
    public static JPanel buttonPanel = new JPanel();
    public static JLabel qLabel = new JLabel("");
    public static TextField answerField = new TextField(8);
    public static Button press = new Button("Press for answer");
    public static JTextArea feedbackField = new JTextArea(5, 28);
    // record the correct answer of 'this' random question
    public static int correctAnswer;
    public static int abstractLength = 0;
    public boolean negativeAnswer = false;

    /**
     * Extension 2: record the accuracy as "numOfCorrectA correct out numOfQuestions"<br>
     */
    private static int numOfQuestions = 0;
    private static int numOfCorrectA = 0;

    /**
     * Constructor: MathTeacher<br>
     * Description: GUI setting and corresponding actionListener<br>
     */

    public MathTeacher() {
        /**
         * FRAME settings<br>
         * Layout: BorderLayout<br>
         * containing: panel1-- textPanel<br>
         *             panel2-- feedbackPanel<br>
         *             panel3-- buttonPanel<br>
         */
        parentFrame.setSize(400, 300);
        //set the frame in center
        parentFrame.setLocationRelativeTo(null);
        parentFrame.setLayout(new BorderLayout());

        /**
         * Panel1: textPanel settings<br>
         * layout: FlowLayout<br>
         * containing: a randomly generated question<br>
         *             a textField for inputting<br>
         *             a press button to check answers<br>
         */
        textPanel.setLayout(new FlowLayout());
        textPanel.add(qLabel);
        randomQuestion();
        answerField.addKeyListener(new KeyListener() {
            /**
             * task 2: improvement on task 1<br>
             *         ensure the user is only allowed to supply valid input values<br>
             */
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            /**
             * only integers 0-9 and minus signal '-' are allowed to be typed in<br>
             * limit the length and max value, i.e. answer is between -9 and 100<br>
             */
            public void keyTyped(KeyEvent e) {
                char keyIn = e.getKeyChar();
                //Case: KeyIn is '-'
                if (keyIn == '-') {
                    if (abstractLength == 0) {
                        negativeAnswer = true;
                    } else {
                        invalid();
                        e.consume();
                    }
                }
                //Case: KeyIn is other characters except for integer 0-9 and '-'
                else if ((keyIn < KeyEvent.VK_0 || keyIn > KeyEvent.VK_9)) {
                    invalid();
                    e.consume();
                }
                //Case: KeyIn is integer 0-9
                else {
                    if (negativeAnswer) {
                        //Case: existing answerField is an integer between -9 and 0
                        if (abstractLength != 0) {
                            invalid();
                            e.consume();
                        }
                        //Case: existing answerField is only a negative symbol
                        else
                            abstractLength++;
                    }
                    //Case: answerField is 10-99
                    else if (abstractLength >= 2) {
                        int existingNum = Integer.valueOf(answerField.getText());
                        //only the final answer 100 is valid
                        if (!(existingNum == 10 && keyIn == KeyEvent.VK_0)) {
                            invalid();
                            e.consume();
                        }
                    } else
                        abstractLength++;
                }

            }
        });
        textPanel.add(answerField);

        press.addActionListener(e -> {
            if (!answerField.getText().equals("")) {
                feedbackField.setText("");
                boolean check = checkAnswer();
                feedbackField.setText("Correct answer: " + qLabel.getText() + correctAnswer + "\n");
                feedbackField.append("You are ");
                if (check) {
                    feedbackField.append("right.\n");
                } else {
                    feedbackField.append("wrong\n");
                }
                feedbackField.append("Accuracy: " + numOfCorrectA + " correct out of " + numOfQuestions + "\n");
                feedbackField.append("You can click the button 'Clear' to cancel all your input and clear this feedback page.");
                refresh();
                randomQuestion();
            }
        });
        textPanel.add(press);

        /**
         * Panel2: feedbackPanel settings<br>
         * Layout: default(FlowLayout)<br>
         * Containing: a feedback text area, giving<br>
         *             - The correct answer of the last question<br>
         *             - Whether the student's answer is right<br>
         *             - Accuracy: The number of correct answers & all the questions<br>
         *             - an instruction for button "Clear"<br>
         */
        feedbackField.setLineWrap(true);
        feedbackField.setWrapStyleWord(true);
        feedbackPanel.add(feedbackField);

        /**
         * Panel3 (Extension 3): buttonPanel settings<br>
         * Layout: GridLayout<br>
         * Containing: 10 number buttons<br>
         *              1 "Clear" button -- cleans up both the answerField and the feedbackField<br>
         *              1 Minus button -- for negative numbers<br>
         */
        buttonPanel.setLayout(new GridLayout(4, 3));
        String[] buttonText = {"9", "8", "7", "6", "5", "4", "3", "2", "1", "0", "-", "Clear"};
        JButton[] buttons = new JButton[buttonText.length];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            buttons[i].setText(buttonText[i]);
            buttonPanel.add(buttons[i]);
            JButton thisButton = buttons[i];
            /**
             * Extension 3: offers a “calculator key pad” with the digits 0-9
             */
            thisButton.addActionListener(e -> {
                switch (thisButton.getText()) {
                    case "-":
                        if (answerField.getText().equals("")) {
                            answerField.setText("-");
                            negativeAnswer = true;
                        } else
                            invalid();
                        break;
                    case "Clear":
                        feedbackField.setText("");
                        refresh();
                        break;
                    default:
                        String buttonNum = thisButton.getText();
                        if (abstractLength < 2) {
                            String num = answerField.getText();
                            //case: -9 to 99
                            if ((abstractLength == 0 && negativeAnswer) || (!negativeAnswer)) {
                                num = num.concat(buttonNum);
                                answerField.setText(num);
                                abstractLength++;
                            } else
                                invalid();
                        }
                        //Case: 100
                        else if (abstractLength == 2 && Integer.valueOf(answerField.getText()) == 10) {
                            JButton tempButton = (JButton) e.getSource();
                            String temp = tempButton.getText();
                            if (temp.equals("0")) {
                                //existing number is 10, except for 0, no other input is allowed<br>
                                String num = answerField.getText();
                                num = num.concat(buttonNum);
                                answerField.setText(num);
                                abstractLength++;
                            } else
                                invalid();
                        } else
                            invalid();
                        break;
                }
            });
        }

        /**
         * adds three panels to the frame<br>
         */
        textPanel.setPreferredSize(new Dimension(0, 35));
        parentFrame.add(textPanel, BorderLayout.PAGE_START);
        feedbackPanel.setPreferredSize(new Dimension(0, 150));
        parentFrame.add(feedbackPanel, BorderLayout.CENTER);
        buttonPanel.setPreferredSize(new Dimension(0, 150));
        parentFrame.add(buttonPanel, BorderLayout.PAGE_END);
        parentFrame.setVisible(true);
        parentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Method Name: ramdomQuestion<br>
     * Function: Randomly generates a question and set it as a label displayed in panel1-- the textPanel<br>
     */

    public void randomQuestion() {
        int argument1 = (int) (1 + Math.random() * 10);
        int argument2 = (int) (1 + Math.random() * 10);
        char arithOperation = ' ';
        String ranDomQ;
        int ramdomOperation = (int) (Math.random() * 4);
        switch (ramdomOperation) {
            case 0:
                arithOperation = '+';
                correctAnswer = argument1 + argument2;
                break;
            case 1:
                arithOperation = '-';
                correctAnswer = argument1 - argument2;
                break;
            case 2:
                arithOperation = 'x';
                correctAnswer = argument1 * argument2;
                break;
            case 3:
                /**
                 * Extension 1: makes argument 1 divisible by argument 2
                 */
                arithOperation = '/';
                while (argument2 == 0) {
                    argument2 = (int) (Math.random() * 10);
                }
                correctAnswer = argument1;
                argument1 = argument1 * argument2;
                break;
            default:
                break;
        }
        //join argument1, arithmetic operations and argument2 to be a random question
        ranDomQ = String.valueOf(argument1) + " " + arithOperation + " " + String.valueOf(argument2) + " = ";
        qLabel.setText(ranDomQ);
        numOfQuestions++;
    }

    /**
     * Method Name: checkAnswer<br>
     * Function: check whether the student's answer is right<br>
     *
     * @return the correctness of the student's answer<br>
     */

    public boolean checkAnswer() {
        String answerString = answerField.getText();
        if (answerString.equals(String.valueOf(correctAnswer))) {
            if (feedbackField.getText().equals("")) {
                // it is a new question, i.e. NOT aswering the same quesion
                numOfCorrectA++;
            }
            return true;
        } else
            return false;
    }

    /**
     * Method Name: invalid<br>
     * Function: inform the user of their incorrect input and go back to the primary state<br>
     */

    public void invalid() {
        feedbackField.setText("Invalid input, press the 'Clear' button and try again please.");
        refresh();
    }

    /**
     * Method Name: refresh<br>
     * Function: set answerField, abstractLength and negativeAnswer to primary default value
     */

    public void refresh(){
        answerField.setText("");
        abstractLength = 0;
        negativeAnswer = false;
    }

    /**
     * Main method: launch the application<br>
     * - Give a welcome message using a Dialog window<br>
     * - New a MathTeacher class<br>
     * - Show MathTeacher GUI after pressing the confirmation button in the dialog window<br>
     *
     * @param args null
     */

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welcome to practice MATH!");
        MathTeacher mathT = new MathTeacher();
    }
}
