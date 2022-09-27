package ku.cs.shop.controllers;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import ku.cs.App;
import ku.cs.shop.models.Product;
import ku.cs.shop.models.ProductList;
import ku.cs.shop.services.Listener;
import ku.cs.shop.services.ProductListFileDataSource;

import java.io.IOException;

public class BuyProductController {
    @FXML private Label total;
    private double price;
    private int amount;
    private int textFieldAmount;
    private Parent root;
    private Product product;
    PauseTransition delay = new PauseTransition(Duration.seconds(5));
    private ProductListFileDataSource productDataSource;
    ProductList productList;

    public void setInfo(Product product, int amount, ProductList productList, ProductListFileDataSource productListFileDataSource){
        this.product = product;
        this.price = product.getPrice();
        this.textFieldAmount = amount;
        this.productDataSource = productListFileDataSource;
        this.productList = productList;
        this.amount = product.getAmount();
        total.setText("Quantity: " + amount+"\nTotal Price: "+ App.CURRENCY+ price*amount);
    }


    public void setText(String text){
        total.setText(text);
        total.setTextFill(Color.RED);
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleConfirmButton(ActionEvent actionEvent) {
        if(amount > 0){
            total.setMaxHeight(50);
            total.setMaxHeight(50);
            total.setText("Successful!");
            total.setTextFill(Color.GREEN);
            product.buyProduct(textFieldAmount);
            productDataSource.writeData(productList);
            System.out.println(product.getAmount());

            Node n = (Node) actionEvent.getSource();
            Stage stage = (Stage) n.getScene().getWindow();
            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished( event -> stage.close() );
            delay.play();

            try {
                com.github.saacsos.FXRouter.goTo("marketplace");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า marketplace ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }

        }
    }
}
