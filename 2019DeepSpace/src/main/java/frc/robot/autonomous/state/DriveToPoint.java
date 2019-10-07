package frc.robot.autonomous.state;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.autonomous.Coordinate;

import static frc.robot.utility.Math.clamp;

public class DriveToPoint implements frc.robot.autonomous.state.State {
    final Coordinate destination;
    double bearing;

    public DriveToPoint(Coordinate destination, double bearing) {
        this.destination = destination;
        this.bearing = bearing;
    }
    public boolean run(Robot robot) {
        final double currentHeading = robot.viveMeasurements.get_Y_rot();
        final Coordinate currentLocation = robot.viveMeasurements.getLocation();
        if(!robot.viveMeasurements.isValidCooardinates(currentLocation)) {
            robot.drivetrain.drive(0, 0);
            return false;
        }

        final double distance = Coordinate.getDistance(currentLocation, destination);
        if (distance < Constants.DISTANCE_TOLERANCE) {
            robot.drivetrain.drive(0, 0);
            return true;
        }

        double offsetDistance = 0.3 * distance;
        if (distance < 0.5)
            offsetDistance = 0;

        Coordinate targetLocation = Coordinate.getApproachCoordinate(destination,  bearing,  offsetDistance);
        double targetHeading = Coordinate.getHeading(currentLocation, targetLocation);
        double headingError = (currentHeading - targetHeading + 900) % 360 - 180;

        Coordinate alternateTargetLocation = Coordinate.getApproachCoordinate(destination,  bearing,  -offsetDistance);
        final double alternateTargetHeading = Coordinate.getHeading(currentLocation, alternateTargetLocation);
        double alternateHeadingError = (currentHeading - alternateTargetHeading + 900) % 360 - 180;
        // 180 - alternateHeadingError
        alternateHeadingError = (180 - alternateHeadingError + 900) % 360 - 180;

        boolean reverse = false;
        System.out.println("Heading errors " + headingError + ", " + alternateHeadingError);
        if (Math.abs(alternateHeadingError) < Math.abs(headingError)) {
            reverse = true;
            System.out.println("Reversing ");
            targetLocation = alternateTargetLocation;
            targetHeading = alternateTargetHeading;
            headingError = alternateHeadingError;
        }

        SmartDashboard.putNumber("Target Location X", targetLocation.getX());
        SmartDashboard.putNumber("Target Location Z", targetLocation.getZ());
        SmartDashboard.putNumber("Target Location Heading", targetHeading);

        double speed = Constants.AUTO_DRIVE_SPEED;
        double slow_speed= Constants.AUTO_DRIVE_SLOW_SPEED;

        SmartDashboard.putNumber("DriveToPoint Distance", distance);
        SmartDashboard.putNumber("DriveToPoint destinationHeading", targetHeading);
        SmartDashboard.putNumber("DriveToPoint currentHeading", currentHeading);
        SmartDashboard.putNumber("DriveToPoint headingError", headingError);

        final double headingDivisor = 40;
        double left = 0.5 - headingError / headingDivisor;
        double right = 0.5 + headingError / headingDivisor;
        left = clamp(left);
        right = clamp(right);
        // At >= 60 degrees, we have left = 1, right = -1
        // At <= -60 degrees, we have left = -1, right = 1

        if (distance < Constants.SLOW_DOWN_DISTANCE) {
            // change to slow speed if close to target
            speed = slow_speed;
        }
        if (reverse)
            speed = -speed;

        robot.drivetrain.drive(left * speed, right * speed);

        return false;
    }
    public void updateDashboard(boolean debug, Robot robot)
    {
        SmartDashboard.putNumber("DriveToPoint_Distance", Coordinate.getDistance(robot.viveMeasurements.getLocation(), destination));
        SmartDashboard.putNumber("DriveToPoint_Heading", Coordinate.getHeading(robot.viveMeasurements.getLocation(), destination));
    }
}