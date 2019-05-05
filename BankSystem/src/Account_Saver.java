public class Account_Saver extends Account {

    /**
     * open a saver account
     * @param birth
     * @return
     */
    public int openSaverAccount(String birth) {
        /**
         * Use the customer's birthday as default PIN when an account is opened
         * AccID is generated by openAccount() function in Account class, and it's unique
         * @see Account#openAccount(java.lang.String)
         */
        openAccount(birth);
        setType(Type.SAVER);
        System.out.println(toString());
        return this.accID; //to be recorded in File "CreditAgency.txt"
    }
}
