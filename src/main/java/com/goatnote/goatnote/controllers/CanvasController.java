package com.goatnote.goatnote.controllers;

import com.goatnote.goatnote.models.CanvasModel;
import com.goatnote.goatnote.models.PathImage;
import com.goatnote.goatnote.models.Point;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class CanvasController extends AnchorPane implements Initializable {

    @FXML
    private Canvas canvas;

    @FXML
    private Button calculateButton;

    private boolean isPencil = true;
    private boolean activeCalculation = false;
    private List<Point> path;
    private CanvasModel canvasModel = new CanvasModel();
    private GraphicsContext gc;

    double dragStartX;
    double dragStartY;

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
            dragStartX = point.getX();
            dragStartY = point.getY();
        });

        canvas.setOnMouseDragged(mouseEvent -> {
            if (activeCalculation){
                gc.setLineWidth(3);
                gc.setStroke(Color.GRAY);
            }

            Point point = new Point(mouseEvent.getX(), mouseEvent.getY());
                path.add(point);
                draw(path);
        });

        canvas.setOnMouseReleased(mouseEvent -> {
            if (!activeCalculation){
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
            }
            else {
                canvasModel.addState(new Pair<>(path, gc.getStroke().toString()));
            }

        });
    }

    @FXML
    private void calculateAction(){
        if (activeCalculation){
            gc.setLineWidth(5);
            gc.setStroke(Color.BLACK);

            List<Point> rectanglePoints = PathImage.cropLargestRectangle(path);

            int left = (int) Math.round(rectanglePoints.get(0).getX());
            int right = (int) Math.round(rectanglePoints.get(2).getX());
            int top = (int) Math.round(rectanglePoints.get(1).getY());
            int bottom = (int) Math.round(rectanglePoints.get(3).getY());
            int width = right - left;
            int height = bottom - top;

            undoAction();

            SnapshotParameters params = new SnapshotParameters();
            params.setViewport(new Rectangle2D(left, top, right, bottom)); // Replace with the portion of the canvas you want to save

            WritableImage snapshot = canvas.snapshot(params, new WritableImage(width, height));

            try {
                File file = new File("canvas_snapshot.png"); // Replace with the file path and name that you want to use
                boolean food = ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);

                BufferedImage pngBuffer = ImageIO.read(file);
                BufferedImage jpgBuffer = new BufferedImage(pngBuffer.getWidth(),pngBuffer.getHeight(), BufferedImage.TYPE_INT_RGB);
                File jpgFile = new File("canvas_snapshot.jpg");
                jpgBuffer.createGraphics().drawImage(pngBuffer, 0, 0, java.awt.Color.WHITE, null);
                ImageIO.write(jpgBuffer, "jpg", jpgFile);
                boolean a = food;
            } catch (IOException e) {
                e.printStackTrace();
            }

            activeCalculation = false;
            return;
        }
        activeCalculation = true;
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
