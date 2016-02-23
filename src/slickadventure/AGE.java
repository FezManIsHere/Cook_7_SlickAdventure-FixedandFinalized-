package slickadventure;
import java.awt.Font;
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
        public static Player playerguy;
        public static Lightning bolt;
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
                bolt = new Lightning((int) playerguy.x + 5, (int) playerguy.y + 5);
        }
        public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
	throws SlickException {
                camera.centerOn((int) playerguy.x, (int) playerguy.y);
		camera.drawMap();
		camera.translateGraphics();
		playerguy.sprite.draw((int) playerguy.x, (int) playerguy.y);
		//g.setFont((org.newdawn.slick.Font) new Font("TimesRoman", Font.PLAIN, 10)); 
                g.drawString("Time Left: " + playerguy.health/1000, camera.cameraX + 10,
		camera.cameraY + 10);
                winning.stream().filter((s) -> (Statue.isvisible)).forEach((s) -> {
                    s.currentImage.draw(s.x, s.y);
                if (bolt.isIsVisible()) {
                    bolt.orbpic.draw(bolt.getX(), bolt.getY());
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
		float fdelta = delta * playerguy.speed;
		playerguy.setpdelta(fdelta);
		double rightlimit = (grassMap.getWidth() * SIZE) - (SIZE * 0.75);
		float projectedright = playerguy.x + fdelta + SIZE;
		boolean cangoright = projectedright < rightlimit;
		if (input.isKeyDown(Input.KEY_SPACE)) {
                    bolt.setX ((int)playerguy.x);
                    bolt.setY ((int)playerguy.y);
                    //magic8ball.hitbox.setX(bolt.getX());
                    //magic8ball.hitbox.setY(bolt.getY());
                    bolt.setIsVisible(true);
                    bolt.setTimeExists(50);
                    if (playerguy.sprite == playerguy.right) {
                        if (bolt.getTimeExists() > 0 && (!isBlocked(bolt.x + 10, bolt.y))) {
                            bolt.xmove = 10;
                        }
                        bolt.ymove = 0;
                    } else if (playerguy.sprite == playerguy.left) {
                        bolt.xmove = -10;
                        bolt.ymove = 0;
                    } else if (playerguy.sprite == playerguy.up) {
                        bolt.xmove = 0;
                        bolt.ymove = -10;
                    } else if (playerguy.sprite == playerguy.down) {
                        bolt.xmove = 0;
                        bolt.ymove = 10;
                    }
                    //magic8ball.setIsVisible(!bolt.isIsVisible());
                }else if (input.isKeyDown(Input.KEY_UP)) {
			playerguy.sprite = playerguy.up;
			float fdsc = (float) (fdelta - (SIZE * .15));
			if (!(isBlocked(playerguy.x, playerguy.y - fdelta) || isBlocked((float) (playerguy.x + SIZE + 1.5), playerguy.y - fdelta))) {
				playerguy.sprite.update(delta);
				playerguy.y -= fdelta;
			}
		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			playerguy.sprite = playerguy.down;
			if (!isBlocked(playerguy.x, playerguy.y + SIZE*2 + fdelta) && !isBlocked(playerguy.x + SIZE - 1, playerguy.y + SIZE*2 + fdelta)) {
				playerguy.sprite.update(delta);
				playerguy.y += fdelta;
			}
		} else if (input.isKeyDown(Input.KEY_LEFT)) {
			playerguy.sprite = playerguy.left;
                        if (!(isBlocked(playerguy.x - fdelta, playerguy.y) || isBlocked(playerguy.x - fdelta, playerguy.y + SIZE - 1))) {
				playerguy.sprite.update(delta);
				playerguy.x -= fdelta;
			}
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			playerguy.sprite = playerguy.right;
                        if (cangoright && (!(isBlocked(playerguy.x + SIZE + fdelta, playerguy.y) || isBlocked(playerguy.x + SIZE + fdelta, playerguy.y + SIZE - 1)))) {
				playerguy.sprite.update(delta);
				playerguy.x += fdelta;
			} 
		} 
		playerguy.rect.setLocation(playerguy.getplayershitboxX(), playerguy.getplayershitboxY());
                winning.stream().filter((s) -> (playerguy.rect.intersects(s.hitbox))).filter((s) -> (Statue.isvisible)).map((s) -> {
                    Statue.isvisible = false;
                return s;
            }).map((_item) -> {
                makevisible();
                return _item;
            }).forEach((_item) -> {
                sbg.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            });
		for (Enemy e: dudes) {
                    if (playerguy.rect.intersects(e.rect)) {
                        if (e.isVisible) {
                        playerguy.health -= 10000;
                        e.isVisible = false;
                        }
                    }
                
                }
                for (Enemy e: dudes) {
                    if (bolt.hitbox.intersects(e.rect)) {
                        if (e.isVisible) {
                            e.isVisible = false;
                        }
                    }
                }
                if (bolt.isIsVisible()) {
                        if (bolt.getTimeExists() > 0) {
                            bolt.setX(bolt.x += bolt.xmove);
                            bolt.setY(bolt.y += bolt.ymove);
                            bolt.hitbox.setX(bolt.getX());
                            bolt.hitbox.setY(bolt.getY());
                            bolt.countdown();
                        } else {
                            bolt.setIsVisible(false);
                        }
                    }
                playerguy.health -= counter/1000;
		if(playerguy.health <= 0){
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