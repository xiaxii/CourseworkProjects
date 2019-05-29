import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Test Class of Station Entity
 * @version 2.0
 * @author SE Group 101 (Taizhou QING, Haoran CUI, Jiayi PANG, Xi XIA, Yuanrong SHAO, Xi CUI)
 */

public class StationControlTest {

	/**
	 * Test different situation of scooter parking in the station
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StationControlTest sct = new StationControlTest();
		sct.choice();

	}
	
	/**
     * Operations to make a choice to test
     * <p>Containing: <br>
     *  1. 8 choices for testing <br>
     *  2. 2 functions to test: Pick Up and Return <br>
     */
	public void choice() {
			boolean status = true;
		  	System.out.println("Please choose the unit test individually.");
		  	System.out.println("1. Test user has not registered.");
	        System.out.println("2. Test user is not existed in the database. ");
	        System.out.println("3. Test user has not paid for a fine. ");
	        System.out.println("4. Test user has not returned scooter before picking up again");
	        System.out.println("5. Test user picks up successfully.");
	        System.out.println("6. Test user need to pick up a scooter before returning it back.");
	        System.out.println("7. Test user returns back successfully. ");
	        System.out.println("8. Exit.");
	      
	        try {
	            while (status) {
	                System.out.println("Your choice: ");
	                InputStreamReader isr = new InputStreamReader(System.in);
	                BufferedReader br = new BufferedReader(isr);
	                String choice_str = br.readLine();
	                int choice = Integer.valueOf(choice_str);
	                switch (choice) {
	                case 1: this.TestData_UserHasNotRegistered();
	                				break;
	                case 2: this.TestData_UserDoesNotExist();
	                				break;
	                case 3: this.TestData_UserHasNotPaidForFine();
	                				break;
	                case 4:  this.TestData_UserHasNotReturnedScooter();
	                				break;
	                case 5: this.TestData_UserPickUpSuccessfully();
	                				break;
	                case 6: this.TestData_UserNeedPickUpScooterBeforeReturn();
	                				break;
	                case 7: this.TestData_UserReturnScooterSuccessfully();
	                				break;
	                case 8: status =false;
	                				break;
	                default: System.out.println("Your Input is invalid.");
	                				break;
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
	//Test Pick Up
	/**
     * Operations to test data that user can pick up a scooter successfully
     */
	public void TestData_UserPickUpSuccessfully() {
		String QMID = "161192664";
		int pickup=1;
		this.CaseJudgement(QMID,pickup);
	}
	
	/**
     * Operations to test data that user hasn't registered before.
     */
	public void TestData_UserHasNotRegistered() {
		String QMID = "161187833";
		int pickup=1;
		this.CaseJudgement(QMID, pickup);
	}
	
	/**
     * Operations to test data that user doesn't exist
     */
	public void TestData_UserDoesNotExist() {
		String QMID = "161187844";
		int pickup=1;
		this.CaseJudgement(QMID, pickup);
	}
	
	/**
     * Operations to test data that user hasn't returned the scooter
     */
	public void TestData_UserHasNotReturnedScooter() {
		String QMID = "161187914";
		int pickup = 1;
		this.CaseJudgement(QMID, pickup);
	}
	
	/**
     * Operations to test data that user hasn't paid for a fine
     */
	public void TestData_UserHasNotPaidForFine() {
		String QMID = "161188645";
		int pickup=1;
		this.CaseJudgement(QMID, pickup);
	}
	

	//Test Return
	/**
     * Operations to test data that user need to pick up a scooter before returning.
     */
	public void TestData_UserNeedPickUpScooterBeforeReturn() {
		String QMID ="161187729";
		int re_turn = 2;
		this.CaseJudgement(QMID, re_turn);
	}
	
	/**
     * Operations to test data that user return the scooter successfully
     */
	public void TestData_UserReturnScooterSuccessfully() {
		String QMID = "161187914";
		int re_turn=2;
		this.CaseJudgement(QMID, re_turn);
	}
	
	/**
     * Operations to judge what kind of case to be tested
     */
	public void CaseJudgement(String QMID, int flag) {
		StationControl sc = new StationControl();
		int caseNum = sc.readID(QMID, flag);
		
		if(caseNum==1&&flag==1) {
			System.out.println("User has picked up successfully.");
		}else if(caseNum==1&&flag==2){
		     System.out.println("User has returned successfully");	
		}else if(caseNum==2) {
			System.out.println("User hasn't registered before.");
		}else if(caseNum==3) {
			System.out.println("User hasn't paid for a fine.");
		}else if(caseNum==4) {
			System.out.println("User hasn't returned the scooter.");
		}else if(caseNum==5) {
			System.out.println("User needs to pick up a scooter first.");
		}else if(caseNum==6) {
			System.out.println("User doesn't exist in the database.");
		}
	}
	
	

}
