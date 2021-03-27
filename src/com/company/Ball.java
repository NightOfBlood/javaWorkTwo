package com.company;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Ball extends Component {
    protected int dx;
    protected int dy;
    int radius;
    public boolean isCollided = false;

    Canvas canvas;
    List<Color> colors = Arrays.asList(Color.RED, Color.BLUE, Color.PINK, Color.GREEN, Color.YELLOW, Color.BLACK);
    int colorDelay = 0;
    int colorNumber;

    private Color getColor() {
        colorDelay++;
        if (colorDelay > 50) {
            colorDelay = 0;
            colorNumber = new Random().nextInt(colors.size());
        }
        return colors.get(colorNumber);
    }

    public Ball(int x, int y, int radius, Canvas canvas) {
        setBounds(x, y, radius * 2, radius * 2);
        this.canvas = canvas;
        this.radius = radius;
        Random random = new Random();
        dx = -5 + random.nextInt(10);
        dy = -5 + random.nextInt(10);
        colorNumber = random.nextInt(colors.size());
    }

    public void startDraw() {
        Thread animation = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (Exception ex) {
                }
                move();
            }
        });
        animation.start();
    }

    protected void move() {
        boolean isChanged = false;

        if (isCollideWithHorizontalBorder()) {
            dx = -dx;
            isChanged = true;
        }
        if (isCollideWithVerticalBorder()) {
            dy = -dy;
            isChanged = true;
        }
        for (Ball obj : canvas.objects) {
            if (!isChanged && !obj.equals(this) && isCollidedWithBall(obj) && !isCollided) {
                isCollided=true;
                obj.isCollided = true;
                int temp = dx;
                dx = obj.dx;
                obj.dx = temp;

                temp = dy;
                dy = obj.dy;
                obj.dy = temp;
                double v = (Math.sqrt(Math.pow(radius + obj.radius, 2)) - Math.sqrt((Math.pow(getX() + radius - obj.getX() - obj.radius, 2) + Math.pow(getY() + radius - obj.getY() - obj.radius, 2))))  + 1;
                setLocation((int)(getX()+v*(1-cos(getX(),getY(),obj.getX(),obj.getY()))), (int)(getY()-v*cos(getX(),getY(),obj.getX(),obj.getY())));
                //obj.setLocation((int)(getX()-v*(1-cos(getX(),getY(),obj.getX(),obj.getY()))), (int)(getY()+v*cos(getX(),getY(),obj.getX(),obj.getY())));
                isCollided=false;
                obj.isCollided = false;
            }
        }
        setLocation(getX() + dx, getY() + dy);
    }

    protected boolean isCollidedWithBall(Ball ball) {
        return Math.pow(getX() + radius - ball.getX() - ball.radius, 2) + Math.pow(getY() + radius - ball.getY() - ball.radius, 2) < Math.pow(radius + ball.radius, 2);
    }

    protected boolean isCollideWithHorizontalBorder() {
        return getX() + getWidth() > getParent().getWidth() -canvas.getPadding() || getX() < canvas.getPadding() ;
    }

    protected boolean isCollideWithVerticalBorder() {
        return getY() + getHeight() > getParent().getHeight()-canvas.getPadding() || getY() < canvas.getPadding()*4;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D gg = (Graphics2D) g;
        gg.setColor(getColor());
        gg.fillOval(0, 0, 2*radius, 2*radius);
    }

    public double cos(int x1, int y1, int x2, int y2){
        return (x1*x2+y1*y2) / (Math.sqrt(x1*x1+y1*y1) *Math.sqrt(x2*x2+y2*y2));
    }
}