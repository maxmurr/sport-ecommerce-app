package ku.cs.shop.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ku.cs.shop.models.Account;
import ku.cs.shop.models.AccountList;
import ku.cs.shop.services.AccountListFileDataSource;
import ku.cs.shop.services.StringConfiguration;


import java.io.IOException;
import java.util.ArrayList;


public class AdminPageController {

    @FXML private TableView<Account> accountTable;
    @FXML private ImageView image;
    @FXML private Label usernameLabel;
    @FXML private Label nameLabel;
    @FXML private Label lastnameLabel;
    @FXML private Label storeLabel;
    @FXML private Label timeLabel;
    @FXML Pane changePasswordPane;
    @FXML PasswordField currentPasswordField;
    @FXML PasswordField newPasswordField;
    @FXML PasswordField confirmNewPasswordField;
    @FXML Label changePasswordValidate;

    private String newPassword;
    private String confirmNewPassword;
    private String currentPassword;
    private String changePasswordCorrect;
    private String oldPassword;

    private AccountListFileDataSource dataSource;
    private AccountList accountList;
    private ObservableList<Account> accountObservableList;
    private Account selectedAccount;

    public void initialize() {
        dataSource = new AccountListFileDataSource("csv-data","accounts.csv");
        accountList = dataSource.readData();

        clearSelectedAccount();
        showAccountData();

        accountTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedAccount(newValue);
            }
        });
    }

    private void showAccountData() {
        accountTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        accountObservableList = FXCollections.observableArrayList(accountList.toList());
        accountObservableList.remove(accountList.getAccount("admin1"));
        accountTable.setItems(accountObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:UserName", "field:username"));
        configs.add(new StringConfiguration("title:Store", "field:store"));
        configs.add(new StringConfiguration("title:LastLogin", "field:time"));


        for (StringConfiguration conf : configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            accountTable.getColumns().add(col);
            accountTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }
    }


    private void showSelectedAccount(Account account) {
        selectedAccount = account;
        usernameLabel.setText(account.getUsername());
        nameLabel.setText(account.getName());
        lastnameLabel.setText(account.getLastName());
        storeLabel.setText(account.getStore());
        timeLabel.setText(String.valueOf(account.getTimeToString()));
        image.setImage(new Image("file:"+selectedAccount.getImagePath(), true));
    }

    private void clearSelectedAccount() {
        selectedAccount = null;
        usernameLabel.setText("");
        storeLabel.setText("");
        timeLabel.setText("");
        nameLabel.setText("");
        lastnameLabel.setText("");
    }


    @FXML
    public void handleLogoutButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void handleChangePasswordHyperLink(ActionEvent actionEvent) {
        changePasswordPane.setVisible(true);
    }

    public void handleSubmitChangePassword(ActionEvent actionEvent) {
        Account adminac = accountList.getAccount("admin1");
        currentPassword = currentPasswordField.getText();
        newPassword = newPasswordField.getText();
        confirmNewPassword = confirmNewPasswordField.getText();
        oldPassword = adminac.getPassword();
        adminac.setPassword(newPassword);
        adminac.setConfirmPassword(newPassword);
        if ((changePasswordCorrect = accountList.checkCorrect(currentPassword,newPassword,confirmNewPassword,oldPassword)) != null) {
            changePasswordValidate.setText(changePasswordCorrect);
            if(changePasswordCorrect.equals("Field must not empty.")){
                if(currentPasswordField.getText().equals("")){
                    currentPasswordField.setStyle("-fx-border-color: red;");
                }else{
                    currentPasswordField.setStyle("-fx-border-color: none;");
                }
                if(newPasswordField.getText().equals("")){
                    newPasswordField.setStyle("-fx-border-color: red;");
                }else{
                    newPasswordField.setStyle("-fx-border-color: none;");
                }
                if(confirmNewPasswordField.getText().equals("")){
                    confirmNewPasswordField.setStyle("-fx-border-color: red;");
                }else{
                    confirmNewPasswordField.setStyle("-fx-border-color: none;");
                }
                adminac.setPassword(oldPassword);
                adminac.setConfirmPassword(oldPassword);

            }
            if(changePasswordCorrect.equals("The password don't match.")){
                confirmNewPasswordField.setStyle("-fx-border-color: red;");
                newPasswordField.setStyle("-fx-border-color: red;");
                adminac.setPassword(oldPassword);
                adminac.setConfirmPassword(oldPassword);
            }

            if(changePasswordCorrect.equals("Incorrect Current Password")) {
                currentPasswordField.setStyle("-fx-border-color: red;");
                currentPasswordField.setText("");
                newPasswordField.setText("");
                confirmNewPasswordField.setText("");
                adminac.setPassword(oldPassword);
                adminac.setConfirmPassword(oldPassword);
            }
            if(currentPasswordField.equals("The password didn't change")){
                currentPasswordField.setText("");
                newPasswordField.setText("");
                confirmNewPasswordField.setText("");
            }
            if(changePasswordCorrect.equals("Password Change")){
                try {
                    com.github.saacsos.FXRouter.goTo("login");
                } catch (IOException e) {
                    System.err.println("ไปที่หน้า login ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }
            }
        }
        dataSource.writeData(accountList);
    }

    @FXML public void handleCancelChangePasswordHyperLink(){
        changePasswordPane.setVisible(false);
        currentPasswordField.setStyle("-fx-border-color: none;");
        newPasswordField.setStyle("-fx-border-color: none;");
        confirmNewPasswordField.setStyle("-fx-border-color: none;");
        currentPasswordField.setText("");
        newPasswordField.setText("");
        confirmNewPasswordField.setText("");
    }
}

