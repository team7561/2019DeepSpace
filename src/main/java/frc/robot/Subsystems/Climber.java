package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.Ports;
import frc.robot.Speeds;

public class Climber {
    TalonSRX climberA;
    TalonSRX climberB;
    VictorSPX climberVacuum;
    DoubleSolenoid climberRelease;
    public void init()
    {
        climberA = new TalonSRX(Ports.CLIMB_ELEVATOR_A_CANID);
        climberB = new TalonSRX(Ports.CLIMB_ELEVATOR_B_CANID);
        climberVacuum = new VictorSPX(Ports.CLIMB_VACUUM_CANID);
        climberRelease = new DoubleSolenoid(Ports.CLIMBER_SOLENOID_CHANNEL_A, Ports.CLIMBER_SOLENOID_CHANNEL_B);

    }
    private void setSpeed(double speed)
    {
        climberA.set(ControlMode.PercentOutput, speed);
        climberB.set(ControlMode.PercentOutput, -speed);
    }
    private void setVacuumSpeed(double speed)
    {
        climberVacuum.set(ControlMode.PercentOutput, speed);
    }
    public void liftDeploy()
    {
        setSpeed(Speeds.CLIMBER_DEPLOY_SPEED);
    }
    public void releaseSolenoid()
    {
        climberRelease.set(DoubleSolenoid.Value.kReverse);
    }
    public void extendSolenoid()
    {
        climberRelease.set(DoubleSolenoid.Value.kForward);
    }
    public void pullUp()
    {
        setSpeed(Speeds.CLIMBER_LIFT_SPEED);
    }
    public void vacuumStart()
    {
        setSpeed(Speeds.CLIMBER_VACUUM_SPEED);
    }
    public void climbStop()
    {
        setSpeed(Speeds.CLIMBER_STOP_SPEED);
    }

}
