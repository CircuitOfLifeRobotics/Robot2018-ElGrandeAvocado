package com.team3925.frc2018;

import com.team3925.frc2018.commands.DriveManual;
import com.team3925.frc2018.commands.SetSuperStructureState;
import com.team3925.frc2018.commands.ShiftHigh;
import com.team3925.frc2018.commands.autos.CenterSwitchAuto;
import com.team3925.frc2018.commands.autos.DriveForwardAuto;
import com.team3925.frc2018.subsystems.Arm;
import com.team3925.frc2018.subsystems.Arm.ArmState;
import com.team3925.frc2018.subsystems.Drivetrain;
import com.team3925.frc2018.subsystems.Elevator;
import com.team3925.frc2018.subsystems.Elevator.ElevatorState;
import com.team3925.frc2018.subsystems.Intake;
import com.team3925.frc2018.subsystems.Intake.IntakeState;
import com.team3925.utils.Paths;

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
		Paths.generate_Autos();
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
		isElevatorZeroed = true;
		
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
	}

	@Override
	public void autonomousPeriodic() {
	}

	public void teleopInit() {
		if (isElevatorZeroed == false) {
			zero();
			System.out.println("ZeroCalled");
			new SetSuperStructureState(ElevatorState.BOTTOM, ArmState.RETRACTED, IntakeState.HOLD).start();
		}
		isElevatorZeroed = false;
		Drivetrain.getInstance();
		drive.start();
	}
	
	@Override
	public void teleopPeriodic() {
//		if (Timer.getFPGATimestamp() < 10) {//back
//			RobotMap.DrivetrainMap.LEFT_MASTER.set(1);
//			RobotMap.DrivetrainMap.LEFT_SLAVE_A.set(0);
//			RobotMap.DrivetrainMap.LEFT_SLAVE_B.set(0);
//			
//			RobotMap.DrivetrainMap.RIGHT_MASTER.set(1);
//			RobotMap.DrivetrainMap.RIGHT_SLAVE_A.set(0);
//			RobotMap.DrivetrainMap.RIGHT_SLAVE_B.set(0);
//		}
//		
//		if (Timer.getFPGATimestamp() > 10 && Timer.getFPGATimestamp() < 20) {//forward
//			System.out.println("A\'s");
//			RobotMap.DrivetrainMap.LEFT_MASTER.set(0);
//			RobotMap.DrivetrainMap.LEFT_SLAVE_A.set(1);
//			RobotMap.DrivetrainMap.LEFT_SLAVE_B.set(0);
//			
//			RobotMap.DrivetrainMap.RIGHT_MASTER.set(0);
//			RobotMap.DrivetrainMap.RIGHT_SLAVE_A.set(1);
//			RobotMap.DrivetrainMap.RIGHT_SLAVE_B.set(0);
//		}
//		if (Timer.getFPGATimestamp() > 20 && Timer.getFPGATimestamp() < 30) {//
//			System.out.println("B\'s");
//			RobotMap.DrivetrainMap.LEFT_MASTER.set(0);
//			RobotMap.DrivetrainMap.LEFT_SLAVE_A.set(0);
//			RobotMap.DrivetrainMap.LEFT_SLAVE_B.set(1);
//			
//			RobotMap.DrivetrainMap.RIGHT_MASTER.set(0);
//			RobotMap.DrivetrainMap.RIGHT_SLAVE_A.set(0);
//			RobotMap.DrivetrainMap.RIGHT_SLAVE_B.set(1);
//		}
//		if (Timer.getFPGATimestamp() > 30) {
//			System.out.println("All done");
//			RobotMap.DrivetrainMap.LEFT_MASTER.set(0);
//			RobotMap.DrivetrainMap.LEFT_SLAVE_A.set(0);
//			RobotMap.DrivetrainMap.LEFT_SLAVE_B.set(0);
//			
//			RobotMap.DrivetrainMap.RIGHT_MASTER.set(0);
//			RobotMap.DrivetrainMap.RIGHT_SLAVE_A.set(0);
//			RobotMap.DrivetrainMap.RIGHT_SLAVE_B.set(0);
//		}
		Arm.getInstance().log();
		Elevator.getInstance().log();
		SmartDashboard.putString("Elevator State", Elevator.getInstance().getState().toString());
		SmartDashboard.putString("Intake State", Intake.getInstance().getState().toString());
		SmartDashboard.putString("Arm State", Arm.getInstance().getState().toString());
	}
	
	@Override
	public void testPeriodic() {
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
