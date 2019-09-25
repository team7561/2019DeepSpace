package frc.robot.autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ViveMeasurements {


    public enum LOCATION {
        NEAR_ROCKET,
        NEAR_CARGO_SHIP,
        OTHER
    }
    public ViveMeasurements()
    {

    }
    public boolean isValidCooardinates(Coordinate coordinate)
    {
        if (coordinate.x < -10 || coordinate.x > 10)
        {
            return false;
        }
        if (coordinate.z < -10 || coordinate.z > 10)
        {
            return false;
        }
        return true;
    }
    public double getX()
    {
        return SmartDashboard.getNumber("x", -999);
    }
    public double getY()
    {
        return SmartDashboard.getNumber("y", -999);
    }
    public double getZ()
    {
        return SmartDashboard.getNumber("z", -999);
    }
    public double get_Y_rot()
    {
        return SmartDashboard.getNumber("y_rot", -999)+270;
    }
    public Coordinate getLocation()
    {
        return new Coordinate(getX(), getZ());
    }
}
