package frc.robot.autonomous.state;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.autonomous.Coordinate;

public class DriveToPoint implements State {
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
        double distance = Coordinate.getDistance(currentLocation, destination);
        if (distance < Constants.DISTANCE_TOLERANCE)
            return false;
        double destinationHeading = Coordinate.getHeading(currentLocation, destination);
        if (reverse)
        {
            destinationHeading += 180;
        }
        double currentHeading = robot.drivetrain.getAngle();
        double headingError = (currentHeading - destinationHeading + 900) % 360 - 180;
        if (Math.abs(headingError) < Constants.DISTANCE_TOLERANCE) {
            // drive towards destination
            // slow down if we are close
        } else {
            // turn towards destination
        }
        robot.arm.raise();
        return true;
    }
}