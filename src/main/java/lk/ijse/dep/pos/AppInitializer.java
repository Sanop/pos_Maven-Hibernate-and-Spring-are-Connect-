package lk.ijse.dep.pos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class AppInitializer extends Application {
    private static AnnotationConfigApplicationContext ctx;

    public static AnnotationConfigApplicationContext getApplicationContext(){
        return ctx;
    }

    public static void main(String[] args) {
        ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/MainForm.fxml"));
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("JDBC POS");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }


}
