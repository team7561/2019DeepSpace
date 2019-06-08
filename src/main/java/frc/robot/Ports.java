package frc.robot;

public class Ports {

    // CAN Bus Node IDs
    public static int DRIVE_LEFT_A_CHANNEL = 1;
    public static int DRIVE_LEFT_B_CHANNEL = 2;
    public static int DRIVE_RIGHT_A_CHANNEL = 3;
    public static int DRIVE_RIGHT_B_CHANNEL = 4;
    public static int LIFT_A_CANID = 11;
    public static int LIFT_B_CANID = 12;
    public static int CARGO_INTAKE_CANID = 21;
    public static int HATCH_INTAKE_CANID = 22;
    public static int CLIMB_ELEVATOR_A_CANID = 31;
    public static int CLIMB_ELEVATOR_B_CANID = 32;
    public static int CLIMB_VACUUM_CANID = 41;
    public static int ARM_CHANNEL_CANID = 51;

    // PWM control channels
    public static int LED_CONTROLLER_CHANNEL = 5;
    public static int INTAKE_CHANNEL = 7;

    // DIO ports
    public static int LIMIT_ARM_LOWER = 0;
    public static int LIMIT_ARM_UPPER = 1;
    public static int LIMIT_LIFT_UPPER = 2;

    public static int ENCODER_LIFT_A_CHANNEL = 4;
    public static int ENCODER_LIFT_B_CHANNEL = 5;
    public static int ENCODER_LEFT_A_CHANNEL = 6;
    public static int ENCODER_LEFT_B_CHANNEL = 7;
    public static int ENCODER_RIGHT_A_CHANNEL = 8;
    public static int ENCODER_RIGHT_B_CHANNEL = 9;

    // Analog Channels
    public static int POSITION_CHANNEL = 1;
    public static int ULTRASONIC_CHANNEL = 2;

    // PCM ports
    public static int INTAKE_SOLENOID_CHANNEL_A = 0;
    public static int INTAKE_SOLENOID_CHANNEL_B = 1;

}