package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

public class BenDrive {
    public static void drive(Robot robot, XboxController xboxController)
    {
        // Controls for lift
        if(xboxController.getY(GenericHID.Hand.kLeft)>0.2)
        {
            robot.lift.lower();
        }
        else if(xboxController.getY(GenericHID.Hand.kLeft)<-0.2)
        {
            robot.lift.raise();
        }
        else if(xboxController.getBButton())
        {
            robot.lift.raise();
        }
        else if (xboxController.getAButton())
        {
            robot.lift.lower();
        }
        else
        {
            robot.lift.stop();
        }

        // Controls for Arm
        if(xboxController.getY(GenericHID.Hand.kRight)>0.2)
        {
            robot.arm.lower();
        }
        else if(xboxController.getY(GenericHID.Hand.kRight)<-0.2)
        {
            robot.arm.raise();
        }
        else if(xboxController.getXButton())
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
