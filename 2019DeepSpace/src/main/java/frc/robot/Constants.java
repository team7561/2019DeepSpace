package frc.robot;

import frc.robot.autonomous.Coordinate;

public class Constants {
    public static int EJECT_TIME = 2;
    public static int INTAKE_TIME = 2;
    public static int CARGO_STALL_CURRENT = 2;


    public static double DISTANCE_TOLERANCE = 0.2;
    public static double SLOW_DOWN_DISTANCE = 0.4;
    public static double TURNING_THRESHOLD = 60;
    public static double ANGLE_TOLERANCE = 5;

    public static double ROCKET_X_COORD = 1.11;
    public static double ROCKET_Z_COORD = -3.76;
    public static double ROCKET_APPROACH_X_COORD = 0.66;
    public static double ROCKET_APPROACH_Z_COORD = -3.31;
    public static double CARGO_SHIP_X_COORD = 0.44;
    public static double CARGO_SHIP_Z_COORD = -4.18;
    public static double LOADING_STATION_X_COORD = -0.16;
    public static double LOADING_STATION_Z_COORD = -3.18;
    public static double LOADING_STATION_APPROACH_X_COORD = 0.44;
    public static double LOADING_STATION_APPROACH_Z_COORD = -3.16;
    public static double CARGO_SHIP_APPROACH_X_COORD = 0.86;
    public static double CARGO_SHIP_APPROACH_Z_COORD = -4.01;
    public static double CENTRE_X_COORD = 0.35;
    public static double CENTRE_Z_COORD = -3.16;

    public static Coordinate ROCKET_COORD = new Coordinate(ROCKET_X_COORD, ROCKET_Z_COORD);
    public static Coordinate ROCKET_APPROACH_COORD = new Coordinate(ROCKET_APPROACH_X_COORD, ROCKET_APPROACH_Z_COORD);
    public static Coordinate CARGO_SHIP_COORD = new Coordinate(CARGO_SHIP_X_COORD, CARGO_SHIP_Z_COORD);
    public static Coordinate CARGO_SHIP_APPROACH_COORD = new Coordinate(CARGO_SHIP_APPROACH_X_COORD, CARGO_SHIP_APPROACH_Z_COORD);
    public static Coordinate LOADING_STATION_COORD = new Coordinate(LOADING_STATION_X_COORD, LOADING_STATION_Z_COORD);
    public static Coordinate LOADING_STATION_APPROACH_COORD = new Coordinate(LOADING_STATION_APPROACH_X_COORD, LOADING_STATION_APPROACH_Z_COORD);
}