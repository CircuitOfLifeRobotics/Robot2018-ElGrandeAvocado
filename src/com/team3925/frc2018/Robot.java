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
	PowerDistributionPanel pdp;

	@Override
	public void robotInit() {
		pdp = new PowerDistributionPanel();
		drive = new DriveManual(OI.getInstance());
		test = new MotionProfileCommand((new Waypoint[] {
				new Waypoint(-5, 0, 0),
				new Waypoint(0, 0, 0),
		}));
	}

	@Override
	public void teleopInit() {
		drive.start();
		Drivetrain.getInstance().getLeftMaster().setSelectedSensorPosition(0, 0, 0);
	}

	@Override
	public void autonomousInit() {
		test.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		System.out.println(Drivetrain.getInstance().getLeftSpeed());
		SmartDashboard.putNumber("Velocity Left" , Drivetrain.getInstance().getLeftSpeed());
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		System.out.println(Drivetrain.getInstance().getLeftEncoderPosition() / 1024 * (Math.PI * 0.5));
	}
}
