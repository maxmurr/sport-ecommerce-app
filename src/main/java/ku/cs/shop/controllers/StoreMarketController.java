package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import ku.cs.shop.models.Account;
import ku.cs.shop.models.AccountList;
import ku.cs.shop.models.Product;
import ku.cs.shop.models.ProductList;
import ku.cs.shop.services.*;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class StoreMarketController implements Initializable{
        @FXML
        private GridPane gridPane;
        private ProductList productList;

        private Listener listener;
        private Parent root;
        private ProductListFileDataSource productDataSource;
        private Account account;
        private AccountList accountList;
        private AccountListFileDataSource dataSource;
        private ProductList storeList;
        @FXML private Label storeName;

        @Override
        public void initialize(URL url, ResourceBundle rb) {
            gridPane.getColumnConstraints().clear();
            gridPane.getRowConstraints().clear();
            gridPane.getChildren().clear();
            gridPane.setGridLinesVisible(false);

            account = (Account) com.github.saacsos.FXRouter.getData();
            dataSource = new AccountListFileDataSource("csv-data","accounts.csv");
            accountList = dataSource.readData();
            account = accountList.getAccount(account.getUsername());
            storeName.setText(account.getStore());
            getData();

            if(storeList.countProduct() > 0){
                listener = new Listener() {
                    @Override
                    public void onClickListener(Product product) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/checkout.fxml"));
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        CheckoutController checkoutController = loader.getController();
                        checkoutController.setInfo(product, storeList, productDataSource);

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setTitle("tothemoon");
                        stage.show();

                        Stage stage1 = (Stage) gridPane.getScene().getWindow();
                        stage1.close();
                    }
                };
            }


            int columns = 0;
            int row = 1;

            try {
                for(int i=0; i<storeList.countProduct(); i++) {
                    Product product = storeList.getProduct(i);
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/ku/cs/productmarket.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    ProductController productController = fxmlLoader.getController();
                    productController.setData(product, listener);

                    if(columns == 4){
                        columns = 0;
                        ++row;
                    }

                    gridPane.add(anchorPane, columns++, row);

                    gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setMaxWidth(Region.USE_COMPUTED_SIZE);

                    gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                    gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    gridPane.setMaxHeight(Region.USE_COMPUTED_SIZE);

                    GridPane.setMargin(anchorPane, new Insets(3));

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void getData(){
            productDataSource = new ProductListFileDataSource("csv-data","products.csv");
            productList = productDataSource.readData();
            storeList = new ProductList();

            System.out.println(account.getStore());
            for (Product product: productList.getAllProducts()){
                if (product.getMarketName().equals(account.getStore())){
                    storeList.addProduct(product);
                }
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
            List<Product> products = storeList.getAllProducts();

            Node node = gridPane.getChildren().get(0);
            gridPane.getChildren().clear();
            gridPane.getChildren().add(0,node);

            int columns = 0;
            int row = 1;

            try {
                for(int i=0; i<storeList.countProduct(); i++) {
                    Product product = storeList.getProduct(i);
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/ku/cs/productmarket.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    ProductController productController = fxmlLoader.getController();
                    productController.setData(product, listener);

                    if(columns == 4){
                        columns = 0;
                        ++row;
                    }

                    gridPane.add(anchorPane, columns++, row);

                    gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setMaxWidth(Region.USE_COMPUTED_SIZE);

                    gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                    gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    gridPane.setMaxHeight(Region.USE_COMPUTED_SIZE);

                    GridPane.setMargin(anchorPane, new Insets(3));
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
            List<Product> products = storeList.getAllProducts();

            Collections.sort(products, new PriceComparatorLowToHigh());
            Node node = gridPane.getChildren().get(0);
            gridPane.getChildren().clear();
            gridPane.getChildren().add(0,node);

            int columns = 0;
            int row = 1;

            try {
                for(int i=0; i<storeList.countProduct(); i++) {
                    Product product = storeList.getProduct(i);
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/ku/cs/productmarket.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    ProductController productController = fxmlLoader.getController();
                    productController.setData(product, listener);

                    if(columns == 4){
                        columns = 0;
                        ++row;
                    }

                    gridPane.add(anchorPane, columns++, row);

                    gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setMaxWidth(Region.USE_COMPUTED_SIZE);

                    gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                    gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    gridPane.setMaxHeight(Region.USE_COMPUTED_SIZE);

                    GridPane.setMargin(anchorPane, new Insets(3));
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
            List<Product> products = storeList.getAllProducts();

            Collections.sort(products, new PriceComparatorHighToLow());
            Node node = gridPane.getChildren().get(0);
            gridPane.getChildren().clear();
            gridPane.getChildren().add(0,node);

            int columns = 0;
            int row = 1;

            try {
                for(int i=0; i<storeList.countProduct(); i++) {
                    Product product = storeList.getProduct(i);
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/ku/cs/productmarket.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    ProductController productController = fxmlLoader.getController();
                    productController.setData(product, listener);

                    if(columns == 4){
                        columns = 0;
                        ++row;
                    }

                    gridPane.add(anchorPane, columns++, row);

                    gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setMaxWidth(Region.USE_COMPUTED_SIZE);

                    gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                    gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    gridPane.setMaxHeight(Region.USE_COMPUTED_SIZE);

                    GridPane.setMargin(anchorPane, new Insets(3));
                }
            } catch (IOException e) {
                e.printStackTrace();
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

        @FXML
        public  void handleBackButton(ActionEvent actionEvent){
            try {
                com.github.saacsos.FXRouter.goTo("marketplace", account);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า marketplace ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }

    }

