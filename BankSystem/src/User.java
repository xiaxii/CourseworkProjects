/**
 * Entity Class: User
 * @version 3.3
 * @author SE Group 101 (Taizhou QING, Haoran CUI, Jiayi PANG, Xi XIA, Yuanrong SHAO, Xi CUI)
 */
public class User {

    public String name;
    public String qmID;
    public String mailAddress;
    protected boolean fine;

    /**
     * Constructor <br>
     * used in: ManagePayFine#actionPerformed(java.awt.event.ActionEvent)
     * @param qmID provided qmID
     */
    public User(String qmID) {
        this.setQmID(qmID);
    }


    /**
     * Constructor <br>
     * used in: ManageRegistration#actionPerformed(java.awt.event.ActionEvent)
     * @param name provided name
     * @param qmID provided qmID
     * @param mailAddress provided mailAddress
     */
    public User(String name, String qmID, String mailAddress) {

        this.setName(name);
        this.setQmID(qmID);
        this.setMailAddress(mailAddress);
        this.setFine(false);
    }

    /*
     * Setters and Getters
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQmID() {
        return qmID;
    }

    public void setQmID(String qmID) {
        this.qmID = qmID;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public boolean getFine() {
        return fine;
    }

    public void setFine(boolean fine) {
        this.fine = fine;
    }


}
