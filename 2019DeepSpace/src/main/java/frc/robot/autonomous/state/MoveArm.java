package frc.robot.autonomous.state;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;

public class MoveArm implements frc.robot.autonomous.state.State {
    final double angle;
    final boolean x_value;
    final boolean reverse;

    public MoveArm(double angle) {
        this.angle = angle;
        x_value = true;
        reverse = false;
    }

    public MoveArm(double angle, boolean x_value, boolean reverse) {
        this.angle = angle;
        this.x_value = x_value;
        this.reverse = reverse;
    }
    public boolean run(Robot robot) {
        boolean armStopped = false;

        updateDashboard();
        double armAngle = 0;
        double arm_angle_error = 0;
        armAngle = robot.viveMeasurements.get_Arm_X_rot();
        arm_angle_error = -(armAngle - angle);
        if (reverse)
        {
            arm_angle_error = -arm_angle_error;
        }
        /*double arm_angle_error = armAngle - angle;
        arm_angle_error = -arm_angle_error;*/
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