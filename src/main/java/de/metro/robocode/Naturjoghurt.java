package de.metro.robocode;

import robocode.*;
import robocode.Robot;

import java.awt.*;

import static robocode.util.Utils.normalRelativeAngleDegrees;

public class Naturjoghurt extends Robot {

    int time = 0;
    double radius = 80.0;
    double angle = 70.0;
    double lastX = 0;
    double lastY = 0;

    @Override
    public void run(  ) {

        paintPink(  );

        while ( true ) {

            time++;
            if ( getX() < 100
                    || getX() > getBattleFieldWidth() - 100
                    || getY() < 100
                    || getX() > getBattleFieldHeight() - 100 ) {
                turnRight( getAngle() );
                ahead( getRadius() );
            }

            ahead( getRadius() );
            //turnLeft( angle );
            //turnGunLeft( angle );
            //fireBullet( getEnergy(  ) );

            
        }
    }

    public void onScannedRobot( ScannedRobotEvent e ) {

        final double gunTurnAmt = normalRelativeAngleDegrees( e.getBearing(  ) + ( getHeading(  ) - getRadarHeading(  ) ) );
        turnRight( gunTurnAmt );
        turnGunRight( gunTurnAmt );

        fire( 3 );
    }

    public void onHitRobot( HitRobotEvent e ) {

        final double gunTurnAmt = normalRelativeAngleDegrees( e.getBearing() + ( getHeading() - getRadarHeading() ) );
        turnGunRight( gunTurnAmt );
        fire( 3 );
    }

    public void onHitByBullet( HitByBulletEvent e ) {

        ahead( getRadius() );
        turnLeft( getAngle() - e.getBearing(  ) );
    }

    public void onWin( final WinEvent e ) {

        for ( int i = 0; i < 100; i++ ) {
            // turnRight( 50 );
            if ( i % 2 == 0 ) {
                paintPink(  );
            } else {
                paintGreen(  );
            }
        }
    }

    /**
     *    Private utility functions
     */

    private double getAngle() {
        return angle + time % 20;
    }

    private double getRadius() {
        return radius + time % 20;
    }

    private void paintPink(  ) {

        setBodyColor( new Color( 255, 0, 102 ) );
        setGunColor( new Color( 0, 255, 0 ) );
        setRadarColor( new Color( 255, 0, 102 ) );
        setScanColor( Color.white );
        setBulletColor( Color.pink );
    }

    private void paintGreen(  ) {

        setBodyColor( new Color( 0, 255, 0 ) );
        setGunColor( new Color( 255, 0, 102 ) );
        setRadarColor( new Color( 0, 255, 0 ) );
        setScanColor( Color.white );
        setBulletColor( Color.green );
    }
}
