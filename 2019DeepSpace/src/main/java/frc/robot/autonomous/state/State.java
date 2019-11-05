package frc.robot.autonomous.state;

import frc.robot.Robot;

public interface State {
    // Return true if done
    public boolean run(Robot robot);
}
