/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  XboxController xbox;
  Joystick joystick;
  frc.robot.MotorController motor;
  Spark turret;

  @Override
  public void robotInit() {
    //xbox = new XboxController(1);
    joystick = new Joystick(2);

    motor = new frc.robot.MotorController(8);
    SmartDashboard.putNumber("motor raw", 1500);

    turret = new Spark(7);
  }


  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }
  @Override
  public void teleopInit()
  {
  }
  @Override
  public void teleopPeriodic() {
    double throttle = joystick.getThrottle();
    //motor_Raw = (int) SmartDashboard.getNumber("motor raw", 1500);
    //motor.setRaw(motor_Raw);
    motor.updateDashboard();
    turret.set(throttle);
    SmartDashboard.putNumber("Turret Speed", turret.get());

  }

  @Override
  public void testPeriodic() {
  }
}
