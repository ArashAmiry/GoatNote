package com.goatnote.goatnote;

import com.goatnote.goatnote.models.Point;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

    public static void main(String[] args) {
        Point p1 = new Point(5,5);
        Point p2 = new Point(9.99,5);
        System.out.println(p1.intersect(p2));

        launch();
    }
}