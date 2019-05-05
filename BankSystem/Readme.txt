there are three txt File in src/File directory:

1. CreditAgency.txt
record the registered customer's information:
  String name: name of the customer
  String address: address of the customer
  String birth: birthday of the customer
  boolean credit: whether the customer has a satisfactory credit history

2. AccountFinder.txt
record the basic information to find an account
  String name: name of the customer
  String address: address of the customer
  String birth: birthday of the customer
  int accID: account ID

3. Accounts.txt
record details of all accounts
  int accID: account ID
  Type type: 3 types, SAVER, CURRENT, JUNIOR
  String PIN: personal identification number
  double balance
  double overdraftLimit: 0 for Junior and Saver accounts, otherwise, a number is record as the overdraftLimit of current accounts
  double clearing: 0 for cleared, record the amount when not cleared
  AccountStatus status: default status is "SUSPENDED", other status are "ACTIVE", "CLOSED"
  boolean noticed: true for noticed before withdrawal, false for not noticed