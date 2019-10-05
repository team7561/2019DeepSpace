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
    public boolean isValidAngle(double angle)
    {
        if (angle < -360 || angle > 600)
        {
            return false;
        }
        return true;
    }
    public boolean isValidHeight(double height)
    {
        if (height < -3 || height > 1)
        {
            return false;
        }
        return true;
    }
    public double getX()
    {
        return SmartDashboard.getNumber("x1", -999);
    }
    public double getY()
    {
        return SmartDashboard.getNumber("y1", -999);
    }
    public double getZ()
    {
        return SmartDashboard.getNumber("z1", -999);
    }
    public double get_Y_rot()
    {
        return SmartDashboard.getNumber("y_rot1", -999);
    }
    public double get_Arm_Y()
    {
        return SmartDashboard.getNumber("y2", -999);
    }
    public double get_Arm_X_rot()
    {
        return SmartDashboard.getNumber("x_rot2", -999)+270;
    }
    public Coordinate getLocation()
    {
        return new Coordinate(getX(), getZ());
    }
}
