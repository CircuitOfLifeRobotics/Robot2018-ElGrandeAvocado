package com.team3925.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team3925.frc2018.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

	private final TalonSRX leftIntake = RobotMap.IntakeMap.LEFT_INTAKE;

	private final TalonSRX rightIntake = RobotMap.IntakeMap.RIGHT_INTAKE;
	
	private final TalonSRX liftMotor = RobotMap.IntakeMap.LIFT_MOTOR;

	private final DoubleSolenoid grabSolenoid = RobotMap.IntakeMap.GRAB_SOLENOID;

	private static Intake instance;

	public static Intake getInstance() {
		if (instance == null)
			instance = new Intake();
		return instance;
	}

	private Intake() {
	}

	public void setIntakeRollers(double speed) {
		leftIntake.set(ControlMode.PercentOutput, speed);
		rightIntake.set(ControlMode.PercentOutput, speed);
	}
	public void setLiftMotorRaw(double speed) {
		liftMotor.set(ControlMode.PercentOutput, speed);
	}

	public void setGrabber(boolean grab) {
		grabSolenoid.set((grab) ? Value.kForward : Value.kReverse);
	}

	@Override
	protected void initDefaultCommand() {
	}

}
