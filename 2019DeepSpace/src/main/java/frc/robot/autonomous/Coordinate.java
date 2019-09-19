package frc.robot.autonomous;

public class Coordinate {
    final double x;
    final double z;
    public Coordinate(double x, double z)
    {
        this.x = x;
        this.z = z;
    }
    public static double getHeading(Coordinate origin, Coordinate destination)
    {
        double deltaX = destination.x - origin.x;
        double deltaZ = destination.z - origin.z;
        return Math.toDegrees(Math.atan2(deltaZ, deltaX));
    }
    public static double getDistance(Coordinate origin, Coordinate destination)
    {
        double deltaX = destination.x - origin.x;
        double deltaZ = destination.z - origin.z;
        return Math.sqrt(deltaZ * deltaZ + deltaX * deltaX);
    }

    public double getX() { return x; }
    public double getZ() { return z; }
}
