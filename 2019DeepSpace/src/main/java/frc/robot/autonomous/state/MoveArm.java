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
        updateDashboard();
        double arm_height_error = robot.viveMeasurements.get_Arm_Y() - height;
        double arm_angle_error = robot.viveMeasurements.get_Arm_X_rot() - angle;
        if (robot.viveMeasurements.isValidAngle(robot.viveMeasurements.get_Arm_X_rot())) {
            if (Math.abs(arm_angle_error) < Constants.ARM_ANGLE_TOLERNENCE) {
                robot.arm.stop();
            } else {
                if (arm_angle_error > Constants.ARM_ANGLE_TOLERNENCE) {
                    robot.arm.raise();
                } else if (arm_angle_error > Constants.ARM_ANGLE_SLOW_TOLERNENCE) {
                    robot.arm.raise_slowly();
                } else if (arm_angle_error < Constants.ARM_ANGLE_TOLERNENCE) {
                    robot.arm.lower();
                } else if (arm_angle_error < Constants.ARM_ANGLE_SLOW_TOLERNENCE) {
                    robot.arm.lower_slowly();
                } else {
                    robot.arm.stop();
                }
            }

            if (robot.viveMeasurements.isValidAngle(robot.viveMeasurements.get_Arm_Y())) {
                if (Math.abs(arm_height_error) < Constants.ARM_HEIGHT_TOLERENCE) {
                    robot.lift.stop();
                }
                else
                {
                    if (arm_height_error > Constants.ARM_HEIGHT_TOLERENCE) {
                        robot.lift.raise();
                    } else if (arm_height_error > Constants.ARM_HEIGHT_SLOW_TOLERENCE) {
                        robot.lift.raise_slowly();
                    } else if (arm_height_error < Constants.ARM_HEIGHT_TOLERENCE) {
                        robot.lift.lower();
                    } else if (arm_height_error < Constants.ARM_HEIGHT_SLOW_TOLERENCE) {
                        robot.lift.lower_slowly();
                    } else {
                        robot.arm.stop();
                    }
                }
            }
            if ((Math.abs(arm_height_error) < Constants.ARM_HEIGHT_TOLERENCE) && Math.abs(arm_angle_error) < Constants.ARM_ANGLE_TOLERNENCE)
            {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }
    public void updateDashboard()
    {
        SmartDashboard.putNumber("Move to arm angle: ", angle);
        SmartDashboard.putNumber("Move to arm height: ", height);
    }
}