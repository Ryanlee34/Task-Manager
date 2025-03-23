package model;
import java.util.UUID;
import java.time.Instant;

/** Class that defines and handles authentication for each user of the Task Manager*/
public class User {

    private String uniqueId;
    private String userName;
    private String password;
    private Instant timeStamp;
    private String fullName;
    private String emailAddress;

    /**Validator Methods*/
    private void validateUserName(String userName) {
       if (userName== null || userName.trim().isEmpty()) {
           throw new IllegalArgumentException("UserName can't be Null or Empty");
       }
    }

    private void validatePassword(String password) {
       if (password == null || password.trim().isEmpty()) {
           throw new IllegalArgumentException("Password can't be Null or Empty");
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

    /** Constructor */
    public User(String userName, String password, String fullName, String emailAddress) {

        this.uniqueId = UUID.randomUUID().toString();

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

    /**Authentication Methods*/
    public boolean verifyPassword(String enteredPassword) {
        return this.password.equals(enteredPassword);
    }

    public boolean verifyUser(String inputUserName, String inputPassword) {
        return this.userName.equals(inputUserName) && this.password.equals(inputPassword);
    }

    /**Getters*/
    public String getUniqueId() {
        return uniqueId;
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

    /**Override Methods*/
    @Override
    public String toString() {
        return "User{" +
                "uniqueId='" + uniqueId + '\'' +
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

        return uniqueId.equals(user.uniqueId);
    }

    @Override
    public int hashCode() {
        return uniqueId.hashCode();
    }

}
