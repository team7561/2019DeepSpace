package frc.robot.Autonomous;

import frc.robot.Drivers.ADIS16448_IMU;

public class ViveMeasurements {

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
    public void angleToShip()
    {

    }
    public void getX()
    {

    }
    public void getY()
    {

    }
}
