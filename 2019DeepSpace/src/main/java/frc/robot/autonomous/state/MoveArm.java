package frc.robot.autonomous.state;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;

public class MoveArm implements frc.robot.autonomous.state.State {
    final double angle;

    public MoveArm(double angle) {
        this.angle = angle;
    }
    public boolean run(Robot robot) {
        boolean armStopped = false;

        updateDashboard();
        double armAngle = robot.viveMeasurements.getArmAngle();
        double arm_angle_error = armAngle - angle;

        SmartDashboard.putNumber("Vive Arm Angle", armAngle);
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

        return armStopped;
    }
    public void updateDashboard()
    {
        SmartDashboard.putNumber("Move to arm angle: ", angle);
    }
}