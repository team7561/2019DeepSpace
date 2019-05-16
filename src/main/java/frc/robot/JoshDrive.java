package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class JoshDrive {
    public static void init()
    {
        SmartDashboard.putNumber("Target Angle", 0);
        SmartDashboard.putNumber("Target Height", -1);
        SmartDashboard.putNumber("Target Arm Angle", 45);
    }
    public static void drive(Robot robot, XboxController xboxController)
    {
        double curr_angle = SmartDashboard.getNumber("Right Pitch", 0);
        double target_angle = SmartDashboard.getNumber("Left Pitch", 0);
        double error = (360 + curr_angle - target_angle) % 360;
        double error_magnitude = (180-Math.abs(180-error))/180*0.3+0.1;
        double deadband = 5;

        SmartDashboard.putNumber("Error Value", error);
        SmartDashboard.putNumber("Error Magnitude", error_magnitude);
        SmartDashboard.putNumber("Current Angle", curr_angle);
        //SmartDashboard.putNumber("Target Angle", target_angle);

        double inverted = 1;


        //System.out.println("Josh Tracking");
        if (xboxController.getAButton())
        {
            System.out.println("Vive Tracking");
            if ((error > 180) && (error < 360-deadband))
            {
                System.out.println("Turning right");
                robot.drivetrain.drive(inverted*error_magnitude, inverted*error_magnitude);
            }
            else if ((error < 180) && (error > deadband))
            {
                System.out.println("Turning left");
                robot.drivetrain.drive(inverted*-error_magnitude, inverted*-error_magnitude);
            }
            else {
                robot.drivetrain.drive(0,0);
            }
        }
        else
        {
            robot.drivetrain.drive(0, 0);
        }

        double currentHeight = SmartDashboard.getNumber("Right Y", -1);
        double targetHeight = SmartDashboard.getNumber("Target Height", -1);
        double heightDeadband = 0.02;
        double error_height = currentHeight - targetHeight;
        double armErrorMagnitude = error_height / 2;
        
        SmartDashboard.putNumber("error_height", error_height);
        SmartDashboard.putNumber("armErrorMagnitude", armErrorMagnitude);

        if (xboxController.getBButton())
        {
            if (targetHeight != -1)
                {
                    if (error_height > heightDeadband)
                    {
                        robot.lift.setMotorSpeed(armErrorMagnitude + 0.2);
                    }
                    else if (error_height < -heightDeadband)
                    {
                        robot.lift.setMotorSpeed(armErrorMagnitude - 0.2);
                    }
                    else
                    {
                        robot.lift.stop();
                    }
                }
                else
                {
                    robot.lift.stop();
                }
            if (false)
                {
                if (targetHeight != -1)
                {
                    if (currentHeight < targetHeight-heightDeadband)
                    {
                        robot.lift.raise();
                    }
                    else if (currentHeight > targetHeight+heightDeadband)
                    {
                        robot.lift.lower();
                    }
                    else
                    {
                        robot.lift.stop();
                    }
                }
                else
                {
                    robot.lift.stop();
                }
            }

        }
        else
        {
            robot.lift.stop();
        }
    }
}
