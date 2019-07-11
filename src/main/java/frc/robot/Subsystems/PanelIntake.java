package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;

public class PanelIntake implements Subsystem {

    DoubleSolenoid intakeSolenoid;

    public PanelIntake()
    {
        intakeSolenoid = new DoubleSolenoid(Ports.INTAKE_SOLENOID_CHANNEL_A, Ports.INTAKE_SOLENOID_CHANNEL_B);
    }

    public void getPannel()
    {
        intakeSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void ejectPannel()
    {
        intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putString("Panel Intake Status", intakeSolenoid.get().toString());
    }
}
