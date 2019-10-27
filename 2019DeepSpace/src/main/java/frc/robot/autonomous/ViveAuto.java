package frc.robot.autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.autonomous.state.*;

public class ViveAuto {
    int currentStep = 0;
    State[] strategy = new State[]{
            //new DriveToPoint(Constants.LOADING_STATION_APPROACH_COORD, -10),
            new DriveToPoint(new Coordinate(2.0512, -0.624), -123.13),
            new DriveToPoint(new Coordinate(-0.258, 0.5518), -25),
            new DriveToPoint(Constants.LOADING_STATION_COORD, -7.6),
            //new DriveToPoint(Constants.LOADING_STATION_APPROACH_COORD, -170),
            /*new MoveArm(60),
            new MoveLift(-0.9),
            new DriveToPoint(Constants.CARGO_SHIP_APPROACH_COORD, -77),
            new DriveToPoint(Constants.CARGO_SHIP_COORD, -77),
            new DriveToPoint(Constants.CARGO_SHIP_APPROACH_COORD, -77),
            new DriveToPoint(Constants.LOADING_STATION_APPROACH_COORD, -77),
            new DriveToPoint(Constants.LOADING_STATION_COORD, -77),
            new ReceiveHatchPanel(),
            new DriveToPoint(Constants.LOADING_STATION_APPROACH_COORD, -77),
            new DriveToPoint(Constants.CARGO_SHIP_APPROACH_COORD, -77),
            new DriveToPoint(Constants.CARGO_SHIP_COORD, -77),*/
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
