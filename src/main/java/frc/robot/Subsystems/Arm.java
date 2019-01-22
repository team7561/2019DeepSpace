package frc.robot.Subsystems;
import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.Ports;
import frc.robot.Speeds;

public class Arm implements Subsystem{
    VictorSP armMotor;
    public Arm() {
        armMotor = new VictorSP(Ports.ARM_CHANNEL);

    }

    private void  armSpeed (double speed)
    {
        armMotor.set(speed);
    }

    public void raise()
    {
        armSpeed(Speeds.ARM_RAISE_SPEED);

    }

    public void lower()
    {
        armSpeed(Speeds.ARM_LOWER_SPEED);
    }

    public void stop()
    {
        armSpeed(Speeds.ARM_STOP_SPEED);
    }
    public void updateDashboard()
    {}
}
