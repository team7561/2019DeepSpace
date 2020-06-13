package frc.robot;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class MotorController {
    double value;
    PWM motorPWM;

    // Max PWM pulse in ms
    double max = 2000;
    // The high end of the deadband range pulse width in ms
    double deadbandMax = 1900;
    // The center (off) pulse width in ms
    double center = 1500;
    // The low end of the deadband pulse width in ms
    double deadbandMin = 1100;
    // The minimum pulse width in ms
    double min = 100;

    public MotorController(int port)
    {
        motorPWM = new PWM(port);
        motorPWM.setBounds(max, deadbandMax, center, deadbandMin, min);
    }
    public void set(double speed)
    {
        motorPWM.setSpeed(speed);

    }
    public void setRaw(int value)
    {
        motorPWM.setRaw(value);
    }
    public double get()
    {
        return motorPWM.getSpeed();
    }
    public void updateDashboard()
    {
        SmartDashboard.putNumber("Motor Raw", motorPWM.getRaw());
    }
}