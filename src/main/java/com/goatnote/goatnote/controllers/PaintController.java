package com.goatnote.goatnote.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;


public class PaintController extends AnchorPane implements Initializable {

    @FXML
    private Canvas canvas;

    private GraphicsContext gc;
    private double cursorX, cursorY;

    /*gc.setLineWidth(5);
    gc.setStroke(Color.BLACK);*/

    @FXML
    private void mouseLaunch(MouseEvent mouseEvent){
        cursorX = mouseEvent.getX();
        cursorY = mouseEvent.getY();
        System.out.println(cursorX);
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(5);
        gc.setStroke(Color.BLACK);

        canvas.setOnMousePressed(mouseEvent -> {
           mouseLaunch(mouseEvent);
        });

        canvas.setOnMouseDragged(mouseEvent -> {
            gc.strokeLine(cursorX, cursorY, mouseEvent.getX(), mouseEvent.getY());
            cursorX = mouseEvent.getX();
            cursorY = mouseEvent.getY();
        });
    }
}
