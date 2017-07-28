package de.metro.robocode;

import robocode.*;
import robocode.Robot;

import java.awt.*;

public class Naturjoghurt extends Robot {

    @Override
    public void run() {

        paintPink();
        int time = 0;
        double radius = 100.0;
        double angle = 90.0;

        while (true) {
            time++;

            ahead(radius);
            turnLeft(angle);
            turnGunLeft(angle);
            fireBullet(getEnergy());
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        fire(3);
    }

    public void onHitByBullet( HitByBulletEvent e ) {
        ahead( 100 );
        turnLeft(90 - e.getBearing());
    }

    public void onWin( final WinEvent e ) {
        for ( int i = 0; i < 100; i++ ) {
            turnRight( 50 );
            if ( i % 2 == 0 ) {
                paintPink();
            } else {
                paintGreen();
            }
        }
    }

    /**
     *    Private utility functions
     */

    private void paintPink() {
        setBodyColor(new Color(255, 0, 102));
        setGunColor(new Color(0, 255, 0));
        setRadarColor(new Color(255, 0, 102));
        setScanColor(Color.white);
        setBulletColor(Color.pink);
    }

    private void paintGreen() {
        setBodyColor(new Color(0, 255, 0));
        setGunColor(new Color(255, 0, 102));
        setRadarColor(new Color(0, 255, 0));
        setScanColor(Color.white);
        setBulletColor(Color.green);
    }
}
