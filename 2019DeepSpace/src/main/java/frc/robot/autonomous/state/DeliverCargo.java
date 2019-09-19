package frc.robot.autonomous.state;

import frc.robot.Robot;

public class DeliverCargo implements State {
    public boolean run(Robot robot)
    {
        robot.ballintake.ejectBall();
        return true;
    }
}
