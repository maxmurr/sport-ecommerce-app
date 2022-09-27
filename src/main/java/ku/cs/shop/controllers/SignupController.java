package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.github.saacsos.FXRouter;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ku.cs.shop.models.Account;
import ku.cs.shop.models.AccountList;
import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.AccountListFileDataSource;

import java.io.IOException;
import java.time.LocalDateTime;

public class SignupController {

    private DataSource<AccountList> dataSource;


    @FXML private Label warningSignup;
    @FXML private TextField nameTextField;
    @FXML private TextField lastnameTextField;
    @FXML private TextField usernameSignupTextField;
    @FXML private TextField passwordSignupTextField;
    @FXML private TextField confirmPasswordTextField;
    @FXML private DatePicker dateOfBirthDatePicker;


    @FXML
    public void initialize()
    {
        dataSource = new AccountListFileDataSource("csv-data","accounts.csv");
        AccountList accountList = dataSource.readData();
        warningSignup.setVisible(false);
    }
    @FXML
    public void handleHomeButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleSignupButton(ActionEvent actionEvent) {
        String name = nameTextField.getText();
        String lastname = lastnameTextField.getText();
        String username = usernameSignupTextField.getText();
        String password = passwordSignupTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();
        String dateOfBirth = dateOfBirthDatePicker.getValue() + "";
        LocalDateTime time = LocalDateTime.now();
        String imagePath = "images/profiledefault.jpg";
        String storeName = null;

        dataSource = new AccountListFileDataSource("csv-data","accounts.csv");
        AccountList accountList = dataSource.readData();


        Account account = new Account(username, password, confirmPassword, name, lastname, dateOfBirth, imagePath, storeName, time);
        switch (accountList.checkField(account)) {
            case 0: // not pass ข้อมูลไม่ครบ
                warningSignup.setVisible(true);
                warningSignup.setText("Please fill out the information completely");

                nameTextField.clear();
                lastnameTextField.clear();
                usernameSignupTextField.clear();
                passwordSignupTextField.clear();
                confirmPasswordTextField.clear();


                break;

            case 1: //  pass ลงทะเบียนสำเร็จ
                if (accountList.usernameNotUse(username)) {
                    warningSignup.setVisible(true);
                    warningSignup.setText("You have signed up successfully");
                    try {
                        {
                            FXRouter.goTo("login");
                        }
                    } catch (IOException e) {
                        System.err.println("ไปที่หน้า login ไม่ได้");
                        System.err.println("ให้ตรวจสอบการกำหนด route");
                    }
                    accountList.addAccount(account);
                    dataSource.writeData(accountList);
                    break;
                }
                warningSignup.setVisible(true);
                warningSignup.setText("Username is already use");

                nameTextField.clear();
                lastnameTextField.clear();
                usernameSignupTextField.clear();
                passwordSignupTextField.clear();
                confirmPasswordTextField.clear();
                break;


            case 2: // ยืนยันรหัสผ่านไม่ตรงกัน
                warningSignup.setVisible(true);
                warningSignup.setText("Password not match");

                nameTextField.clear();
                lastnameTextField.clear();
                usernameSignupTextField.clear();
                passwordSignupTextField.clear();
                confirmPasswordTextField.clear();
                break;

            default:
                break;


        }

    }}




