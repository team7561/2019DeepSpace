package frc.robot.Subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;
import frc.robot.Speeds;

import javax.sound.sampled.Port;

public class Lift implements Subsystem{

    TalonSRX liftMotorA, liftMotorB;
    DigitalInput limitSwitch;

    public void init()
    {
        liftMotorA = new TalonSRX(Ports.LIFT_LEFT_A_CANID);
        liftMotorB = new TalonSRX(Ports.LIFT_LEFT_A_CANID);
        liftMotorA.setNeutralMode(NeutralMode.Brake);
        liftMotorB.setNeutralMode(NeutralMode.Brake);
    }

    private void setMotorSpeed(double speed)
    {
        liftMotorA.set(ControlMode.PercentOutput, -speed);
        liftMotorB.set(ControlMode.PercentOutput, -speed);
    }

    public void raise()
    {
//        setMotorSpeed(0.8);
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
        SmartDashboard.putNumber("Lift Motor Speed", liftMotorA.getOutputCurrent());
    }


}