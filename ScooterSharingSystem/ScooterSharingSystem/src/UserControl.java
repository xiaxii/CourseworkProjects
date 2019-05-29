import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Control Class for User Entity
 * @version 3.3
 * @author SE Group 101 (Taizhou QING, Haoran CUI, Jiayi PANG, Xi XIA, Yuanrong SHAO, Xi CUI)
 */
public class UserControl {

    public static String pathname_parent = "src/File/";

    /**
     * Check Registration
     * @param qmID provided qmID
     * @return whether it's registered
     */
    public static boolean checkRegisterInfo(String qmID) {
        try {
            String pathname = pathname_parent + "registers.txt";
            File filename = new File(pathname);
            if (!filename.exists()) {
                filename.createNewFile();
            }
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = br.readLine();
            while (line != null) {
                String[] personalInfo = line.split(",");
                if (qmID.equals(personalInfo[1])) {
                    br.close();
                    return true;
                }
                line = br.readLine();
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Check the format of ID(9 digits)
     * @param input inputted ID
     * @return whether it satisfy the format
     */
    public static boolean checkIDFormat(String input) {
        try {
            if ((Integer.parseInt(input) <= 999999999 || Integer.parseInt(input) >= 100000000)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check the format of mail address
     * @param input inputted mail address
     * @return whether the mail address satisfy the format
     */
    public static boolean checkEmail(String input) {
        int count = 0;
        int x = 0;
        for (int i = 0; i < input.length() - 1; i++) {
            String str = input.substring(i, i + 1);
            if (str.equals("@")) {
                count++;
                x = i;
                break;
            }
        }

        if (count == 1 && x != 0 && x != input.length() - 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * register and record it in "registers.txt"
     * @param name provided name
     * @param qmID provided qmID
     * @param mailAddress provided mailAddress
     * @return Feedback of registration
     */
    public static String register(String name, String qmID, String mailAddress) {
        if (UserControl.checkStdInfo(name, qmID, mailAddress)) {

            if (checkRegisterInfo(qmID)) {
                return "You have already registered.";
            } else {
                try {
                    File writename = new File(pathname_parent + "registers.txt");
                    BufferedWriter out = new BufferedWriter(new FileWriter(writename, true));
                    String line;
                    line = name + "," + qmID + "," + mailAddress + ",false" + "\r\n";
                    out.write(line);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return "Succeed in registration!";
            }
        } else {
            return "Your information is invalid";
        }
    }

    /**
     * Check whether the unregistered user exists in "stuInfo.txt"
     * @param name provided name
     * @param qmID provided qmID
     * @param mailAddress provided mailAddress
     * @return true for registered, false for unregistered
     */
    public static boolean checkStdInfo(String name, String qmID, String mailAddress) {
        try {
            String pathname = pathname_parent + "stdInfo.txt";
            File filename = new File(pathname);
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = br.readLine();
            while (line != null) {
                String[] personalInfo = line.split(",");
                if (name.equals(personalInfo[0]) && qmID.equals(personalInfo[1]) && mailAddress.equals(personalInfo[2])) {
                    br.close();
                    return true;
                }
                line = br.readLine();
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Clear the fine
     * @param user User entity
     * @return Feedback of payment
     */
    public static String payFine(User user) {
        String result = null;
        try {
            File fileText = new File(pathname_parent + "registers.txt");
            BufferedReader br;
            PrintWriter pw;
            StringBuffer buff = new StringBuffer();
            br = new BufferedReader(new FileReader(fileText));
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] personalInfo = line.split(",");
                if (user.getQmID().equals(personalInfo[1])) {
                    if (personalInfo[3].equals(String.valueOf(false))) {
                        buff.append(line + "\r\n");
                        result = "You don't have a fine.";
                    } else {
                        personalInfo[3] = String.valueOf(false);

                        buff.append(personalInfo[0] + "," + personalInfo[1] + "," + personalInfo[2] + ","
                                + personalInfo[3] + "\r\n");
                        result = "Your fine is cleared.";
                    }
                } else {
                    buff.append(line + "\r\n");
                }
            }
            pw = new PrintWriter(new FileWriter(fileText), true);
            pw.print(buff);
            br.close();
            pw.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }


    /**
     * Weekly report
     * @param ID provided qmID
     * @return Feedback of weekly report
     */

    public static String weeklyReportOf1User(String ID) {
        String total_reports = null;
        if (UserControl.checkRegisterInfo(ID)) {
            try {
                String pathname = pathname_parent + "weeklyReport.txt";
                File filename = new File(pathname);
                InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
                BufferedReader br = new BufferedReader(reader);
                String line;
                line = br.readLine();
                while (line != null) {
                    String[] personalInfo = line.split(",");
                    if (ID.equals(personalInfo[0])) {
                        if (total_reports == null) {
                            total_reports = "<html> " + "<br>" +
                                    "<table>" +
                                    "<tr>" +
                                    "<th>qmID</th>" + "<th>start_time</th>" + "<th>end_time</th>" +
                                    "</tr>";
                            total_reports = total_reports + "<br>" +
                                    "<tr>" +
                                    "<th>" + personalInfo[0] + "</th>" + "<th>" + personalInfo[1] + "</th>" + "<th>" + personalInfo[2] + "</th>" +
                                    "</tr>" +
                                    "<br/>";
                        } else {
                            total_reports = total_reports + "<br>" +
                                    "<tr>" +
                                    "<th>" + personalInfo[0] + "</th>" + "<th>" + personalInfo[1] + "</th>" + "<th>" + personalInfo[2] + "</th>" +
                                    "</tr>" +
                                    "<br/>";

                        }
                    }
                    line = br.readLine();
                }
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (total_reports == null) {
                total_reports = "You haven't used our service this week.";
            } else {
                total_reports = total_reports + "</html>";
            }
        } else {
            ReportWeekly reports = new ReportWeekly();
            total_reports = "You haven't registered or the input is invalid";
            reports.weeklyReportGUI(total_reports);
        }
        return total_reports;
    }
}


