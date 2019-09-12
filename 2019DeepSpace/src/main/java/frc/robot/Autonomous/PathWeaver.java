package frc.robot.Autonomous;

import edu.wpi.first.wpilibj.Notifier;
import frc.robot.Subsystems.Drivetrain;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.Pathfinder;

public class PathWeaver {

    private EncoderFollower m_left_follower;
    private EncoderFollower m_right_follower;
    private Notifier m_follower_notifier;

    //private static final int k_ticks_per_rev = 540;
    private static final int k_ticks_per_rev = 540;
    private static final double k_wheel_diameter = 4.0 *25.4 / 1000.0;
    private static final double k_max_velocity = 0.2;

    private static final String k_path_name = "output/TestPathStraight";

    public void init(Drivetrain drivetrain)
    {
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

            //m_follower_notifier = new Notifier(this::followPath);
            //m_follower_notifier.startPeriodic(left_trajectory.get(0).dt);
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    public void followPath(Drivetrain drivetrain) {
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
}
