package ku.cs.shop.models;

import javafx.scene.image.Image;
import ku.cs.shop.services.SortLogInTime;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

// รับข้อมูลมาจาก class account
public class AccountList {
    private ArrayList<Account> accountData;
    private  Account account;
    public AccountList() {
        accountData = new ArrayList<>();}

    public void addAccount(Account account) { accountData.add(account);}


    public boolean checkUsername(Account ac,String username){
        if (ac.getUsername().equals(username)){return true;}
        return false;
    }

    public  boolean checkPassword(Account ac,String password){
        if (ac.getPassword().equals(password)) {
//            System.out.println(ac.getName());
            return true;}
        return false;
    }

    public boolean checkStoreName(Account ac,String store){
        if (ac.getStore().equals(store)) {
            return true;
        }
        return false;
    }

    public Account searchAccountByStoreName(String storeName){
        for (Account ac : accountData) {
            if (this.checkStoreName(ac,storeName)) {
                account = ac;
                return ac;
            }
        }
        return null;
    }

    // ใส่ข้อมูลไม่ครบ signup
    public boolean checkData(Account ac) {
        if (ac.getUsername().isEmpty() || ac.getPassword().isEmpty() || ac.getName().isEmpty() || ac.getLastName().isEmpty() || ac.getConfirmPassword().isEmpty() || ac.getDateOfBirth().isEmpty()) {
            return false;
        }
        return true;
    }
    // // ใส่ข้อมูลไม่ครบ log in
    public boolean checkLogin(Account ac){
        if (ac.getUsername().isEmpty() || ac.getPassword().isEmpty() ) {
            return false;
        }
        return true;

    }


    // สำหรับใช้ switch case
    public int checkField(Account ac) {
        if (this.checkData(ac)) {
            if (ac.getConfirmPassword().equals(ac.getPassword())) { return 1;}
            return 2;
        } else return 0;
    }


    public Account searchAccount(String username,String password){
        for (Account ac : accountData) {
            if (this.checkUsername(ac,username) && this.checkPassword(ac,password)) {
                account = ac;
                return ac;
            }
        }
        return null;
    }

    public Account searchAccountByUsername(String username){
        for (Account ac : accountData) {
            if (this.checkUsername(ac,username)) {
                account = ac;
                return ac;
            }
        }
        return null;
    }

    public boolean checkAccount(String username,String password){
        for (Account ac : accountData) {
            if (this.checkUsername(ac,username) &&  this.checkPassword(ac,password)) {
                account = ac;
                return true;
            }
        }
        return false;
    }

    public Account getAccount(String username){
        for (Account ac : accountData) {
            if (this.checkUsername(ac,username)) {
                account = ac;
                return account;
            }
        }
        return null;
    }
    //Check username ไม่ซ้ำ
    public boolean usernameNotUse(String username){
        for (Account ac : accountData){
            if(this.checkUsername(ac, username)) return false;
        }
        return true;
    }


    public Account getAccount() {return account;}

    // รายชื่อ account Staff ทั้งหมด
    public ArrayList<Account> toList() { return accountData; } // รายชื่อ account Staff ทั้งหมด



    public String toString(){
        return "Account: " +  accountData.toString();
    }


    public String toCsv(){
        String result = "";
        for (Account account: this.accountData){
            result += account.toCsv() + "\n";
        }
        return result;
    }

    public void sort() {
        accountData.sort(new SortLogInTime());
    }

    public int countAccount() {
        return accountData.size();
    }

    public String checkCorrect(String currentPassword,String newPassword,String confirmNewPassword,String oldPassword) {
        if(newPassword.isEmpty() || confirmNewPassword.isEmpty() || currentPassword.isEmpty()){
            return "Field must not empty.";
        }
        if(!currentPassword.matches(oldPassword))
            return "Incorrect Current Password";
        if (!confirmNewPassword.matches(newPassword))
            return "The password don't match.";
        if(newPassword.equals(oldPassword))
            return "The password didn't change";
        else
            return "Password Change";
    }
}







