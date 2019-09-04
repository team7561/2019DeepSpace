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
import edu.wpi.first.wpilibj.Notifier;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
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


  private EncoderFollower m_left_follower;
  private EncoderFollower m_right_follower;
  private Notifier m_follower_notifier;

  
  //private static final int k_ticks_per_rev = 540;
  private static final int k_ticks_per_rev = 540;
  private static final double k_wheel_diameter = 4.0 *25.4 / 1000.0;
  private static final double k_max_velocity = 0.2;

  private static final String k_path_name = "output/TestPathStraight";

  @Override
  public void robotInit() {
    debug = false;
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    invertedDrive = false;
    speedControl = 0.5;
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
    drivetrain.resetEncoders();
    climber.stopVacuum();

    try {
    Trajectory left_trajectory = PathfinderFRC.getTrajectory(k_path_name + ".left");
    Trajectory right_trajectory = PathfinderFRC.getTrajectory(k_path_name + ".right");
    
    m_left_follower = new EncoderFollower(left_trajectory);
    m_right_follower = new EncoderFollower(right_trajectory);

    m_left_follower.configureEncoder(drivetrain.getLeftEncoder(), k_ticks_per_rev, k_wheel_diameter);
    // You must tune the PID values on the following line!
    m_left_follower.configurePIDVA(0.5, 0.0, 0.0, 1 / k_max_velocity, 0);

    m_right_follower.configureEncoder(drivetrain.getRightEncoder(), k_ticks_per_rev, k_wheel_diameter);
    // You must tune the PID values on the following line!
    m_right_follower.configurePIDVA(0.5, 0.0, 0.0, 1 / k_max_velocity, 0);
    
    m_follower_notifier = new Notifier(this::followPath);
    m_follower_notifier.startPeriodic(left_trajectory.get(0).dt);
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
    }

  }
private void followPath() {
    if (m_left_follower.isFinished() || m_right_follower.isFinished()) {
      m_follower_notifier.stop();
      drivetrain.drive(-0.05,-0.05);
    } else {
      double left_speed = m_left_follower.calculate(drivetrain.getLeftEncoder());
      double right_speed = m_right_follower.calculate(drivetrain.getRightEncoder());
      double heading = drivetrain.getAngle();
      double desired_heading = Pathfinder.r2d(m_left_follower.getHeading());
      double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
      //double turn =  (0.8 * (-1.0/80.0) * heading_difference)/4;
      double turn = 0;
      drivetrain.drive((left_speed + turn), (right_speed - turn));
      drivetrain.updateDashboard(true);
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
    followPath();
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