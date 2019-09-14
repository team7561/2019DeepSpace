package frc.robot.Subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;
import frc.robot.Speeds;

public class BallIntake implements Subsystem {

    VictorSPX ballIntakeMotor;
    DigitalInput intakeLimitSwitch;

    public BallIntake()
    {
        ballIntakeMotor = new VictorSPX(Ports.CARGO_INTAKE_CANID);
    }

    //set speed of both intake motors
    private void intakeSpeed (double speed)
    {
        ballIntakeMotor.set(ControlMode.PercentOutput, speed);
    }

    //Get the Ball
    public void getBall()
    {
        intakeSpeed(Speeds.GET_BALL_SPEED);
    }

    //For keep in the Ball while driving
    public void keepBall()
    {
        intakeSpeed(Speeds.KEEP_BALL_SPEED);
    }
    // Returns if high enough current to assume ball in possession
    public boolean hasBall(double current)
    {
        return (ballIntakeMotor.getMotorOutputPercent() < 0 && current > 2);
    }

    //Ejects the Ball fast
    public void ejectBall()
    {
        intakeSpeed(Speeds.EJECT_BALL_SPEED);
    }

   /* //Ejects the Ball slow
    public void ejectBallSlow()
    {
        intakeSpeed(-0.4);
    }
   */
    //Stops intake
    public void stop()
    {
        intakeSpeed(Speeds.STOP_BALL_SPEED);
    }


    public void updateDashboard(boolean debug)
    {
        if (debug)
        {
            SmartDashboard.putNumber("Intake Power", ballIntakeMotor.getMotorOutputVoltage());
        }
    }




}
