package com.team3925.utils;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class TalonSRXFactory {
	
	public static TalonSRX createDefaultTalon(int id) {
		return new TalonSRX(id); //TODO: Implement full factory
	}

	public static TalonSRX createPermanentSlaveTalon(int id, int masterId) {
		final TalonSRX talon = createDefaultTalon(id);
		talon.set(ControlMode.Follower, masterId);
		return talon;
	}

}
