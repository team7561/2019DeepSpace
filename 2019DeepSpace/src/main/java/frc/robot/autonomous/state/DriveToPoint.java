package frc.robot.autonomous.state;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.autonomous.Coordinate;

import static frc.robot.utility.Math.clamp;

public class DriveToPoint implements frc.robot.autonomous.state.State {
    final Coordinate destination;
    boolean reverse;

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

            double speed = Constants.AUTO_DRIVE_SPEED;
            double slow_speed= Constants.AUTO_DRIVE_SLOW_SPEED;
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

            reverse = false;
            if ( Math.abs(headingError) > 90) {
                System.out.println("Reversing");
                reverse = true;
                // 180 - headingError
                headingError = (180 - headingError + 900) % 360 - 180;

                // or should it be 180 + headingError?
            }

            {
                double headingDivisor = 40;
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
            }
            //robot.arm.raise();
        }
        robot.drivetrain.drive(0,0);
        return false;
    }
    public void updateDashboard(boolean debug, Robot robot)
    {
        SmartDashboard.putNumber("DriveToPoint_Distance", Coordinate.getDistance(robot.viveMeasurements.getLocation(), destination));
        SmartDashboard.putNumber("DriveToPoint_Heading", Coordinate.getHeading(robot.viveMeasurements.getLocation(), destination));
    }
}