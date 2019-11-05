package frc.robot.autonomous.state;

import frc.robot.Robot;

public class Stop implements State {
    public boolean run(Robot robot)
    {
        robot.arm.stop();
        robot.ballintake.stop();
        robot.climber.stopClimbing();
        robot.drivetrain.drive(0,0);
        robot.lift.stop();
        return false;
    }
}
