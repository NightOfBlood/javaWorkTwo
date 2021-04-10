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
    boolean isChanged = false;


    Canvas canvas;
    List<Color> colors = Arrays.asList(Color.RED, Color.BLUE, Color.PINK, Color.GREEN, Color.YELLOW, Color.BLACK);

    int colorDelay = 0;
    int colorNumber;

    protected Color getColor() {
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
        dx = (-5 + random.nextInt(10))*10;
        dy = (-5 + random.nextInt(10))*10;
        colorNumber = random.nextInt(colors.size());
    }

    public void startDraw() {
        Thread animation = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50);
                } catch (Exception ex) {
                }
                move();
            }
        });
        animation.start();
    }

    protected void move() {
        //isChanged = false;
            if (isCollideWithWall()) {
                isChanged = true;
                wallCollision();
            } else {
                for (Ball obj : canvas.objects) {
                    if (!obj.equals(this) && isCollidedWithBall(obj) /*&& !isCollided *//*&& !obj.isChanged*/) {
                        ballsCollision(obj);
                    }
                }
            }

        setLocation(getX() + dx, getY() + dy);
        isCollided = false;
        isChanged = false;
    }

    protected void ballsCollision(Ball obj){
        int rnd = new Random().nextInt();
        System.out.println(rnd+" before balls Collision: \n This: "+toString() +"\n Obj: " +obj.toString());
        if (obj instanceof RadioBall) {
            if (!isCollided) {
                isCollided = true;
                dx = -dx;
                dy = -dy;
            }
        } else {
            //isCollided = true;
            //obj.isCollided = true;


            synchronized (Ball.class) {
                int tempX = dx;
                int tempY = dy;
                boolean t1 = false;
                boolean t2 = false;
                if (!isCollided && !obj.isCollided && !isChanged) {
                    System.out.println("rnd = " + rnd);
                    t1 = true;
                    if(dx*obj.dx >0)
                        dx = -obj.dx;
                    else if(dy*obj.dy >0)
                        dy = -obj.dy;
                    else{
                        dx = obj.dx;
                        dy = obj.dy;
                    }
                    setLocation(getX() + dx, getY() + dy);

                }

                if (!isCollided && !obj.isCollided && !obj.isChanged) {
                    System.out.println("rnd = " + rnd);

                    t2 = true;
                    obj.dx = tempX;
                    obj.dy = tempY;
                    obj.setLocation(obj.getX() + obj.dx, obj.getY() + obj.dy);
                }
                isCollided = t1;
                obj.isCollided = t2;
            }


            //double v = (Math.sqrt(Math.pow(radius + obj.radius, 2)) - Math.sqrt((Math.pow(getX() + radius - obj.getX() - obj.radius, 2) + Math.pow(getY() + radius - obj.getY() - obj.radius, 2)))) + 1;
            //setLocation((int) (getX() + v * (1 - cos(getX(), getY(), obj.getX(), obj.getY()))), (int) (getY() - v * cos(getX(), getY(), obj.getX(), obj.getY())));
            System.out.println(rnd+ " after balls Collision: \n This: "+toString() +"\n Obj: " +obj.toString());

            /*isCollided = false;
            obj.isCollided = false;*/
        }
    }

    protected void wallCollision(){
        if (getX() <= canvas.getPadding() * 4) {
            setLocation(canvas.getPadding() * 4 + 1, getY());
            dx = dx > 0 ? dx : -dx;
        } else if (getX() + getWidth() >= getParent().getWidth() - canvas.getPadding()) {
            setLocation(getParent().getWidth() - getWidth() - canvas.getPadding() - 1, getY());
            dx = dx < 0 ? dx : -dx;
        }
        if (getY() <= canvas.getPadding()) {
            setLocation(getX(), canvas.getPadding() + 1);
            dy = dy > 0 ? dy : -dy;
        } else if (getY() + getHeight() >= getParent().getHeight() - canvas.getPadding()) {
            setLocation(getX(), getParent().getHeight() - canvas.getPadding() - getHeight() - 1);
            dy = dy < 0 ? dy : -dy;
        }
    }

    protected boolean isCollidedWithBall(Ball ball) {
        return Math.pow(getX() + radius - ball.getX() - ball.radius, 2) + Math.pow(getY() + radius - ball.getY() - ball.radius, 2) < Math.pow(radius + ball.radius, 2);
    }

    protected boolean isCollideWithWall() {
        return (getY() + getHeight() > getParent().getHeight() - canvas.getPadding() || getY() < canvas.getPadding() * 4)
                || (getX() + getWidth() > getParent().getWidth() - canvas.getPadding() || getX() < canvas.getPadding());
    }

    @Override
    public String toString() {
        return "x= " + getX() +
        "; y= " + getY() +
                "; dx= " + dx +
                "; dy= " + dy +
                "; radius= " + radius;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D gg = (Graphics2D) g;
        gg.setColor(getColor());
        gg.fillOval(0, 0, 2 * radius, 2 * radius);
    }

    public double cos(int x1, int y1, int x2, int y2) {
        return (x1 * x2 + y1 * y2) / (Math.sqrt(x1 * x1 + y1 * y1) * Math.sqrt(x2 * x2 + y2 * y2));
    }
}


