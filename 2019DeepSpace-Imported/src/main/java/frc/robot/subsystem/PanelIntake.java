package frc.robot.subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;

public class PanelIntake implements Subsystem {

    public DoubleSolenoid intakeSolenoid;

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

    public boolean isClosed()
    {
        return intakeSolenoid.get() == DoubleSolenoid.Value.kForward;
    }
    public boolean isOpen()
    {
        return intakeSolenoid.get() == DoubleSolenoid.Value.kForward;
    }
    @Override
    public void updateDashboard(boolean debug) {
        SmartDashboard.putString("Panel Intake Status", intakeSolenoid.get().toString());
    }
}
