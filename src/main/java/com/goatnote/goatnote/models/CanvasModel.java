package com.goatnote.goatnote.models;

import javafx.geometry.Point2D;
import java.util.List;
import java.util.Stack;

public class CanvasModel {

    private Stack<List<Point2D>> states = new Stack<>();

    public void addState(List<Point2D> path){
        states.push(path);
    }

    public void undoState(){
        states.pop();
    }

    public Stack<List<Point2D>> getStates(){
        return states;
    }
}
