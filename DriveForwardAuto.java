package com.team3925.frc2018.commands.autos;

import com.team3925.frc2018.commands.SetSuperStructureState;
import com.team3925.frc2018.subsystems.Arm.ArmState;
import com.team3925.frc2018.subsystems.Elevator.ElevatorState;
import com.team3925.frc2018.subsystems.Intake.IntakeState;
import com.team3925.utils.MotionProfileCommand;
import com.team3925.utils.Paths;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveForwardAuto extends CommandGroup {

	public DriveForwardAuto() {
		addParallel(new SetSuperStructureState(ElevatorState.BOTTOM, ArmState.RETRACTED, IntakeState.HOLD));
		addParallel(new MotionProfileCommand(Paths.getTraj("center_center"),false,false));
	}

}
