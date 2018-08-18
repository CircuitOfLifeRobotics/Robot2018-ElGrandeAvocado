 package com.team3925.frc2018.commands.autos;

import com.team3925.frc2018.commands.SetSuperStructureState;
import com.team3925.frc2018.subsystems.Arm.ArmState;
import com.team3925.frc2018.subsystems.Elevator.ElevatorState;
import com.team3925.frc2018.subsystems.Intake.IntakeState;
import com.team3925.utils.DelayedCommand;
import com.team3925.utils.MotionProfileCommand;
import com.team3925.utils.Paths;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitUntilCommand;

public class CenterSwitchAuto extends CommandGroup {
	
	Boolean mirrored;
	
	public CenterSwitchAuto(char side) {
		if (side == 'L' || side == 'l') {
			mirrored = true;
		} else if (side == 'R' || side == 'r') {
			mirrored = false;
		}
		
		addSequential(new SetSuperStructureState(ElevatorState.BOTTOM, ArmState.RETRACTED, IntakeState.HOLD));
		addSequential(new MotionProfileCommand(Paths.getTraj("center_switch"),mirrored,false));
		addParallel(new DelayedCommand(new SetSuperStructureState(ElevatorState.SWITCH, ArmState.FORWARD_EXTENDED, IntakeState.HOLD),2));
		addSequential(new SetSuperStructureState(ElevatorState.SWITCH, ArmState.FORWARD_EXTENDED, IntakeState.DROP));

	}

}
