package frc.robot.autonomous.state;

import frc.robot.Robot;

public class DeliverHatchPanel implements State {
    public boolean run(Robot robot)
    {
        /*
        if(robot.arm.atLowLimit())
        {
            robot.panelintake.ejectPannel();
            return true;
        }
        else
        {
            robot.arm.lower();
            return false;
        }*/
        return true;
    }
}
