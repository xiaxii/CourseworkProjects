
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;


public class GUI_BankSystem {
    public final static JFrame BankSystemFrame = new JFrame("A Simple Bank System");

    public static JPanel SystemIntroPanel = new JPanel();
    public static JPanel buttonPanel = new JPanel();


    // Horizontal Align: left
    public static JLabel SystemIntroLabel = new JLabel("", SwingConstants.LEFT);

    public GUI_BankSystem() {
        SystemIntroPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        /**
         * FRAME settings<br>
         * Layout: BorderLayout<br>
         * containing: panel1-- SystemIntroPanel<br>
         *             panel2-- buttonPanel<br>
         */
        BankSystemFrame.setSize(400, 320);
        //set the BankSystemFrame in center
        BankSystemFrame.setLocationRelativeTo(null);
        BankSystemFrame.setLayout(new BorderLayout());

        /**
         * Panel1: SystemIntroPanel settings<br>
         * layout: FlowLayout<br>
         * containing: hint of 6 functions <br>
         */
        SystemIntroPanel.setLayout(new FlowLayout());
        SystemIntroPanel.add(SystemIntroLabel);
        SystemIntroLabel.setText("<html><body>Before using this system, <br>" +
                "make sure that you are one of the registered customer: <br>" +
                "Peter, London, 1993-02-23, unsatisfactory credit <br>" +
                "Xi, Beijing, 1998-08-09, satisfactory credit <br>" +
                "Mary, New York, 2008-10-02, satisfactory credit <br>" +
                "<br>" +
                "Here are 6 functions in our bank system.<br>" +
                "Please press the button below to choose a function.</body></html>");


        /**
         * Panel2: buttonPanel settings<br>
         * Layout: GridLayout<br>
         * Containing: 6 function buttons<br>
         */
        buttonPanel.setLayout(new GridLayout(3, 2));
        String[] buttonText = {"Open Account", "Deposit Funds", "Clear Funds", "Withdraw Funds", "Suspend Account", "Close Account"};
        JButton[] buttons = new JButton[buttonText.length];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            buttons[i].setText(buttonText[i]);
            buttonPanel.add(buttons[i]);
            JButton thisButton = buttons[i];

            thisButton.addActionListener(e -> {
                switch (thisButton.getText()) {
                case "Open Account":
                    BankSystemFrame.setVisible(false);
                    GUI_OpenAccount();
                    break;
                case "Deposit Funds":
                    BankSystemFrame.setVisible(false);
                    GUI_DepositFunds();
                    break;
                case "Clear Funds":
                    BankSystemFrame.setVisible(false);
                    GUI_ClearFunds();
                    break;
                case "Withdraw Funds":
                    BankSystemFrame.setVisible(false);
                    GUI_WithdrawFunds();
                    break;
                case "Suspend Account":
                    BankSystemFrame.setVisible(false);
                    GUI_SuspendAccount();
                    break;
                case "Close Account":
                    BankSystemFrame.setVisible(false);
                    GUI_CloseAccount();
                    break;
                default:
                    break;
                }
            });
        }

        /**
         * add two panels to the BankSystemFrame<br>
         */
        SystemIntroPanel.setPreferredSize(new Dimension(350, 150));
        BankSystemFrame.add(SystemIntroPanel, BorderLayout.PAGE_START);
        buttonPanel.setPreferredSize(new Dimension(350, 150));
        BankSystemFrame.add(buttonPanel, BorderLayout.PAGE_END);
        BankSystemFrame.setVisible(true);

        BankSystemFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Function1. Open Account
     */
    public void GUI_OpenAccount() {

        JFrame OpenAccountFrame = new JFrame("Open Account");

        /**
         * Panel1 of OpenAccountFrame: InfoPanel
         * Get information for opening a new account
         */
        JPanel InfoPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        InfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JLabel askInputLabel = new JLabel();
        JLabel nameLabel = new JLabel();
        JLabel addressLabel = new JLabel();
        JLabel birthLabel = new JLabel();
        JLabel typeLabel = new JLabel();

        JTextField nameTextField = new JTextField(20);
        JTextField addressTextField = new JTextField(20);
        JTextField birthTextField = new JTextField(20);
        JComboBox<String> accTypeCombo = new JComboBox<>();
        JButton back = new JButton();
        JButton open = new JButton();

        OpenAccountFrame.setSize(320, 300);
        //set the Frame in center
        OpenAccountFrame.setLocationRelativeTo(null);

        InfoPanel.setLayout(new GridLayout(9, 1));
        InfoPanel.add(askInputLabel);
        askInputLabel.setText("Please enter your information below.");
        /**
         * 4 information required for opening an account:
         * - name
         * - address
         * - birthday
         * - type of the account
         */
        InfoPanel.add(nameLabel);
        InfoPanel.add(nameTextField);
        nameLabel.setText("Name");

        InfoPanel.add(addressLabel);
        InfoPanel.add(addressTextField);
        addressLabel.setText("Address");

        InfoPanel.add(birthLabel);
        InfoPanel.add(birthTextField);
        birthLabel.setText("Birthday (e.g. 1998-08-09)");

        InfoPanel.add(typeLabel);
        InfoPanel.add(accTypeCombo);
        typeLabel.setText("Account Type (please choose from the menu)");
        accTypeCombo.addItem("Saver");
        accTypeCombo.addItem("Junior");
        accTypeCombo.addItem("Current");

        /**
         * Panel2 of OpenAccountFrame: buttonPanel
         * two button:
         * - Back: go back to bank system home page
         * - Open Account
         */
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(back);
        back.setText("Back");
        buttonPanel.add(open);
        open.setText("Open Account");

        back.addActionListener(e -> {
            OpenAccountFrame.dispose();
            BankSystemFrame.setVisible(true);
        });

        open.addActionListener(e -> {
            Customer thisCustomer = new Customer(nameTextField.getText(), addressTextField.getText(), birthTextField.getText());
            if (thisCustomer.isCreditSatisfactory()) {
                String name = nameTextField.getText();
                String address = addressTextField.getText();
                String birth = birthTextField.getText();
                int accID = 0;
                String accType = accTypeCombo.getItemAt(accTypeCombo.getSelectedIndex());

                switch (accType) {
                case "Saver":
                    Account_Saver newSaverAcc = new Account_Saver();
                    accID = newSaverAcc.openSaverAccount(birth);
                    newSaverAcc.writeAccFinder(name, address, birth, accID);
                    newSaverAcc.writeAcc();
                    successOperation();
                    break;
                case "Junior":
                    /**
                     * Only customers under the age of 16 may open a Junior account.
                     * @see Customer#isAgeUnder16(java.lang.String)
                     */
                    Customer verifyAge = new Customer();
                    if (verifyAge.isAgeUnder16(birth)) {
                        Account_Junior newJuniorAcc = new Account_Junior();
                        accID = newJuniorAcc.openJuniorAccount(birth);
                        newJuniorAcc.writeAccFinder(name, address, birth, accID);
                        newJuniorAcc.writeAcc();
                        successOperation();
                    } else {
                        invalidInput(); //age is larger that 16
                    }
                    break;
                case "Current":
                    Account_Current newCurrentAcc = new Account_Current();
                    accID = newCurrentAcc.openCurrentAccount(birth);
                    /**
                     * all current account's overdraft limit is set as 2000
                     * @see Account_Current#openCurrentAccount(java.lang.String)
                     */
                    newCurrentAcc.writeAccFinder(name, address, birth, accID);
                    newCurrentAcc.writeAcc();
                    successOperation();
                    break;
                default:
                    invalidInput(); // not an appropriate type
                    break;

                }
            } else {
                invalidInput(); // Customer is not registered
            }
            nameTextField.setText("");
            addressTextField.setText("");
            birthTextField.setText("");
        });

        /**
         * adds two panels to the OpenAccountFrame<br>
         */
        InfoPanel.setPreferredSize(new Dimension(0, 220));
        OpenAccountFrame.add(InfoPanel, BorderLayout.PAGE_START);
        buttonPanel.setPreferredSize(new Dimension(0, 50));
        OpenAccountFrame.add(buttonPanel, BorderLayout.PAGE_END);
        OpenAccountFrame.setVisible(true);
        OpenAccountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    /**
     * Function2. Deposit Funds
     */
    public void GUI_DepositFunds() {
        JFrame DepositFundsFrame = new JFrame("Deposit Funds");

        /**
         * Panel1 of DepositFundsFrame: InfoPanel
         * Get information for Depositing Funds
         */
        JPanel InfoPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        InfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JLabel askInputLabel = new JLabel();
        JLabel accIDLabel = new JLabel();
        JLabel clearModeLabel = new JLabel();
        JLabel amountLabel = new JLabel();

        JTextField accIDTextField = new JTextField(20);
        JComboBox<String> clearModeCombo = new JComboBox<>();
        JTextField amountTextField = new JTextField(20);
        JButton back = new JButton();
        JButton deposit = new JButton();

        DepositFundsFrame.setSize(320, 280);
        //set the Frame in center
        DepositFundsFrame.setLocationRelativeTo(null);

        InfoPanel.setLayout(new GridLayout(7, 1));
        InfoPanel.add(askInputLabel);
        askInputLabel.setText("Please enter your information below.");
        /**
         * 3 information required for Depositing Funds:
         * - accID
         * - clearing mode
         * - deposit amount
         */
        InfoPanel.add(accIDLabel);
        InfoPanel.add(accIDTextField);
        accIDLabel.setText("accID");

        InfoPanel.add(clearModeLabel);
        InfoPanel.add(clearModeCombo);
        clearModeLabel.setText("Clearing mode (please choose from the menu)");
        clearModeCombo.addItem("Fully cleared");
        clearModeCombo.addItem("Uncleared");

        InfoPanel.add(amountLabel);
        InfoPanel.add(amountTextField);
        amountLabel.setText("Deposit amount");

        /**
         * Panel2 of DepositFundsFrame: buttonPanel
         * two button:
         * - Back: go back to bank system home page
         * - Deposit Funds
         */
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(back);
        back.setText("Back");
//        buttonPanel.add(notice);
//        notice.setText("Notice");
        buttonPanel.add(deposit);
        deposit.setText("Deposit Funds");


        back.addActionListener(e -> {
            DepositFundsFrame.dispose();
            BankSystemFrame.setVisible(true);
        });

//        notice.addActionListener(e -> {
//            int accID = Integer.valueOf(accIDTextField.getText());
//            Account noticeAcc = new Account();
//            noticeAcc.notice(accID);
//        });

        deposit.addActionListener(e -> {
            if (accIDTextField.getText().equals("") || amountTextField.getText().equals("") ||
                    clearModeCombo.getItemAt(clearModeCombo.getSelectedIndex()).equals("")) {
                invalidInput(); // empty input
            } else {
                int accID = Integer.valueOf(accIDTextField.getText());
                int amount = Integer.valueOf(amountTextField.getText());
                String clearMode = clearModeCombo.getItemAt(clearModeCombo.getSelectedIndex());
                System.out.println("get deposit command: " + accID + ", " + clearMode + ", " + amount);
                boolean successFlag = false;
                // deposit
                Account modifyAcc = new Account();
                if (modifyAcc.isAccActive(accID)) {
                    successFlag = modifyAcc.depositFunds(accID, amount, clearMode);
                    if (!successFlag) {
                        invalidInput();
                    } else {
                        successOperation();
                    }
                    // clean textFeild
                    accIDTextField.setText("");
                    accIDTextField.setText("");
                } else {
                    invalidInput(); // no active account with provided accID
                }
            }
        });

        /**
         * adds two panels to the DepositFundsFrame<br>
         */
        InfoPanel.setPreferredSize(new Dimension(0, 200));
        DepositFundsFrame.add(InfoPanel, BorderLayout.PAGE_START);
        buttonPanel.setPreferredSize(new Dimension(0, 50));
        DepositFundsFrame.add(buttonPanel, BorderLayout.PAGE_END);
        DepositFundsFrame.setVisible(true);
        DepositFundsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Function3. Clear Funds
     */
    public void GUI_ClearFunds() {
        JFrame ClearFundsFrame = new JFrame("Clear Funds");

        /**
         * Panel1 of ClearFundsFrame: InfoPanel
         * Get information for Clearing Funds
         */
        JPanel InfoPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        InfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JLabel askInputLabel = new JLabel();
        JLabel accIDLabel = new JLabel();
        JLabel PINLabel = new JLabel();

        JTextField accIDField = new JTextField();
        JPasswordField PINField = new JPasswordField();
        PINField.setEchoChar('*');

        JButton back = new JButton();
        JButton clear = new JButton();

        ClearFundsFrame.setSize(250, 200);
        //set the BankSystemFrame in center
        ClearFundsFrame.setLocationRelativeTo(null);

        InfoPanel.setLayout(new GridLayout(5, 1));
        InfoPanel.add(askInputLabel);
        askInputLabel.setText("Please enter your information below.");
        /**
         * 2 information required for Clearing Funds:
         * - accID
         * - PIN
         */
        InfoPanel.add(accIDLabel);
        InfoPanel.add(accIDField);
        accIDLabel.setText("accID");

        InfoPanel.add(PINLabel);
        InfoPanel.add(PINField);
        PINLabel.setText("PIN");

        /**
         * Panel2 of ClearFundsFrame: buttonPanel
         * two button:
         * - Back: go back to bank system home page
         * - Clear Funds
         */
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(back);
        back.setText("Back");
        buttonPanel.add(clear);
        clear.setText("Clear Funds");

        back.addActionListener(e -> {
            ClearFundsFrame.dispose();
            BankSystemFrame.setVisible(true);
        });

        clear.addActionListener(e -> {
            String providedAccID = accIDField.getText();
            String providedPIN = new String(PINField.getPassword());
            if (providedAccID.equals("") || providedPIN.equals("")) {
                invalidInput(); // empty input
            } else {
                boolean isVerified = false;
                boolean isCleared = false;
                Account VerifyIDnPIN = new Account();
                isVerified = VerifyIDnPIN.accIDnPINVerify(providedAccID, providedPIN);
                if (isVerified) {
                    isCleared = VerifyIDnPIN.clearFunds(providedAccID);
                    if (isCleared) {
                        successOperation(); // Funds are cleared
                    }
                } else {
                    invalidInput(); //not verified
                }
            }
            accIDField.setText("");
            PINField.setText("");
        });

        /**
         * adds two panels to the ClearFundsFrame<br>
         */
        InfoPanel.setPreferredSize(new Dimension(0, 120));
        ClearFundsFrame.add(InfoPanel, BorderLayout.PAGE_START);
        buttonPanel.setPreferredSize(new Dimension(0, 40));
        ClearFundsFrame.add(buttonPanel, BorderLayout.PAGE_END);
        ClearFundsFrame.setVisible(true);
        ClearFundsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Function4. Withdraw Funds
     */
    public void GUI_WithdrawFunds() {

        JFrame WithdrawFundsFrame = new JFrame("Withdraw Funds");

        /**
         * Panel1 of WithdrawFundsFrame: InfoPanel
         * Get information for Withdrawing Funds
         */
        JPanel InfoPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        InfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JLabel askInputLabel = new JLabel();
        JLabel accIDLabel = new JLabel();
        JLabel amountLabel = new JLabel();
        JLabel PINLabel = new JLabel();

        JTextField accIDTextField = new JTextField(20);
        JTextField amountTextField = new JTextField(20);
        JPasswordField PINField = new JPasswordField();
        PINField.setEchoChar('*');

        JButton back = new JButton();
        JButton withdraw = new JButton();
        JButton notice = new JButton();

        WithdrawFundsFrame.setSize(350, 280);
        //set the BankSystemFrame in center
        WithdrawFundsFrame.setLocationRelativeTo(null);

        InfoPanel.setLayout(new GridLayout(7, 1));
        InfoPanel.add(askInputLabel);
        askInputLabel.setText("Please enter your information below.");
        /**
         * 3 information required for Withdrawing Funds:
         * - accID
         * - clearing mode
         * - withdraw amount
         */
        InfoPanel.add(accIDLabel);
        InfoPanel.add(accIDTextField);
        accIDLabel.setText("accID");

        InfoPanel.add(amountLabel);
        InfoPanel.add(amountTextField);
        amountLabel.setText("Withdraw Funds");

        InfoPanel.add(PINLabel);
        InfoPanel.add(PINField);
        PINLabel.setText("PIN");

        /**
         * Panel2 of WithdrawFundsFrame: buttonPanel
         * two button:
         * - Back: go back to bank system home page
         * - Withdraw Funds
         */
        buttonPanel.setLayout(new GridLayout(1, 3));
        buttonPanel.add(back);
        back.setText("Back");
        buttonPanel.add(notice);
        notice.setText("Notice");
        buttonPanel.add(withdraw);
        withdraw.setText("Withdraw Funds");


        back.addActionListener(e -> {
            WithdrawFundsFrame.dispose();
            BankSystemFrame.setVisible(true);
        });

        notice.addActionListener(e -> {
            String accID = accIDTextField.getText();
            String PIN = new String(PINField.getPassword());
            Account noticeAcc = new Account();
            boolean isVerified = noticeAcc.accIDnPINVerify(accID, PIN);
            if (isVerified) {
                boolean isNoticed = noticeAcc.notice(accID);
                if (isNoticed) {
                    successOperation(); // verified and noticed
                } else {
                    invalidInput(); // not noticed
                }
            }
            else {
                invalidInput(); // not pass verification
            }
            accIDTextField.setText("");
            amountTextField.setText("");
            Arrays.fill(PINField.getPassword(), ' ');
        });

        withdraw.addActionListener(e -> {
            String accID = accIDTextField.getText();
            String PIN = new String(PINField.getPassword());
            Account withdrawAcc = new Account();
            boolean isVerified = withdrawAcc.accIDnPINVerify(accID, PIN);
            if(isVerified){
                boolean isWithdrawn = false;
                double withdrawAmount = Double.valueOf(amountTextField.getText());
                isWithdrawn = withdrawAcc.withdrawFunds(accID,withdrawAmount);
                if(isWithdrawn){
                    successOperation(); // verified and withdrawn
                }else {
                    invalidInput(); // not withdrawn
                }
            }else {
                invalidInput(); // not pass verification
            }
            accIDTextField.setText("");
            amountTextField.setText("");
            Arrays.fill(PINField.getPassword(), ' ');
        });

        /**
         * adds two panels to the WithdrawFundsFrame<br>
         */
        InfoPanel.setPreferredSize(new Dimension(0, 200));
        WithdrawFundsFrame.add(InfoPanel, BorderLayout.PAGE_START);
        buttonPanel.setPreferredSize(new Dimension(0, 50));
        WithdrawFundsFrame.add(buttonPanel, BorderLayout.PAGE_END);
        WithdrawFundsFrame.setVisible(true);
        WithdrawFundsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /**
     * Function5. Suspend Account
     */
    public void GUI_SuspendAccount() {
        JFrame SuspendAccountFrame = new JFrame("Suspend Account");

        /**
         * Panel1 of SuspendAccountFrame: InfoPanel
         * Get information for Suspending Account
         */
        JPanel InfoPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        InfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JLabel askInputLabel = new JLabel();
        JLabel accIDLabel = new JLabel();
        JLabel PINLabel = new JLabel();

        JTextField accIDField = new JTextField();
        JPasswordField PINField = new JPasswordField();
        PINField.setEchoChar('*');

        JButton back = new JButton();
        JButton clear = new JButton();

        SuspendAccountFrame.setSize(300, 200);
        //set the BankSystemFrame in center
        SuspendAccountFrame.setLocationRelativeTo(null);

        InfoPanel.setLayout(new GridLayout(5, 1));
        InfoPanel.add(askInputLabel);
        askInputLabel.setText("Please enter your information below.");
        /**
         * 2 information required for Suspending Account:
         * - accID
         * - PIN
         */
        InfoPanel.add(accIDLabel);
        InfoPanel.add(accIDField);
        accIDLabel.setText("accID");

        InfoPanel.add(PINLabel);
        InfoPanel.add(PINField);
        PINLabel.setText("PIN");

        /**
         * Panel2 of SuspendAccountFrame: buttonPanel
         * two button:
         * - Back: go back to bank system home page
         * - Suspend Account
         */
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(back);
        back.setText("Back");
        buttonPanel.add(clear);
        clear.setText("Suspend Account");

        back.addActionListener(e -> {
            SuspendAccountFrame.dispose();
            BankSystemFrame.setVisible(true);
        });

        clear.addActionListener(e -> {
            String providedAccID = accIDField.getText();
            String providedPIN = new String(PINField.getPassword());
            if (providedAccID.equals("") || providedPIN.equals("")) {
                invalidInput(); // empty input
            } else {
                boolean isVerified = false;
                boolean isSuspendeded = false;
                Account VerifyIDnPIN = new Account();
                isVerified = VerifyIDnPIN.accIDnPINVerify(providedAccID, providedPIN);
                if (isVerified) {
                    isSuspendeded = VerifyIDnPIN.suspendAccount(providedAccID);
                    if (isSuspendeded) {
                        successOperation(); // Funds are cleared
                    }
                } else {
                    invalidInput(); //not verified
                }
            }
            accIDField.setText("");
            PINField.setText("");
        });

        /**
         * adds two panels to the SuspendAccountFrame<br>
         */
        InfoPanel.setPreferredSize(new Dimension(0, 120));
        SuspendAccountFrame.add(InfoPanel, BorderLayout.PAGE_START);
        buttonPanel.setPreferredSize(new Dimension(0, 40));
        SuspendAccountFrame.add(buttonPanel, BorderLayout.PAGE_END);
        SuspendAccountFrame.setVisible(true);
        SuspendAccountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Function6. Close Account
     */
    public void GUI_CloseAccount() {
        JFrame CloseAccountFrame = new JFrame("Close Account");

        /**
         * Panel1 of CloseAccountFrame: InfoPanel
         * Get information for Closing Account
         */
        JPanel InfoPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        InfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JLabel askInputLabel = new JLabel();
        JLabel accIDLabel = new JLabel();
        JLabel PINLabel = new JLabel();

        JTextField accIDField = new JTextField();
        JPasswordField PINField = new JPasswordField();
        PINField.setEchoChar('*');

        JButton back = new JButton();
        JButton clear = new JButton();

        CloseAccountFrame.setSize(250, 200);
        //set the BankSystemFrame in center
        CloseAccountFrame.setLocationRelativeTo(null);

        InfoPanel.setLayout(new GridLayout(5, 1));
        InfoPanel.add(askInputLabel);
        askInputLabel.setText("Please enter your information below.");
        /**
         * 2 information required for Closing Account:
         * - accID
         * - PIN
         */
        InfoPanel.add(accIDLabel);
        InfoPanel.add(accIDField);
        accIDLabel.setText("accID");

        InfoPanel.add(PINLabel);
        InfoPanel.add(PINField);
        PINLabel.setText("PIN");

        /**
         * Panel2 of CloseAccountFrame: buttonPanel
         * two button:
         * - Back: go back to bank system home page
         * - Close Account
         */
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(back);
        back.setText("Back");
        buttonPanel.add(clear);
        clear.setText("Close Account");

        back.addActionListener(e -> {
            CloseAccountFrame.dispose();
            BankSystemFrame.setVisible(true);
        });

        clear.addActionListener(e -> {
            String providedAccID = accIDField.getText();
            String providedPIN = new String(PINField.getPassword());
            if (providedAccID.equals("") || providedPIN.equals("")) {
                invalidInput(); // empty input
            } else {
                boolean isVerified = false;
                boolean isClosed = false;
                Account VerifyIDnPIN = new Account();
                isVerified = VerifyIDnPIN.accIDnPINVerify(providedAccID, providedPIN);
                if (isVerified) {
                    isClosed = VerifyIDnPIN.closeAccount(providedAccID);
                    if (isClosed) {
                        successOperation(); // Funds are cleared
                    }
                } else {
                    invalidInput(); //not verified
                }
            }
            accIDField.setText("");
            PINField.setText("");
        });

        /**
         * adds two panels to the CloseAccountFrame<br>
         */
        InfoPanel.setPreferredSize(new Dimension(0, 120));
        CloseAccountFrame.add(InfoPanel, BorderLayout.PAGE_START);
        buttonPanel.setPreferredSize(new Dimension(0, 40));
        CloseAccountFrame.add(buttonPanel, BorderLayout.PAGE_END);
        CloseAccountFrame.setVisible(true);
        CloseAccountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /**
     * Reaction to all kinds of successful operation
     */
    public void successOperation() {
        JOptionPane.showMessageDialog(null, "Success!");
    }

    /**
     * Reaction to all kinds of invalid input
     */
    public void invalidInput() {
        JOptionPane.showMessageDialog(null, "Invalid Input!");
    }

    /**
     * Main method: launch the application<br>
     * - Give a welcome message using a Dialog window<br>
     * - New a BankSystem class<br>
     * - Show MathTeacher GUI_BankSystem after pressing the confirmation button in the dialog window<br>
     *
     * @param args null
     */
    public static void main(String[] args) {
//        JOptionPane.showMessageDialog(null, "Welcome to your Bank System!");
        GUI_BankSystem BankSystem = new GUI_BankSystem();
    }
}
