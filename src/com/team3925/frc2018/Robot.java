package com.team3925.frc2018;

import com.team3925.frc2018.commands.DriveManual;
import com.team3925.frc2018.commands.RunElevator;
import com.team3925.frc2018.commands.RunElevatorRaw;
import com.team3925.frc2018.commands.RunIntakeLiftRaw;
import com.team3925.frc2018.commands.ZeroIntake;
import com.team3925.frc2018.commands.autos.CenterSwitchAuto;
import com.team3925.frc2018.commands.autos.CenterSwitchAuto.AutoSide;
import com.team3925.frc2018.commands.autos.DriveForwardAuto;
import com.team3925.frc2018.subsystems.Drivetrain;
import com.team3925.frc2018.subsystems.Elevator;
import com.team3925.frc2018.subsystems.Intake;
import com.team3925.frc2018.subsystems.Elevator.ElevatorState;

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
	RunElevator elevate;
	RunElevatorRaw elevateRaw;
	RunIntakeLiftRaw liftRaw;
	Command testAuto;
	Command zeroIntake;
	UsbCamera camera;
	SendableChooser<String> autoSelector;
	boolean isElevatorZeroed = false;

	@Override
	public void robotInit() {
		autoSelector = new SendableChooser<String>();
		drive = new DriveManual(OI.getInstance());
		elevateRaw = new RunElevatorRaw();
		liftRaw = new RunIntakeLiftRaw();
		zeroIntake = new ZeroIntake();
		camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(320, 240);
		camera.setFPS(12);
		autoSelector.addDefault("Drive Forward", "DriveForward");
		autoSelector.addObject("CenterSwitch", "Center");
		SmartDashboard.putData("Auto", autoSelector);
		isElevatorZeroed = false;
	}

	@Override
	public void autonomousInit() {
		isElevatorZeroed = true;
		Elevator.getInstance().zero();
		Intake.getInstance().zeroLift();
		testAuto = new DriveForwardAuto();
		if (autoSelector.getSelected().equals("DriveForward")) {
			testAuto = new DriveForwardAuto();
		}else if(autoSelector.getSelected().equals("Center")){
			if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
				testAuto = new CenterSwitchAuto(AutoSide.LEFT);
				System.out.println("RUNnningLEFt");
			}else if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'R') {
				testAuto = new CenterSwitchAuto(AutoSide.RIGHT);
			}
		}
		testAuto.start();
	}

	@Override
	public void autonomousPeriodic() {
	}

	public void teleopInit() {
		if (!isElevatorZeroed) {
			Elevator.getInstance().zero();
			Intake.getInstance().zeroLift();
		}
		drive.start();
		Intake.getInstance().setIntakeRollers(0.3);
//		Elevator.getInstance().setPosition(ElevatorState.BOTTOM);
//		Intake.getInstance().zeroLift();
	}

	@Override
	public void teleopPeriodic() {
		if (Elevator.getInstance().getLimitSwitch()) {
			Elevator.getInstance().zero();
		}
	}

	@Override
	public void robotPeriodic() {
		Logger.getInstance().update();
		Scheduler.getInstance().run();
	}
	
	@Override
	public void disabledInit() {
		isElevatorZeroed = false;
	}
}
