package com.team3925.frc2018;

public class Logger {
//	SendablePIDTuner drivetrainTuner;
	
	private static Logger instance;
	public static Logger getInstance() {
		if (instance == null)
			instance = new Logger();
		return instance;
	}
	
	private Logger() {
//		drivetrainTuner = new SendablePIDTuner(Drivetrain.getInstance(), Drivetrain.getInstance());
	}
	public void update() {
//		drivetrainTuner.updateDashboard();
	}
}
