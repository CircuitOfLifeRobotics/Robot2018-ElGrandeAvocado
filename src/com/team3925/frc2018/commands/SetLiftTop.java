package com.team3925.frc2018.commands;

import com.team3925.frc2018.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class SetLiftTop extends Command{

	@Override
	protected void initialize() {
		Intake.getInstance().setAngle(85);
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}
	

}
