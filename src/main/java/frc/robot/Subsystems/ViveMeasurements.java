package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ViveMeasurements {
    public double curr_angle = SmartDashboard.getNumber("Right Pitch", 0);
    public double target_angle = SmartDashboard.getNumber("Left Pitch", 0);
}
