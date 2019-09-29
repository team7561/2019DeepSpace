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
    public boolean run(Robot robot)
    {
        double arm_height_error = robot.viveMeasurements.get_Arm_Y() - height;
        double arm_angle_error = robot.viveMeasurements.get_Arm_X_rot() - angle;

        if (abs(arm_angle_error) > )

        public static double ARM_HEIGHT_TOLERENCE = 0.1;
        public static double ARM_ANGLE_TOLERNENCE = 10;
        return false;
    }
}