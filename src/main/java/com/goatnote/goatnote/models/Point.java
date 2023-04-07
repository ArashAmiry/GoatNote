package com.goatnote.goatnote.models;

import javafx.geometry.Point2D;

public class Point extends Point2D {

    double pointSize = 2.5;

    public Point(double x, double y) {
        super(x, y);
    }

    public boolean intersect(Point other) {
        double x1 = this.getX() - pointSize;
        double y1 = this.getY() - pointSize;
        double x2 = this.getX() + pointSize;
        double y2 = this.getY() + pointSize;
        double x3 = other.getX() - pointSize;
        double y3 = other.getY() - pointSize;
        double x4 = other.getX() + pointSize;
        double y4 = other.getY() + pointSize;

        if (x1 < x4 && x2 > x3 &&
                y1 < y4 && y2 > y3){
            return true;
        }

        return false;
    }


}
