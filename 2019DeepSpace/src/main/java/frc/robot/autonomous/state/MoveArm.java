package frc.robot.autonomous.state;

import frc.robot.autonomous.Coordinate;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.autonomous.Coordinate;


public class MoveArm implements frc.robot.autonomous.state.State {
    final double angle;
    final double height;

    public MoveArm(double angle, double height) {
        this.angle = angle;
        this.height = height;
    }
    public boolean run(Robot robot) {
        boolean armStopped = false;
        boolean liftStopped = false;

        updateDashboard();
        double arm_height_error = robot.viveMeasurements.get_Arm_Y() - height;
        double arm_angle_error = robot.viveMeasurements.get_Arm_X_rot() - angle;
        System.out.println("Robot arm: "+robot.viveMeasurements.get_Arm_X_rot()+" Target Angle: "+ angle + " Arm Angle Error: "+ arm_angle_error);
        System.out.println("Robot lift: "+robot.viveMeasurements.get_Arm_Y()+" Target Height: "+ height + " Arm Height Error: "+ arm_height_error);
        if (!robot.viveMeasurements.isValidAngle(robot.viveMeasurements.get_Arm_X_rot())) {
            System.out.println("Invalid arm position");
            return false;
        }

        System.out.println("Moving Arm");
        if (arm_angle_error < -Constants.ARM_ANGLE_SLOW_TOLERNENCE) {
            robot.arm.raise();
            System.out.println("Raise arm fast");
        }  else if (arm_angle_error < -Constants.ARM_ANGLE_TOLERNENCE) {
            robot.arm.raise_slowly();
            System.out.println("Raise arm slowly");
        }  else if (arm_angle_error < Constants.ARM_ANGLE_TOLERNENCE) {
            robot.arm.stop();
            System.out.println("Stop arm");
            armStopped = true;
        }   else if (arm_angle_error < Constants.ARM_ANGLE_SLOW_TOLERNENCE) {
            robot.arm.lower_slowly();
            System.out.println("Lower arm slowly");
        } else {
            robot.arm.lower();
            System.out.println("Lower arm");
        }

        System.out.println("Moving Lift");
        if (arm_height_error < -Constants.ARM_HEIGHT_TOLERENCE) {
            robot.lift.raise();
            System.out.println("Raise lift");
        }  else if (arm_height_error < Constants.ARM_HEIGHT_TOLERENCE) {
            robot.lift.stop();
            System.out.println("Stop lift");
            liftStopped = true;
        }   else if (arm_height_error < Constants.ARM_HEIGHT_SLOW_TOLERENCE) {
            robot.lift.lower_slowly();
            System.out.println("Lower lift slowly");
        }   else {
            robot.lift.lower();
            System.out.println("Lower lift");
        }

        return armStopped && liftStopped;
    }
    public void updateDashboard()
    {
        SmartDashboard.putNumber("Move to arm angle: ", angle);
        SmartDashboard.putNumber("Move to arm height: ", height);
    }
}