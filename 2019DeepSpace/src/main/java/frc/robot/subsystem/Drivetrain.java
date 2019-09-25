package frc.robot.subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
//import frc.robot.driver.ADIS16448_IMU;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;

public class Drivetrain implements Subsystem {

    double lastError;
    final double encoderRatio = 2;

    //VictorSPX leftA, leftB, rightA, rightB;
    CANSparkMax leftA, leftB, rightA, rightB;
    //ADIS16448_IMU adis;
    AHRS ahrs;

    public Drivetrain()
    {
        leftA = new CANSparkMax(Ports.DRIVE_LEFT_A_CANID, MotorType.kBrushless);
        leftB = new CANSparkMax(Ports.DRIVE_LEFT_B_CANID, MotorType.kBrushless);
        rightA = new CANSparkMax(Ports.DRIVE_RIGHT_A_CANID, MotorType.kBrushless);
        rightB = new CANSparkMax(Ports.DRIVE_RIGHT_B_CANID, MotorType.kBrushless);
        leftA.getEncoder().setPositionConversionFactor(42);
        leftB.getEncoder().setPositionConversionFactor(42);
        rightA.getEncoder().setPositionConversionFactor(42);
        rightB.getEncoder().setPositionConversionFactor(42);
        //adis = new ADIS16448_IMU();
        ahrs = new AHRS(SerialPort.Port.kMXP);
        ahrs.reset();
        ahrs.zeroYaw();
        resetEncoders();

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
    public void resetEncoders()
    {
        leftA.getEncoder().setPosition(0);
        leftB.getEncoder().setPosition(0);
        rightA.getEncoder().setPosition(0);
        rightB.getEncoder().setPosition(0);

    }

    public double getAngle()
    {
        return ahrs.getYaw();
        
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
    public double calculateError(double targetAngle)
    {
        double error = getAngle() - targetAngle;

        while (error > 180) {
            error = error - 360;
        }

        while (error < -180) {
            error = error + 360;
        }
        return error;
    }
    public void turnToAngle(double targetAngle, double speed)
    {
        double error = calculateError(targetAngle);
        if(error < 0)
        {
            drive(speed, -speed);
        }
        else
        {
            drive(-speed, speed);
        }
        this.lastError = error;
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
            SmartDashboard.putNumber("Left A Encoder", leftA.getEncoder().getPosition());
            SmartDashboard.putNumber("Left B Encoder", leftB.getEncoder().getPosition());
            SmartDashboard.putNumber("Right A Encoder", rightA.getEncoder().getPosition());
            SmartDashboard.putNumber("Right B Encoder", rightB.getEncoder().getPosition());
                     /* Display 6-axis Processed Angle Data                                      */
          SmartDashboard.putBoolean(  "IMU_Connected",        ahrs.isConnected());
          SmartDashboard.putBoolean(  "IMU_IsCalibrating",    ahrs.isCalibrating());
          SmartDashboard.putNumber(   "IMU_Yaw",              ahrs.getYaw());
          SmartDashboard.putNumber(   "IMU_Pitch",            ahrs.getPitch());
          SmartDashboard.putNumber(   "IMU_Roll",             ahrs.getRoll());

        }
    }
    public int getLeftEncoder()
    {
        return (int) (leftA.getEncoder().getPosition()+leftB.getEncoder().getPosition())/2;
    }
    public int getRightEncoder()
    {
        return (int) -(rightA.getEncoder().getPosition()+rightB.getEncoder().getPosition())/2;
    }
}
