package frc.robot.Subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionController implements Subsystem {


        private boolean is_enabled, driverVision, tapeVision, cargoVision;
        private NetworkTableEntry tapeDetected, cargoDetected, tapeYaw, cargoYaw, driveWanted, tapeWanted, cargoWanted, videoTimestamp;
        private double targetAngle, cargoAngle, tapeAngle, timestamp;
        private String desiredTarget = "";
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
        tapeVision = true;
        cargoVision = false;
    }

        public void reset() {
    }

        public void update() {
        driverVision = false;


        driveWanted.setBoolean(driverVision);
        tapeWanted.setBoolean(tapeVision);
        cargoWanted.setBoolean(cargoVision);
        SmartDashboard.putBoolean("Cargo detected", cargoDetected.getBoolean(false));

        if(cargoDetected.getBoolean(false)) {
            cargoAngle = cargoYaw.getDouble(0);
        } else {
            targetAngle = 0;
        }
        if(tapeDetected.getBoolean(false)) {
            tapeAngle = tapeYaw.getDouble(0);
        } else {
            targetAngle = 0;
        }
    }

        public double targetYaw() {
        update();
        if (cargoVision){
            return cargoAngle;
            }
        else {
            return tapeAngle;
        }
    }


    public void updateDashboard()
    {
        update();
        SmartDashboard.putNumber("Cargo Yaw", cargoAngle);
        SmartDashboard.putNumber("Tape Yaw", tapeAngle);
        SmartDashboard.putNumber("Target Yaw", targetYaw());
        SmartDashboard.putString("Desired Target", desiredTarget);
    }
    public void setCargo()
    {
        desiredTarget = "Cargo";
        cargoVision = true;
        tapeVision = false;
        update();
    }
    public void setTape()
    {
        desiredTarget = "Tape";
        cargoVision = false;
        tapeVision = true;
        update();
    }
}
