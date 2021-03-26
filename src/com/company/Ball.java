package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Ball extends Component {
    protected int x, y, width, height, dx, dy;
    int radius;
    boolean isActive;
    Canvas canvas;
    List<Color> colors= Arrays.asList(Color.RED,Color.BLUE,Color.PINK,Color.GREEN,Color.YELLOW,Color.BLACK);
    int colorDelay=0;
    int colorNumber;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    private Color getColor() {
        colorDelay++;
        if(colorDelay>50){
            colorDelay=0;
            colorNumber=new Random().nextInt(colors.size());
        }
       return colors.get(colorNumber);
    }

    public Ball(int x, int y, int radius, Canvas canvas) {
        this.x = x;
        this.y = y;
        this.width = radius*2;
        this.height = radius*2;
        this.canvas = canvas;

        isActive = true;
        Thread animation = new Thread(() -> {
            while (isActive) {
                repaint();
                try {
                    Thread.sleep(100);
                } catch (Exception ex) {
                }
            }
        });

        this.radius = radius;
        Random random = new Random();
        dx = 1 + random.nextInt(5);
        dy = 1 + random.nextInt(5);
        colorNumber=random.nextInt(colors.size());
        animation.start();
    }

    protected boolean isCollidedWithBall(Ball g1) {

        return (Math.sqrt(Math.pow(g1.x - x - dx + g1.dx, 2) + Math.pow(g1.y - y - dy + g1.dy, 2)) <= (((Ball) g1).radius + radius)
                && (x + radius < g1.x || x - radius > g1.x));
    }

    protected boolean isCollideWithHorizontalBorder(Canvas map) {
        return getLeft() <= Math.abs(dx) || getRight() >= map.getWidth() - Math.abs(dx);
    }

    protected boolean isCollideWithVerticalBorder(Canvas map) {
        return getTop() <= Math.abs(dy) || getBot() >= map.getHeight() - Math.abs(dy);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D gg = (Graphics2D) g;
        x += dx;
        y += dy;


        boolean isChanged = false;

        if (isCollideWithHorizontalBorder(canvas)) {
            dx = -dx;
            isChanged = true;
        }
        if (isCollideWithVerticalBorder(canvas)) {
            dy = -dy;
            isChanged = true;
        }
        for (Ball obj : canvas.objects) {
            if (!isChanged && !obj.equals(this) && isCollidedWithBall(obj)) {
                int temp = dx;
                dx=obj.dx;
                obj.dx = temp;
                temp = dy;
                dy = obj.dy;
                obj.dy = temp;
            }
        }
        x += dx;
        y += dy;

        synchronized (gg){
            gg.setColor(getColor());
            draw(gg);
        }
    }

    public void draw(Graphics2D gg) {
        gg.fillOval(x, y, width, height);
    }

    public float getTop() {
        return y;
    }

    public float getBot() {
        return y + height;
    }

    public float getLeft() {
        return x;
    }

    public float getRight() {
        return x + width;
    }
}