package com.example.plotter;

import java.util.Comparator;

public class PointsComp implements Comparator<Point> {
    @Override
    public int compare(Point p1, Point p2) {
        return p1.y.compareTo(p2.y);
    }
}
