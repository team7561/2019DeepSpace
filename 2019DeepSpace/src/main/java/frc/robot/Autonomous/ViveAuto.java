package frc.robot.Autonomous;

import frc.robot.Robot;

public class ViveAuto {
    public enum MODE {
        ACQUIRE_HATCH_PANEL,
        ACQUIRE_CARGO,
        MOVE_CARGO_SHIP,
        SCORE_HATCH_PANEL,
        SEARCH_CARGO
    }
    public void acquire_hatch_panel(Robot robot)
    {
        // Travel to  in front of loading station
        // Move forward and grab hatch panel
        // Actuate hatch system
        robot.panelintake.getPannel();

    }
    public void acquire_cargo(Robot robot)
    {
        // Travel to  in front of loading station
        // Move forward and grab hatch panel
        // Actuate cargo system
        robot.ballintake.getBall();
    }
    public void score_cargo_ship()
    {

    }
    public void mainLoop()
    {

    }

}
