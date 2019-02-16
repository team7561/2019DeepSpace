package frc.robot.Subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.Ports;

public class Drivetrain implements Subsystem {

    double lastError;
    final double encoderRatio = 2;

    public ADXRS450_Gyro gyro;
    VictorSPX leftA, leftB, rightA, rightB;
    Encoder leftEncoder, rightEncoder;

    public void init()
    {
        leftA = new VictorSPX(Ports.DRIVE_LEFT_A_CHANNEL);
        leftB = new VictorSPX(Ports.DRIVE_LEFT_B_CHANNEL);
        rightA = new VictorSPX(Ports.DRIVE_RIGHT_A_CHANNEL);
        rightB = new VictorSPX(Ports.DRIVE_RIGHT_B_CHANNEL);
        rightEncoder = new Encoder(2, 3);
        rightEncoder.setDistancePerPulse(1);
        leftEncoder = new Encoder(0, 1);
        leftEncoder.setDistancePerPulse(1);
    }

    //sets the speeds of all driving motors
    public void drive(double leftSpeed, double rightSpeed) {
        leftA.set(ControlMode.PercentOutput, -leftSpeed);
        leftB.set(ControlMode.PercentOutput, -leftSpeed);
        rightA.set(ControlMode.PercentOutput, rightSpeed);
        rightB.set(ControlMode.PercentOutput, rightSpeed);
    }

    //teleop driving
    public void arcadeDrive(double x, double y, double speed, boolean inverted) {
        x = x * Math.abs(x) * speed;
        y = y * Math.abs(y) * speed;

        double right = y + x;
        double left = - y + x;
        if (left > 1) {
            left = 1;
        }
        if (right > 1) {
            right = 1;
        }
        if (inverted == true) {
            drive(left, right);
        }
        else
        {
            drive(-left, -right);
        }
    }
    //Resets gyro
    public void resetGyro()
    {
        gyro.reset();
    }

    //reads gyro (between 0-360)
    /*public double readGyro()
    {
        return (gyro.getAngle() % 360 + 360) % 360;
    }
*/
    //resets both encoders
    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    public double getEncoderAvg()
    {
//        return - encoderRatio * (((rightEncoder.get()) + (leftEncoder.get())) / 2);

        return ((encoderRatio * ((rightEncoder.get())/*+(leftEncoder.get())*/)) / -2);
    }

    //adjusts the speed based on how far has been driven
    public void distanceSensitivity(double leftSpeed, double rightSpeed, double currentDistance, double targetDistance)
    {
        final double firstSensitivity = 0.6;
        final double secondSensitivity = 0.9;

        if(currentDistance < 500){
            drive(firstSensitivity*leftSpeed, firstSensitivity*rightSpeed);
        }
        else if(currentDistance < 1000){
            drive(secondSensitivity*leftSpeed, secondSensitivity*rightSpeed);
        }
        else if((targetDistance - currentDistance) < 1000)
        {
            drive(firstSensitivity*leftSpeed, firstSensitivity*rightSpeed);
        }
        else if((targetDistance - currentDistance) < 2400)
        {
            drive(secondSensitivity*leftSpeed, secondSensitivity*rightSpeed);
        }
        else
        {
            drive(leftSpeed, rightSpeed);
        }
    }

    //drives straight using gyro
    public void driveStraight(double speed, double targetAngle, double currentDistance, double targetDistance)
    {
        //sensitivity settings so you can change all 4 instances of it at once
        final double firstSensitivity = 0.85;
        final double secondSensitivity = 0.5;

        double error = calculateError(targetAngle);
        if (error < -10) {
            distanceSensitivity(speed , speed* secondSensitivity, currentDistance, targetDistance);
        }
        else if (error < 0)
        {
            distanceSensitivity(speed, speed * firstSensitivity, currentDistance, targetDistance);
        }
        else if (error < 10)
        {
            distanceSensitivity(speed * firstSensitivity, speed, currentDistance, targetDistance);
        }
        else
        {
            distanceSensitivity(speed * secondSensitivity, speed, currentDistance, targetDistance);
        }
    }

    //calculates the error between the target angle and the current angle
    public double calculateError(double targetAngle)
    {
        //double error = readGyro() - targetAngle;
        double error = 0;
        while (error > 180) {
            error = error - 360;
        }

        while (error < -180) {
            error = error + 360;
        }
        return error;
    }

    //turns to specified angle
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
    public void updateDashboard()
    {
        //SmartDashboard.putNumber("Gyro Angle", readGyro());
        SmartDashboard.putNumber("Left Power", leftA.getMotorOutputPercent());
        SmartDashboard.putNumber("Right Power", rightA.getMotorOutputPercent());
        SmartDashboard.putNumber("Left Encoder", -leftEncoder.get() * encoderRatio);
        SmartDashboard.putNumber("Right Encoder", -rightEncoder.get() * encoderRatio);
        SmartDashboard.putNumber("Encoder Average", getEncoderAvg());
        SmartDashboard.putNumber("Error", lastError);



    }

}
