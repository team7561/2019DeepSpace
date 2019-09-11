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
import frc.robot.Autonomous.PathWeaver;
import frc.robot.Autonomous.ViveAuto;
import frc.robot.Autonomous.ViveMeasurements;
import frc.robot.Drivers.LEDController;
import frc.robot.Subsystems.*;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Timer;


public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  double curr_angle, target_angle;
  Joystick joystick = new Joystick(1);
  XboxController xboxController = new XboxController(2);
  Arm arm = new Arm();
  Climber climber = new Climber();
  BallIntake ballintake = new BallIntake();
  public PanelIntake panelintake = new PanelIntake();
  Drivetrain drivetrain = new Drivetrain();
  VisionController visionController = new VisionController();
  Lift lift = new Lift();
  PathWeaver pathweaver;
  LEDController ledController = new LEDController();
  Timer matchTimer = new Timer();
  NetworkTable table;
  String autoMode;

  boolean invertedDrive;
  double speedControl;
  boolean debug;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();


  @Override
  public void robotInit() {
    debug = false;
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    invertedDrive = false;
    speedControl = 0.5;
    climber.stopVacuum();
    pathweaver = new PathWeaver();

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

    if (autoMode == "Pathfinder")
    {
      pathweaver.init(drivetrain);
    }
    else
    {

    }
  }

  public void drive() {

    JamesDrive.drive(this, joystick);
    BenDrive.drive(this, xboxController);
    if (matchTimer.get() > 130)
    {
      climber.deployLift();
      climber.runVacuum();
    }
  }
  @Override
  public void autonomousPeriodic() {
    pathweaver.followPath(drivetrain);
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
    drivetrain.updateDashboard(debug);
    lift.updateDashboard(debug);
    arm.updateDashboard(debug);
    visionController.updateDashboard(debug);
    ballintake.updateDashboard(debug);
    climber.updateDashboard(debug);
    panelintake.updateDashboard(debug);
    
  }
}