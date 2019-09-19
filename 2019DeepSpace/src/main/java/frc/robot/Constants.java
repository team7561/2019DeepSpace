package frc.robot;

import frc.robot.autonomous.Coordinate;

public class Constants {
    public static int EJECT_TIME = 2;
    public static int INTAKE_TIME = 2;
    public static int CARGO_STALL_CURRENT = 2;


    public static double DISTANCE_TOLERANCE = 0.4;

    public static double ROCKET_X_COORD = 3;
    public static double ROCKET_Z_COORD = 3;
    public static double CARGO_SHIP_X_COORD = 3;
    public static double CARGO_SHIP_Z_COORD = 3;

    public static Coordinate ROCKET_COORD = new Coordinate(ROCKET_X_COORD, ROCKET_Z_COORD);
    public static Coordinate ROCKET__APPROACH_COORD = new Coordinate(ROCKET_X_COORD, ROCKET_Z_COORD);
    public static Coordinate CARGO_SHIP_COORD = new Coordinate(ROCKET_X_COORD, ROCKET_Z_COORD);
    public static Coordinate CARGO_SHIP_APPROACH_COORD = new Coordinate(ROCKET_X_COORD, ROCKET_Z_COORD);
    public static Coordinate LOADING_STATION_COORD = new Coordinate(ROCKET_X_COORD, ROCKET_Z_COORD);
    public static Coordinate LOADING_STATION_APPROACH = new Coordinate(ROCKET_X_COORD, ROCKET_Z_COORD);
}