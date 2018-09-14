package com.team3925.frc2018;

public class Constants {
	public static final int TIMEOUT_MS = 0;
	public static final int PID_ID_X = 0;
	public static final int CTRE_ENCODER_TICKS_PER_REV = 4096;
	public static final double GEAR_CURR_OFFSET_THING = 0.0;
	
	public static boolean isGrabbing;

	public static final double SWITCH_SETPOINT = 40000;

	public static class ElevatorSetpoints {
		public static final int MAX_HEIGHT = 70000;//70000
		public static final int SCALE_TOP = 70000;//70000
		public static final int SCALE_MED = 63679;//63679
		public static final int SCALE_LOW = 55666;//55666
		public static final int SWITCH = 23000;//40000
		public static final int BOTTOM = 0;
		public static final int DEPLOY_HEIGHT = 36000;
	
	}
}
