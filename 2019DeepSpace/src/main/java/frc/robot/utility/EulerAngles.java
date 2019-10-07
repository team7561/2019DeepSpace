package frc.robot.utility;

public class EulerAngles {
    double roll;
    double pitch;
    double yaw;
    public EulerAngles(double roll, double pitch, double yaw)
    {
        this.roll = roll;
        this.pitch = pitch;
        this.yaw = yaw;
    }
    public Quaternion convertToQuaternion()
    {
        double r = 0;
        double i= 0;
        double j = 0;
        double k = 0;

        return new Quaternion(r, i, j, k);
    }
}
