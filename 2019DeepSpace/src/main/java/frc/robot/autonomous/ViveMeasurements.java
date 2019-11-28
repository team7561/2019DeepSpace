package frc.robot.autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.utility.EulerAngles;
import frc.robot.utility.Quaternion;

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
        return SmartDashboard.getNumber("x2", -999);
    }
    public double getY()
    {
        return SmartDashboard.getNumber("y2", -999);
    }
    public double getZ()
    {
        return SmartDashboard.getNumber("z2",-999);
    }
    public double get_Y_rot()
    {
        return SmartDashboard.getNumber("y_rot1", -999);
    }
    public double get_Arm_Y()
    {
        return SmartDashboard.getNumber("y1", -999);
    }
    public double get_Arm_X_rot()
    {
        return SmartDashboard.getNumber("x_rot1", -999);
    }
    public double get_Arm_Z_rot()
    {
        return SmartDashboard.getNumber("z_rot2", -999);
    }
    public EulerAngles getTracker1EulerAngles()
    {
        double roll = SmartDashboard.getNumber("x_rot1", -999);
        double pitch = SmartDashboard.getNumber("y_rot1", -999);
        double yaw = SmartDashboard.getNumber("z_rot1", -999);
        return new EulerAngles(roll, pitch, yaw);
    }
    public EulerAngles getTracker2EulerAngles()
    {
        double roll = SmartDashboard.getNumber("x_rot2", -999);
        double pitch = SmartDashboard.getNumber("y_rot2", -999);
        double yaw = SmartDashboard.getNumber("z_rot2", -999);
        return new EulerAngles(roll, pitch, yaw);
    }
    public Quaternion getTracker1Quaterions()
    {
        double r = SmartDashboard.getNumber("r1", -999);
        double i = SmartDashboard.getNumber("i1", -999);
        double j = SmartDashboard.getNumber("j1", -999);
        double k = SmartDashboard.getNumber("k1", -999);
        return new Quaternion(r, i, j, k);
    }
    public Quaternion getTracker2Quaterions()
    {
        double r = SmartDashboard.getNumber("r2", -999);
        double i = SmartDashboard.getNumber("i2", -999);
        double j = SmartDashboard.getNumber("j2", -999);
        double k = SmartDashboard.getNumber("k2", -999);
        return new Quaternion(r, i, j, k);
    }
    public double getArmAngle()
    {
        double result = Quaternion.multiply(getTracker1Quaterions().conjugate(), getTracker2Quaterions()).normalise().toPitch();
        System.out.println(result);
        return result;
    }
    public Coordinate getLocation()
    {
        return new Coordinate(getX(), getZ());
    }
}
