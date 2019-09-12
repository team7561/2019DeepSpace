package frc.robot;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;


public class LiamDrive {
    public static void drive(Robot robot, Joystick joystick) {
        // Controls for lift
        // Controls for arm
        // Controls for intake
        // Controls for drivin

        if (joystick.getRawButton(1)) {

            robot.ballintake.ejectBall();
        } else if (joystick.getRawButton(2)){
            robot.ballintake.getBall();

        }else{
            robot.ballintake.keepBall();
        }
        if (joystick.getRawButton(7)) {
            robot.arm.lower();
        }
        else if (joystick.getRawButton(8)) {
            robot.arm.raise();
        } else{
            robot.arm.stop();
        }

        if (joystick.getRawButton(9)) {
            robot.lift.lower();
        }
        else if (joystick.getRawButton(10)) {
            robot.lift.raise();
        } else{
            robot.lift.stop();
        }

        double x =joystick.getX(GenericHID.Hand.kLeft);
        double y = joystick.getY(GenericHID.Hand.kLeft);
        double twist = joystick.getZ();
        if (Math.abs(twist) < 0.4)
        {
            twist = 0;
        }

        double speed = joystick.getThrottle();
        boolean inverted = false;
        robot.drivetrain.arcadeDrive(x, y, speed, inverted);
        robot.drivetrain.arcadeDriveLiam(x, y, twist, speed, inverted);
    }
}