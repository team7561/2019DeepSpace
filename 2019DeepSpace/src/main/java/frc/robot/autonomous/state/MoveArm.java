package frc.robot.autonomous.state;

import frc.robot.autonomous.Coordinate;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.autonomous.Coordinate;


public class MoveArm implements frc.robot.autonomous.state.State {
    final double angle;

    public MoveArm(double angle) {
        this.angle = angle;
    }
    public boolean run(Robot robot) {
        boolean armStopped = false;
        boolean liftStopped = false;

        updateDashboard();
        double arm_angle_error = robot.viveMeasurements.get_Arm_X_rot() - angle;
        System.out.println("Robot arm: "+robot.viveMeasurements.get_Arm_X_rot()+" Target Angle: "+ angle + " Arm Angle Error: "+ arm_angle_error);
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

        return liftStopped;
    }
    public void updateDashboard()
    {
        SmartDashboard.putNumber("Move to arm angle: ", angle);
    }
}