package ku.cs.shop.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import ku.cs.shop.models.Account;
import ku.cs.shop.models.Product;
import ku.cs.shop.models.ProductList;
import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.ProductListFileDataSource;
import ku.cs.shop.services.ProductListHardcodeDataSource;
import ku.cs.shop.services.SelectedItems;

public class ProductCardController {

    @FXML
    private Label productNameLabel;

    @FXML
    private Label productAmountLabel;

    @FXML
    private Label productPriceLabel;

    @FXML
    private ImageView productImageView;

    @FXML
    private VBox productBox;

    @FXML
    private Circle circle;

    @FXML void click(MouseEvent mouseEvent){
        selectedItems.selectedByClick(product);
    }

    private  Product product;
    private SelectedItems selectedItems;

    public void setDataProduct(Product product, SelectedItems selectedItems){

        this.product = product;
        this.selectedItems = selectedItems;
        if(product.checkLowAmount()){
            circle.setStroke(Color.RED);
        }else {
            circle.setStroke(Color.GREEN);
        }

        productImageView.setImage(new Image("file:"+product.getPath()));
        productNameLabel.setText(product.getName());
        productAmountLabel.setText(product.getAmount()+"");
        productPriceLabel.setText(product.getPrice()+"Baht.");
    }


}
