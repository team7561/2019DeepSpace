package frc.robot.interfaces;

import frc.robot.Robot;

public class ReallDrivetrain implements DriveTrainInterface {
    Robot robot;
    ReallDrivetrain(Robot robot)
    {
        robot = this.robot;
    }
    public double getBearing()
    {
        return robot.drivetrain.getAngle();
    }
    public double getLocationX()
    {
        return robot.viveMeasurements.getX();
    }
    public double getLocationZ()
    {
        return robot.viveMeasurements.getZ();
    }
    public void tankDrive(double left, double right)
    {
        robot.drivetrain.drive(left, right);
    }
}
