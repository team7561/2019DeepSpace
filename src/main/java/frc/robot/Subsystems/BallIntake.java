package frc.robot.Subsystems;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.Speeds;

public class BallIntake implements Subsystem {

    VictorSP ballIntakeMotor;
    DigitalInput intakeLimitSwitch;

    boolean currentlyFixing = false;
    Timer fixTimer = new Timer();

    public void init()
    {
        ballIntakeMotor = new VictorSP(Ports.INTAKE_CHANNEL);
    }

    //set speed of both intake motors
    private void intakeSpeed (double speed)
    {
        SmartDashboard.putNumber("Intake speed:", speed);

        ballIntakeMotor.set(speed);
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


    public void updateDashboard()
    {
        SmartDashboard.putNumber("Intake Power", ballIntakeMotor.get());
    }




}
