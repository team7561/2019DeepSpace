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
        if (coordinate.x < -2 || coordinate.x > 3)
        {
            return false;
        }
        if (coordinate.z < -2 || coordinate.z > 3)
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
    public Coordinate getLocation()
    {
        return new Coordinate(getX(), getZ());
    }
}
