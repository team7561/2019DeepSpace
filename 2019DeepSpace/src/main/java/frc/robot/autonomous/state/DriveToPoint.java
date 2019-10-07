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
        final Coordinate currentLocation = robot.viveMeasurements.getLocation();
        final double distance = Coordinate.getDistance(currentLocation, destination);

        double offsetDistance = 0.3 * distance;
        if (distance < 0.5)
            offsetDistance = 0;
        //offsetDistance = distance;

        Coordinate targetLocation = Coordinate.getApproachCoordinate(destination,  bearing,  offsetDistance);
        SmartDashboard.putNumber("Target Location X", targetLocation.getX());
        SmartDashboard.putNumber("Target Location Z", targetLocation.getZ());
        SmartDashboard.putNumber("Target Location Heading", bearing);

        if(!robot.viveMeasurements.isValidCooardinates(currentLocation)) {
            robot.drivetrain.drive(0, 0);
            return false;
        }
        double speed = Constants.AUTO_DRIVE_SPEED;
        double slow_speed= Constants.AUTO_DRIVE_SLOW_SPEED;
        if (distance < Constants.DISTANCE_TOLERANCE) {
            robot.drivetrain.drive(0, 0);
            return true;
        }
        final double destinationHeading = Coordinate.getHeading(currentLocation, targetLocation);

        final double currentHeading = robot.viveMeasurements.get_Y_rot();
        double headingError = (currentHeading - destinationHeading + 900) % 360 - 180;
        SmartDashboard.putNumber("DriveToPoint Distance", distance);
        SmartDashboard.putNumber("DriveToPoint destinationHeading", destinationHeading);
        SmartDashboard.putNumber("DriveToPoint currentHeading", currentHeading);
        SmartDashboard.putNumber("DriveToPoint headingError", headingError);

        boolean reverse = false;
       /*if ( headingError > 90 || headingError < -90) {
            //System.out.println("Reversing");
            reverse = true;
            // 180 - headingError
            headingError = (180 - headingError + 900) % 360 - 180;

            // or should it be 180 + headingError?
        }*/

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