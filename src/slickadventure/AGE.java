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
        public static Orb magic8ball;
        public static Enemy baddy, baddy1, baddy2;
        public ArrayList<Enemy> dudes = new ArrayList();
	private static TiledMap grassMap;
	private static AppGameContainer app;
	private static Camera camera;
	public static int counter = 0;
	private static final int SIZE = 32;
	private static final int SCREEN_WIDTH = 1000;
	private static final int SCREEN_HEIGHT = 750;
	public AGE(int xSize, int ySize) {
        }
	public void init(GameContainer gc, StateBasedGame sbg)
	throws SlickException {
		gc.setTargetFrameRate(60);
		gc.setShowFPS(false);
		grassMap = new TiledMap("res/anewhope.tmx");
		camera = new Camera(gc, grassMap);
		Blocked.blocked = new boolean[grassMap.getWidth()][grassMap.getHeight()];
		for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
				int tileID = grassMap.getTileId(xAxis, yAxis, 1);
				String value = grassMap.getTileProperty(tileID,
				"blocked", "false");
                                if ("true".equals(value)) {
                                    Blocked.blocked[xAxis][yAxis] = true;
				}
			}
		}
                for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
                int xBlock = (int) xAxis;
                int yBlock = (int) yAxis;
                if (!Blocked.blocked[xBlock][yBlock]) {
                    if (yBlock % 27 == 0 && xBlock % 37 == 0) {
                        
                    }
                }

            }
        }
                baddy = new Enemy(1000, 1000);
                baddy1 = new Enemy(2000, 2000);
                baddy2 = new Enemy(3000, 3000);
                dudes.add(baddy);
                dudes.add(baddy1);
                dudes.add(baddy2);
                artifact = new Statue(3070, 75);
                winning.add(artifact);
                playerguy = new Player();
                magic8ball = new Orb((int) Player.x + 5, (int) Player.y + 5);
        }
        public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
	throws SlickException {
                camera.centerOn((int) Player.x, (int) Player.y);
		camera.drawMap();
		camera.translateGraphics();
		Player.sprite.draw((int) Player.x, (int) Player.y);
		g.drawString("Time Left: " + Player.health/1000, camera.cameraX + 10,
		camera.cameraY + 10);
                winning.stream().filter((s) -> (Statue.isvisible)).forEach((s) -> {
                    s.currentImage.draw(s.x, s.y);
                if (magic8ball.isIsVisible()) {
                    magic8ball.orbpic.draw(magic8ball.getX(), magic8ball.getY());
                }
                });
            for (Enemy e : dudes) {
                if (e.isVisible) {
                    e.currentanime.draw(e.Bx, e.By);
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
		if (input.isKeyDown(Input.KEY_SPACE)) {
                    magic8ball.setX ((int)playerguy.x);
                    magic8ball.setY ((int)playerguy.y);
                    //magic8ball.hitbox.setX(magic8ball.getX());
                    //magic8ball.hitbox.setY(magic8ball.getY());
                    magic8ball.setIsVisible(true);
                    magic8ball.setTimeExists(50);
                    if (playerguy.sprite == playerguy.right) {
                        if (magic8ball.getTimeExists() > 0 && (!isBlocked(magic8ball.x + 10, magic8ball.y))) {
                            magic8ball.xmove = 10;
                        }
                        magic8ball.ymove = 0;
                    } else if (playerguy.sprite == playerguy.left) {
                        magic8ball.xmove = -10;
                        magic8ball.ymove = 0;
                    } else if (playerguy.sprite == playerguy.up) {
                        magic8ball.xmove = 0;
                        magic8ball.ymove = -10;
                    } else if (playerguy.sprite == playerguy.down) {
                        magic8ball.xmove = 0;
                        magic8ball.ymove = 10;
                    }
                    //magic8ball.setIsVisible(!magic8ball.isIsVisible());
                }else if (input.isKeyDown(Input.KEY_UP)) {
			Player.sprite = Player.up;
			float fdsc = (float) (fdelta - (SIZE * .15));
			if (!(isBlocked(Player.x, Player.y - fdelta) || isBlocked((float) (Player.x + SIZE + 1.5), Player.y - fdelta))) {
				Player.sprite.update(delta);
				Player.y -= fdelta;
			}
		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			Player.sprite = Player.down;
			if (!isBlocked(Player.x, Player.y + SIZE*2 + fdelta) && !isBlocked(Player.x + SIZE - 1, Player.y + SIZE*2 + fdelta)) {
				Player.sprite.update(delta);
				Player.y += fdelta;
			}
		} else if (input.isKeyDown(Input.KEY_LEFT)) {
			Player.sprite = Player.left;
                        if (!(isBlocked(Player.x - fdelta, Player.y) || isBlocked(Player.x - fdelta, Player.y + SIZE - 1))) {
				Player.sprite.update(delta);
				Player.x -= fdelta;
			}
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			Player.sprite = Player.right;
                        if (cangoright && (!(isBlocked(Player.x + SIZE + fdelta, Player.y) || isBlocked(Player.x + SIZE + fdelta, Player.y + SIZE - 1)))) {
				Player.sprite.update(delta);
				Player.x += fdelta;
			} 
		} 
		Player.rect.setLocation(Player.getplayershitboxX(), Player.getplayershitboxY());
                winning.stream().filter((s) -> (Player.rect.intersects(s.hitbox))).filter((s) -> (Statue.isvisible)).map((s) -> {
                    Statue.isvisible = false;
                return s;
            }).map((_item) -> {
                makevisible();
                return _item;
            }).forEach((_item) -> {
                sbg.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            });
		for (Enemy e: dudes) {
                    if (Player.rect.intersects(e.rect)) {
                        if (e.isVisible) {
                        Player.health -= 10000;
                        e.isVisible = false;
                        }
                    }
                
                }
                for (Enemy e: dudes) {
                    if (magic8ball.hitbox.intersects(e.rect)) {
                        if (e.isVisible) {
                            e.isVisible = false;
                        }
                    }
                }
                if (magic8ball.isIsVisible()) {
                        if (magic8ball.getTimeExists() > 0) {
                            magic8ball.setX(magic8ball.x += magic8ball.xmove);
                            magic8ball.setY(magic8ball.y += magic8ball.ymove);
                            magic8ball.hitbox.setX(magic8ball.getX());
                            magic8ball.hitbox.setY(magic8ball.getY());
                            magic8ball.countdown();
                        } else {
                            magic8ball.setIsVisible(false);
                        }
                    }
                Player.health -= counter/1000;
		if(Player.health <= 0){
			makevisible();
			sbg.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
                
	for (Enemy e : dudes) {
            e.move();
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
	}
}