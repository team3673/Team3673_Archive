/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// The segment grid appears as follows:
//
//   AAAAAAAAAAAAAAAAAA
// FF                  BB
// FF  HH    JJ    KK  BB
// FF   HH   JJ   KK   BB
// FF   HH   JJ   KK   BB
// FF    HH  JJ  KK    BB
// FF                  BB
//   G1G1G1G1  G2G2G2G2
// EE                  CC
// EE    LL  MM  NN    CC
// EE   LL   MM   NN   CC
// EE   LL   MM   NN   CC
// EE  LL    MM    NN  CC
// EE                  CC
//   DDDDDDDDDDDDDDDDDD  DP

package frc.robot;

/**
 * Add your docs here.
 */
public final class Digit {
	public static enum Segment {
		A,
		B,
		C,
		D,
		E,
		F,
		G1,
		G2,
		H,
		J,
		K,
		L,
		M,
		N,
		DP
    }
    
    public static final Segment[] segmentList = {
        Segment.A,
        Segment.B,
        Segment.C,
        Segment.D,
        Segment.E,
        Segment.F,
        Segment.G1,
        Segment.G2,
        Segment.H,
        Segment.J,
        Segment.K,
        Segment.L,
        Segment.M,
        Segment.N,
        Segment.DP
    };
	
	public static final byte[][] Segments = {
		{ 0b00000001 , 0b00000000 }, // Segment A
		{ 0b00000010 , 0b00000000 }, // Segment B
		{ 0b00000100 , 0b00000000 }, // Segment C
		{ 0b00001000 , 0b00000000 }, // Segment D
		{ 0b00010000 , 0b00000000 }, // Segment E
		{ 0b00100000 , 0b00000000 }, // Segment F
		{ 0b01000000 , 0b00000000 }, // Segment G1
		{ (byte) 0b10000000 , 0b00000000 }, // Segment G2
		{ 0b00000000 , 0b00000001 }, // Segment H
		{ 0b00000000 , 0b00000010 }, // Segment J
		{ 0b00000000 , 0b00000100 }, // Segment K
		{ 0b00000000 , 0b00100000 }, // Segment L
		{ 0b00000000 , 0b00010000 }, // Segment M
		{ 0b00000000 , 0b00001000 }, // Segment N
		{ 0b00000000 , 0b01000000 }  // Segment DP
	};
	
    public static final Segment Adjacents[][] = {
		{ Segment.B, Segment.K, Segment.J, Segment.H, Segment.F },	// Adjacents to Segment A
		{ Segment.C, Segment.G2, Segment.K, Segment.A },	// Adjacents to Segment B
		{ Segment.D, Segment.N, Segment.G2, Segment.B },	// Adjacents to Segment C
		{ Segment.E, Segment.L, Segment.M, Segment.N, Segment.C },	// Adjacents to Segment D
		{ Segment.D, Segment.L, Segment.G1, Segment.F },	// Adjacents to Segment E
		{ Segment.E, Segment.G1, Segment.H, Segment.A },	// Adjacents to Segment F
		{ Segment.F, Segment.H, Segment.J, Segment.K, Segment.G2, Segment.N, Segment.M, Segment.L, Segment.E },	// Adjacents to Segment G1
		{ Segment.C, Segment.N, Segment.M, Segment.L, Segment.G1, Segment.H, Segment.J, Segment.K, Segment.B },	// Adjacents to Segment G2
		{ Segment.A, Segment.J, Segment.K, Segment.G2, Segment.N, Segment.M, Segment.L, Segment.G1, Segment.F },	// Adjacents to Segment H
		{ Segment.A, Segment.K, Segment.G2, Segment.N, Segment.M, Segment.L, Segment.G1, Segment.H },	// Adjacents to Segment J
		{ Segment.B, Segment.G2, Segment.N, Segment.M, Segment.L, Segment.G1, Segment.H, Segment.J, Segment.A },	// Adjacents to Segment K
		{ Segment.E, Segment.G1, Segment.H, Segment.J, Segment.K, Segment.G2, Segment.N, Segment.M, Segment.D },	// Adjacents to Segment L
		{ Segment.D, Segment.L, Segment.G1, Segment.H, Segment.J, Segment.K, Segment.G2, Segment.N },	// Adjacents to Segment M
		{ Segment.D, Segment.M, Segment.L, Segment.G1, Segment.H, Segment.J, Segment.K, Segment.G2, Segment.C },	// Adjacents to Segment N
		{  }	// Adjacents to Segment DP
	};

	// Adapted partially from Team 1493, the rest was done using regular expressions!
	public static final byte[][] CharReg = { //charreg is short for character registry
		{(byte)0b00000000, (byte)0b00000000}, //   
		{(byte)0b00000110, (byte)0b01000000}, // ! 
		{(byte)0b00100000, (byte)0b00000010}, // " 
		{(byte)0b11001110, (byte)0b00010010}, // # 
		{(byte)0b11101101, (byte)0b00010010}, // $ 
		{(byte)0b00100100, (byte)0b00100100}, // % 
		{(byte)0b01011101, (byte)0b00001011}, // & 
		{(byte)0b00000000, (byte)0b00000100}, // ' 
		{(byte)0b00000000, (byte)0b00001100}, // ( 
		{(byte)0b00000000, (byte)0b00100001}, // ) 
		{(byte)0b11000000, (byte)0b00111111}, // * 
		{(byte)0b11000000, (byte)0b00010010}, // + 
		{(byte)0b00000000, (byte)0b00100000}, // , 
		{(byte)0b11000000, (byte)0b00000000}, // - 
		{(byte)0b00000000, (byte)0b00000000}, // . 
		{(byte)0b00000000, (byte)0b00100100}, // / 
		{(byte)0b00111111, (byte)0b00100100}, // 0 
		{(byte)0b00000110, (byte)0b00000000}, // 1 
		{(byte)0b11011011, (byte)0b00000000}, // 2 
		{(byte)0b10001111, (byte)0b00000000}, // 3 
		{(byte)0b11100110, (byte)0b00000000}, // 4 
		{(byte)0b01101001, (byte)0b00001000}, // 5 
		{(byte)0b11111101, (byte)0b00000000}, // 6 
		{(byte)0b00000111, (byte)0b00000000}, // 7 
		{(byte)0b11111111, (byte)0b00000000}, // 8 
		{(byte)0b11101111, (byte)0b00000000}, // 9 
		{(byte)0b00000000, (byte)0b00010010}, // : 
		{(byte)0b00000000, (byte)0b00100010}, // ; 
		{(byte)0b00000000, (byte)0b00001100}, // < 
		{(byte)0b11001000, (byte)0b00000000}, // = 
		{(byte)0b00000000, (byte)0b00100001}, // > 
		{(byte)0b10000011, (byte)0b00010000}, // ? 
		{(byte)0b10111011, (byte)0b00000010}, // @ 
		{(byte)0b11110111, (byte)0b00000000}, // A 
		{(byte)0b10001111, (byte)0b00010010}, // B 
		{(byte)0b00111001, (byte)0b00000000}, // C 
		{(byte)0b00001111, (byte)0b00010010}, // D 
		{(byte)0b11111001, (byte)0b00000000}, // E 
		{(byte)0b01110001, (byte)0b00000000}, // F 
		{(byte)0b10111101, (byte)0b00000000}, // G 
		{(byte)0b11110110, (byte)0b00000000}, // H 
		{(byte)0b00000000, (byte)0b00010010}, // I 
		{(byte)0b00011110, (byte)0b00000000}, // J 
		{(byte)0b01110000, (byte)0b00001100}, // K 
		{(byte)0b00111000, (byte)0b00000000}, // L 
		{(byte)0b00110110, (byte)0b00000101}, // M 
		{(byte)0b00110110, (byte)0b00001001}, // N 
		{(byte)0b00111111, (byte)0b00000000}, // O 
		{(byte)0b11110011, (byte)0b00000000}, // P 
		{(byte)0b00111111, (byte)0b00001000}, // Q 
		{(byte)0b11110011, (byte)0b00001000}, // R 
		{(byte)0b11101101, (byte)0b00000000}, // S 
		{(byte)0b00000001, (byte)0b00010010}, // T 
		{(byte)0b00111110, (byte)0b00000000}, // U 
		{(byte)0b00110000, (byte)0b00100100}, // V 
		{(byte)0b00110110, (byte)0b00101000}, // W 
		{(byte)0b00000000, (byte)0b00101101}, // X 
		{(byte)0b00000000, (byte)0b00010101}, // Y 
		{(byte)0b00001001, (byte)0b00100100}, // Z 
		{(byte)0b00111001, (byte)0b00000000}, // [ 
		{(byte)0b00000000, (byte)0b00001001}, // \ 
		{(byte)0b00001111, (byte)0b00000000}, // ] 
		{(byte)0b00000011, (byte)0b00100100}, // ^ 
		{(byte)0b00001000, (byte)0b00000000}, // _ 
		{(byte)0b00000000, (byte)0b00000001}, // ` 
		{(byte)0b01011000, (byte)0b00010000}, // a 
		{(byte)0b01111000, (byte)0b00001000}, // b 
		{(byte)0b11011000, (byte)0b00000000}, // c 
		{(byte)0b10001110, (byte)0b00100000}, // d 
		{(byte)0b01011000, (byte)0b00100000}, // e 
		{(byte)0b01110001, (byte)0b00000000}, // f 
		{(byte)0b10001110, (byte)0b00000100}, // g 
		{(byte)0b01110000, (byte)0b00010000}, // h 
		{(byte)0b00000000, (byte)0b00010000}, // i 
		{(byte)0b00001110, (byte)0b00000000}, // j 
		{(byte)0b00000000, (byte)0b00011110}, // k 
		{(byte)0b00110000, (byte)0b00000000}, // l 
		{(byte)0b11010100, (byte)0b00010000}, // m 
		{(byte)0b01010000, (byte)0b00010000}, // n 
		{(byte)0b11011100, (byte)0b00000000}, // o 
		{(byte)0b01110000, (byte)0b00000001}, // p 
		{(byte)0b10000110, (byte)0b00000100}, // q 
		{(byte)0b01010000, (byte)0b00000000}, // r 
		{(byte)0b10001000, (byte)0b00001000}, // s 
		{(byte)0b01111000, (byte)0b00000000}, // t 
		{(byte)0b00011100, (byte)0b00000000}, // u 
		{(byte)0b00000100, (byte)0b00001000}, // v 
		{(byte)0b00010100, (byte)0b00101000}, // w 
		{(byte)0b11000000, (byte)0b00101000}, // x 
		{(byte)0b00001100, (byte)0b00001000}, // y 
		{(byte)0b01001000, (byte)0b00100000}, // z 
		{(byte)0b01001001, (byte)0b00100001}, // { 
		{(byte)0b00000000, (byte)0b00010010}, // | 
		{(byte)0b10001001, (byte)0b00001100}, // } 
		{(byte)0b00100000, (byte)0b00000101}, // ~ 
		{(byte)0b11111111, (byte)0b00111111}  // DEL 
    };
    
    public static final String CharInd = " !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
}
