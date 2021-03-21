package com.company;

import java.awt.*;
import java.util.Random;

public class Ball extends GeometricObject {

    int radius;

    public Ball(int radius, int x, int y, Canvas canvas) {
        super(x, y, 2 * radius, 2 * radius, canvas);
        this.radius = radius;
        Random random = new Random();
        dx = 1;
        dy = 1;
    }


    @Override
    public void draw(Graphics2D gg) {
        gg.fillOval(x, y, width, height);
    }
}
