package com.example.lab2a;

import java.util.Arrays;

public class Perceptron {

    double[] weights = new double[3];
    int limit;
    double learnRate;
    double sum;

    public Perceptron(int limit, double learnRate) {
        this.limit = limit;
        this.learnRate = learnRate;

        Arrays.fill(weights, 0);
    }

    private String activationFunc(double sum) {
        if (sum > limit) {
            return "A";
        }
        return "B";
    }

    public String guess(int[] input) {
        sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum += input[i] * weights[i];
        }
        return activationFunc(sum);
    }

    public void train(int[] input, String target) {
        String guess = guess(input);
//        System.out.println("Expected value: " + target + ". Guessed value: " + guess);
        if (!guess.equals(target)) {
//            System.out.println("Changing the weights...");
            double error = limit - sum;
            for (int i = 0; i < weights.length; i++) {
                weights[i] += error * input[i] * learnRate;
            }
        } else {
//            System.out.println("Right guess!");
        }
    }

    @Override
    public String toString() {
        return "Perceptron`s weights: w1 = " + weights[0] + ", w2 = " + weights[1] + ", w_bias = " + weights[2];
    }
}
