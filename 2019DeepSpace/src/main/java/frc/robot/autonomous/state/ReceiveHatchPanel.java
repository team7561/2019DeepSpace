package frc.robot.autonomous.state;

import frc.robot.Robot;

public class ReceiveHatchPanel implements State
{
    public boolean run(Robot robot)
    {
        robot.panelintake.getPannel();
        return true;
    }
}
