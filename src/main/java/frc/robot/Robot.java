/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.Arm;
import frc.robot.Subsystems.BallIntake;
import frc.robot.Subsystems.Drivetrain;
import frc.robot.Subsystems.Lift;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
//import edu.wpi.first.networktables.NetworkTable;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends IterativeRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  Joystick joystick = new Joystick(1);
  XboxController xboxController = new XboxController(2);
  Arm arm = new Arm();
  BallIntake ballintake = new BallIntake();
  Drivetrain drivetrain = new Drivetrain();
  Lift lift = new Lift();
  NetworkTable table;
  boolean invertedDrive;
  double speedControl;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    drivetrain.init();
    lift.init();
    ballintake.init();

    invertedDrive = false;
    speedControl = 1;

    table = NetworkTable.getTable("GRIP/myContoursReport");
    //CameraServer.setRes
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

  /**
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    CameraServer.getInstance().startAutomaticCapture();
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard.
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // autoSelected = SmartDashboard.getString("Auto Selector",
    // defaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  @Override
  public void autonomousPeriodic() {

    JamesDrive.drive(this, joystick);
    //BenDrive.drive( this, xboxController);
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  @Override
  public void teleopInit() {
  }
  @Override
  public void teleopPeriodic() {
    JamesDrive.drive(this, joystick);
    BenDrive.drive( this, xboxController);
    double[] defaultValue = new double[2];

    double[] distances = table.getNumberArray("centreX", defaultValue);
    //System.out.println(distances[0]);
    //System.out.println(distances[1]);
    SmartDashboard.putNumber("Distance 0", distances[0]);



    /*if(xboxController.getRawAxis(1) > 0.5)
    {
      lift.raise();
    }
    else if (xboxController.getRawAxis(1) < -0.5)
    {
      lift.lower();
    }
    else
    {
      lift.stop();
    }*/
/*
    if (xboxController.getAButton()) {
      lift.raise();
    }
    else if (xboxController.getBButton())
    {
      lift.lower();
    }
    else
    {
      lift.stop();
    }

    if (xboxController.getXButton())
    {
      arm.raise();
    }
    else if (xboxController.getYButton())
    {
      arm.lower();
    }
    else
    {
      arm.stop();
    }
    */
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
    drivetrain.updateDashboard();
    lift.updateDashboard();
  }
}
