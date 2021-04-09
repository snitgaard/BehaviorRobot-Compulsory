import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.Sound;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class EscapePressed implements Behavior, KeyListener{
	private boolean suppressed = false;
	private boolean escapePressed = false;
	private MovePilot pilot;
	
	public EscapePressed(MovePilot p) {
		pilot = p;
		Button.ESCAPE.addKeyListener(this);
	}
 
	@Override
	public boolean takeControl() {
		return escapePressed;
	}

	@Override
	public void action() {
		System.out.println("Escape pressed. Terminating...");
		suppressed = false;
		pilot.stop();
		Sound.buzz();
		System.exit(0);
		while(!suppressed)
		{
			Thread.yield();
		}
		
	}

	@Override
	public void suppress() {
		suppressed = true;
		
	}

	@Override
	public void keyPressed(Key k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(Key k) {
		escapePressed = true;
	}

}
