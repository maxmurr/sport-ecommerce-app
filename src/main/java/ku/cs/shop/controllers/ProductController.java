package ku.cs.shop.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import ku.cs.App;
import ku.cs.shop.models.Product;
import ku.cs.shop.services.Listener;

public class ProductController {

    @FXML
    private ImageView itemImage;
    @FXML private Label itemPriceLabel;
    @FXML private Label itemNameLabel;
    private Product product;
    private Listener listener;

    @FXML private void click(MouseEvent mouseEvent){
        listener.onClickListener(product);
    }

    public void setData(Product product, Listener myListener){
        this.product = product;
        this.listener = myListener;
        itemNameLabel.setText(product.getName());
        itemPriceLabel.setText(String.valueOf(App.CURRENCY + product.getPrice()));
        Image image = new Image("file:"+product.getPath());
        itemImage.setImage(image);
    }
}
