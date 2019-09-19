package frc.robot.autonomous.state;

import frc.robot.Robot;

public class DeliverHatchPanel implements State {
    public boolean run(Robot robot)
    {
        robot.arm.lower();
        if(robot.arm.)
        robot.panelintake.ejectPannel();
        return true;
    }
}
