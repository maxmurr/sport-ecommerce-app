package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class MakerController {
    @FXML private ImageView maxImage;
    @FXML private ImageView kanImage;
    @FXML private ImageView ufaImage;
    @FXML private ImageView namImage;

    @FXML
    public void initialize() {
        String path = getClass().getResource("/ku/cs/images/max_pic.jpg").toExternalForm();
        String path2 = getClass().getResource("/ku/cs/images/kan_pic.jpg").toExternalForm();
        String path3 = getClass().getResource("/ku/cs/images/ufa_pic.jpg").toExternalForm();
        String path4 = getClass().getResource("/ku/cs/images/nam_pic.jpg").toExternalForm();
        maxImage.setImage(new Image(path));
        kanImage.setImage(new Image(path2));
        ufaImage.setImage(new Image(path3));
        namImage.setImage(new Image(path4));
    }

    @FXML
    public void handleLoginButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
