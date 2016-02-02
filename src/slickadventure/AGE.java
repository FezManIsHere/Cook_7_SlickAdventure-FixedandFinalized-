package slickadventure;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

public class AGE extends BasicGameState {
	public Statue artifact;
        public ArrayList<Statue> winning = new ArrayList();
        public Player playerguy;
	private boolean[][] hostiles;
	private static TiledMap grassMap;
	private static AppGameContainer app;
	private static Camera camera;
	public static int counter = 0;
	// Player stuff
	//private Animation sprite, up, down, left, right, wait;
	/**
	 * 
	 * The collision map indicating which tiles block movement - generated based
	 * 
	 * on tile properties
	 */
	// changed to match size of sprites & map
	private static final int SIZE = 32;
	// screen width and height won't change
	private static final int SCREEN_WIDTH = 1000;
	private static final int SCREEN_HEIGHT = 750;
	public AGE(int xSize, int ySize) {

	}
	public void init(GameContainer gc, StateBasedGame sbg)
	throws SlickException {
		gc.setTargetFrameRate(60);
		gc.setShowFPS(false);
		// *******************
		// Scenerey Stuff
		// ****************
		grassMap = new TiledMap("res/anewhope.tmx");
		// Ongoing checks are useful
		camera = new Camera(gc, grassMap);
		// *********************************************************************************
		// Player stuff --- these things should probably be chunked into methods
		// and classes
		// *********************************************************************************
		// *****************************************************************
		// Obstacles etc.
		// build a collision map based on tile properties in the TileD map
		Blocked.blocked = new boolean[grassMap.getWidth()][grassMap.getHeight()];
		// System.out.println("Map height:" + grassMap.getHeight());
		// System.out.println("Map width:" + grassMap.getWidth());
		// There can be more than 1 layer. You'll check whatever layer has the
		// obstacles.
		// You could also use this for planning traps, etc.

		for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
				// int tileID = grassMap.getTileId(xAxis, yAxis, 0);
				// Why was this changed?
				// It's a Different Layer.
				// You should read the TMX file. It's xml, i.e.,human-readable
				// for a reason
				int tileID = grassMap.getTileId(xAxis, yAxis, 1);
				String value = grassMap.getTileProperty(tileID,
				"blocked", "false");

				if ("true".equals(value)) {
					Blocked.blocked[xAxis][yAxis] = true;
				}
			}
		}
	
		// A remarkably similar process for finding hostiles
		hostiles = new boolean[grassMap.getWidth()][grassMap.getHeight()];
                artifact = new Statue(3070, 75);
                winning.add(artifact);
                playerguy = new Player();
			}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
	throws SlickException {
		camera.centerOn((int) Player.x, (int) Player.y);
		camera.drawMap();
		camera.translateGraphics();
		// it helps to add status reports to see what's going on
		// but it gets old quickly
		
		playerguy.sprite.draw((int) Player.x, (int) Player.y);
				g.drawString("Time Left: " + Player.health/1000, camera.cameraX + 10,
				camera.cameraY + 10);
		for (Statue s: winning) {
			if (s.isvisible) {
				s.currentImage.draw(s.x, s.y);
			}
		}
        }

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
	throws SlickException {
		counter += delta;
		Input input = gc.getInput();
		float fdelta = delta * Player.speed;
		Player.setpdelta(fdelta);
		double rightlimit = (grassMap.getWidth() * SIZE) - (SIZE * 0.75);
		float projectedright = Player.x + fdelta + SIZE;
		boolean cangoright = projectedright < rightlimit;
		// there are two types of fixes. A kludge and a hack. This is a kludge.

                if (input.isKeyDown(Input.KEY_UP)) {
			playerguy.sprite = Player.up;
			float fdsc = (float) (fdelta - (SIZE * .15));

			if (!(isBlocked(Player.x, Player.y - fdelta) || isBlocked((float) (Player.x + SIZE + 1.5), Player.y - fdelta))) {
				playerguy.sprite.update(delta);
				// The lower the delta the slower the sprite will animate.
				Player.y -= fdelta;
			}

		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			playerguy.sprite = Player.down;

			if (!isBlocked(Player.x, Player.y + SIZE*2 + fdelta) && !isBlocked(Player.x + SIZE - 1, Player.y + SIZE*2 + fdelta)) {
				playerguy.sprite.update(delta);
				Player.y += fdelta;
			}

		} else if (input.isKeyDown(Input.KEY_LEFT)) {
			playerguy.sprite = Player.left;

			if (!(isBlocked(Player.x - fdelta, Player.y) || isBlocked(Player.x - fdelta, Player.y + SIZE - 1))) {
				playerguy.sprite.update(delta);
				Player.x -= fdelta;
			}

		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			playerguy.sprite = Player.right;
                        // the boolean-kludge-implementation

			if (cangoright && (!(isBlocked(Player.x + SIZE + fdelta, Player.y) || isBlocked(Player.x + SIZE + fdelta, Player.y + SIZE - 1)))) {
				playerguy.sprite.update(delta);
				Player.x += fdelta;
			} 
		}

		Player.rect.setLocation(Player.getplayershitboxX(),
				Player.getplayershitboxY());

                for (Statue s : winning) {
			if (Player.rect.intersects(s.hitbox)) {
				if (s.isvisible) {
					s.isvisible = false;
					makevisible();
					sbg.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
				}
			}
		}
		 
		Player.health -= counter/1000;
		if(Player.health <= 0){
			makevisible();
			sbg.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
	}

	public int getID() {
		return 1;
	}

	public void makevisible(){
		}
	
	private boolean isBlocked(float tx, float ty) {
		int xBlock = (int) tx / SIZE;
		int yBlock = (int) ty / SIZE;
		return Blocked.blocked[xBlock][yBlock];
		// this could make a better kludge
	}
}