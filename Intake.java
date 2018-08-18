package com.team3925.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.team3925.frc2018.Constants;
import com.team3925.frc2018.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

	public static enum IntakeGrabberState {
		OPEN, CLOSED, INTAKE, UNKNOWN;
	}

	public static enum IntakeRollerState {
		INTAKE, SHOOT, DROP, HOLD, UNKNOWN, ROLL_OUT;
	}

	public static enum IntakeState {
		INTAKE, SHOOT, DROP, HOLD, UNKNOWN, OPEN, CLOSED, ROLL_OUT;
	}

	private final TalonSRX leftIntake = RobotMap.IntakeMap.LEFT_INTAKE;
	// private final VictorSPX leftIntake = RobotMap.IntakeMap.LEFT_INTAKE;

	private final VictorSPX rightIntake = RobotMap.IntakeMap.RIGHT_INTAKE;

	private final DoubleSolenoid grabSolenoid = RobotMap.IntakeMap.GRAB_SOLENOID;
	 private final DoubleSolenoid springSolenoid = RobotMap.IntakeMap. SPRING_SOLENOID;

	private IntakeGrabberState grabState = IntakeGrabberState.UNKNOWN;
	private IntakeRollerState rollState = IntakeRollerState.UNKNOWN;
	private IntakeState state = IntakeState.UNKNOWN;

	private static Intake instance;

	public static Intake getInstance() {
		if (instance == null)
			instance = new Intake();
		return instance;
	}

	private Intake() {
		leftIntake.setInverted(false);
		rightIntake.setInverted(true);
	}

	private void setIntakeRollers(double speed) {
		if (Math.abs(speed) <= 0)
			speed = 0.25;
		leftIntake.set(ControlMode.PercentOutput, speed);
		rightIntake.set(ControlMode.PercentOutput, speed);
	}

	private void setGrabberState(IntakeGrabberState state) {
		this.grabState = state;
		switch (state) {
		case OPEN:
			grabSolenoid.set(Value.kForward);
			springSolenoid.set(Value.kForward);

			break;
		// springSolenoid.set(Value.kForward);
		case INTAKE:
			grabSolenoid.set(Value.kOff);
			springSolenoid.set(Value.kForward);
			break;
		// springSolenoid.set(Value.kForward);
		case CLOSED:
			grabSolenoid.set(Value.kReverse);
			springSolenoid.set(Value.kReverse);			break;
		// springSolenoid.set(Value.kForward);
		}

	}

	private void setRollerState(IntakeRollerState state) {
		this.rollState = state;
		switch (state) {
		case DROP:
			setIntakeRollers(Constants.IntakeSetpoints.DROP_PWR);
			break;
		case HOLD:
			setIntakeRollers(Constants.IntakeSetpoints.HOLD_PWR);
			break;
		case INTAKE:
			setIntakeRollers(Constants.IntakeSetpoints.INTAKE_PWR);
			break;
		case SHOOT:
			setIntakeRollers(Constants.IntakeSetpoints.SHOOT_PWR);
			break;
		default:
			break;
		}
	}

	public void setState(IntakeState state) {
		if (state != IntakeState.UNKNOWN) {
			switch (state) {
			case DROP:
				setGrabberState(IntakeGrabberState.OPEN);
				setRollerState(IntakeRollerState.DROP);
				break;
			case HOLD:
				setGrabberState(IntakeGrabberState.CLOSED);
				setRollerState(IntakeRollerState.HOLD);
				break;
			case INTAKE:
				// setGrabberState(IntakeGrabberState.OPEN);
				setRollerState(IntakeRollerState.INTAKE);
				break;
			case SHOOT:
				setGrabberState(IntakeGrabberState.CLOSED);
				setRollerState(IntakeRollerState.SHOOT);
				break;
			case OPEN:
				setGrabberState(IntakeGrabberState.OPEN);
				setRollerState(IntakeRollerState.INTAKE);
				break;
			case CLOSED:
				setGrabberState(IntakeGrabberState.CLOSED);
			case ROLL_OUT:
				setRollerState(IntakeRollerState.SHOOT);
			default:
				break;
			}
		}
	}
	
	public void changeState(IntakeState state) {
		this.state = state;
	}

	public IntakeGrabberState getGrabberState() {
		return this.grabState;
	}

	public IntakeRollerState getRollerState() {
		return this.rollState;
	}

	public IntakeState getState() {
		return this.state;
	}

	@Override
	protected void initDefaultCommand() {
	}

	public double getLeftCurrent() {
		return leftIntake.getOutputCurrent();
	}

	public double getRightCurrent() {
		return rightIntake.getOutputCurrent();
	}

}
