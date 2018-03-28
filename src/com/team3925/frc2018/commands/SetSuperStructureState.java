package com.team3925.frc2018.commands;

import com.team3925.frc2018.Constants;
import com.team3925.frc2018.subsystems.Arm;
import com.team3925.frc2018.subsystems.Arm.ArmState;
import com.team3925.frc2018.subsystems.Elevator;
import com.team3925.frc2018.subsystems.Elevator.ElevatorState;
import com.team3925.frc2018.subsystems.Intake;
import com.team3925.frc2018.subsystems.Intake.IntakeState;

import edu.wpi.first.wpilibj.command.Command;

public class SetSuperStructureState extends Command {

	private ElevatorState elevatorState;
	private ArmState armState;
	private IntakeState intakeState;

	private ElevatorState estate;
	private IntakeState istate;
	private ArmState astate;

	boolean armSet;
	boolean intakeSet;
	boolean elevatorSet;

	boolean waitForArm;
	boolean waitForElevator;

	public SetSuperStructureState(ElevatorState elevatorState, ArmState armState, IntakeState intakeState) {
		requires(Elevator.getInstance());
		requires(Intake.getInstance());
		requires(Arm.getInstance());

		this.estate = elevatorState;
		this.astate = armState;
		this.istate = intakeState;

		elevatorSet = false;
		armSet = false;
		intakeSet = false;

		waitForArm = false;
		waitForElevator = false;
	}

	@Override
	protected void initialize() {

		elevatorState = estate;
		intakeState = istate;
		armState = astate;

		System.out.println(elevatorState + "  OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO" + "");

		elevatorSet = false;
		armSet = false;
		intakeSet = false;

		waitForArm = false;
		waitForElevator = false;

		// REPLACED BELOW
		// if (!(armState == ArmState.REVERSE_EXTENDED || Arm.getInstance().getState()
		// == ArmState.SCALE_ANGLE)
		// && (intakeState == IntakeState.DROP || intakeState == IntakeState.SHOOT)) {
		// armState = ArmState.FORWARD_EXTENDED;
		// }

		// If you don't know where to put the systems, use the current state of the
		// systems.
		// Now you never have to worry about things being unknown

		if (armState != ArmState.REVERSE_EXTENDED && armState != ArmState.SCALE_ANGLE) {
			// if you DON'T want to go to Reverse or Scale angle
			if (intakeState == IntakeState.DROP || intakeState == IntakeState.SHOOT) {
				// and you want to drop/shoot the cube
				// set the desired armState to forward
				armState = ArmState.FORWARD_EXTENDED;
			}
		}
		if (armState == ArmState.UNKNOWN) {
			armState = Arm.getInstance().getState();
			System.out.println(armState + "  ARMSTATE");
		}

		if (elevatorState == ElevatorState.UNKNOWN) {
			elevatorState = Elevator.getInstance().getState();
			System.out.println(elevatorState + "  ElevatorSTATE");
		}

		if (intakeState == IntakeState.UNKNOWN) {
			intakeState = Intake.getInstance().getState();
			System.out.println(intakeState + "    INtakeSTate");
		}

	}

	@Override
	protected void execute() {

		if (armState == ArmState.UNKNOWN) {
			armState = Arm.getInstance().getState();
		}

		if (elevatorState == ElevatorState.UNKNOWN) {
			elevatorState = Elevator.getInstance().getState();
		}

		if (intakeState == IntakeState.UNKNOWN) {
			intakeState = Intake.getInstance().getState();
		}

		if (!elevatorSet) {
			// if you havent set the elevator

			if (Elevator.getInstance().positionAtState(elevatorState) <= Constants.ElevatorSetpoints.DEPLOY_HEIGHT) {
				// and you are trying to move BELOW the threshold

				if (Arm.getInstance().safeToGoDown()) {
					// and it is safe to go down
					// set the elevator
					Elevator.getInstance().setPosition(elevatorState);
					Elevator.getInstance().setState(elevatorState);
					elevatorSet = true;
				}
			} else {
				// and you are trying to stay ABOVE the threshold
				// set the elevator
				Elevator.getInstance().setPosition(elevatorState);
				Elevator.getInstance().setState(elevatorState);
				elevatorSet = true;
			}
		}

		// REPLACED ABOVE
		// if (Elevator.getInstance().positionAtState(elevatorState) <=
		// Constants.ElevatorSetpoints.DEPLOY_HEIGHT) {
		// if (Arm.getInstance().safeToGoDown()) {
		// Elevator.getInstance().setPosition(elevatorState);
		// elevatorDone = true;
		// }
		// } else {
		// Elevator.getInstance().setPosition(elevatorState);
		// }

		if (!armSet) {
			// if you haven't set the arm
			if ((elevatorState == ElevatorState.BOTTOM) || (Elevator.getInstance().atSetpoint() && elevatorSet)) {
				// if you want to go to the bottom, or you are at setpoint

				if (armState == ArmState.REVERSE_EXTENDED) {
					// and you are trying to deploy BACKWARDS

					if (Elevator.getInstance().safeToDeployBackwards()) {
						// and it is SAFE to deploy the arm backwards
						// set the arm
						Arm.getInstance().setSetpoint(armState);
						Arm.getInstance().setState(armState);
						armSet = true;
					}
				} else {
					Arm.getInstance().setSetpoint(armState);
					Arm.getInstance().setState(armState);
					armSet = true;
				}
			}
		}

		// REPLACED ABOVE
		// if (armState == ArmState.REVERSE_EXTENDED) {
		// if (Elevator.getInstance().safeToDeployBackwards()) {
		// Arm.getInstance().setSetpoint(armState);
		// armDone = true;
		// } else {
		// Arm.getInstance().setSetpoint(ArmState.RETRACTED);
		// armDone = false;
		// }
		// } else {
		// Arm.getInstance().setSetpoint(armState);
		// armDone = true;
		// }

		if (!intakeSet) {
			// if you haven't set the intake

			if (elevatorSet) {

				if (intakeState != IntakeState.HOLD) {
					// and you do NOT want to hold

					if (armSet) {
						// and your arm is set to it's final position

						if (Arm.getInstance().isAtSetpoint()) {
							// and the arm has reached it's setpoint
							Intake.getInstance().setState(intakeState);
							Intake.getInstance().changeState(intakeState);
							intakeSet = true;
						}
					}
				} else {
					// and you want to hold
					// set the intake (state will be hold)
					Intake.getInstance().setState(intakeState);
					Intake.getInstance().changeState(intakeState);
					intakeSet = true;
				}
			}

		}

		// REPLACED ABOVE
		// if (intakeState == IntakeState.HOLD || intakeState == IntakeState.UNKNOWN) {
		// Intake.getInstance().setState(intakeState);
		// intakeDone = true;
		// } else {
		// if (Arm.getInstance().isAtSetpoint() && armDone) {
		// Intake.getInstance().setState(intakeState);
		// intakeDone = true;
		// } else {
		// intakeDone = false;
		// }
		// }
	}

	@Override
	protected void end() {

		// Elevator.getInstance().setState(elevatorState);
		// Arm.getInstance().setState(armState);
		// Intake.getInstance().changeState(intakeState);

		// armState = Arm.getInstance().getState();
		// elevatorState = Elevator.getInstance().getState();
		// intakeState = Intake.getInstance().getState();
	}

	@Override
	protected void interrupted() {
		this.end();
	}

	@Override
	protected boolean isFinished() {
		// Everything is done being set
		return (armSet && intakeSet && elevatorSet);
	}

}
