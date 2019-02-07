package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Spark;
import frc.robot.Ports;

public class LEDController {
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

}
