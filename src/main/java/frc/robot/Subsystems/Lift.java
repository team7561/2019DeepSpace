package frc.robot.Subsystems;
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;
import frc.robot.Speeds;

public class Lift implements Subsystem{

    private CANSparkMax liftMotorA, liftMotorB;
    private DigitalInput limitSwitch;
    private Encoder liftEncoder;

    public Lift()
    {
        liftMotorA = new CANSparkMax(Ports.LIFT_A_CANID, CANSparkMaxLowLevel.MotorType.kBrushed);
        liftMotorB = new CANSparkMax(Ports.LIFT_B_CANID, CANSparkMaxLowLevel.MotorType.kBrushed);
        liftMotorA.setIdleMode(CANSparkMax.IdleMode.kBrake);
        liftMotorB.setIdleMode(CANSparkMax.IdleMode.kBrake);
        liftMotorB.follow(liftMotorA);
        liftEncoder = new Encoder(Ports.ENCODER_LIFT_A_CHANNEL, Ports.ENCODER_LIFT_B_CHANNEL);
        limitSwitch = new DigitalInput(Ports.LIMIT_LIFT_UPPER);
    }

    public void resetEncoder()
    {
        liftEncoder.reset();
    }
    public void setMotorSpeed(double speed)
    {
        liftMotorA.set(speed);
    }

    public void raise()
    {
        liftMotorA.setOpenLoopRampRate(1);
        setMotorSpeed(Speeds.LIFT_UP_SPEED);
    }

    public void lower()
    {
        liftMotorA.setOpenLoopRampRate(1);
        setMotorSpeed(Speeds.LIFT_DOWN_SPEED);
    }

    //Stop lift at current position
    public void stop()
    {
        liftMotorA.setOpenLoopRampRate(0.5);
        setMotorSpeed(Speeds.LIFT_STOP_SPEED);
    }

    public void updateDashboard()
    {
        SmartDashboard.putNumber("Lift Motor A Current", liftMotorA.getOutputCurrent());
        SmartDashboard.putNumber("Lift Motor B Current", liftMotorB.getOutputCurrent());
        SmartDashboard.putNumber("Lift A Speed", liftMotorA.get());
        SmartDashboard.putNumber("Lift B Speed", liftMotorB.get());
        SmartDashboard.putNumber("Lift Encoder", liftEncoder.get());
    }

}