package frc.robot.autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.autonomous.state.*;

public class ViveAuto {
    int currentStep = 0;
    State[] strategy = new State[]{
            new DriveToPoint(Constants.LOADING_STATION_APPROACH_COORD),
            new DriveToPoint(Constants.LOADING_STATION_COORD),
            new DriveToPoint(Constants.LOADING_STATION_APPROACH_COORD, true),
            new DriveToPoint(Constants.CARGO_SHIP_APPROACH_COORD),
            new DriveToPoint(Constants.CARGO_SHIP_COORD),
            new DriveToPoint(Constants.CARGO_SHIP_APPROACH_COORD, true),
            /*new DriveToPoint(Constants.LOADING_STATION_APPROACH_COORD),
            new DriveToPoint(Constants.LOADING_STATION_COORD),
            new ReceiveHatchPanel(),
            new DriveToPoint(Constants.LOADING_STATION_APPROACH_COORD, true),
            new DriveToPoint(Constants.CARGO_SHIP_APPROACH_COORD),
            new DriveToPoint(Constants.CARGO_SHIP_COORD),*/
            new Stop()
            //new DeliverHatchPanel()
    };
    public boolean run(Robot robot)
    {
        SmartDashboard.putNumber("Vive Auto", currentStep);
        boolean result = strategy[currentStep].run(robot);
        SmartDashboard.putBoolean("Vive Auto result", result);
        if (result)
        {
            ++currentStep;
        }
        return result;
    }
    public void reset()
    {
        currentStep = 0;
    }
}