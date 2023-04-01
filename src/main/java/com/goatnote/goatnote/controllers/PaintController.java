package com.goatnote.goatnote.controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;


public class PaintController extends AnchorPane {

    @FXML
    Canvas canvas;

    @FXML
    private void mouseLaunch(){
        System.out.println("Started drawing");
    }

}
