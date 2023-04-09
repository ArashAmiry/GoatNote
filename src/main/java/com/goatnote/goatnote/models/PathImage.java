package com.goatnote.goatnote.models;

import java.util.ArrayList;
import java.util.List;

public class PathImage {
    public static List<Point> cropLargestRectangle(List<Point> path){
        Point leftSide = new Point(path.get(0).getX(), path.get(0).getY());
        Point top = new Point(path.get(0).getX(), path.get(0).getY());
        Point rightSide = new Point(path.get(0).getX(), path.get(0).getY());
        Point bottom = new Point(path.get(0).getX(), path.get(0).getY());

        for (Point point : path){
            if(point.getX() < leftSide.getX()) {
                leftSide = point;
            }
            if (point.getX() > rightSide.getX()){
                rightSide = point;
            }
            if (point.getY() > bottom.getY()){
                bottom = point;
            }
            if (point.getY() < top.getY()){
                top = point;
            }
        }

        List<Point> rectangle = new ArrayList<>();
        rectangle.add(leftSide);
        rectangle.add(top);
        rectangle.add(rightSide);
        rectangle.add(bottom);

        return rectangle;
    }
}
