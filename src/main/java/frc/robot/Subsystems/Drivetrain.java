package frc.robot.Subsystems;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;
import edu.wpi.first.wpilibj.Spark;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drivetrain implements Subsystem {

    double lastError;
    final double encoderRatio = 2;

    //VictorSPX leftA, leftB, rightA, rightB;
    CANSparkMax leftA, leftB, rightA, rightB;

    public Drivetrain()
    {
        leftA = new CANSparkMax(Ports.DRIVE_LEFT_A_CANID, MotorType.kBrushless);
        leftB = new CANSparkMax(Ports.DRIVE_LEFT_B_CANID, MotorType.kBrushless);
        rightA = new CANSparkMax(Ports.DRIVE_RIGHT_A_CANID, MotorType.kBrushless);
        rightB = new CANSparkMax(Ports.DRIVE_RIGHT_B_CANID, MotorType.kBrushless);
    }

    //sets the speeds of all driving motors
    public void drive(double leftSpeed, double rightSpeed) {
        //leftSpeed = leftSpeed;
        //rightSpeed = rightSpeed;
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
    //teleop driving
    public void arcadeDriveLiam(double x, double y, double twist, double speed, boolean inverted) {
        x = x * Math.abs(x) * speed;
        y = y * Math.abs(y) * speed;
        x = twist;
        double right = y + x;
        double left = - (y - x);
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
    public void updateDashboard()
    {
        //SmartDashboard.putNumber("Gyro Angle", readGyro());
        SmartDashboard.putNumber("Left A Power", leftA.get());
        SmartDashboard.putNumber("Left B Power", leftB.get());
        SmartDashboard.putNumber("Right A Power", rightA.get());
        SmartDashboard.putNumber("Right B Power", rightB.get());
    }
}
