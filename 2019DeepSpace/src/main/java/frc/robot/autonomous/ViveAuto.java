package frc.robot.autonomous;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.autonomous.state.*;

public class ViveAuto {
    int currentStep = 0;
    State[] strategy = new State[]{
            new DriveToPoint(Constants.CARGO_SHIP_APPROACH_COORD),
            new DriveToPoint(Constants.CARGO_SHIP_COORD),
            new DeliverCargo(),
            new DriveToPoint(Constants.CARGO_SHIP_APPROACH_COORD, true),
            new DriveToPoint(Constants.LOADING_STATION_APPROACH),
            new DriveToPoint(Constants.LOADING_STATION_COORD),
            new ReceiveHatchPanel(),
            new DriveToPoint(Constants.LOADING_STATION_APPROACH, true),
            new DriveToPoint(Constants.CARGO_SHIP_APPROACH_COORD),
            new DriveToPoint(Constants.CARGO_SHIP_COORD),
            new DeliverHatchPanel()
    };
    public boolean run(Robot robot)
    {
        boolean result = strategy[currentStep].run(robot);
        if (result)
        {
            ++currentStep;
        }
        return result;
    }
}
