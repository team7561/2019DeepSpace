package frc.robot.Drivers;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;
import frc.robot.Subsystems.Subsystem;

public class LEDController implements Subsystem {
    private Spark blinkin;
    public LEDController()
    {
        blinkin = new Spark(Ports.LED_CONTROLLER_CHANNEL);
    }

    private void setRawMode(double mode)
    {
        blinkin.set(mode);
    }
    public void setRainbow()
    {
        setRawMode(-0.99);
    }
    public void setWaves()
    {
        setRawMode(0.53);
    }
    public void turnOff(){
        setRawMode(0.99);
    }
    public void updateDashboard(boolean debug)
    {
        if (debug)
        {
            SmartDashboard.putNumber("LED Value", blinkin.get());
        }
    }
}
