package com.goatnote.goatnote.models;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CanvasModel {

    private Stack<List<Point2D>> states = new Stack<>();

    public void addState(List<Point2D> path){
        states.push(path);
    }
}
