package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

public class BenDrive {
    public static void drive(Robot robot, XboxController xboxController)
    {
        // Controls for lift
        if(Math.abs(xboxController.getY(GenericHID.Hand.kLeft))>0.1)
        {
            robot.lift.setMotorSpeed(-xboxController.getY(GenericHID.Hand.kLeft));
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
        if(Math.abs(xboxController.getY(GenericHID.Hand.kRight))>0.1)
        {
            robot.arm.setSpeed(0.35*(-xboxController.getY(GenericHID.Hand.kRight)));
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

        // Controls for Climber
        
        if(xboxController.getBumper(GenericHID.Hand.kLeft))
        {
            robot.climber.deployLift();
            
        }
        else if(xboxController.getBumper(GenericHID.Hand.kRight))
        {
            robot.climber.releaseCarridge();
            robot.climber.runVacuum();
        }
        else if(xboxController.getStartButton())
        {
            robot.climber.retractLift();
        }
        else if(xboxController.getTriggerAxis(GenericHID.Hand.kRight)>0.3)
        {
            robot.climber.pullUp();
        }
        else if(xboxController.getTriggerAxis(GenericHID.Hand.kLeft)>0.3)
        {
            robot.climber.undoWinch();
        }
        else 
        {
            robot.climber.stopClimbing();
        }
        
    }
}
