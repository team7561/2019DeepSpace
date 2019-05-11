package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

public class AlexDrive {
    public static void drive(Robot robot, XboxController xboxController) {

        // Controls for lift
        if (xboxController.getBButton()) {
            robot.lift.raise();
        } else if (xboxController.getAButton()) {
            robot.lift.lower();
        } else {
            robot.lift.stop();
        }

        // Controls for arm
        if (xboxController.getYButton()) {
            robot.arm.raise();
        } else if (xboxController.getXButton()) {
            robot.arm.lower();
        } else {
            robot.arm.stop();
        }

        // Controls for intake
        if (xboxController.getBumper(GenericHID.Hand.kRight)) {
            robot.ballintake.getBall();
        } else if (xboxController.getBumper(GenericHID.Hand.kLeft)) {
            robot.ballintake.ejectBall();
        } else {
            robot.ballintake.keepBall();
        }

        // Controls for driving
        double x = xboxController.getX(GenericHID.Hand.kLeft);
        double y = xboxController.getY(GenericHID.Hand.kLeft);
        //double twist = xboxController.getPOV();
        //if (Math.abs(twist) < 0.4)
        {
            //  twist = 0;
        }
        //
        double speed = xboxController.getTriggerAxis(GenericHID.Hand.kLeft);
        boolean inverted = false;
        robot.drivetrain.arcadeDrive(x, y, speed, inverted);



    }
}