package com.team3925.frc2018;

import com.team3925.frc2018.commands.DriveManual;
import com.team3925.frc2018.commands.JacisTesting;
import com.team3925.frc2018.subsystems.Drivetrain;
import com.team3925.utils.MotionProfileCommand;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Waypoint;

public class Robot extends IterativeRobot {
	DriveManual drive;
	MotionProfileCommand test;
	
	//LOG VARIABLES
	
	

	@Override
	public void robotInit() {
		drive = new DriveManual(OI.getInstance());
		test = new MotionProfileCommand("testAuto");
	}

	public void teleopInit() {
		drive.start();
	}
	
	@Override
	public void autonomousInit() {
		test.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void robotPeriodic() {

	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		System.out.println(Drivetrain.getInstance().getLeftEncoderPosition());
	}
}
