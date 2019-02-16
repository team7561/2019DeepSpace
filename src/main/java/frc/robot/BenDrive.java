package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

public class BenDrive {
    public static void drive(Robot robot, XboxController xboxController)
    {
        if(xboxController.getBumper(GenericHID.Hand.kLeft))
        {
            robot.lift.raise();
            System.out.println("Raising lift");
        }
        else if (xboxController.getAButton())
        {
            robot.lift.lower();
        }
        else
        {
            robot.lift.stop();
        }


        if (xboxController.getXButton())
        {
            System.out.println("Raising arm");
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
