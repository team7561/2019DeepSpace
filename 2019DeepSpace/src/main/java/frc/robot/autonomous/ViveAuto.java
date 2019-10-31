package frc.robot.autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.autonomous.state.*;

public class ViveAuto {
    int currentStep = 0;
    State[] strategy = new State[]{
            //new DriveToPoint(Constants.LOADING_STATION_APPROACH_COORD, -6),
            //new DriveToPoint(Constants.LOADING_STATION_COORD, -7.6, true),
            //new DriveToPoint(Constants.LOADING_STATION_APPROACH_COORD, 55),

            new DriveToPoint(Constants.CARGO_SHIP_APPROACH_COORD, -1),
            new DriveToPoint(Constants.CARGO_SHIP_COORD, -1, true),
            new DeliverCargo(),
            new StopDeliverCargo(),
            new DriveToPoint(Constants.CARGO_SHIP_APPROACH_COORD, -0),

            new DriveToPoint(Constants.LOADING_STATION_APPROACH_COORD, -6),
            new DriveToPoint(Constants.LOADING_STATION_COORD, -7.6, true),
            new DriveToPoint(Constants.LOADING_STATION_APPROACH_COORD, 55),

            new DriveToPoint(Constants.ROCKET_APPROACH_COORD, 169),
            new DriveToPoint(Constants.ROCKET_COORD, 170.5, true),
            new DriveToPoint(Constants.ROCKET_APPROACH_COORD, -140),

            //new MoveArm(60),
            //new MoveLift(-0.9),
            /*new DriveToPoint(Constants.LOADING_STATION_APPROACH_COORD, -77),
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
