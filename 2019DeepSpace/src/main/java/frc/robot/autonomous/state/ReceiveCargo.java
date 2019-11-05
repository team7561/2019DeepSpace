package frc.robot.autonomous.state;

import frc.robot.Constants;
import frc.robot.Robot;

public class ReceiveCargo {
    public boolean run(Robot robot)
    {
        robot.ballintake.getBall();
        if (robot.ballintake.hasBall())
        {
            robot.ballintake.stop();
            return true;
        }
        return false;
    }
}
