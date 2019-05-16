package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

public class JamesDrive {
    public static void drive(Robot robot, Joystick joystick)
    {
        robot.speedControl = (joystick.getThrottle()+1)/2;

        if (joystick.getRawButton(7))
        {
            robot.invertedDrive = false;
        }
        if (joystick.getRawButton(8))
        {
            robot.invertedDrive = true;
        }
        if (joystick.getRawButton(4))
        {
            robot.drivetrain.drive(-robot.speedControl, robot.speedControl);
        }
        else if(joystick.getRawButton(11))
        {
            //robot.drivetrain.turnToAngle(robot.visionController.targetYaw(), Speeds.VISION_DRIVE_SPEED);
        }
        else
        {
            robot.drivetrain.arcadeDrive(joystick.getX(GenericHID.Hand.kLeft),joystick.getY(GenericHID.Hand.kLeft), robot.speedControl, robot.invertedDrive);
        }
        if (joystick.getRawButton(9))
        {
            robot.visionController.setCargo();
        }
        else if (joystick.getRawButton(10))
        {
            robot.visionController.setTape();
        }
        if (joystick.getTrigger())
        {
            robot.ballintake.ejectBall();
        }
        else if (joystick.getRawButton(2))
        {
            robot.ballintake.getBall();
        }
        else {
            robot.ballintake.keepBall();
        }
        if (joystick.getTrigger())
        {
            //robot.panelintake.getPannel();
        }
        else if (joystick.getRawButton(2))
        {
            //robot.panelintake.ejectPannel();
        }
        else {
        }

    }
}
