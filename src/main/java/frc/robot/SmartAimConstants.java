// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Add your docs here. */
public final class SmartAimConstants {
    /* Physical configuration:
    ch is the height of the camera from the ground (inches);
    th is the height of the apriltag from the ground (56.375 inches on a standard field);
    sh is the vertical distance between the center of the apriltag and the center of the speaker opening (24.0625 inches on a standard field);
    spo is the horizontal offset of the center of the speaker opening from the apriltag (-9 inches on a standard field);
    rox and roy are the x and y offsets of the center of rotation from the camera/limelight respectively;
    shox and shoy are the x and y offsets of the shooter from the center of rotation respectively;
    nvi is the initial velocity of the note (tune this empirically);
    g is gravity in inches/second^2 (always 386.1 unless you need to run the robot on the moon or something) */
    public static final double ch = 5.0; // TODO: CORRECT THIS
    public static final double th = 56.375;
    public static final double sh = 24.0625;
    public static final double spo = -9.0;
    public static final double rox = -16.0; // TODO: CORRECT THIS
    public static final double roy = 4.0; // TODO: CORRECT THIS
    public static final double shox = 6.0; // TODO: CORRECT THIS
    public static final double shoy = 20.0; // TODO: CORRECT THIS
    public static final double nvi = 612; // TODO: TUNE THIS
    public static final double g = 386.1;

    /* Drag constants:
    m is the mass of the note (kg)
    p is the local air pressure in kg/in^3 (1 atm = 0.000020074 kg/in^3)
    Av is the vertical surface area of the note (such that the note is flat against a wall)
    Ah is the horizontal surface area of the note (such that the note is flat against a wall)
    Cv is the vertical drag coefficient of the note
    Ch is the horizontal drag coefficient of the note */
    public static final double m = 0.2353;
    public static final double p = 0.000020074;
    public static final double Av = 75.4;
    public static final double Ah = 28;
    public static final double Cv = 0.6; // TODO: TUNE THIS
    public static final double Ch = 0.6; // TODO: TUNE THIS (probably the same as Cv)
}
