/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;

/**
 * Add your docs here.
 */
public class Snake {
	private int snakeID;
	private int length;
	private int speed;
	
	private byte byteL;
	private byte byteH;
	
	private static final int maxLength = 4;
	
	private int countdown;

	private ArrayList<Int> snake;
	
	private int count;
	private int nextSegment;
	
	public Snake(int snakeID) {
		this.snakeID = snakeID;
		length = 3;
		speed = 10;
		countdown = speed;
		snake = new ArrayList<Int> (0);
		snake.add(new Int((int) (Math.random() * Digit.Segments.length-1)));
	}
	
	public int incLength(){
		length++;
		if(length > maxLength) {
			length = 1;
		}
		return length;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
		if(countdown > speed) {
			countdown = speed;
		}
	}
	
	public void moveSnake() {
		countdown--;
		if(countdown < 0) {
			if(snake.size() <= length) {
                count = 0;
                ArrayList<Int> possibles = new ArrayList<Int> (0);
				for(int adjacent = 0; adjacent < Digit.Adjacents.length; adjacent++) {
					for(Int part : snake) {
						if(part.get() == adjacent) {
							break;
                        }
                        possibles.add(new Int(adjacent));
						count++;
					}
				}
				nextSegment = (int) (Math.random() * count);
				snake.add(possibles.get(nextSegment));
			}
			if(snake.size() > length) {
				snake.remove(0);
			}
			byteL = 0b00000000;
			byteH = 0b00000000;
			for(Int part : snake) {
                System.out.print(part.get());
				byteL |= Digit.Segments[part.get()][0];
				byteH |= Digit.Segments[part.get()][1];
			}
			countdown = speed;
		}
	}
	
	public byte getByteL(int selected) {
		if(selected == snakeID) {
            byte temp = Digit.Segments[Digit.Segment.DP.ordinal()][0];
            temp |= byteL;
			return temp;
		}
		return byteL;
	}

	public byte getByteH(int selected) {
		if(selected == snakeID) {
            byte temp = Digit.Segments[Digit.Segment.DP.ordinal()][1];
            temp |= byteL;
			return temp;
		}
		return byteH;
    }
    
    public int getID() {
        return snakeID;
    }
}
