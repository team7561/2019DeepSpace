/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.autonomous.ViveAuto;
import frc.robot.autonomous.ViveMeasurements;
import frc.robot.driver.LEDController;
import frc.robot.subsystem.*;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.PowerDistributionPanel;


public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  double curr_angle, target_angle;
  public Joystick joystick = new Joystick(1);
  public XboxController xboxController = new XboxController(2);
  public Arm arm = new Arm();
  public Climber climber = new Climber();
  public BallIntake ballintake = new BallIntake();
  public PanelIntake panelintake = new PanelIntake();
  public Drivetrain drivetrain = new Drivetrain();
  public VisionController visionController = new VisionController();
  public Lift lift = new Lift();
  public ViveMeasurements viveMeasurements;
  ViveAuto strategy;
  public LEDController ledController = new LEDController();
  Timer matchTimer = new Timer();
  NetworkTable table;
  String autoMode;

  public PowerDistributionPanel pdp;
  boolean invertedDrive;
  double speedControl;
  boolean debug;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();


  @Override
  public void robotInit() {
    debug = true;
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    invertedDrive = false;
    speedControl = 0.5;
    climber.stopVacuum();
    pdp = new PowerDistributionPanel();
    viveMeasurements = new ViveMeasurements();
    strategy = new ViveAuto();

    table = NetworkTable.getTable("GRIP/myContoursReport");
    CameraServer.getInstance().startAutomaticCapture();
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    matchTimer.start();
    lift.resetEncoder();
    drivetrain.resetEncoders();
    climber.stopVacuum();
    autoMode = m_chooser.getSelected();

    strategy.reset();
  }

  public void drive() {
    JamesDrive.drive(this, joystick);
    BenDrive.drive(this, xboxController);
  }
  @Override
  public void autonomousPeriodic() {
    //pathweaver.followPath(drivetrain);
    strategy.run(this);
    updateDashboards();
  }

  @Override
  public void teleopInit() {
    climber.recoverCarrige();
    panelintake.getPannel();
    climber.stopVacuum();
    drivetrain.resetEncoders();
  }

  @Override
  public void teleopPeriodic() {
    drive();
    double[] defaultValue = new double[2];

    double[] distances = table.getNumberArray("centreX", defaultValue);
    SmartDashboard.putNumber("Distance 0", distances[0]);

    visionController.update();
    updateDashboards();
  }

  @Override
  public void testPeriodic() {
  }
  public void updateDashboards()
  {
    arm.updateDashboard(debug);
    ballintake.updateDashboard(true);
    //climber.updateDashboard(debug);
    drivetrain.updateDashboard(debug);
    lift.updateDashboard(debug);
    //visionController.updateDashboard(debug);
    //panelintake.updateDashboard(debug);

    double armAngle = viveMeasurements.getArmAngle();
    SmartDashboard.putNumber("Vive Arm Angle", armAngle);
    for (int i = 0; i < 10; i++)
    {
      SmartDashboard.putNumber("Channel "+i+" Current", pdp.getCurrent(i));
    }
  }
}