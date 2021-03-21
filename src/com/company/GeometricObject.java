package com.company;

import java.awt.*;

public abstract class GeometricObject extends Component {
    protected int x, y, width, height, dx, dy;
    boolean isActive;
    Canvas canvas;


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public GeometricObject(int x, int y, int width, int height, Canvas canvas) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
        animation.start();
    }

    protected boolean isCollidedWithObjectHorizontal(GeometricObject g1) {

        return (((getRight() + dx) >= g1.getLeft() && (getLeft() + dx) < g1.getLeft()) &&
                (getLeft() + dx) <= g1.getRight() && (getRight() + dx) > g1.getRight())
                && (getBot() + dy >= g1.getTop() && getTop() + dx <= g1.getBot());
    }

    protected boolean isCollidedWithObjectVertical(GeometricObject g1) {
        return ((getBot() + dy) >= g1.getTop() && (getTop() + dy) < g1.getTop() &&
                (getTop() + dy) <= g1.getBot() && getBot() + dy > g1.getBot())
                && (getRight() + dx >= g1.getLeft() && getLeft() + dx <= g1.getRight());
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


        boolean isChangedX = false, isChangedY = false;

        if (isCollideWithHorizontalBorder(canvas)) {
            dx = -dx;
            isChangedX = true;
        }
        if (isCollideWithVerticalBorder(canvas)) {
            dy = -dy;
            isChangedY = true;
        }
        for (GeometricObject obj : canvas.objects) {
            if (!isChangedX && !obj.equals(this) && isCollidedWithObjectHorizontal(obj)) {
                dx = -dx;
            }
            if (!isChangedY && !obj.equals(this) && isCollidedWithObjectVertical(obj)) {
                dy = -dy;
            }
        }
        x += dx;
        y += dy;

        gg.setColor(Color.BLUE);
        draw(gg);
    }

    public abstract void draw(Graphics2D gg);

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