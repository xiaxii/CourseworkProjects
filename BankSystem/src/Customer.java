import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * CreditAgency Class
 * Save three types of CreditAgency information by three types of ArrayLists after creating
 *
 * @author Xi Xia
 * @version 1.1
 */
public class Customer {    //Entity Class:used to model information that is long-lived

    protected String name;
    protected String address;
    protected String birth;
    protected boolean credit;
    // credit = true: the CreditAgency has a satisfactory credit history
    // When provided that credit = true, a new account is opened.

    /**
     * Default Constructor
     */
    public Customer() {
    }

    /**
     * Constructor
     * @param name    name of the new CreditAgency
     * @param address address of the new CreditAgency
     * @param birth   birthday of the new CreditAgency
     */
    public Customer(String name, String address, String birth) {
        this.name = name;
        this.address = address;
        this.birth = birth;
//        this.credit = credit;
    }

    /**
     * Setters and getters
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public boolean isCredit() {
        return credit;
    }

    public void setCredit(boolean credit) {
        this.credit = credit;
    }

    /**
     * Check whether the customer is registered
     *
     * @return
     */
    public boolean isCreditSatisfactory() {
        try {
            String filePathName = "src/File/CreditAgency.txt";
            File filename = new File(filePathName); // 要读取以上路径的文件
            if (!filename.exists()) {
                System.out.println("can't find " + filePathName);
            }
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader bufferedReader = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] personalInfo = line.split(",");//按英文逗号切割字符串
                if (this.name.equals(personalInfo[0]) && this.address.equals(personalInfo[1]) && this.birth.equals(personalInfo[2]) && personalInfo[3].equals("true")) {
                    bufferedReader.close();
                    System.out.println("Satisfactory Credit History");
                    return true;
                }
                line = bufferedReader.readLine(); // 一次读入一行数据
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param birth calculate age by birthday
     * @return whether the age of a customer is under 16
     */
    public boolean isAgeUnder16(String birth) {
        try {
            int age = getAge(parse(birth));           //由出生日期获得年龄***
//            System.out.println("age:"+age);
            if (age < 16) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Following two functions are from the internet code is from the internet
     * @see Customer#parse(java.lang.String)
     * @see Customer#getAge(java.util.Date)
     * @Reference https://www.cnblogs.com/fuchuanzhipan1209/p/9596614.html
     */
    /**
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date parse(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(strDate);
    }

    /**
     * @param birthDay birthday in Date format
     * @return age
     * @throws Exception
     */
    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }

}