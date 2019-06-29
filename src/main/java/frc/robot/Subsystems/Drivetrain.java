package frc.robot.Subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;
import edu.wpi.first.wpilibj.Spark;

public class Drivetrain implements Subsystem {

    double lastError;
    final double encoderRatio = 2;

    //VictorSPX leftA, leftB, rightA, rightB;
    Spark leftA, leftB, rightA, rightB;
    Encoder leftEncoder, rightEncoder;

    public void init()
    {
        leftA = new Spark(Ports.DRIVE_LEFT_A_CHANNEL);
        leftB = new Spark(Ports.DRIVE_LEFT_B_CHANNEL);
        rightA = new Spark(Ports.DRIVE_RIGHT_A_CHANNEL);
        rightB = new Spark(Ports.DRIVE_RIGHT_B_CHANNEL);
        /*leftA = new VictorSPX(Ports.DRIVE_LEFT_A_CHANNEL);
        leftB = new VictorSPX(Ports.DRIVE_LEFT_B_CHANNEL);
        rightA = new VictorSPX(Ports.DRIVE_RIGHT_A_CHANNEL);
        rightB = new VictorSPX(Ports.DRIVE_RIGHT_B_CHANNEL);*/
        rightEncoder = new Encoder(Ports.ENCODER_RIGHT_A_CHANNEL, Ports.ENCODER_RIGHT_B_CHANNEL);
        rightEncoder.setDistancePerPulse(1);
        leftEncoder = new Encoder(Ports.ENCODER_LEFT_A_CHANNEL, Ports.ENCODER_LEFT_B_CHANNEL);
        leftEncoder.setDistancePerPulse(1);
        /*leftA.setIdleMode(CANSparkMax.IdleMode.kCoast);
        leftB.setIdleMode(CANSparkMax.IdleMode.kCoast);
        rightA.setIdleMode(CANSparkMax.IdleMode.kCoast);
        rightB.setIdleMode(CANSparkMax.IdleMode.kCoast);*/
        /*leftA.setNeutralMode(NeutralMode.Coast);
        leftB.setNeutralMode(NeutralMode.Coast);
        rightA.setNeutralMode(NeutralMode.Coast);
        rightB.setNeutralMode(NeutralMode.Coast);*/
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
        x = x * Math.abs(x) * speed;
        y = y * Math.abs(y) * speed;

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
        double error = targetAngle;
        while (error > 180) {
            error = error - 360;
        }

        while (error < -180) {
            error = error + 360;
        }
        return error;
    }

    //turns to specified angle
    public void turnToAngle(double currentAngle, double targetAngle, double speed, double deadband)
    {
        double error = (360 + currentAngle - targetAngle) % 360;
        double error_magnitude = (180-Math.abs(180-error))/180*0.3+0.1;
        //error_magnitude = 0.2;
        SmartDashboard.putNumber("Error", error);
        SmartDashboard.putNumber("Error Magnitude", error_magnitude);
        SmartDashboard.putNumber("Current Angle", currentAngle);
        SmartDashboard.putNumber("Target Angle", targetAngle);
        double inverted = 1;
        if ((error > 180) && (error < 360-deadband))
        {
            System.out.println("Turning right");
            drive(inverted*error_magnitude, inverted*error_magnitude);
        }
        else if ((error < 180) && (error > deadband))
        {
            System.out.println("Turning left");
            drive(inverted*-error_magnitude, inverted*-error_magnitude);
        }
        else {
            drive(0,0);
        }
        this.lastError = error;
    }

    //put dashboard stuff here
    public void updateDashboard()
    {
        //SmartDashboard.putNumber("Gyro Angle", readGyro());
        SmartDashboard.putNumber("Left Power", leftA.get());
        SmartDashboard.putNumber("Right Power", rightA.get());
        SmartDashboard.putNumber("Left Encoder", -leftEncoder.get() * encoderRatio);
        SmartDashboard.putNumber("Right Encoder", -rightEncoder.get() * encoderRatio);
        SmartDashboard.putNumber("Encoder Average", getEncoderAvg());
        SmartDashboard.putNumber("Error", lastError);
    }
}
