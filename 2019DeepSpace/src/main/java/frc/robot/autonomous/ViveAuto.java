package frc.robot.autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.autonomous.state.*;

public class ViveAuto {
    int currentStep = 0;
    double bearing = 47.6;
    State[] strategy = new State[]{
 /*               new DriveToPoint(Constants.LOADING_STATION_APPROACH_COORD, -71.2),
            new DriveToPoint(Constants.LOADING_STATION_COORD, -71.2, true),
            new ReceiveCargo(),
            new DriveToPoint(Constants.LOADING_STATION_APPROACH_COORD, -71.2),

            new DriveToPoint(Constants.CARGO_SHIP_APPROACH_COORD, -60, true),
            new DriveToPoint(Constants.CARGO_SHIP_COORD, -53, true),
            new MoveArm(133, true, true),
            new DeliverCargo(),
            new MoveArm(113, true, true),
            new DriveToPoint(Constants.CARGO_SHIP_APPROACH_COORD, -60),

            new DriveToPoint(Constants.LOADING_STATION_APPROACH_COORD, -71.2),
            new DriveToPoint(Constants.LOADING_STATION_COORD, -71.2, true),
            new ReceiveCargo(),
            new DriveToPoint(Constants.LOADING_STATION_APPROACH_COORD, -71.2),*/

            new DriveToPoint(Constants.ROCKET_APPROACH_COORD, bearing, true),
            new DriveToPoint(Constants.ROCKET_COORD, bearing, true),
            /*
            new MoveArm(26, true, true),
            new DeliverCargo(),
            new MoveArm(65, true, true),

            new DriveToPoint(Constants.ROCKET_APPROACH_COORD, bearing),

            new DriveToPoint(Constants.LOADING_STATION_APPROACH_COORD, -71.2),
            new DriveToPoint(Constants.LOADING_STATION_COORD, -71.2, true),
            new ReceiveCargo(),
            new DriveToPoint(Constants.LOADING_STATION_APPROACH_COORD, -71.2),


            new DriveToPoint(Constants.ROCKET_APPROACH_COORD, bearing),
            new MoveLift(-0.50),
            new DriveToPoint(Constants.ROCKET_COORD, bearing, true),
            new MoveArm(35, true, true),
            new MoveLift(-0.58),

            new DeliverCargo(),
            new MoveArm(65, true, true),

*/
            new Stop()
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
