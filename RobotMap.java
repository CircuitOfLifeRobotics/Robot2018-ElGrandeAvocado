package com.team3925.frc2018.maps;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team3925.utils.TalonSRXFactory;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class RobotMap {
	
	public static final class DrivetrainMap{
		public static final TalonSRX LEFT_MASTER = TalonSRXFactory.createDefaultTalon(0);
		public static final TalonSRX LEFT_SLAVE_A = TalonSRXFactory.createPermanentSlaveTalon(1, LEFT_MASTER.getDeviceID());
		public static final TalonSRX LEFT_SLAVE_B = TalonSRXFactory.createPermanentSlaveTalon(2, LEFT_MASTER.getDeviceID());
		
		public static final TalonSRX RIGHT_MASTER = TalonSRXFactory.createDefaultTalon(3);
		public static final TalonSRX RIGHT_SLAVE_A = TalonSRXFactory.createPermanentSlaveTalon(4, RIGHT_MASTER.getDeviceID());
		public static final TalonSRX RIGHT_SLAVE_B = TalonSRXFactory.createPermanentSlaveTalon(5, RIGHT_MASTER.getDeviceID());
		
		public static final DoubleSolenoid SHIFT_SOLENOID = new DoubleSolenoid(0, 1);
	}
	
	public static final class IntakeMap{
		public static final TalonSRX LEFT_INTAKE = new TalonSRX(6);
		public static final TalonSRX RIGHT_INTAKE = new TalonSRX(7);
		
		public static final DoubleSolenoid GRAB_SOLENOID = new DoubleSolenoid(6, 7);
		public static final DoubleSolenoid HOLD_SOLENOID = new DoubleSolenoid(8, 9);
		public static final DoubleSolenoid LIFT_SOLENOID = new DoubleSolenoid(10, 11);
	}

	public static final class LiftMap{
		public static final TalonSRX MASTER = TalonSRXFactory.createDefaultTalon(8);
		public static final TalonSRX SLAVE  = TalonSRXFactory.createPermanentSlaveTalon(9, MASTER.getDeviceID());
	}
	
	public static final class ElevatorMap{
		public static final TalonSRX MASTER = TalonSRXFactory.createDefaultTalon(10);
		public static final TalonSRX SLAVE_A = TalonSRXFactory.createPermanentSlaveTalon(11, MASTER.getDeviceID());
		public static final TalonSRX SLAVE_B = TalonSRXFactory.createPermanentSlaveTalon(12, MASTER.getDeviceID());
		public static final TalonSRX SLAVE_C = TalonSRXFactory.createPermanentSlaveTalon(13, MASTER.getDeviceID());
		
		public static final DoubleSolenoid GRABBER_SOLENOID = new DoubleSolenoid(2, 3);
		public static final DoubleSolenoid SPATULA_SOLENOID = new DoubleSolenoid(4, 5);
	}
}
