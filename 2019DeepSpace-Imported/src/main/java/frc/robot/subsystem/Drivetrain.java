package frc.robot.subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import frc.robot.Ports;
//import frc.robot.driver.ADIS16448_IMU;
import com.kauailabs.navx.frc.AHRS;

public class Drivetrain implements Subsystem {

    double lastError;
    final double encoderRatio = 2;

    //VictorSPX leftA, leftB, rightA, rightB;
    Talon leftA, rightA;
    Victor leftB, rightB;
    //ADIS16448_IMU adis;
    AHRS ahrs;

    public Drivetrain()
    {
        leftA = new Talon(Ports.DRIVE_LEFT_A_PWM);
        leftB = new Victor(Ports.DRIVE_LEFT_B_PWM);
        rightA = new Talon(Ports.DRIVE_RIGHT_A_PWM);
        rightB = new Victor(Ports.DRIVE_RIGHT_B_PWM);
    }

    //sets the speeds of all driving motors
    public void drive(double leftSpeed, double rightSpeed) {
        leftA.set(leftSpeed);
        leftB.set(leftSpeed);
        rightA.set(-rightSpeed);
        rightB.set(-rightSpeed);
    }

    //teleop driving
    public void arcadeDrive(double x, double y, double speed, boolean inverted) {
        //x = x * Math.abs(x) * speed;
        //y = y * Math.abs(y) * speed;
        SmartDashboard.putNumber("Drivespeed", speed);
        SmartDashboard.putNumber("X", x);
        SmartDashboard.putNumber("Y", y);

        double right = (-y - x)*speed;
        double left = (- (y - x))*speed;
        if (left > 1) {
            left = 1;
        }
        if (right > 1) {
            right = 1;
        }
        if (inverted == true) {
            drive(-left, -right);
        }
        else
        {
            drive(left, right);
        }
    }
    
    //put dashboard stuff here
    public void updateDashboard(boolean debug)
    {
        debug = true;
        if (debug)
        {
            //SmartDashboard.putNumber("Gyro Angle", readGyro());
            SmartDashboard.putNumber("Left A Power", leftA.get());
            SmartDashboard.putNumber("Left B Power", leftB.get());
            SmartDashboard.putNumber("Right A Power", rightA.get());
            SmartDashboard.putNumber("Right B Power", rightB.get());
        }
    }
}
