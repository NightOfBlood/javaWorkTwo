package com.company;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Canvas extends Frame {
    public List<Ball> objects;
    private int padding = 10;


    public static void main(String[] args) {
        Canvas canvas = new Canvas();
    }

    public Canvas(){
        objects = new ArrayList<>();
        setSize(1300,800);
        setResizable(false);
        setVisible(true);

        createBall(false);
        createBall(false);
        createBall(false);
        createBall(false);
        createBall(false);
        createBall(true);

        objects.forEach(this::add);
        objects.forEach(Ball::startDraw);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                for (Ball object : objects) {
                    if (object instanceof RadioBall) {
                        ((RadioBall) object).onKeyPressed(e);
                    }
                }
            }
        });
        //TODO: добавить клик мыши


    }

    public Ball createBall(boolean isRadioBall) {
        Random random = new Random();
        int randomRadius = random.nextInt(10) * 5 + 10;
        int randomX = random.nextInt((getWidth() - padding*4-randomRadius*2) / 10) * 10 + 2*padding;
        int randomY = random.nextInt((getHeight() - 3 * padding*4-randomRadius*2) / 10) * 10 + padding*2;
        System.out.println(randomRadius);
        Ball ball = isRadioBall?  new RadioBall( randomX, randomY, randomRadius, this): new Ball( randomX, randomY, randomRadius, this);

        for (Ball obj : objects) {
            if (!ball.equals(obj) && (ball.isCollidedWithBall(obj))) {
                ball = createBall(isRadioBall);
                return ball;
            }
        }
        objects.add(ball);

        return ball;
    }

    public int getPadding() {
        return padding;
    }
}
