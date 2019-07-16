/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class Toggle {
	private boolean value;
	
	public Toggle() {
		value = false;
	}
	
	public boolean isToggle(boolean newValue) {
		boolean result = false;
		if(value != newValue) {
			value = newValue;
			result = true;
		}
		return result;
	}
}
