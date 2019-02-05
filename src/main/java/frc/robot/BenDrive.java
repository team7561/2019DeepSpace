package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class BenDrive {
    public static void drive(Robot robot, XboxController xboxController)
    {
        if(xboxController.getAButton())
        {
            robot.lift.raise();
            System.out.println("Raising lift");
        }
        else if (xboxController.getBButton())
        {
            robot.lift.lower();
        }
        else
        {
            robot.lift.stop();
        }


    if (xboxController.getXButton())
    {
        robot.arm.raise();
    }
    else if (xboxController.getYButton())
    {
        robot.arm.lower();
    }
    else
    {
        robot.arm.stop();
    }

    }
}
