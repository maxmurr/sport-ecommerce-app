package ku.cs.shop.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import ku.cs.App;
import ku.cs.shop.models.Account;
import ku.cs.shop.models.AccountList;
import ku.cs.shop.models.Product;
import ku.cs.shop.models.ProductList;
import ku.cs.shop.services.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class SellerCreateController {

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
    private TextField productDescriptionTextField;

    @FXML
    private TextField searchProductTextField;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;



    @FXML
    private ListView<Product> productsListView;
    private SelectedItems selectedItems;

    private ProductListFileDataSource productDataSource;
    private ProductList productList;
    private Product selectedProduct;
    private Product unSaveSelectedProduct;
    private Account account;
    private String str;
    @FXML private TextField lowAmountTextField;
    @FXML private Label warningLabel;

    @FXML private Label storeNameLabel;


    @FXML
    public void initialize(){
        warningLabel.setVisible(false);
        str = (String) com.github.saacsos.FXRouter.getData();
        String[] data = str.split(",");
        productDataSource = new ProductListFileDataSource("csv-data","products.csv");
        productList = productDataSource.readData();
        AccountListFileDataSource dataSource = new AccountListFileDataSource("csv-data", "accounts.csv");
        AccountList accountList = dataSource.readData();
        account = accountList.searchAccountByUsername(data[0]);

        if (data[1].equals("null") ){
            selectedProduct = new Product();
            productList.addProduct(selectedProduct);

        }else {
            selectedProduct = productList.searchProductByName(data[1]);
            selectedProduct = productList.searchProductByName(selectedProduct.getName());

            unSaveSelectedProduct = new Product();

            lowAmountTextField.setText(selectedProduct.getLowAmount() + "");
            productNameTextField.setText(selectedProduct.getName());
            productShowImageView.setImage(new Image("file:" + selectedProduct.getPath()));
            productAmountTextField.setText(selectedProduct.getAmount() + "");
            productPriceTextField.setText(selectedProduct.getPrice() + "");
            productDescriptionTextField.setText(selectedProduct.getDescription());

            unSaveSelectedProduct.setLowAmount(Integer.parseInt(lowAmountTextField.getText()));
            unSaveSelectedProduct.setName(productNameTextField.getText());
            unSaveSelectedProduct.setAmount(Integer.parseInt(productAmountTextField.getText()));
            unSaveSelectedProduct.setPrice(Double.parseDouble(productPriceTextField.getText()));
            unSaveSelectedProduct.setPath(selectedProduct.getPath());
            unSaveSelectedProduct.setDescription(productDescriptionTextField.getText());
        }

    }



    @FXML
    public void handleUpdateButton(ActionEvent actionEvent){
        if((!productNameTextField.getText().equals("") && !productAmountTextField.getText().equals("") && !lowAmountTextField.getText().equals("") &&
                !productDescriptionTextField.getText().equals("") && !productPriceTextField.getText().equals(""))){

            selectedProduct.setName(productNameTextField.getText());
            selectedProduct.setAmount(Integer.parseInt(productAmountTextField.getText()));
            selectedProduct.setPrice(Double.parseDouble(productPriceTextField.getText()));
            selectedProduct.setDescription(productDescriptionTextField.getText());
            selectedProduct.setMarketName(account.getStore());
            selectedProduct.setLowAmount(Integer.parseInt(lowAmountTextField.getText()));
            productDataSource.writeData(productList);

            try {
                com.github.saacsos.FXRouter.goTo("seller");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า seller ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }

        } else {
            warningLabel.setText("Information incomplete!");
            warningLabel.setTextFill(Color.RED);
            warningLabel.setVisible(true);
        }


    }

    @FXML
    private void handleResetButton(ActionEvent actionEvent){
        productNameTextField.setText(unSaveSelectedProduct.getName());

        lowAmountTextField.setText(unSaveSelectedProduct.getLowAmount()+"");
        productShowImageView.setImage(new Image("file:"+unSaveSelectedProduct.getPath()));
        selectedProduct.setPath(unSaveSelectedProduct.getPath());
        productAmountTextField.setText(unSaveSelectedProduct.getAmount()+"");
        productPriceTextField.setText(unSaveSelectedProduct.getPrice()+"");
        productDescriptionTextField.setText(unSaveSelectedProduct.getDescription());
    }

    @FXML
    private void handleUploadButton(ActionEvent event){
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
                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING );
                // SET NEW FILE PATH TO IMAGE
                productShowImageView.setImage(new Image(target.toUri().toString()));
                selectedProduct.setPath(destDir + "/" + filename);



            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("seller");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า seller ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


}
