package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.github.saacsos.FXRouter;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ku.cs.shop.models.Account;
import ku.cs.shop.models.AccountList;
import  ku.cs.shop.services.DataSource;
import ku.cs.shop.services.AccountListFileDataSource;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoginController {

    private  DataSource<AccountList> dataSource;

    @FXML private Label warningLabel;
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    private LocalDateTime time;

    @FXML
    public void initialize()
    {
        dataSource = new AccountListFileDataSource();
        AccountList accountList = dataSource.readData();
        warningLabel.setVisible(false);

    }

    @FXML
    public void handleLogInButton(ActionEvent actionEvent) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        dataSource = new AccountListFileDataSource();
        AccountList accountList = dataSource.readData();

        Account account = new Account(username, password);
        if (!accountList.checkLogin(account)) {
            warningLabel.setVisible(true);
            warningLabel.setText("Please fill out the information completely");

            usernameTextField.clear();
            passwordTextField.clear();
        } else {
            if (accountList.checkAccount(username, password)) {
                Account loginAccount =  accountList.searchAccount(username, password);
                try {
                    if(username.equals("admin1")&&password.equals(password)){
                        FXRouter.goTo("admin");
                    }else {
                        FXRouter.goTo("marketplace", account);
                        usernameTextField.clear();
                        passwordTextField.clear();
                    }
                    loginAccount.setTime(time);
                    dataSource.writeData(accountList);
                } catch (IOException e) {
                    System.err.println("ไปที่หน้า marketplace ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }

            } else {
                warningLabel.setVisible(true);
                warningLabel.setText("Incorrect username or password entered. Please try again.");
                usernameTextField.clear();
                passwordTextField.clear();
            }
        }

    }


    @FXML
    public void handleGoToSignUpButton(ActionEvent actionEvent){
        try {
            FXRouter.goTo("signup");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า signup ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


    @FXML
    public void handleMakerButton(ActionEvent actionEvent){
        try {
            FXRouter.goTo("maker");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า maker ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
