package ku.cs.shop.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.github.saacsos.FXRouter;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import ku.cs.shop.models.Account;
import ku.cs.shop.models.Product;
import ku.cs.shop.models.ProductList;
import ku.cs.shop.services.PriceComparatorHighToLow;
import ku.cs.shop.services.PriceComparatorLowToHigh;
import ku.cs.shop.services.ProductListFileDataSource;
import ku.cs.shop.services.SelectedItems;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SellerController {


    @FXML private Label storeName;
    @FXML private ImageView myStoreProfile;


    @FXML
    private TextField productNameTextField;

    @FXML
    private ImageView productShowImageView;

    @FXML
    private TextField  productAmountTextField;

    @FXML
    private TextField productPriceTextField;

    @FXML
    private TextField searchProductTextField;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;
    private Parent root;



    @FXML
    private ListView<Product> productsListView;
    private SelectedItems selectedItems;

    private ProductListFileDataSource productDataSource;
    private ProductList productList;
    private ProductList storeList;
    private Product selectedProduct;
    private Product unSaveSelectedProduct;
    private Account account;
    @FXML private Label storeNameLabel;


    @FXML
    public void initialize(){

        account = (Account) com.github.saacsos.FXRouter.getData();
        storeNameLabel.setText(account.getStore());

        showSelectedProduct(account);
        showListView(account);


    }



    private void showSelectedProduct(Account account){

        productDataSource = new ProductListFileDataSource("csv-data","products.csv");
        productList = productDataSource.readData();
        storeList = new ProductList();

        for (Product product: productList.getAllProducts()){

            if (product.getMarketName().equals(account.getStore())){
                storeList.addProduct(product);
            }
        }




        selectedItems = new SelectedItems() {
            @Override
            public void selectedByClick(Product product) {
                selectedProduct = product;
                unSaveSelectedProduct = new Product();

                try {
                    String str = account.getUsername()+","+selectedProduct.getName();

                    com.github.saacsos.FXRouter.goTo("sellercreate" ,str);
                } catch (IOException e) {
                    System.err.println("ไปที่หน้า sellercreate ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }

            }
        };
    }




    private void showListView(Account account){
        productDataSource = new ProductListFileDataSource("csv-data","products.csv");
        productList = productDataSource.readData();
        storeList = new ProductList();

        for (Product product: productList.getAllProducts()){

            if (product.getMarketName().equals(account.getStore())){
                storeList.addProduct(product);
            }
        }

        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();
        grid.getChildren().clear();
        grid.setGridLinesVisible(false);

        int row = 1;
        int column = 0;
        try{
            for (int i=0; i < storeList.countProduct(); i++){
                Product product = storeList.getProduct(i);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ku/cs/product.fxml"));


                AnchorPane anchorPane = fxmlLoader.load();


                ProductCardController productController = fxmlLoader.getController();
                productController.setDataProduct(product, selectedItems);

                if (column == 3){
                    column = 0;
                    ++row;
                }

                grid.add(anchorPane, column++, row);


                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_COMPUTED_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_COMPUTED_SIZE);

                GridPane.setMargin(anchorPane,new Insets(3));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void newest(ActionEvent event){
        storeList.clear();

        productDataSource = new ProductListFileDataSource("csv-data","products.csv");
        productList = productDataSource.readData();
        storeList = new ProductList();

        for (Product product: productList.getAllProducts()){

            if (product.getMarketName().equals(account.getStore())){
                storeList.addProduct(product);
            }
        }

        Node node = grid.getChildren().get(0);
        grid.getChildren().clear();
        grid.getChildren().add(0,node);

        int row = 1;
        int column = 0;
        try{
            for (int i=0; i < storeList.countProduct(); i++){
                Product product = storeList.getProduct(i);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ku/cs/product.fxml"));


                AnchorPane anchorPane = fxmlLoader.load();


                ProductCardController productController = fxmlLoader.getController();
                productController.setDataProduct(product, selectedItems);

                if (column == 3){
                    column = 0;
                    ++row;
                }

                grid.add(anchorPane, column++, row);


                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_COMPUTED_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_COMPUTED_SIZE);

                GridPane.setMargin(anchorPane,new Insets(3));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void lowToHigh(ActionEvent event){

        productDataSource = new ProductListFileDataSource("csv-data","products.csv");
        productList = productDataSource.readData();
        storeList = new ProductList();

        for (Product product: productList.getAllProducts()){

            if (product.getMarketName().equals(account.getStore())){
                storeList.addProduct(product);
            }
        }

        List<Product> list = storeList.getAllProducts();


        Collections.sort(list, new PriceComparatorLowToHigh());
        Node node = grid.getChildren().get(0);
        grid.getChildren().clear();
        grid.getChildren().add(0,node);

        int row = 1;
        int column = 0;
        try{
            for (int i=0; i < storeList.countProduct(); i++){
                Product product = storeList.getProduct(i);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ku/cs/product.fxml"));


                AnchorPane anchorPane = fxmlLoader.load();


                ProductCardController productController = fxmlLoader.getController();
                productController.setDataProduct(product, selectedItems);

                if (column == 3){
                    column = 0;
                    ++row;
                }

                grid.add(anchorPane, column++, row);


                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_COMPUTED_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_COMPUTED_SIZE);

                GridPane.setMargin(anchorPane,new Insets(3));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML private void highToLow(ActionEvent event){
        productDataSource = new ProductListFileDataSource("csv-data","products.csv");
        productList = productDataSource.readData();
        storeList = new ProductList();

        for (Product product: productList.getAllProducts()){

            if (product.getMarketName().equals(account.getStore())){
                storeList.addProduct(product);
            }
        }

        List<Product> list = storeList.getAllProducts();


        Collections.sort(list, new PriceComparatorHighToLow());
        Node node = grid.getChildren().get(0);
        grid.getChildren().clear();
        grid.getChildren().add(0,node);

        int row = 1;
        int column = 0;
        try{
            for (int i=0; i < storeList.countProduct(); i++){
                Product product = storeList.getProduct(i);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ku/cs/product.fxml"));


                AnchorPane anchorPane = fxmlLoader.load();


                ProductCardController productController = fxmlLoader.getController();
                productController.setDataProduct(product, selectedItems);

                if (column == 3){
                    column = 0;
                    ++row;
                }

                grid.add(anchorPane, column++, row);


                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_COMPUTED_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_COMPUTED_SIZE);

                GridPane.setMargin(anchorPane,new Insets(3));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void handleAddButton(){

        try {
            String str = account.getUsername()+","+"null";

            com.github.saacsos.FXRouter.goTo("sellercreate", str);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า sellercreate ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


    @FXML
    public void searchProduct(){
        productDataSource = new ProductListFileDataSource("csv-data","products.csv");
        productList = productDataSource.readData();
        storeList = new ProductList();

        for (Product product: productList.getAllProducts()){
            if (product.getMarketName().equals(account.getStore())){
                storeList.addProduct(product);
            }
        }
        String searchName = searchProductTextField.getText();
        Product product = storeList.searchProductByName(searchName);
        if (product != null){
            storeList.clear();
            storeList.addProduct(product);
            Node node = grid.getChildren().get(0);
            grid.getChildren().clear();
            grid.getChildren().add(0,node);

            int row = 1;
            int column = 0;
            try{
                for (int i=0; i < storeList.countProduct(); i++){
                    product = storeList.getProduct(i);
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/ku/cs/product.fxml"));


                    AnchorPane anchorPane = fxmlLoader.load();


                    ProductCardController productController = fxmlLoader.getController();
                    productController.setDataProduct(product, selectedItems);

                    if (column == 3){
                        column = 0;
                        ++row;
                    }

                    grid.add(anchorPane, column++, row);


                    grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                    grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    grid.setMaxWidth(Region.USE_COMPUTED_SIZE);

                    grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    grid.setMaxHeight(Region.USE_COMPUTED_SIZE);

                    GridPane.setMargin(anchorPane,new Insets(3));

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        searchProductTextField.clear();
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("marketplace");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า marketplace ไม่ได้");
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
    }

}
