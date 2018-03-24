package com.team3925.frc2018.commands.autos;

import com.team3925.frc2018.commands.SetSuperStructureState;
import com.team3925.frc2018.subsystems.Arm.ArmState;
import com.team3925.frc2018.subsystems.Elevator.ElevatorState;
import com.team3925.frc2018.subsystems.Intake.IntakeState;
import com.team3925.utils.MotionProfileCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class CenterSwitchAuto extends CommandGroup {

	public CenterSwitchAuto(char side) {
		addParallel(new SetSuperStructureState(ElevatorState.BOTTOM, ArmState.RETRACTED, IntakeState.HOLD));
		if (side == 'L' || side == 'l') {
			addSequential(new MotionProfileCommand("CENTER_LEFTSWITCH"));
		} else if (side == 'R' || side == 'r') {
			addSequential(new MotionProfileCommand("CENTER_RIGHTSWITCH"));
		}
		addSequential(new WaitCommand(4));
		addParallel(new SetSuperStructureState(ElevatorState.SWITCH, ArmState.FORWARD_EXTENDED, IntakeState.DROP));
	}

}
