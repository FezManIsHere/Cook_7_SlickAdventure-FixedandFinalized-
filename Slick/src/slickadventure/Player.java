package slickadventure;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
public class Player {
        public float x = 35f;
	public float y = 34f;
	public int health = 100;
	public float speed = .4f;
	 float hitboxX = x + 8f;
	 float hitboxY = y + 8f;
	private int startX, startY, width = 30, height = 42;
	public Shape rect = new Rectangle(getplayershitboxX(),
			getplayershitboxY(), width, height);
	public float pdelta;
	public  Animation playeranime, up, down, left, right, sprite, wait;
        Player() throws SlickException {
            SpriteSheet runningSS = new SpriteSheet(
				"Slick/res/MageOP.png",64, 64, 0);
		up = new Animation();
		up.setAutoUpdate(true);
		up.addFrame(runningSS.getSprite(0, 8), 330);
		up.addFrame(runningSS.getSprite(1, 8), 330);
		up.addFrame(runningSS.getSprite(2, 8), 330);
		up.addFrame(runningSS.getSprite(3, 8), 330);
		up.addFrame(runningSS.getSprite(4, 8), 330);
		up.addFrame(runningSS.getSprite(5, 8), 330);
		up.addFrame(runningSS.getSprite(6, 8), 330);
		up.addFrame(runningSS.getSprite(7, 8), 330);
		up.addFrame(runningSS.getSprite(8, 8), 330);
		down = new Animation();
		down.setAutoUpdate(false);
		down.addFrame(runningSS.getSprite(0, 10), 330);
		down.addFrame(runningSS.getSprite(1, 10), 330);
		down.addFrame(runningSS.getSprite(2, 10), 330);
		down.addFrame(runningSS.getSprite(3, 10), 330);
		down.addFrame(runningSS.getSprite(4, 10), 330);
		down.addFrame(runningSS.getSprite(5, 10), 330);
		down.addFrame(runningSS.getSprite(6, 10), 330);
		down.addFrame(runningSS.getSprite(7, 10), 330);
		down.addFrame(runningSS.getSprite(8, 10), 330);
		left = new Animation();
		left.setAutoUpdate(false);
		left.addFrame(runningSS.getSprite(0, 9), 330);
		left.addFrame(runningSS.getSprite(1, 9), 330);
		left.addFrame(runningSS.getSprite(2, 9), 330);
		left.addFrame(runningSS.getSprite(3, 9), 330);
		left.addFrame(runningSS.getSprite(4, 9), 330);
		left.addFrame(runningSS.getSprite(5, 9), 330);
		left.addFrame(runningSS.getSprite(6, 9), 330);
		left.addFrame(runningSS.getSprite(7, 9), 330);
		left.addFrame(runningSS.getSprite(8, 9), 330);
		right = new Animation();
		right.setAutoUpdate(false);
		right.addFrame(runningSS.getSprite(0, 11), 330);
		right.addFrame(runningSS.getSprite(1, 11), 330);
		right.addFrame(runningSS.getSprite(2, 11), 330);
		right.addFrame(runningSS.getSprite(3, 11), 330);
		right.addFrame(runningSS.getSprite(4, 11), 330);
		right.addFrame(runningSS.getSprite(5, 11), 330);
		right.addFrame(runningSS.getSprite(6, 11), 330);
		right.addFrame(runningSS.getSprite(7, 11), 330);
		right.addFrame(runningSS.getSprite(8, 11), 330);
		wait = new Animation();
		wait.setAutoUpdate(true);
		wait.addFrame(runningSS.getSprite(0, 14), 733);
		wait.addFrame(runningSS.getSprite(1, 14), 733);
		wait.addFrame(runningSS.getSprite(2, 14), 733);
		wait.addFrame(runningSS.getSprite(3, 14), 733);
		sprite = wait;
        }
	public  void setpdelta(float somenum) {
		pdelta = somenum;
	}
	public  float getpdelta() {
		return pdelta;
	}
	public  float getplayersX() {
		return x;
	}
	public  float getplayersY() {
		return y;
	}
	public  float getplayershitboxX() {
		return x + 18f;
	}
	public  float getplayershitboxY() {
		return y + 18f;
	}
	public  void setplayershitboxX() {
		hitboxX = getplayershitboxX();
	}
	public  void setplayershitboxY() {
		hitboxY = getplayershitboxY();
	}
}    
