
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.XboxController;import edu.wpi.first.wpilibj.GenericHID.Hand;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import java.math.*;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private double turningSpeed = 0;
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  CANSparkMax leftFrontMotor, leftBackMotor, rightFrontMotor, rightBackMotor;
  Joystick stick;
  XboxController controller;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */

  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    leftFrontMotor = new CANSparkMax(10, MotorType.kBrushless);
    leftBackMotor = new CANSparkMax(11, MotorType.kBrushless);
    rightFrontMotor = new CANSparkMax(12, MotorType.kBrushless);
    rightBackMotor = new CANSparkMax(13, MotorType.kBrushless);
    stick = new Joystick(1);
    controller = new XboxController(0);
  }
  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */

  @Override
  public void robotPeriodic() {
  }
  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }
  /**
   * This function is called periodically during autonomous.
   */

  @Override
  public void autonomousPeriodic() {
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
  /**
   * This function is called periodically during operator control.
   */

  @Override
  public void teleopPeriodic() {
/*
    double maxSpeed = stick.getThrottle() * 0.5f;
    double stickX = (double)(stick.getMagnitude() * Math.sin(stick.getDirectionRadians()));
    double stickY = (double)(stick.getMagnitude() * Math.cos(stick.getDirectionRadians()));
    double stickTwist = (double)stick.getTwist(); 

    double leftFrontForwardsPower = -stickY;
    double rightFrontForwardsPower = stickY;
    double leftBackForwardsPower = -stickY;
    double rightBackForwardsPower = stickY;

    double leftFrontSidePower = -stickX;
    double rightFrontSidePower = -stickX;
    double leftBackSidePower = +stickX;
    double rightBackSidePower = stickX;

    double leftFrontRotatePower = -stickTwist;
    double rightFrontRotatePower = -stickTwist;
    double leftBackRotatePower = -stickTwist;
    double rightBackRotatePower = -stickTwist; */

    double maxSpeed = stick.getThrottle() * 0.5f;
    double maxRot = 1f;

    double controllerX = controller.getX(Hand.kRight);
    double controllerY = controller.getY(Hand.kRight);
    double controllerTurn = controller.getX(Hand.kLeft); 

    double leftFrontForwardsPower = -controllerY;
    double rightFrontForwardsPower = controllerY;
    double leftBackForwardsPower = -controllerY;
    double rightBackForwardsPower = controllerY;

    double leftFrontSidePower = -controllerX;
    double rightFrontSidePower = -controllerX;
    double leftBackSidePower = +controllerX;
    double rightBackSidePower = controllerX;

    double leftFrontRotatePower = -controllerTurn;
    double rightFrontRotatePower = -controllerTurn;
    double leftBackRotatePower = -controllerTurn;
    double rightBackRotatePower = -controllerTurn;

    double forwardsWeight = 1;
    double sideWeight = 1;
    double rotateWeight = 1;

    double leftFrontPower   =  leftFrontForwardsPower * forwardsWeight +  leftFrontSidePower * sideWeight +  leftFrontRotatePower * rotateWeight;
    double rightFrontPower  = rightFrontForwardsPower * forwardsWeight + rightFrontSidePower * sideWeight + rightFrontRotatePower * rotateWeight;
    double leftBackPower    =   leftBackForwardsPower * forwardsWeight +   leftBackSidePower * sideWeight +   leftBackRotatePower * rotateWeight;
    double rightBackPower   =  rightBackForwardsPower * forwardsWeight +  rightBackSidePower * sideWeight +  rightBackRotatePower * rotateWeight;

    leftFrontPower *= maxSpeed;
    rightFrontPower *= maxSpeed;
    leftBackPower *=  maxSpeed;
    rightBackPower *=  maxSpeed;

    double largest = Math.max( 
                              Math.max(  Math.abs(leftFrontPower),
                                         Math.abs(rightFrontPower) ),
                              Math.max(  Math.abs(leftBackPower), 
                                         Math.abs(rightBackPower) ));

    if (largest > 1) {
      leftFrontPower /= largest;
      rightFrontPower /= largest;
      leftBackPower /= largest;
      rightBackPower /= largest;
    }
 
    leftFrontMotor.set(leftFrontPower);
    rightFrontMotor.set(rightFrontPower);  
    leftBackMotor.set(leftBackPower);
    rightBackMotor.set(rightBackPower);
  }
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}