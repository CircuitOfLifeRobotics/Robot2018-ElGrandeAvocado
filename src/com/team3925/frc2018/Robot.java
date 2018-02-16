package com.team3925.frc2018;

import com.team3925.frc2018.commands.DriveManual;
import com.team3925.frc2018.commands.JacisTesting;
import com.team3925.frc2018.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {
	DriveManual drive;
	JacisTesting test;
	
	@Override
	public void robotInit() {
		drive = new DriveManual(OI.getInstance());
		test = new JacisTesting();
	}

	@Override
	public void teleopInit() {
		drive.start();
	}
	
	@Override
	public void autonomousInit() {
		test.start();
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		System.out.println(Drivetrain.getInstance().getLeftEncoderPosition() + " " + Drivetrain.getInstance().getRightEncoderPosition()
				+ " " + Drivetrain.getInstance().getGyroHeading());
	}
}
