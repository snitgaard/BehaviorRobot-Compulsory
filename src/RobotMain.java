import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Key;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RangeFinderAdapter;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.TouchAdapter;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MoveController;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class RobotMain {
	private static final double TRACK_WIDTH = 14.8;
	private static final double LOW_RANGE = 30.0;

	public static void main(String[] args) {
		Brick brick = BrickFinder.getDefault();
		try
		(
				RegulatedMotor leftMotor = new EV3LargeRegulatedMotor(brick.getPort("B"));
				RegulatedMotor rightMotor = new EV3LargeRegulatedMotor(brick.getPort("C"));
				EV3TouchSensor leftTouchSensor = new EV3TouchSensor(brick.getPort("S1"));
				EV3TouchSensor rightTouchSensor = new EV3TouchSensor(brick.getPort("S2"));
				EV3UltrasonicSensor usSensor = new EV3UltrasonicSensor(brick.getPort("S4"));
		)
		{
			TouchAdapter leftTouch = new TouchAdapter(leftTouchSensor);
			TouchAdapter rightTouch = new TouchAdapter(rightTouchSensor);
			RangeFinderAdapter rf = new RangeFinderAdapter(usSensor.getDistanceMode());
			
			Wheel wheel1 = WheeledChassis.modelWheel(leftMotor,MoveController.WHEEL_SIZE_EV3).offset(-7.0);
			Wheel wheel2 = WheeledChassis.modelWheel(rightMotor, MoveController.WHEEL_SIZE_EV3).offset(7.0);
			Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
			
			MovePilot pilot = new MovePilot(chassis);
			
			pilot.setLinearSpeed(30); // cm per second
			pilot.setLinearAcceleration(10);
			
			//pilot.setAngularSpeed(300);
			//pilot.setAngularAcceleration(10);
			
			Behavior b1 = new DriveForward(pilot);
			Behavior b2 = new HitWall(leftTouch, rightTouch, rf, pilot);
			Behavior b3 = new EscapePressed(pilot);
			Behavior [] bArray = {b1, b2, b3};
			Arbitrator arby = new Arbitrator(bArray);
			arby.go();
		}
	}
}
