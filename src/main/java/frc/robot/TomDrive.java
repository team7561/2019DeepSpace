package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

public class TomDrive {
    public static void drive(Robot robot, XboxController xboxController) {
        // Controls for lift
        if (xboxController.getYButton())
        {
            robot.lift.raise();
        }
        else if (xboxController.getAButton())
        {
            robot.lift.lower();
        }
        else {
            robot.lift.stop();
        }
        // Controls for arm
        // Controls for intake
        // Controls for driving
        double x = xboxController.getX(GenericHID.Hand.kLeft);
        double y = xboxController.getTriggerAxis(GenericHID.Hand.kRight);
        double speed = 0.5;
        robot.drivetrain.arcadeDrive(x, y, speed, false);
    }
}