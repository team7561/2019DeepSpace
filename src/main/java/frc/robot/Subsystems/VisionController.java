package frc.robot.Subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionController implements Subsystem {


        private boolean is_enabled, driverVision, tapeVision, cargoVision;
        private NetworkTableEntry tapeDetected, cargoDetected, tapeYaw, cargoYaw, driveWanted, tapeWanted, cargoWanted, videoTimestamp;
        private double targetAngle, timestamp;
        //private RemoteControl humanControl;
        NetworkTableInstance instance;
        NetworkTable chickenVision;
	public VisionController(/*RemoteControl humanControl*/) {
        /*this.humanControl = humanControl;*/

        instance = NetworkTableInstance.getDefault();
        chickenVision = instance.getTable("ChickenVision");

        tapeDetected = chickenVision.getEntry("tapeDetected");
        cargoDetected = chickenVision.getEntry("cargoDetected");
        tapeYaw = chickenVision.getEntry("tapeYaw");
        cargoYaw = chickenVision.getEntry("cargoYaw");

        driveWanted = chickenVision.getEntry("Driver");
        tapeWanted = chickenVision.getEntry("Tape");
        cargoWanted = chickenVision.getEntry("Cargo");

        videoTimestamp = chickenVision.getEntry("VideoTimestamp");

        tapeVision = cargoVision = false;
        driverVision = true;
    }

        public void reset() {

    }

        public void update() {
        driverVision = false;
        tapeVision = false;
        cargoVision = true;

        driveWanted.setBoolean(driverVision);
        tapeWanted.setBoolean(tapeVision);
        cargoWanted.setBoolean(cargoVision);
        SmartDashboard.putBoolean("Cargo detected", cargoDetected.getBoolean(false));

        if(cargoDetected.getBoolean(false)) {
            targetAngle = cargoYaw.getDouble(0);
            SmartDashboard.putNumber("Cargo Yaw", targetAngle);
        } else {
            targetAngle = 0;
        }


        SmartDashboard.putNumber("Cargo Yaw", targetAngle);

    }

        public double targetYaw() {
        update();
        return targetAngle;
    }


    public void updateDashboard()
    {
    }
}
