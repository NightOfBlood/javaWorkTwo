package com.company;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class RadioBall extends Ball {

    //создание управляемого шара
    public RadioBall(int x, int y, int radius, Canvas canvas) {
        super(x, y, radius, canvas);

    }

    @Override
    protected Color getColor() {
        return Color.MAGENTA;
    }
    //взаимодействие шара по нажатой клавише
    public void onKeyPressed(KeyEvent e){
        switch (e.getKeyChar()){
            case 'a':
                setLocation(getX()-5, getY());
                break;
            case 'w':
                setLocation(getX(), getY()-5);
                break;
            case 's':
                setLocation(getX(), getY()+5);
                break;
            case 'd':
                setLocation(getX()+5, getY());
                break;

            default:
                //System.out.println(e.getKeyCode());
        }
    }

    @Override
    protected void move() {

    }

    //взаимодействие шара по клику мышки
    public void onMouseClick(MouseEvent e) {
        setLocation(e.getX() - radius,e.getY() - radius );
    }
}
