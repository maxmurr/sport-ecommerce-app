package ku.cs.shop.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Account {
    private String username;
    private String password;
    private String confirmPassword;
    private String name;
    private String lastName;
    private String dateOfBirth;
    private String imagePath;
    private LocalDateTime time;
    private String storeName;

    public Account(String username, String password, String confirmPassword, String name, String lastName, String dateOfBirth, String imagePath, String storeName, LocalDateTime time) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.name = name;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.imagePath = imagePath;
        this.storeName = storeName;
        this.time = time;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStore(String storeName) {
        this.storeName = storeName;
    }

    public String getStore(){
        return storeName;
    }

    //last login
    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = LocalDateTime.now();
    }

    // เพื่อเขียนไฟล์ใน csv-data (accounts.csv)
    public String toCsv(){
        return username +  "," + password + "," + confirmPassword +
                "," + name + "," + lastName + "," + dateOfBirth+
        "," + imagePath + "," + storeName + "," + time.format(DateTimeFormatter.ofPattern("HH:mm-dd-MM-yyyy"));
    }

    public String getTimeToString() {
        String pattern = "HH:mm-dd-MM-yyyy";
        DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern(pattern);
        return simpleDateFormat.format(time);
    }

}















