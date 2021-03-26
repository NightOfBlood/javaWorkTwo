package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Canvas extends Frame {
    public List<Ball> objects;

    public static void main(String[] args) {
        Canvas canvas = new Canvas();
    }

    public Canvas(){
        objects = new ArrayList<>();
        setSize(800,600);
        setVisible(true);
        createBall();
        createBall();
        createBall();

        objects.forEach(this::add);
    }

    public Ball createBall() {
        Random random = new Random();
        int randomRadius = random.nextInt(10) * 5 + 10;
        int randomX = random.nextInt((getWidth() - 3 * randomRadius) / 10) * 10 + randomRadius;
        int randomY = random.nextInt((getHeight() - 3 * randomRadius) / 10) * 10 + randomRadius;
        Ball ball = new Ball( randomX, randomY, randomRadius, this);

        for (Ball obj : objects) {
            if (!ball.equals(obj) && (ball.isCollidedWithBall(obj))) {
                ball = createBall();
                return ball;
            }
        }
        objects.add(ball);

        return ball;
    }
}
