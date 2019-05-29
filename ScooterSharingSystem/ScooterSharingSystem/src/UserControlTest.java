import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;

/**
 * Test Class for User Entity
 * @version 2.0
 * @author SE Group 101 (Taizhou QING, Haoran CUI, Jiayi PANG, Xi XIA, Yuanrong SHAO, Xi CUI)
 */
public class UserControlTest {
	
	 public String pathname_registers = "src/File/registers.txt";
	 public String pathname_stdInfo = "src/File/stdInfo.txt";
	 public String pathname_weeklyReport = "src/File/weeklyReport.txt";
	 
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		UserControlTest uct = new UserControlTest();
		uct.choice();
	}
	
	/**
     * Operations to make a choice to test
     * <p>Containing: <br>
     *  1. 9 choices for testing <br>
     *  2. 3 functions to test: Registration, Pay for fine and Usage Report<br>
     */
	public void choice() throws Exception{
		boolean status = true;
		System.out.println("Please choose the unit test indivually.");
		System.out.println("1. Test Input format of ID or Email is wrong");
        System.out.println("2. Test user has registered.");
        System.out.println("3. Test user registers successfully. ");
        System.out.println("4. Test user is not existed in the database. ");
        System.out.println("5. Test user has registered but with no fine ");
        System.out.println("6. Test user has a fine.");
        System.out.println("7. Test user has used scooter before.");
        System.out.println("8. Test user exists in the database without using scooters.");
        System.out.println("9. Exit.");

        while(status) {
        	 System.out.println("Your choice: ");
     		InputStreamReader isr = new InputStreamReader(System.in);
     		BufferedReader br = new BufferedReader(isr);
     		String line=br.readLine();
     		int choice = Integer.parseInt(line);
     		
     		switch(choice) {
     		case 1:  this.TestData_IDNot9DigitsandMailFormatWrong();
     						break;
     		case 2: this.TestData_HasRegistered();
     						break;
     		case 3: this.TestData_UserRegisteredSuccessfully();//jason..
     						break;
     		case 4: this.TestData_NotExist(); 
     						break;
     		case 5: this.TestData_UserDoesNotHaveFine();
     						break;
     		case 6: this.TestData_UserPayForFine();
     						break;
     		case 7: this.TestData_UserUsedScooters();
     						break;
     		case 8: this.TestData_UserNotUsedScootersButExistInDatabase();
     						break;
     		case 9: status=false;
     					  break;
     		default: System.out.println("Your input is invalid.");
     						break;
     		}
        	
        }

	}
	
		//Test Registration
		/**
	     * Operations to test data that input of ID and Email Format are wrong
	     */
	public void TestData_IDNot9DigitsandMailFormatWrong() {
		String QMID = "1611879144";
		String Mailaddress = "y.shaose16.qmul.ac.uk";
		UserControl uc = new UserControl();
		//ID is not 9 digits
		if((uc.checkIDFormat(QMID))==false) {
			System.out.println(uc.checkIDFormat(QMID)+"Your QMID is not 9 digits");
		}
		//mail doesn't satisfy the requirement.
		if(uc.checkEmail(Mailaddress)==false) {
			System.out.println("Your Email doesn't satisfy the requirements.");
		}
		//Format of both of them are wrong.
		if(uc.checkEmail(Mailaddress)==false&&uc.checkIDFormat(QMID)==false) {
			System.out.println("The qmID and Email doesn't satisfy the requirements.");
		}
	}
	
	/**
     * Operations to test data that user doesn't exist in the database
     */
	public void TestData_NotExist() {
		String name = "Hanwen Xu";
		String QMID="161187844";
		String MailAddress = "h.xu@se16.qmul.ac.uk";
		UserControl uc = new UserControl();
		boolean checkStatus = uc.checkStdInfo(name, QMID, MailAddress);
	
		if(!checkStatus) {
			System.out.println("You don't exist in the database.");
		}else {
			System.out.println("You are in the database.");
		}
		
	}
	
	/**
     * Operations to test data that user has registered before.
     */
	public void TestData_HasRegistered() {
		String QMID ="161187914";
		String name = "Xi Xia";
		String MailAddress = "x.xia@se16.qmul.ac.uk";
		
		UserControl uc = new UserControl();
		System.out.println(uc.register(name, QMID, MailAddress));
	}
	
	/**
     * Operations to test data that user has registered successfully
     */
	public void TestData_UserRegisteredSuccessfully() {
		String QMID = "161187833";
		String name = "Xi Cui";
		String MailAddress = "x.cui@se16.qmul.ac.uk";
		
		UserControl uc = new UserControl();
		System.out.println(uc.register(name, QMID, MailAddress));
	}
	
	//Test Pay for fine
	/**
     * Operations to test data that user doesn't have fine.
     */
	public void TestData_UserDoesNotHaveFine() {
		String QMID = "161192664";
		
		User user = new User(QMID);
		UserControl uc = new UserControl();
		System.out.println(uc.payFine(user));
	}
	
	/**
     * Operations to test data that user pay for fine
     */
	public void TestData_UserPayForFine() {
		String QMID = "161188645";
		
		User user = new User(QMID);
		UserControl uc = new UserControl();
		System.out.println(uc.payFine(user));
	}

	//Test Usage Report
	/**
     * Operations to test data that user hasn't used scooters but exists in the database
     */
	public void TestData_UserNotUsedScootersButExistInDatabase() {
		String QMID = "161187833";
		
		User user = new User(QMID);
		UserControl uc = new UserControl();
		System.out.println(uc.weeklyReportOf1User(QMID));
	}
	
	/**
     * Operations to test data that user used scooters.
     */
	public void TestData_UserUsedScooters() {
		String QMID = "161192664";
		try {
			File file = new File(pathname_weeklyReport);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while((line=br.readLine())!=null) {
				String[] Info = line.split(",");
				if(Info[0].equals(QMID)) {
					System.out.println("We have generated weekly report for you.");
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
