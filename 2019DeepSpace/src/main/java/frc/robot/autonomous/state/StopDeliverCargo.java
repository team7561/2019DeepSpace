package frc.robot.autonomous.state;

import frc.robot.Robot;

public class StopDeliverCargo implements State {
    public boolean run(Robot robot)
    {
        robot.ballintake.stop();
        return true;
    }
}
