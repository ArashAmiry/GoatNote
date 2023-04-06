package com.goatnote.goatnote.controllers;

import com.goatnote.goatnote.models.CanvasModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.*;


public class CanvasController extends AnchorPane implements Initializable {

    @FXML
    private Canvas canvas;
    @FXML
    private Button undo;

    private List<Point2D> path;
    private CanvasModel canvasModel = new CanvasModel();
    private GraphicsContext gc;

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
        });

    }

    @FXML
    private void undoAction(){
        if(canvasModel.getStates().size() != 0){
            canvasModel.undoState();
            redrawState();
        }
    }

    private void draw(List<Point2D> path) {
            Point2D startPoint = path.get(path.size() - 2);
            Point2D endPoint = path.get(path.size() - 1);
            gc.strokeLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
    }

    private void redrawState(){
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());

        for (List<Point2D> path : canvasModel.getStates()){
            for (int i = 1; i < path.size(); i += 1){
                Point2D startPoint = path.get(i - 1);
                Point2D endPoint = path.get(i);
                gc.strokeLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
            }
        }
    }
}
