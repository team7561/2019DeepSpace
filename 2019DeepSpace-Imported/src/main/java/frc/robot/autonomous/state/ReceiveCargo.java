package frc.robot.autonomous.state;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.Robot;

public class ReceiveCargo implements State {
    Timer time = new Timer();
    boolean firstRun = true;
    public boolean run(Robot robot)
    {
        robot.drivetrain.drive(0, 0);
        robot.arm.stop();
        robot.lift.stop();
        if(firstRun) {
            time.reset();
            time.start();
            firstRun = false;
        }
        if(time.get() < 1) {
            robot.ballintake.getBall();
            return false;
        }
        robot.ballintake.stop();
        return true;
    }
}
