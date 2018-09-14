package com.team3925.frc2018.commands;

import com.team3925.frc2018.OI;
import com.team3925.frc2018.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class RunIntakeLiftRaw extends Command{
	
	@Override
	protected void execute() {
		Intake.getInstance().setLiftMotorRaw(OI.getInstance().getLiftIntake());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {
		Intake.getInstance().setLiftMotorRaw(0);
	}

}
