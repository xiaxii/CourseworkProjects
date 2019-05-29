import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * UserControl Tester.<br>
 * JUnit4
 * @author Yuanrong SHAO, Xi XIA
 * @version 2.2
 */
public class JUnitUserControlTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: checkRegisterInfo(String qmID)<br>
     * Test Case: 161192664 is not registered, and doesn't exist in the file "registers.txt"
     */
    @Test
    public void testCheckRegisterInfo() throws Exception {
        UserControl uc = new UserControl();
        String qmID = "161192664";
        Boolean Expected = false;
        Boolean Actual = uc.checkRegisterInfo(qmID);
        assertEquals(Expected, Actual);
    }

    /**
     * Method: checkIDFormat(String input)<br>
     * Test Case: 161192664777 is an invalid qmID
     */
    @Test
    public void testCheckIDFormat() throws Exception {
        UserControl uc = new UserControl();
        String qmID = "161192664777";
        Boolean Expected = false;
        Boolean Actual = uc.checkIDFormat(qmID);
        assertEquals(Expected, Actual);
    }

    /**
     * Method: checkEmail(String input)<br>
     * Test Case: y.shaose16.qmul.ac.uk is an invalid mail address
     */
    @Test
    public void testCheckEmail() throws Exception {
        UserControl uc = new UserControl();
        String MailAddress = "y.shaose16.qmul.ac.uk";
        Boolean Expected = false;
        Boolean Actual = uc.checkEmail(MailAddress);
        assertEquals(Expected, Actual);
    }

    /**
     * Method: register(String name, String qmID, String mailAddress)<br>
     * Test Case: Xi XIA has already registered
     */
    @Test
    public void testRegister() throws Exception {
        String name = "Xi Xia";
        String qmID = "161187914";
        String MailAddress = "x.xia@se16.qmul.ac.uk";
        UserControl uc = new UserControl();
        String Expected = "You have already registered.";
        String Actual = uc.register(name, qmID, MailAddress);
        assertEquals(Expected, Actual);
    }

    /**
     * Method: checkStdInfo(String name, String qmID, String mailAddress)<br>
     * Test Case: Xi Cui is a student of QM
     */
    @Test
    public void testCheckStdInfo() throws Exception {
        String name = "Xi Cui";
        String qmID = "161187833";
        String MailAddress = "x.cui@se16.qmul.ac.uk";
        UserControl uc = new UserControl();
        Boolean Expected = true;
        Boolean Actual = uc.checkStdInfo(name, qmID, MailAddress);
        assertEquals(Expected, Actual);
    }

    /**
     * Method: payFine(User user)<br>
     * Test Case: 161187729 has no fine
     */
    @Test
    public void testPayFine() throws Exception {
        String qmID = "161187729";
        User user = new User(qmID);
        UserControl uc = new UserControl();
        String Expected = "You don't have a fine.";
        String Actual = uc.payFine(user);
        assertEquals(Expected, Actual);
    }

    /**
     * Method: weeklyReportOf1User(String ID)<br>
     * Test Case: 161187729 has no record of using scooters
     */
    @Test
    public void testWeeklyReportOf1User() throws Exception {
        String qmID = "161187729";
        UserControl uc = new UserControl();
        String Expected = "You haven't used our service this week.";
        String Actual = uc.weeklyReportOf1User(qmID);
        assertEquals(Expected, Actual);
    }
} 
