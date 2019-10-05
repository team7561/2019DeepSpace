package frc.robot.subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;
import frc.robot.Speeds;
import edu.wpi.first.wpilibj.DigitalInput;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Arm implements Subsystem{
    CANSparkMax armMotor;
    DigitalInput limitUpper;
    DigitalInput limitLower;
    double height;
    public Arm() {
        armMotor = new CANSparkMax(Ports.ARM_CANID, MotorType.kBrushless);
        limitUpper = new DigitalInput(Ports.LIMIT_ARM_UPPER);
        limitLower = new DigitalInput(Ports.LIMIT_ARM_LOWER);
        height = 0;
    }

    public void setSpeed(double speed)
    {
        SmartDashboard.putNumber("Arm Speed", speed);
        if (speed > 0 && !limitUpper.get() || (speed < 0 && !limitLower.get()))
        {
            armMotor.set(0);
        }
        else
        {
            armMotor.set(speed);
        }
        
    }

    public void reset()
    {
    }
    public boolean atLowLimit()
    {
        return !limitLower.get();
    }
    public boolean atHighLimit()
    {
        return !limitUpper.get();
    }
    public void raise()
    {
        if (atHighLimit())
        {
            setSpeed(Speeds.ARM_STOP_SPEED);
        }
        else {
            setSpeed(Speeds.ARM_RAISE_SPEED);
        }
    }
    public void raise_slowly()
    {
        if (atHighLimit())
        {
            setSpeed(Speeds.ARM_STOP_SPEED);
        }
        else {
            setSpeed(Speeds.ARM_RAISE_SPEED / 2);
        }
    }
    public void lower()
    {
        if (atLowLimit())
        {
            setSpeed(Speeds.ARM_STOP_SPEED);
        }
        else {
            setSpeed(Speeds.ARM_LOWER_SPEED);
        }
    }
    public void lower_slowly()
    {
        if (atLowLimit())
        {
            setSpeed(Speeds.ARM_STOP_SPEED);
        }
        else {
            setSpeed(Speeds.ARM_LOWER_SPEED / 2);
        }
    }

    public void stop()
    {
        setSpeed(Speeds.ARM_STOP_SPEED);
    }
    public void updateDashboard(boolean debug)
    {
        SmartDashboard.putBoolean("Arm Upper Limit Switch", limitUpper.get());
        SmartDashboard.putBoolean("Arm Lower Limit Switch", limitLower.get());
        SmartDashboard.putNumber("Arm Speed", armMotor.get());
        SmartDashboard.putNumber("Arm Current", armMotor.getOutputCurrent());
    }
}