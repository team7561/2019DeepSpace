package frc.robot.Subsystems;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;
import frc.robot.Speeds;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;

import static java.lang.Math.abs;

public class Arm implements Subsystem{
    CANSparkMax armMotor;
    CANEncoder armEncoder;
    double height;
    public Arm() {
        armMotor = new CANSparkMax(Ports.ARM_CHANNEL_CANID, CANSparkMaxLowLevel.MotorType.kBrushless);
        armMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        armEncoder = new CANEncoder(armMotor);
        height = 0;
    }

    private void setSpeed(double speed)
    {
        armMotor.set(speed);
    }

    public void raise()
    {
        setSpeed(Speeds.ARM_RAISE_SPEED);
    }

    public void raiseToHeight(double targPos)
    {
        double currPos = armEncoder.getPosition();
        if (abs(currPos - targPos) > 1)
        {
            raise();
        }
        else if (abs(currPos - targPos) < -1)
        {
            lower();
        }
        else
        {
            stop();
        }
    }
    public void lower()
    {
        setSpeed(Speeds.ARM_LOWER_SPEED);
    }

    public void stop()
    {
        setSpeed(Speeds.ARM_STOP_SPEED);
    }
    public void updateDashboard()
    {
        SmartDashboard.putNumber("Arm Current", armMotor.getOutputCurrent());
        SmartDashboard.putNumber("Arm Encoder Position", armEncoder.getPosition());
        SmartDashboard.putNumber("Arm Encoder Speed", armEncoder.getVelocity());
        SmartDashboard.putNumber("Arm Applied Output", armMotor.getAppliedOutput());
    }
}
