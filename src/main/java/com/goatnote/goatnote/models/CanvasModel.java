package com.goatnote.goatnote.models;

import javafx.geometry.Point2D;
import javafx.util.Pair;

import java.util.List;
import java.util.Stack;

public class CanvasModel {

    private Stack<Pair<List<Point2D>, String>> states = new Stack<>();

    public void addState(Pair<List<Point2D>, String> path){
        states.push(path);
    }

    public void undoState(){
        states.pop();
    }

    public Stack<Pair<List<Point2D>, String>> getStates(){
        return states;
    }
}
