package com.team3925.frc2018;

public class Constants {
	public static final int TIMEOUT_MS = 0;
	public static final int PID_ID_X = 0;
	public static final int CTRE_ENCODER_TICKS_PER_REV = 4096;
	public static final double GEAR_CURR_OFFSET_THING = 0.0;
	
	public static class ElevatorSetpoints {
		public static final int MAX_HEIGHT = 0;
		public static final int SCALE_TOP = 77428;
		public static final int SCALE_MED = 70000;
		public static final int SCALE_LOW = 54297;
		public static final int SWITCH = 40000;
		public static final int BOTTOM = 0;
		public static final int DEPLOY_HEIGHT = 0; //TODO: Measure this
	}
	
	public static class ArmSetpoints {
		public static final double EXTENDED = 0;
		public static final double RETRACTED = 90;
		public static final double BACKWARDS = 135;
		public static final double SCALEASSIST = 45;
	}
	
	public static class IntakeSetpoints {
		public static final double HOLD_PWR = 0.25;
		public static final double SHOOT_PWR = -1;
		public static final double DROP_PWR = -0.5;
		public static final double INTAKE_PWR = 1;
	}
}
