package com.goatnote.goatnote.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.File;
import java.net.URL;
import java.util.*;


public class PaintController extends AnchorPane implements Initializable {

    @FXML
    private Canvas canvas;
    @FXML
    private Button eraser;
    @FXML
    private Button pencil;

    WritableImage canvasImage;
    WritableImage snapshot;
    Stack<WritableImage> snapshots = new Stack<>();

    private GraphicsContext gc;
    private double cursorX, cursorY;

    Boolean isPencil = true;

    private void mouseLaunch(MouseEvent mouseEvent){
        cursorX = mouseEvent.getX();
        cursorY = mouseEvent.getY();
        gc.strokeLine(cursorX, cursorY, cursorX, cursorY);
        System.out.println(cursorX + " ; " + cursorY);
    }

    private void mouseAction(MouseEvent mouseEvent){
        gc.strokeLine(cursorX, cursorY, mouseEvent.getX(), mouseEvent.getY());
        cursorX = mouseEvent.getX();
        cursorY = mouseEvent.getY();
    }

    @FXML
    private void selectEraser(){
        snapshots.pop();
        // Clear the canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw the saved snapshot onto the canvas
        gc.drawImage(snapshots.peek(), 0, 0);

        System.out.println("Eraser selected");
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(5);
        gc.setStroke(Color.BLACK);

        canvas.setOnMousePressed(mouseEvent -> {
            /*canvasImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());*/
            mouseLaunch(mouseEvent);
        });

        canvas.setOnMouseDragged(mouseEvent -> {
            mouseAction(mouseEvent);
        });

        canvas.setOnMouseReleased(mouseEvent -> {
            canvasImage = new WritableImage((int) 900, (int) 800);
            snapshot = canvas.snapshot(new SnapshotParameters(), canvasImage);
            snapshots.push(snapshot);
            System.out.println("bam");
        });

    }
}
