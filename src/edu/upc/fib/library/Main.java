package edu.upc.fib.library;

import edu.upc.fib.library.controllers.Home;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    private Home home;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Font.loadFont(getClass().getResource("/fonts/VarelaRound-Regular.ttf").toExternalForm(), 10);
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));

        FXMLLoader fxmlLoader = new FXMLLoader();
        //Pane p = fxmlLoader.load(getClass().getResource("foo.fxml").openStream());
        Parent root = fxmlLoader.load(getClass().getResource("/fxml/home.fxml").openStream());
        home = fxmlLoader.getController();

        primaryStage.getIcons().add(new Image("/imgs/book.png"));
        primaryStage.setTitle("Biblioteca");
        primaryStage.setScene(new Scene(root, 1190, 670));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() {
        home.saveStatus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}