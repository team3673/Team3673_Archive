/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.I2C;

/**
 * Add your docs here.
 */
public class ScrollText {
    private String text;
    private int offset;
	private byte[] byte1 = new byte[10];
    
    public ScrollText(String text) {
        this.text = "   " + text + "    ";
        offset = 0;
    }

    public void update(I2C i2c) {
        byte1[0] = 0b00001111;
        byte1[1] = 0b00001111;
        byte1[2] = Digit.CharReg[Digit.CharInd.indexOf(text.substring(offset+3, offset+4))][0];
        byte1[3] = Digit.CharReg[Digit.CharInd.indexOf(text.substring(offset+3, offset+4))][1];
        byte1[4] = Digit.CharReg[Digit.CharInd.indexOf(text.substring(offset+2, offset+3))][0];
        byte1[5] = Digit.CharReg[Digit.CharInd.indexOf(text.substring(offset+2, offset+3))][1];
        byte1[6] = Digit.CharReg[Digit.CharInd.indexOf(text.substring(offset+1, offset+2))][0];
        byte1[7] = Digit.CharReg[Digit.CharInd.indexOf(text.substring(offset+1, offset+2))][1];
        byte1[8] = Digit.CharReg[Digit.CharInd.indexOf(text.substring(offset, offset+1))][0];
        byte1[9] = Digit.CharReg[Digit.CharInd.indexOf(text.substring(offset, offset+1))][1];
        i2c.writeBulk(byte1);
        offset++;
        if(offset > text.length()-4) {
            offset = 0;
        }
    }
}
