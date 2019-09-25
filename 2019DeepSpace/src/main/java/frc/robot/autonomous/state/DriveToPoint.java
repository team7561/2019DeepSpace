package frc.robot.autonomous.state;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.autonomous.Coordinate;

public class DriveToPoint implements frc.robot.autonomous.state.State {
    final Coordinate destination;
    final boolean reverse;

    public DriveToPoint(Coordinate destination) {
        this.destination = destination;
        this.reverse = false;
    }
    public DriveToPoint(Coordinate destination, boolean reverse) {
        this.destination = destination;
        this.reverse = reverse;
    }
    public boolean run(Robot robot) {
        Coordinate currentLocation = robot.viveMeasurements.getLocation();
        if(robot.viveMeasurements.isValidCooardinates(currentLocation)) {

            double speed = 0.2;
            double slow_speed= 0.15;
            double distance = Coordinate.getDistance(currentLocation, destination);
            if (distance < Constants.DISTANCE_TOLERANCE)
                return true;
            double destinationHeading = Coordinate.getHeading(currentLocation, destination);
            if (reverse) {
                destinationHeading += 180;
            }
            double currentHeading = robot.drivetrain.getAngle();
            double headingError = (currentHeading - destinationHeading + 900) % 360 - 180;
            if (Math.abs(headingError) < Constants.ANGLE_TOLERANCE) {
                if (distance > Constants.SLOW_DOWN_DISTANCE) {
                    // drive towards destination
                    robot.drivetrain.drive(speed, -speed);
                }
                else {
                    // slow down if we are close
                    robot.drivetrain.drive(slow_speed, -slow_speed);
                }
            } else {
                // turn towards destination
                robot.drivetrain.turnToAngle(destinationHeading, speed);

            }
            //robot.arm.raise();
        }
        return false;
    }
    public void updateDashboard(boolean debug, Robot robot)
    {
        SmartDashboard.putNumber("DriveToPoint_Distance", Coordinate.getDistance(robot.viveMeasurements.getLocation(), destination));
        SmartDashboard.putNumber("DriveToPoint_Heading", Coordinate.getHeading(robot.viveMeasurements.getLocation(), destination));
    }
}