package com.team3925.frc2018;

import com.team3925.frc2018.commands.DriveManual;
import com.team3925.frc2018.commands.ShiftHigh;
import com.team3925.frc2018.commands.autos.CenterSwitchAuto;
import com.team3925.frc2018.commands.autos.DriveForwardAuto;
import com.team3925.frc2018.subsystems.Arm;
import com.team3925.frc2018.subsystems.Elevator;
import com.team3925.frc2018.subsystems.Intake;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	DriveManual drive;
	Command auto;
	Command zeroIntake;
	UsbCamera camera;
	SendableChooser<String> autoSelector;
	private static boolean isElevatorZeroed;

	@Override
	public void robotInit() {
		drive = new DriveManual(OI.getInstance());

		//Camera:
		camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(320, 240);
		camera.setFPS(12);
		
		//Autonomous Selector:
		autoSelector = new SendableChooser<String>();
		autoSelector.addDefault("Drive Forward", "DriveForward");
		autoSelector.addObject("Center Switch", "Center");
		autoSelector.addObject("Left Scale", "LeftScale");
		SmartDashboard.putData("Auto", autoSelector);
		
		isElevatorZeroed = false;
	}

	@Override
	public void autonomousInit() {
		//Zero subsystems:
		zero();
		
		//Sets the default case:
		auto = new DriveForwardAuto();
		
		//Shift to high gear
		new ShiftHigh().start();
		
		if (autoSelector.getSelected().equals("DriveForward")) {
			auto = new DriveForwardAuto();
		}else if(autoSelector.getSelected().equals("Center")){
			auto = new CenterSwitchAuto(DriverStation.getInstance().getGameSpecificMessage().charAt(0));
		}
		auto.start();
		isElevatorZeroed = true;
	}

	@Override
	public void autonomousPeriodic() {
	}

	public void teleopInit() {
		if (isElevatorZeroed == false) {
			zero();
			System.out.println("ZeroCalled");
		}
		drive.start();
	}
	
	private void zero() {
		Elevator.getInstance().zero();
		Arm.getInstance().zero();
	}

	@Override
	public void robotPeriodic() {
		Scheduler.getInstance().run();
	}
}
