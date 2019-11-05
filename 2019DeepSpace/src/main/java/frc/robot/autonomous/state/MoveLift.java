package frc.robot.autonomous.state;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;


public class MoveLift implements State {
    final double height;

    public MoveLift(double height) {
        this.height = height;
    }
    public boolean run(Robot robot) {
        boolean liftStopped = false;

        updateDashboard();
        double lift_height_error = robot.viveMeasurements.get_Arm_Y() - height;
        System.out.println("Robot lift: "+robot.viveMeasurements.get_Arm_Y()+" Target Height: "+ height + " Arm Height Error: "+ lift_height_error);
        if (!robot.viveMeasurements.isValidAngle(robot.viveMeasurements.get_Arm_X_rot())) {
            System.out.println("Invalid arm position");
            return false;
        }

        System.out.println("Moving Lift");
        if (lift_height_error < -Constants.ARM_HEIGHT_TOLERENCE) {
            robot.lift.raise();
            System.out.println("Raise lift");
        }  else if (lift_height_error < Constants.ARM_HEIGHT_TOLERENCE) {
            robot.lift.stop();
            System.out.println("Stop lift");
            liftStopped = true;
        }   else if (lift_height_error < Constants.ARM_HEIGHT_SLOW_TOLERENCE) {
            robot.lift.lower_slowly();
            System.out.println("Lower lift slowly");
        }   else {
            robot.lift.lower();
            System.out.println("Lower lift");
        }

        return liftStopped;
    }
    public void updateDashboard()
    {
        SmartDashboard.putNumber("Move to arm height: ", height);
    }
}