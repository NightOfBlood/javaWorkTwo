package com.company;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RadioBall extends Ball {

    public RadioBall(int x, int y, int radius, Canvas canvas) {
        super(x, y, radius, canvas);

    }

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
}
