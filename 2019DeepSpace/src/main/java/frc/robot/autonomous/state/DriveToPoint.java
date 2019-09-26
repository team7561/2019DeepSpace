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
            if (distance < Constants.DISTANCE_TOLERANCE) {
                robot.drivetrain.drive(0, 0);
                return true;
            }
            double destinationHeading = Coordinate.getHeading(currentLocation, destination);
            if (reverse) {
                destinationHeading += 180;
            }
            double currentHeading = robot.viveMeasurements.get_Y_rot();
            double headingError = (currentHeading - destinationHeading + 900) % 360 - 180;
            String headingMessage = "Distance: " + distance + ", destinationHeading " + destinationHeading + ", currentHeading " + currentHeading + ", headingError " + headingError;
            System.out.println(headingMessage);
            if (Math.abs(headingError) < Constants.ANGLE_TOLERANCE || reverse) {
                if (distance < Constants.SLOW_DOWN_DISTANCE) {
                    // change to slow speed if close to target
                    speed = slow_speed;
                }
                if (reverse)
                {
                    // Reverse speed if going in reverse
                    speed = -speed;

                }
                robot.drivetrain.drive(slow_speed, slow_speed);
            } else {
                System.out.println("Turning");
                // turn towards destination
                if (headingError > 0) {
                    robot.drivetrain.drive(-slow_speed / 2, slow_speed / 2);
                }
                else
                {
                    robot.drivetrain.drive(slow_speed / 2, -slow_speed / 2);
                }

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