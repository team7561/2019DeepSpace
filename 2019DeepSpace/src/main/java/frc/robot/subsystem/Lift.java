package frc.robot.subsystem;
;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;
import frc.robot.Speeds;

public class Lift implements Subsystem{

    private VictorSP liftMotorA, liftMotorB;
    private DigitalInput limitSwitch;
    private Encoder liftEncoder;

    public Lift()
    {
        liftMotorA = new VictorSP(Ports.LIFT_A_PWM);
        liftMotorB = new VictorSP(Ports.LIFT_B_PWM);
        //liftM11otorA.setNeutralMode(NeutralMode.Brake);
        //liftMotorB.setNeutralMode(NeutralMode.Brake);
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
        liftMotorA.set(-speed);
        liftMotorB.set(-speed);
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

    public void updateDashboard(boolean debug)
    {
        if (debug)
        {
            SmartDashboard.putNumber("Lift A Speed", liftMotorA.get());
            SmartDashboard.putNumber("Lift B Speed", liftMotorB.get());
            SmartDashboard.putNumber("Lift Encoder", liftEncoder.get());
        }
    }

}