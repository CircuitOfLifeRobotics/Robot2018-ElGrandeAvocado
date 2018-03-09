package com.team3925.frc2018;

import com.team3925.frc2018.commands.DriveManual;
import com.team3925.frc2018.commands.RunElevator;
import com.team3925.frc2018.commands.RunElevatorRaw;
import com.team3925.frc2018.commands.RunIntakeLiftRaw;
import com.team3925.frc2018.commands.ZeroIntake;
import com.team3925.frc2018.subsystems.Elevator;
import com.team3925.frc2018.subsystems.Intake;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {
	DriveManual drive;
	RunElevator elevate;
	RunElevatorRaw elevateRaw;
	RunIntakeLiftRaw liftRaw;
	Command testAuto;
	Command zeroIntake;

	@Override
	public void robotInit() {
		drive = new DriveManual(OI.getInstance());
		elevateRaw = new RunElevatorRaw();
		liftRaw = new RunIntakeLiftRaw();
		zeroIntake = new ZeroIntake();
	}

	@Override
	public void autonomousInit() {
//		Elevator.getInstance().zero();
//		
//		if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
//			testAuto = new CenterLeftSwitchAuto();
//		}else if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'R') {
//			testAuto = new CenterRightSwitchAuto();
//		}
//		testAuto.start();
	}
	
	public void teleopInit() {
		Elevator.getInstance().zero();
		drive.start();
		Intake.getInstance().setIntakeRollers(-0.1);
		zeroIntake.start();
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
