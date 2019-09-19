package frc.robot.autonomous;

public class ViveMeasurements {


    public enum LOCATION {
        NEAR_ROCKET,
        NEAR_CARGO_SHIP,
        OTHER
    }
    public ViveMeasurements()
    {

    }
    public boolean isValidCooardinates(double x, double y, double z)
    {
        if (x < 0 || x > 3)
        {
            return false;
        }
        if (y < 0 || y > 3)
        {
            return false;
        }
        if (z < 0 || z > 2)
        {
            return false;
        }
        return true;
    }
    public void turnToCargoShip()
    {

    }
    public void moveToScore()
    {

    }
    public boolean nearCargoShip()
    {
        double x = 0;
        double z = 0;
        if (0.4<x && x<0.5) {
            if (0.4 < z && z < 0.5) {
                return true;
            }
        }
        return false;
    }
    public void angleToShip()
    {

    }
    public double getX()
    {
        return 0;
    }
    public double getY()
    {
        return 0;
    }
    public double getZ()
    {
        return 0;
    }
    public Coordinate getLocation()
    {
        return new Coordinate(getX(), getZ());
    }
}
