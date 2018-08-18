package com.team3925.frc2018.commands.autos;

import com.team3925.frc2018.commands.SetSuperStructureState;
import com.team3925.frc2018.subsystems.Arm.ArmState;
import com.team3925.frc2018.subsystems.Elevator.ElevatorState;
import com.team3925.frc2018.subsystems.Intake.IntakeState;
import com.team3925.utils.MotionProfileCommand;
import com.team3925.utils.Paths;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftScaleAuto extends CommandGroup {

	public enum AutoSide {
		LEFT, RIGHT
	}

	public LeftScaleAuto() {
		addParallel(new SetSuperStructureState(ElevatorState.BOTTOM, ArmState.RETRACTED, IntakeState.HOLD));
		addSequential(new MotionProfileCommand(Paths.getTraj("Side_Scale"),false,false));
		addParallel(new SetSuperStructureState(ElevatorState.SCALE_MED, ArmState.FORWARD_EXTENDED, IntakeState.SHOOT));
	}

}
