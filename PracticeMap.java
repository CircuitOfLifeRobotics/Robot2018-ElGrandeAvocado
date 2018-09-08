package com.team3925.frc2018.maps;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team3925.utils.TalonSRXFactory;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class PracticeMap {
	
	public static final class DrivetrainMap{
		public static final TalonSRX LEFT_MASTER = TalonSRXFactory.createDefaultTalon(3);
		public static final TalonSRX LEFT_SLAVE_A = TalonSRXFactory.createPermanentSlaveTalon(4, LEFT_MASTER.getDeviceID());
		
		public static final TalonSRX RIGHT_MASTER = TalonSRXFactory.createDefaultTalon(1);
		public static final TalonSRX RIGHT_SLAVE_A = TalonSRXFactory.createPermanentSlaveTalon(2, RIGHT_MASTER.getDeviceID());
		
		public static final DoubleSolenoid SHIFT_SOLENOID = new DoubleSolenoid(0, 1);
	}
	
	public static final class IntakeMap{
		public static final TalonSRX LEFT_INTAKE = new TalonSRX(5);
		public static final TalonSRX RIGHT_INTAKE = new TalonSRX(6);
		
		public static final DoubleSolenoid GRAB_SOLENOID = new DoubleSolenoid(2, 3);
	}
}
