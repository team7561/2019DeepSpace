package frc.robot.utility;

public class Quaternion {
    double r, i, j, k;
    public Quaternion(double r, double i, double j, double k) {
        this.r = r;
        this.i = i;
        this.j = j;
        this.k = k;
    }
    public Quaternion multiply(Quaternion one, Quaternion two)
    {
        return new Quaternion(0,0,0,0);
    }
    public EulerAngles convertToEuler()
    {
        double roll = 0;
        double pitch = 0;
        double yaw = 0;

        return new EulerAngles(roll, pitch, yaw);
    }
}
