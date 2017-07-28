package de.metro.robocode;

import robocode.*;
import robocode.Robot;

import java.awt.*;
import java.util.LinkedList;

import static robocode.util.Utils.normalRelativeAngleDegrees;

public class Naturjoghurt extends Robot {

    boolean isPink;
    int time = 0;
    //double radius = 79.9;
    //double angle = 69.9;
    double radius = 40;
    double angle = 30;
    LinkedList<Position> positionHistories = new LinkedList<Position>();

    @Override
    public void run() {
        paintPink();

        while ( true ) {

            // Increase timer and save position
            time++;
            positionHistories.add( myPositon() );

            // Move the radar all of the time
            turnRadarRight( getAngle() );

            // Do not move into a wall
            if ( getX() < 100
                    || getX() > getBattleFieldWidth() - 100
                    || getY() < 100
                    || getX() > getBattleFieldHeight() - 100 ) {
                turnRight( getAngle() );
                ahead( getRadius() );
            }

            // Default movement -> ahead
            ahead( getRadius() );

            // Back up if stuck
            if ( positionHistories.getLast().equals( myPositon() ) ) {
                back( getRadius() );
                turnRight( getAngle() );
            }
        }
    }

    public void onScannedRobot( ScannedRobotEvent e ) {
        double absoluteBearing = getHeading() + e.getBearing();
        double bearingFromGun = normalRelativeAngleDegrees( absoluteBearing - getGunHeading() );
        turnGunRight( bearingFromGun );
        turnRadarRight( bearingFromGun );

        //final double gunTurnAmt = normalRelativeAngleDegrees( e.getBearing() + ( getHeading() - getRadarHeading() ) );
        //turnGunRight( gunTurnAmt );
        //turnRight( e.getBearing() );//gunTurnAmt );

        fire( 3 );
    }

    public void onHitRobot( HitRobotEvent e ) {
        double absoluteBearing = getHeading() + e.getBearing();
        double bearingFromGun = normalRelativeAngleDegrees( absoluteBearing - getGunHeading() );
        turnGunRight( bearingFromGun );
        turnRadarRight( bearingFromGun );

        //final double gunTurnAmt = normalRelativeAngleDegrees( e.getBearing() + ( getHeading() - getRadarHeading() ) );
        //turnGunRight( gunTurnAmt );
        //turnRight( gunTurnAmt );

        fire( 3 );
    }

    public void onHitByBullet( HitByBulletEvent e ) {
        ahead( getRadius() );
        turnLeft( getAngle() - e.getBearing() );
    }

    public void onWin( final WinEvent e ) {
        for ( int i = 0; i < 100; i++ ) {
            changeColor();
        }
    }

    /**
     *    Private utility functions
     */
    private class Position {
        final double x;
        final double y;
        final double angle;

        private Position( double x, double y, double angle ) {
            this.x = x;
            this.y = y;
            this.angle = angle;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getAngle() {
            return angle;
        }

        @Override public boolean equals( Object o ) {
            if ( this == o )
                return true;
            if ( !( o instanceof Position ) )
                return false;

            Position position = (Position) o;

            if ( Double.compare( position.x, x ) != 0 )
                return false;
            if ( Double.compare( position.y, y ) != 0 )
                return false;
            return Double.compare( position.angle, angle ) == 0;
        }
    }

    private Position myPositon() {
        return new Position( getX(), getY(), getHeading() );
    }

    private double getSalt() {
        return time % 10;
        //return time % 20;
    }

    private double getAngle() {
        return angle + getSalt();
        //return 1;
    }

    private double getRadius() {
        return radius + getSalt();
        //return 1;
    }

    private void paintPink() {
        setBodyColor( new Color( 255, 0, 102 ) );
        setGunColor( new Color( 0, 255, 0 ) );
        setRadarColor( new Color( 255, 0, 102 ) );
        setScanColor( Color.white );
        setBulletColor( Color.pink );
        isPink = true;
    }

    private void paintGreen() {
        setBodyColor( new Color( 0, 255, 0 ) );
        setGunColor( new Color( 255, 0, 102 ) );
        setRadarColor( new Color( 0, 255, 0 ) );
        setScanColor( Color.white );
        setBulletColor( Color.green );
        isPink = false;
    }

    private void changeColor() {
        if ( isPink ) {
            paintGreen();
        } else {
            paintPink();
        }
    }
}
