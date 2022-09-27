package ku.cs.shop.controllers;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import ku.cs.shop.models.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ku.cs.shop.models.AccountList;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

import ku.cs.shop.services.AccountListFileDataSource;
import ku.cs.shop.services.DataSource;

public class ProfileController {
    private Image image;
    private Account account;
    private DataSource<AccountList> dataSource;
    private AccountList accountList;
    private Account account2;
    private Account selectedAccount;
    @FXML private Label usernameLabel;
    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label shopNameLabel;

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

    @FXML public ImageView userImageView;
    final FileChooser fileChooser = new FileChooser(); // สร้าง Object FileChooser


    @FXML
    public void initialize() {
        dataSource = new AccountListFileDataSource();
        accountList = dataSource.readData();
        account = (Account) com.github.saacsos.FXRouter.getData();
        account2 = accountList.getAccount(account.getUsername());
        image = new Image("file:///"+account2.getImagePath());
        userImageView.setImage(new Image("file:"+account2.getImagePath()));

        usernameLabel.setText(account2.getUsername());
        firstNameLabel.setText(account2.getName());
        lastNameLabel.setText(account2.getLastName());

        if(account2.getStore().equals("null")){
            shopNameLabel.setText("");
        } else {
            shopNameLabel.setText(account2.getStore());
        }
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("marketplace",account);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า marketplace ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleLogoutButton(ActionEvent actionEvent){
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

    @FXML public void handleSubmitChangePassword(){
        Account loginaccount = accountList.getAccount();
        currentPassword = currentPasswordField.getText();
        newPassword = newPasswordField.getText();
        confirmNewPassword = confirmNewPasswordField.getText();
        oldPassword = loginaccount.getPassword();
        loginaccount.setPassword(newPassword);
        loginaccount.setConfirmPassword(newPassword);
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
                loginaccount.setPassword(oldPassword);
                loginaccount.setConfirmPassword(oldPassword);

            }
            if(changePasswordCorrect.equals("The password don't match.")){
                confirmNewPasswordField.setStyle("-fx-border-color: red;");
                newPasswordField.setStyle("-fx-border-color: red;");
                loginaccount.setPassword(oldPassword);
                loginaccount.setConfirmPassword(oldPassword);
            }

            if(changePasswordCorrect.equals("Incorrect Current Password")) {
                currentPasswordField.setStyle("-fx-border-color: red;");
                currentPasswordField.setText("");
                newPasswordField.setText("");
                confirmNewPasswordField.setText("");
                loginaccount.setPassword(oldPassword);
                loginaccount.setConfirmPassword(oldPassword);
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

    @FXML public void handleUploadButton(ActionEvent event){
        FileChooser chooser = new FileChooser();
        // SET FILECHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));
        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) event.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        if (file != null){
            try {
                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("images");
                if (!destDir.exists()) destDir.mkdirs();
                // RENAME FILE
                String[] fileSplit = file.getName().split("\\.");
                String filename = LocalDate.now() + "_"+System.currentTimeMillis() + "."
                        + fileSplit[fileSplit.length - 1];
                Path target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath()+System.getProperty("file.separator")+filename
                );
                System.out.println("97"+ target);
//                account2.setImagePath(target.toUri().toString());
                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING );
                // SET NEW FILE PATH TO IMAGE
                account2.setImagePath(destDir + "/" + filename);
                userImageView.setImage(new Image("file:"+account2.getImagePath()));
                dataSource.writeData(accountList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }






}




