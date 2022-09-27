package ku.cs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import com.github.saacsos.FXRouter;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static final String CURRENCY = "฿";

    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "tothemoon", 800, 600);
        configRoute();
        FXRouter.goTo("login");
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static void configRoute() {
        String packageStr = "ku/cs/";
        com.github.saacsos.FXRouter.when("login",packageStr+"login.fxml");
        com.github.saacsos.FXRouter.when("signup",packageStr+"signup_page.fxml");
        com.github.saacsos.FXRouter.when("seller",packageStr+"seller.fxml");
        com.github.saacsos.FXRouter.when("profile",packageStr+"profile.fxml");
        com.github.saacsos.FXRouter.when("marketplace",packageStr+"marketplace.fxml");
        com.github.saacsos.FXRouter.when("checkout",packageStr+"checkout.fxml");
        com.github.saacsos.FXRouter.when("buy",packageStr+"buy.fxml");
        com.github.saacsos.FXRouter.when("admin",packageStr+"admin.fxml");
        com.github.saacsos.FXRouter.when("maker",packageStr+"maker.fxml");
        com.github.saacsos.FXRouter.when("store",packageStr+"store.fxml");
        com.github.saacsos.FXRouter.when("sellercreate",packageStr+"sellercreate.fxml");
        com.github.saacsos.FXRouter.when("storemarket",packageStr+"storemarket.fxml");
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}