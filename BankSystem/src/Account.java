import java.io.*;
/**
 * Basic information of an account: <br>
 *     - accID <br>
 *     - type <br>
 *     - PIN <br>
 *     - balance <br>
 *     - overdraftLimit <br>
 *     - clearing <br>
 *     - accountStatus <br>
 *     - noticed <br>
 *
 * Details of all accounts are in file "BankSystem/src/File/Accounts.txt"
 *
 * @author Xi Xia
 * @version 1.1
 */
public class Account {

    /**
     * Define 3 types of accounts
     */
    public static enum Type {
        SAVER, CURRENT, JUNIOR
    }

    /**
     * Define 3 status of accounts
     */
    public static enum AccountStatus {
        ACTIVE, SUSPENDED, CLOSED
    }

    protected int accID;
    protected Type type;
    protected String PIN;
    /* default settings for all accounts */
    protected double balance = 0;
    /**
     * 0 for Junior and Saver accounts, <br>
     * else, record the overdraftLimit of current accounts
     */
    protected double overdraftLimit = 0;
    /**
     * 0 for cleared, record the amount when not cleared
     */
    protected double clearing = 0;
    /**
     * default status is "SUSPENDED", other status are "ACTIVE", "CLOSED"
     */
    protected AccountStatus status = AccountStatus.SUSPENDED;
    /**
     * true for noticed before withdrawal, false for not noticed
     */
    protected boolean noticed = false;

    /* Following 6 Functions sre the main functions of this Bank System */
    /**
     * Function1. Open Account <br>
     * - make the account activated <br>
     * - allocate an unique account ID
     * @see Account#getAccIDForNewAcc()
     * @param birth use the birthday as default PIN
     */
    public void openAccount(String birth) {
        this.setStatus(AccountStatus.ACTIVE);
        this.setAccID(getAccIDForNewAcc());
        this.setPIN(birth);
    }

    /**
     * Deposit Funds
     * @param providedAccID account ID from the customer's input
     * @param amount depositing amount from the customer's input
     * @param clearMode clearing mode from the customer's input
     * @return whether the operation is successful
     */
    public boolean depositFunds(String providedAccID, double amount, String clearMode) {
        try {
            String oldFilePathName = "src/File/Accounts.txt";
            String tempFilePathName = "src/File/tempAccounts.txt";
            File in = new File(oldFilePathName);
            File out = new File(tempFilePathName);

            InputStreamReader reader = new InputStreamReader(new FileInputStream(in)); // 建立一个输入流对象reader
            BufferedReader bufferedReader = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String readLine = bufferedReader.readLine();
            System.out.println("read" + readLine);

            while (readLine != null) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(out, true));
                String writeLine = "";

                String[] accInfo = readLine.split(",");//按英文逗号切割字符串
                String readAccID = accInfo[0]; //转换为 int accID
                // the account exists and is active
                if (readAccID.equals(providedAccID)) {
                    System.out.println("Account found! " + providedAccID);
                    switch (clearMode) {
                    case "Fully cleared":
                        System.out.println("ClearMode: " + clearMode);
                        double newBalance = Double.valueOf(accInfo[3]) + amount;
                        writeLine = accInfo[0] + "," + accInfo[1] + "," + accInfo[2] + ","
                                + newBalance + "," + accInfo[4] + "," + accInfo[5] + ","
                                + accInfo[6] + "," + accInfo[7] + "\r\n";// 拼接字符串\r\n即为换行
                        System.out.println("writeline " + writeLine);
                        break;
                    case "Uncleared":
                        double newClearing = Double.valueOf(accInfo[5]) + amount;
                        writeLine = accInfo[0] + "," + accInfo[1] + "," + accInfo[2] + ","
                                + accInfo[3] + "," + accInfo[4] + "," + newClearing + ","
                                + accInfo[6] + "," + accInfo[7] + "\r\n";// 拼接字符串\r\n即为换行
                        break;
                    default:
                        break;
                    }
                } else {
                    writeLine = readLine + "\r\n";
                }
                bufferedWriter.write(writeLine);
                bufferedWriter.flush(); // 把缓存区内容压入文件
                bufferedWriter.close(); // 最后记得关闭文件
                readLine = bufferedReader.readLine(); // 一次读入一行数据
            }
            bufferedReader.close();// 最后记得关闭文件
            /**
             * delete old Accounts.txt file, rename tempAccounts.txt to Accounts.txt
             */
            in.delete();
            out.renameTo(in);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return true;
    }

    /**
     * Function2. Clear Funds
     * @param providedAccID account ID from the customer's input
     * @return whether the operation is successful
     */
    public boolean clearFunds(String providedAccID) {
        try {
            String oldFilePathName = "src/File/Accounts.txt";
            String tempFilePathName = "src/File/tempAccounts.txt";
            File in = new File(oldFilePathName);
            File out = new File(tempFilePathName);

            InputStreamReader reader = new InputStreamReader(new FileInputStream(in)); // 建立一个输入流对象reader
            BufferedReader bufferedReader = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String readLine = bufferedReader.readLine();
            System.out.println("read" + readLine);

            while (readLine != null) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(out, true));
                String writeLine = "";

                String[] accInfo = readLine.split(",");//按英文逗号切割字符串
                String readAccID = accInfo[0]; //转换为 int accID
                // the account exists and is active
                if (readAccID.equals(providedAccID)) {
                    System.out.println("Account found! " + providedAccID);
                    double clearing = Double.valueOf(accInfo[5]);
                    double balance = Double.valueOf(accInfo[3]);
                    balance += clearing;
                    clearing = 0.0;
                    writeLine = accInfo[0] + "," + accInfo[1] + "," + accInfo[2] + ","
                            + balance + "," + accInfo[4] + "," + clearing + ","
                            + accInfo[6] + "," + accInfo[7] + "\r\n";// 拼接字符串\r\n即为换行
                    System.out.println("writeline " + writeLine);
                } else {
                    writeLine = readLine + "\r\n";
                }
                bufferedWriter.write(writeLine);
                bufferedWriter.flush(); // 把缓存区内容压入文件
                bufferedWriter.close(); // 最后记得关闭文件
                readLine = bufferedReader.readLine(); // 一次读入一行数据
            }
            bufferedReader.close();// 最后记得关闭文件
            /**
             * delete old Accounts.txt file, rename tempAccounts.txt to Accounts.txt
             */
            in.delete();
            out.renameTo(in);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return true;
    }

    /**
     * Function3. Withdraw Funds
     * A notice must be given before withdrawal
     * @param providedAccID account ID from the customer's input
     * @param amount withdrawing amount from the customer's input
     * @return whether the operation is successful
     */
    public boolean withdrawFunds(String providedAccID, double amount) {
        boolean isWithdrawn = false;
        try {
            String oldFilePathName = "src/File/Accounts.txt";
            String tempFilePathName = "src/File/tempAccounts.txt";
            File in = new File(oldFilePathName);
            File out = new File(tempFilePathName);

            InputStreamReader reader = new InputStreamReader(new FileInputStream(in)); // 建立一个输入流对象reader
            BufferedReader bufferedReader = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String readLine = bufferedReader.readLine();
            while (readLine != null) {
                System.out.println("readline: "+readLine);
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(out, true));
                String writeLine = readLine + "\r\n";

                String[] accInfo = readLine.split(",");//按英文逗号切割字符串
                String readAccID = accInfo[0];
                String notice = accInfo[7];
                // the account exists and is active
                if (readAccID.equals(providedAccID) ) {
                    System.out.println("Account found! " + providedAccID);
                    double balance = Double.valueOf(accInfo[3]);
                    System.out.println("balance "+ balance+ " will withdraw "+amount);
                    String accountType = accInfo[1];
                    switch (accountType) {
                    case "SAVER":
                        if (balance >= amount && notice.equals("true")) {
                            balance -= amount;
                            writeLine = accInfo[0] + "," + accInfo[1] + "," + accInfo[2] + ","
                                    + balance + "," + accInfo[4] + "," + accInfo[5] + ","
                                    + accInfo[6] + "," + "false\r\n";// 拼接字符串\r\n即为换行
                            isWithdrawn = true;
                        }
                    case "JUNIOR":
                        if (balance >= amount) {
                            balance -= amount;
                            writeLine = accInfo[0] + "," + accInfo[1] + "," + accInfo[2] + ","
                                    + balance + "," + accInfo[4] + "," + accInfo[5] + ","
                                    + accInfo[6] + "," + "false\r\n";// 拼接字符串\r\n即为换行
                            isWithdrawn = true;
                        }
                        break;
                    case "CURRENT":
                        double overdraftLimit = Double.valueOf(accInfo[4]);
                        if ((overdraftLimit + balance) >= amount) {
                            balance -= amount;
                            writeLine = accInfo[0] + "," + accInfo[1] + "," + accInfo[2] + ","
                                    + balance + "," + accInfo[4] + "," + accInfo[5] + ","
                                    + accInfo[6] + "," + "false\r\n";// 拼接字符串\r\n即为换行
                            isWithdrawn = true;
                        }
                        break;
                    default:
                        break;
                    }
                }
                System.out.println("writeline: "+writeLine);
                bufferedWriter.write(writeLine);
                bufferedWriter.flush(); // 把缓存区内容压入文件
                bufferedWriter.close(); // 最后记得关闭文件
                readLine = bufferedReader.readLine(); // 一次读入一行数据
            }
            bufferedReader.close();// 最后记得关闭文件
            /**
             * delete old Accounts.txt file, rename tempAccounts.txt to Accounts.txt
             */
            in.delete();
            out.renameTo(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isWithdrawn;
    }

    /**
     * Function4. Suspend Account
     * @param providedAccID account ID from the customer's input
     * @return whether the operation is successful
     */
    public boolean suspendAccount(String providedAccID) {
        try {
            String oldFilePathName = "src/File/Accounts.txt";
            String tempFilePathName = "src/File/tempAccounts.txt";
            File in = new File(oldFilePathName);
            File out = new File(tempFilePathName);

            InputStreamReader reader = new InputStreamReader(new FileInputStream(in)); // 建立一个输入流对象reader
            BufferedReader bufferedReader = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String readLine = bufferedReader.readLine();
            System.out.println("read" + readLine);

            while (readLine != null) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(out, true));
                String writeLine = "";

                String[] accInfo = readLine.split(",");//按英文逗号切割字符串
                String readAccID = accInfo[0]; //转换为 int accID
                // the account exists and is active
                if (readAccID.equals(providedAccID)) {
                    System.out.println("Account found! " + providedAccID);
                    writeLine = accInfo[0] + "," + accInfo[1] + "," + accInfo[2] + ","
                            + accInfo[3] + "," + accInfo[4] + "," + accInfo[5] + ","
                            + "SUSPEND"  + "," + accInfo[7]  + "\r\n";// 拼接字符串\r\n即为换行
                    System.out.println("writeline " + writeLine);
                } else {
                    writeLine = readLine + "\r\n";
                }
                bufferedWriter.write(writeLine);
                bufferedWriter.flush(); // 把缓存区内容压入文件
                bufferedWriter.close(); // 最后记得关闭文件
                readLine = bufferedReader.readLine(); // 一次读入一行数据
            }
            bufferedReader.close();// 最后记得关闭文件
            /**
             * delete old Accounts.txt file, rename tempAccounts.txt to Accounts.txt
             */
            in.delete();
            out.renameTo(in);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return true;
    }

    /**
     * Function5. Close Account
     * @param providedAccID account ID from the customer's input
     * @return whether the operation is successful
     */
    public boolean closeAccount(String providedAccID) {
        try {
            String oldFilePathName = "src/File/Accounts.txt";
            String tempFilePathName = "src/File/tempAccounts.txt";
            File in = new File(oldFilePathName);
            File out = new File(tempFilePathName);

            InputStreamReader reader = new InputStreamReader(new FileInputStream(in)); // 建立一个输入流对象reader
            BufferedReader bufferedReader = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String readLine = bufferedReader.readLine();
            System.out.println("read" + readLine);

            while (readLine != null) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(out, true));
                String writeLine = "";

                String[] accInfo = readLine.split(",");//按英文逗号切割字符串
                String readAccID = accInfo[0]; //转换为 int accID
                // the account exists and is active
                if (readAccID.equals(providedAccID) && accInfo[3].equals("0.0")) {
                    System.out.println("Account found! " + providedAccID);
                    writeLine = accInfo[0] + "," + accInfo[1] + "," + accInfo[2] + ","
                            + accInfo[3] + "," + accInfo[4] + "," + accInfo[5] + ","
                            +  "CLOSED" + "," + accInfo[7] + "\r\n";// 拼接字符串\r\n即为换行
                    System.out.println("writeline " + writeLine);
                } else {
                    writeLine = readLine + "\r\n";
                }
                bufferedWriter.write(writeLine);
                bufferedWriter.flush(); // 把缓存区内容压入文件
                bufferedWriter.close(); // 最后记得关闭文件
                readLine = bufferedReader.readLine(); // 一次读入一行数据
            }
            bufferedReader.close();// 最后记得关闭文件
            /**
             * delete old Accounts.txt file, rename tempAccounts.txt to Accounts.txt
             */
            in.delete();
            out.renameTo(in);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return true;
    }


    /**
     * Function6. Provide an unique accID for a new opened account
     * @return an unique accID
     */
    public int getAccIDForNewAcc() {
        int AccID = 0;
        try {
            String pathname = "src/File/NewAccID.txt";
            File file = new File(pathname);
            //read the new AccID
            InputStreamReader reader = new InputStreamReader(new FileInputStream(pathname)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            int newAccID = Integer.valueOf(br.readLine());
            AccID = newAccID;
            newAccID++;
            //provide new AccID for next new account
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            String line;
            line = newAccID + "";
            System.out.println(line);
            out.write(line);
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AccID;
    }

    /*Following functions provide support for the 6 functions above*/
    /**
     * Write Information into File "AccountFinder.txt"
     * @param customer_name customer name
     * @param customer_address customer address
     * @param customer_birthday customer birthday
     * @param accID account ID
     */
    public void writeAccFinder(String customer_name, String customer_address, String customer_birthday, int accID) {
        try {
            String pathname = "src/File/AccountFinder.txt";
            File file = new File(pathname);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            String line;
            line = customer_name + "," + customer_address + "," + customer_birthday + "," + accID + "\r\n";// 拼接字符串\r\n即为换行
            bufferedWriter.write(line);
            bufferedWriter.flush(); // 把缓存区内容压入文件
            bufferedWriter.close(); // 最后记得关闭文件
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Write Information into File "Accounts.txt"
     */
    public void writeAcc() {
        try {
            String pathname = "src/File/Accounts.txt";
            File file = new File(pathname);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            String line;
            line = accID + "," + type + "," + PIN + "," + balance + "," + overdraftLimit + "," + clearing + "," + status + "," + noticed + "\r\n";// 拼接字符串\r\n即为换行
            bufferedWriter.write(line);
            bufferedWriter.flush(); // 把缓存区内容压入文件
            bufferedWriter.close(); // 最后记得关闭文件
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if the account is active <br>
     * - before withdrawing and depositing <br>
     * @param providedAccID account ID from the customer's input
     * @return whether the account is active
     */
    public boolean isAccActive(String providedAccID) {
        try {
            String filePathName = "src/File/Accounts.txt";
            File filename = new File(filePathName);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = br.readLine();
            while (line != null) {
                String[] accInfo = line.split(",");//按英文逗号切割字符串
                int readAccID = Integer.valueOf(accInfo[0]);
                String readAccStatus = accInfo[6];
                System.out.println("readAccID: " + readAccID + " providedAccID: " + providedAccID);
                System.out.println("readAccStatus: " + readAccStatus);
                System.out.println(accInfo[6].equals(AccountStatus.ACTIVE));
                if (providedAccID.equals(readAccID) && accInfo[6].equals("ACTIVE")) {
                    br.close();
                    System.out.println("Account is active with accID" + providedAccID);
                    return true;
                }
                line = br.readLine(); // 一次读入一行数据
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * A notice must be given before depositing
     * @param providedAccID account ID from the customer's input
     * @return whether the account is successfully noticed
     */
    public boolean notice(String providedAccID) {
        boolean isNoticed = false;
        try {
            String oldFilePathName = "src/File/Accounts.txt";
            String tempFilePathName = "src/File/tempAccounts.txt";
            File in = new File(oldFilePathName);
            File out = new File(tempFilePathName);

            InputStreamReader reader = new InputStreamReader(new FileInputStream(in)); // 建立一个输入流对象reader
            BufferedReader bufferedReader = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String readLine = bufferedReader.readLine();
            System.out.println("read" + readLine);

            while (readLine != null) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(out, true));
                String writeLine = "";

                String[] accInfo = readLine.split(",");//按英文逗号切割字符串
                String readAccID = accInfo[0]; //转换为 int accID
                // the account exists and is active
                if (readAccID.equals(providedAccID)) {
                    System.out.println("Account found! " + providedAccID);
                    writeLine = accInfo[0] + "," + accInfo[1] + "," + accInfo[2] + ","
                            + accInfo[3] + "," + accInfo[4] + "," + accInfo[5] + ","
                            + accInfo[6] + "," + "true\r\n";// 拼接字符串\r\n即为换行
                    isNoticed = true;
                    System.out.println("writeline " + writeLine);
                } else {
                    writeLine = readLine + "\r\n";
                }
                bufferedWriter.write(writeLine);
                bufferedWriter.flush(); // 把缓存区内容压入文件
                bufferedWriter.close(); // 最后记得关闭文件
                readLine = bufferedReader.readLine(); // 一次读入一行数据
            }
            bufferedReader.close();// 最后记得关闭文件
            /**
             * delete old Accounts.txt file, rename tempAccounts.txt to Accounts.txt
             */
            in.delete();
            out.renameTo(in);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return isNoticed;
    }

    /**
     * Verify the accID and PIN when <br>
     * - withdrawing funds <br>
     * - clearing funds <br>
     * - suspending account <br>
     * - closing account
     * @param providedAccID account ID from the customer's input
     * @param providedPIN Pin from the customer's input
     * @return whether the provided PIN is correct for the provided account
     */
    public boolean accIDnPINVerify(String providedAccID, String providedPIN) {
        try {
            String filePathName = "src/File/Accounts.txt";
            File filename = new File(filePathName); // 要读取以上路径的文件
            if (!filename.exists()) {
                System.out.println("can't find " + filePathName);
            }
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader bufferedReader = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] accInfo = line.split(",");//按英文逗号切割字符串
                if (providedAccID.equals(accInfo[0]) && providedPIN.equals(accInfo[2])) {
                    bufferedReader.close(); //验证通过，关闭文件
                    System.out.println("Verified!");
                    return true;
                }
                line = bufferedReader.readLine(); // 一次读入一行数据
            }
            bufferedReader.close(); //读到最后，关闭文件
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    /* getters and setters */
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getAccID() {
        return accID;
    }

    public void setAccID(int accID) {
        this.accID = accID;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    public double getClearing() {
        return clearing;
    }

    public void setClearing(double clearing) {
        this.clearing = clearing;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public boolean isNoticed() {
        return noticed;
    }

    public void setNoticed(boolean noticed) {
        this.noticed = noticed;
    }

    /**
     * Turn all information of an account into a String
     * @return account information String
     */
    @Override
    public String toString() {
        return "Account{" +
                "accID=" + accID +
                ", type=" + type +
                ", PIN='" + PIN + '\'' +
                ", balance=" + balance +
                ", overdraftLimit=" + overdraftLimit +
                ", clearing=" + clearing +
                ", status=" + status +
                ", noticed=" + noticed +
                '}';
    }

}
