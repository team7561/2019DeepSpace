package frc.robot.autonomous.state;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.autonomous.Coordinate;

import static frc.robot.utility.Math.clamp;

public class DriveToPoint implements frc.robot.autonomous.state.State {
    final Coordinate destination;
    final double bearing;
    final boolean forwardOnly;

    public DriveToPoint(Coordinate destination, double bearing) {
        this.destination = destination;
        this.bearing = bearing;
        this.forwardOnly = false;
    }
    public DriveToPoint(Coordinate destination, double bearing, boolean forwardOnly) {
        this.destination = destination;
        this.bearing = bearing;
        this.forwardOnly = forwardOnly;
    }
    public boolean run(Robot robot) {
        final double currentHeading = robot.viveMeasurements.get_Y_rot()+270;
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

        double offsetDistance = 0.1 * distance;
        if (distance < 0.2)
            offsetDistance = 0;

        Coordinate targetLocation = Coordinate.getApproachCoordinate(destination,  bearing+270,  offsetDistance);
        double targetHeading = Coordinate.getHeading(currentLocation, targetLocation);
        double headingError = (currentHeading - targetHeading + 900) % 360 - 180;

        Coordinate alternateTargetLocation = Coordinate.getApproachCoordinate(destination,  bearing+270,  -offsetDistance);
        final double alternateTargetHeading = Coordinate.getHeading(currentLocation, alternateTargetLocation);
        double alternateHeadingError = (currentHeading - alternateTargetHeading + 900) % 360 - 180;
        // 180 - alternateHeadingError
        alternateHeadingError = (180 - alternateHeadingError + 900) % 360 - 180;

        boolean reverse = false;
        System.out.println("Heading errors " + headingError + ", " + alternateHeadingError);
        if (Math.abs(alternateHeadingError) < Math.abs(headingError) && !forwardOnly) {
            reverse = true;
            System.out.println("Reversing ");
            targetLocation = alternateTargetLocation;
            targetHeading = alternateTargetHeading;
            headingError = alternateHeadingError;
        }

        SmartDashboard.putNumber("Target Location X", targetLocation.getX());
        SmartDashboard.putNumber("Target Location Z", targetLocation.getZ());
        SmartDashboard.putNumber("Target Location Heading", targetHeading);

        SmartDashboard.putNumber("Destination Location X", destination.getX());
        SmartDashboard.putNumber("Destination Location Z", destination.getZ());
        SmartDashboard.putNumber("Destination Location Heading", bearing);

        double speed = Constants.AUTO_DRIVE_SPEED;
        double slow_speed= Constants.AUTO_DRIVE_SLOW_SPEED;

        SmartDashboard.putNumber("DriveToPoint Distance", distance);
        SmartDashboard.putNumber("DriveToPoint destinationHeading", targetHeading);
        SmartDashboard.putNumber("DriveToPoint currentHeading", currentHeading);
        SmartDashboard.putNumber("DriveToPoint headingError", headingError);

        final double headingDivisor = 30;
        double left = 0.4 - headingError / headingDivisor;
        double right = 0.4 + headingError / headingDivisor;
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
        //speed *= 0.9;
        robot.drivetrain.drive(left * speed, right * speed);

        return false;
    }
    public void updateDashboard(boolean debug, Robot robot)
    {
        SmartDashboard.putNumber("DriveToPoint_Distance", Coordinate.getDistance(robot.viveMeasurements.getLocation(), destination));
        SmartDashboard.putNumber("DriveToPoint_Heading", Coordinate.getHeading(robot.viveMeasurements.getLocation(), destination));
    }
}