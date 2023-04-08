package com.goatnote.goatnote;

import com.goatnote.goatnote.models.Point;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

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

    public static void main(String[] args) {
        Tesseract tesseract = new Tesseract();

        tesseract.setDatapath("C:\\Users\\Arash\\IdeaProjects\\GoatNote\\src\\main\\resources\\tessdata");
        /*tesseract.setLanguage("equ");*/

        File file = new File("C:\\Users\\Arash\\Desktop\\test.png");

        try {
            String text = tesseract.doOCR(file);
            System.out.println(text);
        } catch (TesseractException e){
            System.out.println(e.getMessage());
        }

        launch();
    }
}