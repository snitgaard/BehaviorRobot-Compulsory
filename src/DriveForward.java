import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class DriveForward implements Behavior{
	private boolean suppressed = false;
	private MovePilot pilot;

	public DriveForward(MovePilot p) {
		pilot = p;
	}
	
	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		System.out.println("Driving forward");
		suppressed = false;
		pilot.forward();
		while(!suppressed)
		{
			Thread.yield();
		}
		pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;	
	}
}
