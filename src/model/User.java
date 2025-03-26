package model;
import java.util.UUID;
import java.time.Instant;

/** Class that defines and handles authentication for each user of the Task Manager*/
public class User {

    public enum AccountType {
        CUSTOMER,
        ADMIN;
    }
    private final AccountType accountType;
    private final String userUniqueId;
    private String userName;
    private String password;
    private final Instant timeStamp;
    private String fullName;
    private String emailAddress;

    /**Validator Methods*/
    private void validateUserName(String userName) {
       if (userName== null || userName.trim().isEmpty()) {
           throw new IllegalArgumentException("UserName can't be Null or Empty.");
       }
    }

    private void validatePassword(String password) {
       if (password == null || password.trim().isEmpty()) {
           throw new IllegalArgumentException("Password can't be Null or Empty.");
       } else if (password.length() > 20) {
           throw new IllegalArgumentException("Password must be a maximum 0f 20 Characters.");
       } else if (password.contains(" ")) {
           throw new IllegalArgumentException("Password cannot contain spaces.");
       }
    }


    private void validateFullName(String fullName) {
       if (fullName == null || fullName.trim().isEmpty()) {
           throw new IllegalArgumentException("Full Name can't be Null or Empty");
       }
    }

    private void validateEmailAddress(String emailAddress) {
       if (emailAddress == null || emailAddress.trim().isEmpty()) {
           throw new IllegalArgumentException("Email can't be Null or Empty");
       }

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!emailAddress.matches(emailRegex)) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private void validateAccountType(AccountType accountType) {
        if ((accountType != AccountType.ADMIN)) {
            throw new IllegalArgumentException("AccountType must be Admin.");
        }
    }

    /** User Constructor */
    public User(String userName, String password, String fullName, String emailAddress) {

        this.userUniqueId = UUID.randomUUID().toString();
        this.accountType = AccountType.CUSTOMER;
        validateUserName(userName);
        validatePassword(password);
        validateFullName(fullName);
        validateEmailAddress(emailAddress);

        this.userName = userName;
        this.password = password;
        this.timeStamp = Instant.now();
        this.fullName = fullName;
        this.emailAddress = emailAddress;

    }
    /** Admin Overloaded Constructor */
    public User(String userName, String password, String fullName, String emailAddress,  AccountType accountType) {
        this.userUniqueId = UUID.randomUUID().toString();
        validateAccountType(accountType);
        validateUserName(userName);
        validatePassword(password);
        validateFullName(fullName);
        validateEmailAddress(emailAddress);
        this.accountType = accountType;
        this.userName = userName;
        this.password = password;
        this.timeStamp = Instant.now();
        this.fullName = fullName;
        this.emailAddress = emailAddress;

    }

    /**Authentication Methods*/
    public boolean verifyPassword(String enteredPassword) {
        return this.password.equals(enteredPassword);
    }

    public boolean verifyUser(String inputUserName, String inputPassword) {
        return this.userName.equals(inputUserName) && this.password.equals(inputPassword);
    }

    /**Getters*/
    public String getUniqueId() {
        return userUniqueId;
    }

    public String getUserName() {
        return userName;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    /**Setters*/
    public void setUserName(String userName) {
        validateUserName(userName);
        this.userName = userName;
    }

    public void setPassword(String password) {
        validatePassword(password);
        this.password = password;
    }


    public void setFullName(String fullName) {
        validateFullName(fullName);
        this.fullName = fullName;
    }

    public void setEmailAddress(String emailAddress) {
        validateEmailAddress(emailAddress);
        this.emailAddress = emailAddress;
    }

    /** Helper methods */
    public boolean isAdmin() {
        return this.accountType == AccountType.ADMIN;
    }

    /**Override Methods*/
    @Override
    public String toString() {
        return "User{" +
                "uniqueId='" + userUniqueId + '\'' +
                ", userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userUniqueId.equals(user.userUniqueId);
    }

    @Override
    public int hashCode() {
        return userUniqueId.hashCode();
    }

}
