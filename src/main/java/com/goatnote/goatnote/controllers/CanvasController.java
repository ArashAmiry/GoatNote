package com.goatnote.goatnote.controllers;

import com.goatnote.goatnote.models.CanvasModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.*;


public class CanvasController extends AnchorPane implements Initializable {

    @FXML
    private Canvas canvas;
    private List<Point2D> path;
    private WritableImage canvasImage;
    private WritableImage snapshot;
    private Stack<WritableImage> snapshots = new Stack<>();

    private CanvasModel canvasModel = new CanvasModel();
    private GraphicsContext gc;
    private double cursorX, cursorY;

/*
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
*/

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(5);
        gc.setStroke(Color.BLACK);

        canvas.setOnMousePressed(mouseEvent -> {
            path = new ArrayList<>();
            Point2D point = new Point2D(mouseEvent.getX(), mouseEvent.getY());
            path.add(point);
        });

        canvas.setOnMouseDragged(mouseEvent -> {
            Point2D point = new Point2D(mouseEvent.getX(), mouseEvent.getY());
            path.add(point);
            draw(path);
        });

        canvas.setOnMouseReleased(mouseEvent -> {
            canvasModel.addState(path);

            /*canvasImage = new WritableImage((int) 900, (int) 800);
            snapshot = canvas.snapshot(new SnapshotParameters(), canvasImage);
            snapshots.push(snapshot);
            System.out.println("bam");*/
        });

    }

    private void draw(List<Point2D> path) {
            Point2D startPoint = path.get(path.size() - 2);
            Point2D endPoint = path.get(path.size()-1);
            gc.strokeLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
        }
}
