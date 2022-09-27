package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import ku.cs.shop.models.Account;
import ku.cs.shop.models.AccountList;
import ku.cs.shop.services.AccountListFileDataSource;
import ku.cs.shop.services.ProductListFileDataSource;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StoreController implements Initializable {
    private Account account;
    @FXML private TextField textField;
    private AccountListFileDataSource accountListFileDataSource;
    private AccountList accountList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        account = (Account) com.github.saacsos.FXRouter.getData();
        accountListFileDataSource = new AccountListFileDataSource("csv-data","accounts.csv");
        accountList = accountListFileDataSource.readData();
        account = accountList.getAccount(account.getUsername());
    }

    @FXML
    public void handleConfirmButton(ActionEvent actionEvent){


        if(!textField.getText().equals("")){
            try {
                account.setStore(textField.getText());
                accountListFileDataSource.writeData(accountList);
                com.github.saacsos.FXRouter.goTo("seller", account);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า seller ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }
}
