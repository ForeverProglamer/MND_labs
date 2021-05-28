package com.example.lab2a;

public class Point {
    int x1;
    int x2;
    int bias = 1;
    String label;

    public Point(int x1, int x2, String label) {
        this.x1 = x1;
        this.x2 = x2;
        this.label = label;
    }

    public Point(int x1, int x2, int limit) {
        this.x1 = x1;
        this.x2 = x2;

         if (x2 > limit) label = "A";
         else label = "B";
    }
}
