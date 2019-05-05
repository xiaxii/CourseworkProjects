import java.io.*;

public class Account {

    public static enum Type {
        SAVER, CURRENT, JUNIOR
    }

    public static enum AccountStatus {
        ACTIVE, SUSPENDED, CLOSED
    }

    protected int accID;
    protected Type type;
    protected String PIN;
    /**
     * default settings for all accounts
     */
    protected double balance = 0;
    protected double overdraftLimit = 0;
    //0 for Junior and Saver accounts, record the overdraftLimit of current accounts
    protected double clearing = 0;
    //0 for cleared, record the amount when not cleared
    protected AccountStatus status = AccountStatus.SUSPENDED;
    //default status is "SUSPENDED", other status are "ACTIVE", "CLOSED"
    protected boolean noticed = false;
    //true for noticed before withdrawal, false for not noticed


    /**
     * getters and setters
     *
     * @return
     */
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

    /**
     * Write Information into File "AccountFinder.txt"
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

    public boolean isAccActive(int providedAccID) {
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
                if (providedAccID == readAccID && accInfo[6].equals("ACTIVE")) {
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

    /**
     * Open Account
     *
     * @param birth use the birthday as default PIN
     *              - make the account activated
     *              - allocate an unique accID for the new account opened
     * @see Account#getAccIDforNewAcc()
     */
    public void openAccount(String birth) {
        this.setStatus(AccountStatus.ACTIVE);
        this.setAccID(getAccIDforNewAcc());
        this.setPIN(birth);
    }

    public int getAccIDforNewAcc() {
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

    /**
     * Deposit Funds
     */
    public boolean depositFunds(int providedAccID, double amount, String clearMode) {
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
                int readAccID = Integer.valueOf(accInfo[0]); //转换为 int accID
                // the account exists and is active
                if (readAccID == providedAccID) {
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
     * Clear Funds
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
     * Withdraw Funds
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
                // the account exists and is active
                if (readAccID.equals(providedAccID)) {
                    System.out.println("Account found! " + providedAccID);
                    double balance = Double.valueOf(accInfo[3]);
                    System.out.println("balance "+ balance+ " will withdraw "+amount);
                    String accountType = accInfo[1];
                    switch (accountType) {
                    case "SAVER":
                    case "JUNIOR":
                        if (balance >= amount) {
                            balance -= amount;
                            writeLine = accInfo[0] + "," + accInfo[1] + "," + accInfo[2] + ","
                                    + balance + "," + accInfo[4] + "," + accInfo[5] + ","
                                    + accInfo[6] + "," + accInfo[7] + "\r\n";// 拼接字符串\r\n即为换行
                            isWithdrawn = true;
                        }
                        break;
                    case "CURRENT":
                        double overdraftLimit = Double.valueOf(accInfo[4]);
                        if ((overdraftLimit + balance) >= amount) {
                            balance -= amount;
                            writeLine = accInfo[0] + "," + accInfo[1] + "," + accInfo[2] + ","
                                    + balance + "," + accInfo[4] + "," + accInfo[5] + ","
                                    + accInfo[6] + "," + accInfo[7] + "\r\n";// 拼接字符串\r\n即为换行
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
     * Suspend Account
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
                            + accInfo[6] + "," + "SUSPEND8\r\n";// 拼接字符串\r\n即为换行
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
     * Close Account
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
                if (readAccID.equals(providedAccID)) {
                    System.out.println("Account found! " + providedAccID);
                    writeLine = accInfo[0] + "," + accInfo[1] + "," + accInfo[2] + ","
                            + accInfo[3] + "," + accInfo[4] + "," + accInfo[5] + ","
                            + accInfo[6] + "," + "CLOSED\r\n";// 拼接字符串\r\n即为换行
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


}
