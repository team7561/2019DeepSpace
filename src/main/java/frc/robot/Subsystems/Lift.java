package frc.robot.Subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;
import frc.robot.Speeds;

import javax.sound.sampled.Port;

public class Lift implements Subsystem{

    private TalonSRX liftMotorA, liftMotorB;
    private DigitalInput limitSwitch;
    private Encoder liftEncoder;

    public void init()
    {
        liftMotorA = new TalonSRX(Ports.LIFT_LEFT_CANID);
        liftMotorB = new TalonSRX(Ports.LIFT_RIGHT_CANID);
        liftMotorA.setNeutralMode(NeutralMode.Brake);
        liftMotorB.setNeutralMode(NeutralMode.Brake);
        liftMotorA.configContinuousCurrentLimit(5, 100);
        liftMotorB.configContinuousCurrentLimit(5, 100);
        liftEncoder = new Encoder(Ports.ENCODER_LIFT_A_CHANNEL, Ports.ENCODER_LIFT_B_CHANNEL);
    }

    private void resetEncoder()
    {
        liftEncoder.reset();
    }
    private void setMotorSpeed(double speed)
    {
        liftMotorA.set(ControlMode.PercentOutput, -speed);
        liftMotorB.set(ControlMode.PercentOutput, speed);
    }

    public void raise()
    {
        setMotorSpeed(Speeds.LIFT_UP_SPEED);
    }

    public void lower()
    {
        setMotorSpeed(Speeds.LIFT_DOWN_SPEED);
    }

    //Stop lift at current position
    public void stop()
    {
        setMotorSpeed(Speeds.LIFT_STOP_SPEED);
    }

    public void updateDashboard()
    {
        SmartDashboard.putNumber("Lift Motor A Current", liftMotorA.getOutputCurrent());
        SmartDashboard.putNumber("Lift Motor B Current", liftMotorB.getOutputCurrent());
        SmartDashboard.putNumber("Lift A Speed", liftMotorA.getMotorOutputVoltage());
        SmartDashboard.putNumber("Lift B Speed", liftMotorB.getMotorOutputVoltage());
        SmartDashboard.putNumber("Lift Encoder", liftEncoder.get());

    }

}