package frc.robot.Subsystems;
import edu.wpi.first.wpilibj.Spark;
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

    private void setSpeed(double speed)
    {
        armMotor.set(speed);
    }

    public void reset()
    {
    }
    public void raise()
    {
        if (!limitUpper.get())
        {
            setSpeed(Speeds.ARM_STOP_SPEED);
        }
        else {
            setSpeed(Speeds.ARM_RAISE_SPEED);
        }
    }

    public void lower()
    {
        if (!limitLower.get())
        {
            setSpeed(Speeds.ARM_STOP_SPEED);
        }
        else {
            setSpeed(Speeds.ARM_LOWER_SPEED);
        }
    }

    public void stop()
    {
        setSpeed(Speeds.ARM_STOP_SPEED);
    }
    public void updateDashboard()
    {
        SmartDashboard.putBoolean("Arm Upper Limit Switch", limitUpper.get());
        SmartDashboard.putBoolean("Arm Lower Limit Switch", limitLower.get());
        SmartDashboard.putNumber("Arm Speed", armMotor.get());
    }
}