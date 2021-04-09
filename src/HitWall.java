import java.util.Random;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RangeFinderAdapter;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.TouchAdapter;
import lejos.robotics.navigation.MoveController;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class HitWall implements Behavior {
	private static final double TRACK_WIDTH = 14.8;
	private static final double LOW_RANGE = 30.0;
	private boolean suppressed = false;
	private MovePilot pilot;
	private TouchAdapter leftTouch;
	private TouchAdapter rightTouch;
	private RangeFinderAdapter rangeFinder;

	public HitWall(TouchAdapter lTouch, TouchAdapter rTouch, RangeFinderAdapter rf, MovePilot p) {
		leftTouch = lTouch;
		rightTouch = rTouch;
		rangeFinder = rf;
		pilot = p;
	}

	@Override
	public boolean takeControl() {
		return leftTouch.isPressed() || rightTouch.isPressed() || rangeFinder.getRange() < 25;
	}

	@Override
	public void action() {
		suppressed = false;
		System.out.println("Robot has hit a wall");
		Sound.beepSequence();
		
		Random rnd = new Random();
		
		pilot.rotate(rnd.nextInt(720)-360);

		while (!suppressed && pilot.isMoving()) {
			Thread.yield();
		}
		pilot.stop();
		
	}

	@Override
	public void suppress() {
		suppressed = true;

	}
}
