package com.goatnote.goatnote.controllers;

import com.goatnote.goatnote.models.CanvasModel;
import com.goatnote.goatnote.models.Point;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.net.URL;
import java.util.*;


public class CanvasController extends AnchorPane implements Initializable {

    @FXML
    private Canvas canvas;
    @FXML
    private Button undo;

    private boolean isPencil = true;
    private List<Point> path;
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
            Point point = new Point(mouseEvent.getX(), mouseEvent.getY());
            path.add(point);
        });

        canvas.setOnMouseDragged(mouseEvent -> {
            Point point = new Point(mouseEvent.getX(), mouseEvent.getY());
            path.add(point);
            draw(path);
        });

        canvas.setOnMouseReleased(mouseEvent -> {
            if (!isPencil){
                for (Pair<List<Point>, String> previousPath : canvasModel.getStates()){
                    for (Point oldPoint : previousPath.getKey()){
                        for (Point newPoint : path){
                            if(newPoint.intersect(oldPoint)){
                                canvasModel.addState(new Pair<>(path, gc.getStroke().toString()));
                                return;
                            }

                        }
                    }
                }
            }
            else {
                canvasModel.addState(new Pair<>(path, gc.getStroke().toString()));
            }
        });

    }

    @FXML
    private void undoAction(){
        if(canvasModel.getStates().size() != 0){
            canvasModel.undoState();
            redrawState();
            if (!isPencil){
                gc.setStroke(Color.rgb(244,244,244));
            }
        }
    }

    @FXML
    private void pencilActivated(){
        isPencil = true;
        gc.setStroke(Color.BLACK);
    }

    @FXML
    private void eraserActivated(){
        isPencil = false;
        gc.setStroke(Color.rgb(244,244,244));
    }

    private void draw(List<Point> path) {
            Point2D startPoint = path.get(path.size() - 2);
            Point2D endPoint = path.get(path.size() - 1);
            gc.strokeLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
    }

    private void redrawState(){
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());

        for (Pair<List<Point>, String> path : canvasModel.getStates()){
            gc.setStroke(Color.valueOf(path.getValue()));

            for (int i = 1; i < path.getKey().size(); i += 1){
                Point startPoint = path.getKey().get(i - 1);
                Point endPoint = path.getKey().get(i);
                gc.strokeLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
            }
        }
    }
}
