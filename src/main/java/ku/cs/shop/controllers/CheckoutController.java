package ku.cs.shop.controllers;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import ku.cs.App;
import ku.cs.shop.models.Account;
import ku.cs.shop.models.AccountList;
import ku.cs.shop.models.Product;
import ku.cs.shop.models.ProductList;
import ku.cs.shop.services.AccountListFileDataSource;
import ku.cs.shop.services.ProductListFileDataSource;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CheckoutController implements Initializable{

    @FXML
    private ImageView productImg;
    @FXML private Label productPriceLabel;
    @FXML private Label productNameLabel;
    @FXML private Label total;
    private Product product;
    private Image image;
    @FXML private TextField textField;
    private int amount;
    private int textFieldAmount = 1;
    private int totalAmount;
    @FXML Label left;
    private Parent root;
    private double price;
    private Account account;
    private ProductList productList;
    private ProductListFileDataSource productListFileDataSource;
    @FXML private Pane buyProduct;
    @FXML private Label productDesLabel;
    @FXML private Hyperlink storeName;
    private ProductList storeList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        account = (Account) com.github.saacsos.FXRouter.getData();
        textField.setText("0");
        buyProduct.setVisible(false);
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setInfo(Product product, ProductList productList, ProductListFileDataSource productListFileDataSource){
        this.product = product;
        this.price = product.getPrice();
        this.amount = product.getAmount();
        this.productListFileDataSource = productListFileDataSource;
        this.productList = productList;
        this.storeName.setText(product.getMarketName());
        if(product.getDescription() == null){
            productDesLabel.setText("No description.");
        } else {
            productDesLabel.setText(product.getDescription());
        }
        productNameLabel.setText(product.getName());
        productPriceLabel.setText(String.valueOf(App.CURRENCY + product.getPrice()));
        left.setText(amount + " Left.");
        image = new Image("file:"+product.getPath());
        productImg.setImage(image);
        if(amount == 0){
            textField.setText("0");
        }else {
            textField.setText("1");
        }
    }

    @FXML
    public void handleStoreButton(ActionEvent actionEvent){
        AccountListFileDataSource accountListFileDataSource = new AccountListFileDataSource("csv-data","accounts.csv");
        AccountList accountList = accountListFileDataSource.readData();

        product.getMarketName();
        account = accountList.searchAccountByStoreName(product.getMarketName());
        try {
            com.github.saacsos.FXRouter.goTo("storemarket", account);
            Node n = (Node) actionEvent.getSource();
            Stage stage = (Stage) n.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            System.err.println("ไปที่หน้า storemarket ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


    @FXML
    public void handleProfileButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("profile", account);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า profile ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
        Stage stage1 = (Stage) productImg.getScene().getWindow();
        stage1.close();
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("marketplace");
            Node n = (Node) actionEvent.getSource();
            Stage stage = (Stage) n.getScene().getWindow();
            stage.close();

        } catch (IOException e) {
            System.err.println("ไปที่หน้า marketplace ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleBackButton2(ActionEvent actionEvent) {
        buyProduct.setVisible(false);
    }

    @FXML
    public void handleConfirmButton(ActionEvent actionEvent) {
        if(amount > 0){
            total.setText("Successful!");
            total.setTextFill(Color.GREEN);
            product.buyProduct(textFieldAmount);
            productListFileDataSource.writeData(productList);

            Node n = (Node) actionEvent.getSource();
            Stage stage = (Stage) n.getScene().getWindow();
            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished( event -> buyProduct.setVisible(false) );
            delay.play();
        }
    }

    @FXML
    public void handleAddButton(ActionEvent actionEvent) {
        if(Integer.parseInt(textField.getText()) < amount){
            int num = Integer.parseInt(textField.getText());
            textField.setText(Integer.toString(num+1));
            textFieldAmount += 1;
        }
    }

    @FXML
    public void handleMinusButton(ActionEvent actionEvent){
        if(Integer.parseInt(textField.getText()) > 1){
            int num = Integer.parseInt(textField.getText());
            textField.setText(Integer.toString(num-1));
            textFieldAmount -= 1;
        }
    }

    @FXML
    public void handleBuyButton(ActionEvent actionEvent){
        if(amount > 0) {
            total.setText("Quantity: " + textFieldAmount+"\nTotal Price: "+ App.CURRENCY+ price*textFieldAmount);
            buyProduct.setVisible(true);

        } else {
            total.setText("Out of Stock!");
            total.setTextFill(Color.RED);
            buyProduct.setVisible(true);
        }
    }

}
