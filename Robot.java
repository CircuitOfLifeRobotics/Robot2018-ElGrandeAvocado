package com.team3925.frc2018;

import com.team3925.frc2018.commands.DriveManual;
import com.team3925.frc2018.commands.RunElevator;
import com.team3925.frc2018.commands.RunElevatorRaw;
import com.team3925.frc2018.commands.RunIntakeLiftRaw;
import com.team3925.frc2018.commands.ZeroIntake;
import com.team3925.frc2018.commands.autos.CenterSwitchAuto;
import com.team3925.frc2018.commands.autos.CenterSwitchAuto.AutoSide;
import com.team3925.frc2018.commands.autos.DriveForwardAuto;
import com.team3925.frc2018.commands.autos.LeftScaleAuto;
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
	private static boolean isElevatorZeroed;

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
		autoSelector.addObject("Left Scale", "LeftScale");
		SmartDashboard.putData("Auto", autoSelector);
		isElevatorZeroed = false;
	}

	@Override
	public void autonomousInit() {
		Intake.getInstance().setIntakeRollers(0.25);
		Elevator.getInstance().zero();
		Intake.getInstance().zeroLift();
		testAuto = new DriveForwardAuto();
		System.out.println(DriverStation.getInstance().getGameSpecificMessage().charAt(0));
		if (autoSelector.getSelected().equals("DriveForward")) {
			testAuto = new DriveForwardAuto();
		}else if(autoSelector.getSelected().equals("Center")){
			if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
				testAuto = new CenterSwitchAuto(AutoSide.LEFT);
			}else if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'R') {
				testAuto = new CenterSwitchAuto(AutoSide.RIGHT);
			}
		}
		testAuto = new LeftScaleAuto();
		testAuto.start();
		isElevatorZeroed = true;
	}

	@Override
	public void autonomousPeriodic() {
	}

	public void teleopInit() {
		if (isElevatorZeroed == false) {
			Elevator.getInstance().zero();
			Intake.getInstance().zeroLift();
			System.out.println("ZeroCalled");
		}
		drive.start();
		Intake.getInstance().setIntakeRollers(0.25);
		Intake.getInstance().counterInit();
//		Elevator.getInstance().setPosition(ElevatorState.BOTTOM);
//		Intake.getInstance().zeroLift();
	}

	@Override
	public void teleopPeriodic() {
			Elevator.getInstance().log();
			Intake.getInstance().stopIntake();
			//System.out.println(Intake.getInstance().isSwitchSet());
			
			Intake.getInstance().dsOutput();
			
			Elevator.getInstance().outputCurrent();
			Drivetrain.getInstance().outputCurrent();
			Intake.getInstance().outputCurrent();
	

	}

	@Override
	public void robotPeriodic() {
		Logger.getInstance().update();
		Scheduler.getInstance().run();
		
		
	}
	
	@Override
	public void disabledInit() {
	}
}
