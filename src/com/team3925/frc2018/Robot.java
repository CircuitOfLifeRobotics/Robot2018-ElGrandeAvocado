package com.team3925.frc2018;

import com.team3925.frc2018.commands.DriveManual;
import com.team3925.frc2018.commands.RunElevatorRaw;
import com.team3925.frc2018.subsystems.Drivetrain;
import com.team3925.frc2018.subsystems.Elevator;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {
	DriveManual drive;
	RunElevatorRaw elevateRaw;

	@Override
	public void robotInit() {
		drive = new DriveManual(OI.getInstance());
		elevateRaw = new RunElevatorRaw();
	}

	@Override
	public void autonomousInit() {
	}
	
	public void teleopInit() {
		Elevator.getInstance().zero();
		Drivetrain.getInstance().getGyroHeading();
		drive.start();
		elevateRaw.start();
	}
	
	@Override
	public void teleopPeriodic() {
	}
	
	@Override
	public void robotPeriodic() {
		Logger.getInstance().update();
		Scheduler.getInstance().run();
	}
}
