package frc.robot.Subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;
import frc.robot.Speeds;

public class Lift implements Subsystem{

    private VictorSPX liftMotorA, liftMotorB;
    private DigitalInput limitSwitch;
    private Encoder liftEncoder;

    public Lift()
    {
        liftMotorA = new VictorSPX(Ports.LIFT_A_CANID);
        liftMotorB = new VictorSPX(Ports.LIFT_B_CANID);
        liftMotorA.setNeutralMode(NeutralMode.Brake);
        liftMotorB.setNeutralMode(NeutralMode.Brake);
        //liftMotorB.setIdleMode(CANSparkMax.IdleMode.kBrake);
        //liftMotorB.follow(liftMotorA);
        liftEncoder = new Encoder(Ports.ENCODER_LIFT_A_CHANNEL, Ports.ENCODER_LIFT_B_CHANNEL);
        limitSwitch = new DigitalInput(Ports.LIMIT_LIFT_UPPER);
    }

    public void resetEncoder()
    {
        liftEncoder.reset();
    }
    public void setMotorSpeed(double speed)
    {
        liftMotorA.set(ControlMode.PercentOutput, speed);
        liftMotorB.set(ControlMode.PercentOutput, speed);
    }

    public void raise()
    {
        liftMotorA.configOpenloopRamp(1);
        liftMotorB.configOpenloopRamp(1);
        setMotorSpeed(Speeds.LIFT_UP_SPEED);
    }

    public void lower()
    {
        liftMotorA.configOpenloopRamp(1);
        liftMotorB.configOpenloopRamp(1);
        setMotorSpeed(Speeds.LIFT_DOWN_SPEED);
    }

    //Stop lift at current position
    public void stop()
    {
        liftMotorA.configOpenloopRamp(0.4);
        liftMotorB.configOpenloopRamp(0.4);
        setMotorSpeed(Speeds.LIFT_STOP_SPEED);
    }

    public void updateDashboard()
    {
        SmartDashboard.putNumber("Lift Motor A Temp", liftMotorA.getTemperature());
        SmartDashboard.putNumber("Lift Motor B Temp", liftMotorB.getTemperature());
        SmartDashboard.putNumber("Lift A Speed", liftMotorA.getMotorOutputPercent());
        SmartDashboard.putNumber("Lift B Speed", liftMotorB.getMotorOutputPercent());
        SmartDashboard.putNumber("Lift Encoder", liftEncoder.get());
    }

}