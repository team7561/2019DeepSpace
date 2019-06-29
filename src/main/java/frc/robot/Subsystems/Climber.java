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
    DoubleSolenoid climberDeploy;
    public void init()
    {
        climberA = new TalonSRX(Ports.CLIMB_ELEVATOR_A_CANID);
        climberB = new TalonSRX(Ports.CLIMB_ELEVATOR_B_CANID);
        climberVacuum = new VictorSPX(Ports.CLIMB_VACUUM_CANID);
        climberRelease = new DoubleSolenoid(Ports.CLIMBER_RELEASE_SOLENOID_CHANNEL_A, Ports.CLIMBER_RELEASE_SOLENOID_CHANNEL_B);
        climberDeploy = new DoubleSolenoid(Ports.CLIMBER_DEPLOY_SOLENOID_CHANNEL_A, Ports.CLIMBER_DEPLOY_SOLENOID_CHANNEL_B);

    }
    private void setWinchSpeed(double speed)
    {
        climberA.set(ControlMode.PercentOutput, speed);
        climberB.set(ControlMode.PercentOutput, -speed);
    }
    private void setVacuumSpeed(double speed)
    {
        climberVacuum.set(ControlMode.PercentOutput, speed);
    }
    public void deployLift()
    {
        climberDeploy.set(DoubleSolenoid.Value.kReverse);
    }
    public void retractLift()
    {
        climberDeploy.set(DoubleSolenoid.Value.kForward);
    }
    public void releaseCarridge()
    {
        climberRelease.set(DoubleSolenoid.Value.kReverse);
    }
    public void recoverCarrige()
    {
        climberRelease.set(DoubleSolenoid.Value.kForward);
    }
    public void pullUp()
    {
        setWinchSpeed(Speeds.CLIMBER_LIFT_SPEED);
    }
    public void startVacuum()
    {
        setVacuumSpeed(Speeds.CLIMBER_VACUUM_SPEED);
    }
    public void stopVacuum()
    {
        setVacuumSpeed(0);
    }
    public void climbStop()
    {
        setWinchSpeed(Speeds.CLIMBER_STOP_SPEED);
    }

}
