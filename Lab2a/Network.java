package com.example.lab2a;

import java.util.ArrayDeque;
import java.util.Arrays;

public class Network {

    Point[] points;
    int limit;
    double learnRate;
    int iterations;

    public Network(Point[] points, int limit, double learnRate, int iterations) {
        this.points = points;
        this.limit = limit;
        this.learnRate = learnRate;
        this.iterations = iterations;
    }

    public double[] train() {

        ArrayDeque<Point> pointArrayDeque = new ArrayDeque<Point>(Arrays.asList(points));
        Perceptron perceptron = new Perceptron(limit, learnRate);

        Point currentPoint;
        for (int i = 0; i < iterations; i++) {
//            System.out.println("Iteration №" + (i + 1));
            currentPoint = pointArrayDeque.pop();

            int[] input = {currentPoint.x1, currentPoint.x2, currentPoint.bias};
            String target = currentPoint.label;
            perceptron.train(input, target);

            pointArrayDeque.addLast(currentPoint);
        }

//        System.out.println(perceptron);

        return perceptron.weights;
    }

}
