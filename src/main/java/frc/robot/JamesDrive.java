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
        if (joystick.getRawButton(1))
        {
            robot.drivetrain.drive(-robot.speedControl, robot.speedControl);
        }
        else
        {
            robot.drivetrain.arcadeDrive(joystick.getX(GenericHID.Hand.kLeft),joystick.getY(GenericHID.Hand.kLeft), robot.speedControl, robot.invertedDrive);
        }
        //drivetrain.arcadeDrive(joystick.getX(GenericHID.Hand.kLeft), joystick.getY(GenericHID.Hand.kLeft));
        if (joystick.getRawButton(11))
        {
            robot.lift.raise();
        }
        else if (joystick.getRawButton(12))
        {
            robot.lift.lower();
        }
        else {
            robot.lift.stop();
        }
        if (joystick.getTrigger())
        {
            robot.ballintake.getBall();
        }
        else if (joystick.getRawButton(2))
        {
            robot.ballintake.ejectBall();
        }
        else {
            robot.ballintake.stop();
        }
    }
}
