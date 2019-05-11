package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class JoshDrive {
    public static void drive(Robot robot, XboxController xboxController)
    {
        if (xboxController.getAButton())
        {
            robot.drivetrain.turnToAngle(robot.viveMeasurements.curr_angle, robot.viveMeasurements.target_angle, 0.5, 2);

        }
    }
}
