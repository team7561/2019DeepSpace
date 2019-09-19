package frc.robot.autonomous.state;

import frc.robot.Constants;
import frc.robot.Robot;

public class ReceiveCargo {
    public boolean run(Robot robot)
    {
        robot.ballintake.getBall();
        if (robot.ballintake.hasBall(robot.pdp.getCurrent(Constants.CARGO_STALL_CURRENT)))
        {
            return true;
        }
        return false;
    }
}
