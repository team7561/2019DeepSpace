package frc.robot.autonomous.state;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;

public class DeliverCargo implements State {
    Timer time = new Timer();
    boolean firstRun = true;
    public boolean run(Robot robot)
    {
        if(firstRun)
            time.start();
        firstRun = false;

        robot.ballintake.ejectBall();
        if(time.get() < 1)
            return false;
        return true;
    }
}
