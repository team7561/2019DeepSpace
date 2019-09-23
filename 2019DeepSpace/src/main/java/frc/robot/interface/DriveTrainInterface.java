public interface DriveTrainInterface {
    double getBearing();
    double getLocationX();
    double getLocationZ();
    void tankDrive(double left, double right);
}
