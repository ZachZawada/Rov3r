package pack;

//Zach Zawada
//ID: 10540525

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.hardware.sensor.*;

import java.util.Random;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.*;
import lejos.utility.Delay;

public class PerpetualRov3r {

	MovePilot pilot;
	Random ran = new Random();
	
	
	public static void main(String[] args) {
//		double diam = DifferentialPilot.WHEEL_SIZE_EV3;
//		double trackwidth = 15.2;
//		DifferentialPilot rov3r = new DifferentialPilot(diam, trackwidth, Motor.C, Motor.B);
		
		Wheel rwheel = WheeledChassis.modelWheel(Motor.B, 1.5).offset(7.6);
		Wheel lwheel = WheeledChassis.modelWheel(Motor.C, 1.5).offset(-7.6);
		Chassis chassis = new WheeledChassis(new Wheel[]{rwheel, lwheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		
		EV3UltrasonicSensor ultra = new EV3UltrasonicSensor(SensorPort.S4);
		SensorMode distMode = ultra.getMode("Distance");
		float distance;
		Sound.beep();
		
		float utVarRight;
		float utVarLeft;
		float [] sample = new float[distMode.sampleSize()];
		
		pilot.forward();
		while(!Button.ESCAPE.isDown()){
			distMode.fetchSample(sample, 0);
				distance = sample[0];
				LCD.drawString("DIST: " + sample[0], 0, 0);
//				LCD.drawFloat(distance, 3, 5, 2);
				
//				pilot.forward();
				if(distance <= 0.3){
					pilot.stop();
					pilot.travel(-5);					
					pilot.rotate(-25);
					distMode.fetchSample(sample, 0);
					utVarLeft = sample[0];
					
					pilot.rotate(50);
					distMode.fetchSample(sample, 0);
					utVarRight = sample[0];
					
					if(utVarLeft > utVarRight){
						pilot.rotate(-50);
						pilot.forward();
					}
					else{
					pilot.forward();
					}
//					pilot.forward();
				}
			Delay.msDelay(50);
		}
		ultra.close();
		
	}

}
