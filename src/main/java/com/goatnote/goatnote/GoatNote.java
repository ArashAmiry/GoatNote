package com.goatnote.goatnote;

import com.goatnote.goatnote.models.CalculatoraAPI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import okhttp3.Response;


import java.io.File;
import java.io.IOException;

public class GoatNote extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("canvas.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 800);
        stage.setTitle("GoatNote");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        Response response = CalculatoraAPI.calculateImage("asdasd");
        launch();
    }
}