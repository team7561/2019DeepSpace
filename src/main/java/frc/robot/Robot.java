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
import frc.robot.Subsystems.*;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.networktables.NetworkTable;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
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
  PanelIntake panelintake = new PanelIntake();
  Drivetrain drivetrain = new Drivetrain();
  VisionController visionController = new VisionController();
  Lift lift = new Lift();
  LEDController ledController = new LEDController();
  Timer matchTimer = new Timer();
  NetworkTable table;
  boolean invertedDrive;
  double speedControl;
  boolean debug;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    debug = false;
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    invertedDrive = false;
    speedControl = 1;
    climber.stopVacuum();

    table = NetworkTable.getTable("GRIP/myContoursReport");
    CameraServer.getInstance().startAutomaticCapture();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    matchTimer.start();
    lift.resetEncoder();
    climber.stopVacuum();
  }

  public void drive() {

    JamesDrive.drive(this, joystick);
    BenDrive.drive(this, xboxController);
    if (matchTimer.get() > 130)
    {
      climber.deployLift();
      climber.runVacuum();
    }
    //TomDrive.drive(this, xboxController);
    //LiamDrive.drive(this, joystick);
    //AlexDrive.drive(this, xboxController);
    //JoshDrive.drive(this, xboxController);
  }
  @Override
  public void autonomousPeriodic() {
    drive();
  }

  @Override
  public void teleopInit() {
    climber.recoverCarrige();
    panelintake.getPannel();
    climber.stopVacuum();
  }

  @Override
  public void teleopPeriodic() {
    drive();
    double[] defaultValue = new double[2];

    double[] distances = table.getNumberArray("centreX", defaultValue);
    //System.out.println(distances[0]);
    //System.out.println(distances[1]);
    SmartDashboard.putNumber("Distance 0", distances[0]);

    visionController.update();
    updateDashboards();
  }

  /**
   * This function is called periodically during test mode.
   */
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
