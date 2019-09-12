package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;
import frc.robot.Speeds;

public class Climber implements Subsystem {
    TalonSRX climberMotorA;
    TalonSRX climberMotorB;
    VictorSPX climberVacuumMotor;
    DoubleSolenoid climberReleaseSolenoid;
    DoubleSolenoid climberDeploySoleniod;
    DigitalInput climberLimitDigitalInput;
    public Climber()
    {
        climberMotorA = new TalonSRX(Ports.CLIMB_ELEVATOR_A_CANID);
        climberMotorB = new TalonSRX(Ports.CLIMB_ELEVATOR_B_CANID);
        climberMotorA.setInverted(false);
        climberMotorB.setInverted(true);
        climberVacuumMotor = new VictorSPX(Ports.CLIMB_VACUUM_CANID);
        climberReleaseSolenoid = new DoubleSolenoid(Ports.CLIMBER_RELEASE_SOLENOID_CHANNEL_A, Ports.CLIMBER_RELEASE_SOLENOID_CHANNEL_B);
        climberDeploySoleniod = new DoubleSolenoid(Ports.CLIMBER_DEPLOY_SOLENOID_CHANNEL_A, Ports.CLIMBER_DEPLOY_SOLENOID_CHANNEL_B);
        climberLimitDigitalInput = new DigitalInput(Ports.LIMIT_CLIMB_LOWER);

    }
    private void setWinchSpeed(double speed)
    {
        climberMotorA.set(ControlMode.PercentOutput, speed);
        climberMotorB.set(ControlMode.PercentOutput, -speed);
    }
    private void setVacuumSpeed(double speed)
    {
        climberVacuumMotor.set(ControlMode.PercentOutput, speed);
    }
    public void deployLift()
    {
        climberDeploySoleniod.set(DoubleSolenoid.Value.kReverse);
    }
    public void retractLift()
    {
        climberDeploySoleniod.set(DoubleSolenoid.Value.kForward);
    }
    public void releaseCarridge()
    {
        climberReleaseSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    public void recoverCarrige()
    {
        climberReleaseSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    public void pullUp()
    {
        setWinchSpeed(Speeds.CLIMBER_LIFT_SPEED);
        /*if (climberVacuumMotor.getMotorOutputPercent() > 0) {
            
        }*/
    }
    public void undoWinch()
    {
        setWinchSpeed(-Speeds.CLIMBER_LIFT_SPEED);
   
    }
    public void runVacuum()
    {
        setVacuumSpeed(Speeds.CLIMBER_VACUUM_SPEED);
    }
    public void stopVacuum()
    {
        setVacuumSpeed(0);
    }
    public void stopClimbing()
    {
        setWinchSpeed(Speeds.CLIMBER_STOP_SPEED);
    }
    public void updateDashboard(boolean debug)
    {
        if (debug)
            {
            SmartDashboard.putNumber("Climber Motor A Speed", climberMotorA.getMotorOutputPercent());
            SmartDashboard.putNumber("Climber Motor A Current", climberMotorA.getOutputCurrent());
            SmartDashboard.putNumber("Climber Motor B Speed", climberMotorB.getMotorOutputPercent());
            SmartDashboard.putNumber("Climber Motor B Current", climberMotorB.getOutputCurrent());
            SmartDashboard.putNumber("Climber Vacuum speed", climberVacuumMotor.getMotorOutputPercent());
            SmartDashboard.putBoolean("Climber Limit Switch", climberLimitDigitalInput.get());
            SmartDashboard.putString("Climber Release Solenoid", climberReleaseSolenoid.get().toString());
            SmartDashboard.putString("Climber Deploy Solenoid", climberDeploySoleniod.get().toString());
        }


    }

}
