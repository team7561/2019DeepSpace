package frc.robot.interfaces;
public interface ArmInterface {
    public void raise();
    public void lower();
    public boolean atTopLimit();
    public boolean atBottomLimit();
}
