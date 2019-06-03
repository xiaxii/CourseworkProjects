import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Control Class of Station Entity
 * @version 3.3
 * @author SE Group 101 (Taizhou QING, Haoran CUI, Jiayi PANG, Xi XIA, Yuanrong SHAO, Xi CUI)
 */
public class StationControl {
    public static String parentPath = "/Users/xiaxi/Desktop/Software_group101/src/File/";

    /**
     * Get the number of scooters parking in the thisStation
     * @param station thisStation that you want to see
     * @return the number of scooters in the thisStation
     */
    public static int getCurrentNum(String station) {

        try {
            String numberpathname = parentPath +  "numOfScooters.txt";
            File filename = new File(numberpathname);
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = br.readLine();
            while (line != null) {
                String[] personalInfo = line.split(",");
                if (personalInfo[0].equals(station)) {
                    br.close();
                    return Integer.parseInt(personalInfo[1]);
                }
                line = br.readLine();
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * Operations to pick up a scooter
     * @param pathName the file's relative path
     * @param stationName thisStation name
     */
    public static void pickUpScooter(String pathName, String stationName) {
        int currentNum = getCurrentNum(stationName);
        modifyFileContent(stationName, Integer.toString(currentNum), Integer.toString(currentNum - 1));
        beginTime(pathName, 1);
    }

    /**
     * Record the begin time in the file
     * @param pathName the file's relative path
     * @param flag 1 for pick up other for return
     * @return begin time
     */
    public static String beginTime(String pathName, int flag) {
        if (flag == 1) {
            String data = recordTime();
            try {
                String pathname = parentPath  + pathName + ".txt";
                File file = new File(pathname);
                if (!file.exists()) {
                    file.createNewFile();
                }

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file.getPath()));

                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                bufferedWriter.append(data + "\n");
                bufferedWriter.close();
                outputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        } else {
            String line = "";
            try {
                File file = new File(parentPath + pathName + ".txt");
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                line = bufferedReader.readLine();
                bufferedReader.close();
                fileReader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return line;
        }
    }

    /**
     * Operations to return a scooter
     * @param userID the ID of user
     * @param stationName thisStation name
     */
    public static void returnScooter(String userID, String stationName) {
        int currentNum = getCurrentNum(stationName);
        modifyFileContent(stationName, Integer.toString(currentNum), Integer.toString(currentNum + 1));
        record(userID, beginTime(userID, 2), recordTime());
        if (calculateTime(userID)) {
            recordFine(userID);
        }
        File file = new File( parentPath +  userID + ".txt");
        file.delete();

    }

    /**
     * Read student's id when picking up a scooter
     * @param ID User's ID
     * @param flag 1 for pick up, other for return
     * @return the symbol
     */
    public static int readID(String ID, int flag) {
        try {

            String pathname = parentPath +  "registers.txt";
            File filename = new File(pathname);
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = br.readLine();
            while (line != null) {
                if (line.contains(ID)) {
                    if (line.contains(String.valueOf(true))) {
                        br.close();
                        return 3;
                    }
                    File testExist = new File(parentPath +   ID + ".txt");
                    if (testExist.exists() && flag == 1) {
                        br.close();
                        return 4;
                    }
                    if (!testExist.exists() && flag == 2) {
                        br.close();
                        return 5;
                    }
                    br.close();
                    return 1;
                }
                line = br.readLine();
            }
            br.close();

            pathname = parentPath +  "stdInfo.txt";
            File file = new File(pathname);
            InputStreamReader reader1 = new InputStreamReader(
                    new FileInputStream(file)
            );
            BufferedReader bufferedReader = new BufferedReader(reader1);
            line = bufferedReader.readLine();
            while (line != null) {
                String[] personalInfo = line.split(",");
                if (personalInfo[1].equals(ID)) {
                    bufferedReader.close();
                    return 2;
                }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            return 6;

        } catch (Exception e) {
        }
        return 6;
    }

    /**
     * Get current time
     * @return current time 
     */
    public static String recordTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return dateFormat.format(new Date());
    }

    /**
     * Calculate time when return
     * @param userID User ID
     * @return true for breaking rules false for no breaking
     */
    public static boolean calculateTime(String userID) {
        try {
            String pathname = parentPath +   userID + ".txt";
            File filename = new File(pathname);
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = br.readLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date fromDate3 = dateFormat.parse(line);
            Date toDate3 = dateFormat.parse(recordTime());
            long from3 = fromDate3.getTime();
            long to3 = toDate3.getTime();
            int minutes = (int) ((to3 - from3) / (60 * 1000));
            br.close();
            String pathname1 = parentPath +  "weeklyReport.txt";
            File filename1 = new File(pathname1);
            InputStreamReader reader1 = new InputStreamReader(
                    new FileInputStream(filename1));
            BufferedReader br1 = new BufferedReader(reader1);
            String line1;
            int minuteday = 0;
            Calendar calendar = Calendar.getInstance();
            line1 = br1.readLine();
            do {

                if (line1.contains(userID) && line1.contains(Integer.toString(calendar.get(Calendar.DATE)))) {
                    String[] time = line1.split(",");
                    Date fromDate1 = dateFormat.parse(time[1]);
                    Date toDate1 = dateFormat.parse(time[2]);
                    long from1 = fromDate1.getTime();
                    long to1 = toDate1.getTime();
                    minuteday = minuteday + (int) ((to1 - from1) / (1000 * 60));
                }
            } while ((line1 = br1.readLine()) != null);
            br.close();
            if (minutes > 30 || (minuteday + minutes) > 120)
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Modify contents of the file "numOfScooters.txt"
     * @param station the symbol
     * @param oldStr the data need to be changed
     * @param newStr the new data
     */
    public static void modifyFileContent(String station, String oldStr, String newStr) {
        String path = parentPath +  "numOfScooters.txt";
        File file = new File(path);
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file)));
            File newFile = new File(parentPath +  "newFile");
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(newFile, true)));
            String string = null;
            while ((string = br.readLine()) != null) {
                if (string.contains(station)) {
                    string = string.replace(oldStr, newStr);
                }
                bw.write(string);
                bw.newLine();
            }
            br.close();
            bw.close();
            String filePath = file.getPath();
            file.delete();
            newFile.renameTo(new File(filePath));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Record the usage
     * @param ID User's ID
     * @param PTime Pick up time
     * @param RTime Return time
     */
    public static void record(String ID, String PTime, String RTime) {
        FileOutputStream out = null;
        try {
            File file = new File(parentPath +  "weeklyReport.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            out = new FileOutputStream(file, true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            StringBuffer sb = new StringBuffer();
            sb.append(ID + "," + PTime + "," + RTime + "\n");

            bufferedWriter.append(ID + "," + PTime + "," + RTime + "\n");
            bufferedWriter.close();
            outputStreamWriter.close();
            out.close();
        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Record fine if the user break the rules
     * @param ID User ID
     */
    public static void recordFine(String ID) {
        PrintWriter pw = null;
        try {
            File fileText = new File(parentPath +  "registers.txt");
            BufferedReader br = null;
            StringBuffer buff = new StringBuffer();
            br = new BufferedReader(new FileReader(fileText));

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] personalInfo = line.split(",");
                if (ID.equals(personalInfo[1])) {
                    personalInfo[3] = String.valueOf(true);
                    buff.append(personalInfo[0] + "," + personalInfo[1] + "," + personalInfo[2] + ","
                            + personalInfo[3] + "\r\n");
                } else {
                    buff.append(line + "\r\n");
                }
            }
            pw = new PrintWriter(new FileWriter(fileText), true);
            pw.print(buff);
            br.close();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
