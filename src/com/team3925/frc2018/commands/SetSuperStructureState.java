package com.team3925.frc2018.commands;

import com.team3925.frc2018.subsystems.Arm;
import com.team3925.frc2018.subsystems.Arm.ArmState;
import com.team3925.frc2018.subsystems.Elevator;
import com.team3925.frc2018.subsystems.Elevator.ElevatorState;
import com.team3925.frc2018.subsystems.Intake;
import com.team3925.frc2018.subsystems.Intake.IntakeState;

import edu.wpi.first.wpilibj.command.Command;

public class SetSuperStructureState extends Command {

	ElevatorState elevatorState;
	ArmState armState;
	IntakeState intakeState;

	boolean armDone;
	boolean intakeDone;

	public SetSuperStructureState(ElevatorState elevatorState, ArmState armState, IntakeState intakeState) {
		requires(Elevator.getInstance());
		requires(Intake.getInstance());
		requires(Arm.getInstance());

		this.elevatorState = elevatorState;
		this.armState = armState;
		this.intakeState = intakeState;

		armDone = false;
		intakeDone = false;
	}

	@Override
	protected void initialize() {
		Elevator.getInstance().setPosition(elevatorState);
		armDone = false;
		intakeDone = false;
	}

	@Override
	protected void execute() {
		if (armState == ArmState.REVERSE_EXTENDED) {
			if (Elevator.getInstance().safeToDeployBackwards()) {
				Arm.getInstance().setSetpoint(armState);
				armDone = true;
			} else {
				Arm.getInstance().setSetpoint(ArmState.RETRACTED);
				armDone = false;
			}
		} else {
			Arm.getInstance().setSetpoint(armState);
			armDone = true;
		}

		if (intakeState == IntakeState.HOLD || intakeState == IntakeState.UNKNOWN) {
			Intake.getInstance().setState(intakeState);
			intakeDone = true;
		} else {
			if (Arm.getInstance().isAtSetpoint() && armDone) {
				Intake.getInstance().setState(intakeState);
				intakeDone = true;
			} else {
				intakeDone = false;
			}
		}
	}

	@Override
	protected boolean isFinished() {
		return (armDone && intakeDone);
	}

}
