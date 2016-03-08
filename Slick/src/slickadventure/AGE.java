package slickadventure;
import java.awt.Font;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;
import slickadventure.Enemy.Direction;
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
        int prevHitTime = 0;
        int newHitTime = 0;
        public int newEnemyHitTime = 0;
        public int prevEnemyHitTime = 0;
        public static Music music;
        public static Sound sound;
	public AGE(int xSize, int ySize) {
        }
        
	public void init(GameContainer gc, StateBasedGame sbg)
	throws SlickException {
		gc.setTargetFrameRate(60);
		gc.setShowFPS(false);
		grassMap = new TiledMap("Slick/res/anewhope.tmx");
                music = new Music("Slick/res/Ahrix Nova.ogg");
                sound = new Sound("Slick/res/bong.ogg");
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
                Trap.trap = new boolean[grassMap.getWidth()][grassMap.getHeight()];
		for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
				int tileID = grassMap.getTileId(xAxis, yAxis, 2);
				String value = grassMap.getTileProperty(tileID,
				"trap", "false");
                                if ("true".equals(value)) {
                                    Trap.trap[xAxis][yAxis] = true;
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
                baddy = new Enemy(434, 2827);
                baddy1 = new Enemy(1815, 1826);
                baddy2 = new Enemy(3088, 3057);
                dudes.add(baddy);
                dudes.add(baddy1);
                dudes.add(baddy2);
                artifact = new Statue(3070, 75);
                winning.add(artifact);
                playerguy = new Player();
                bolt = new Lightning((int) playerguy.x + 5, (int) playerguy.y + 5);
                bolt.plasmaLeft = 10;
                baddy.setHealth(100);
                baddy1.setHealth(100);
                baddy2.setHealth(100);
                music.loop();
        }
        
        public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
	throws SlickException {
                camera.centerOn((int) playerguy.x, (int) playerguy.y);
		camera.drawMap();
		camera.translateGraphics();
		playerguy.sprite.draw((int) playerguy.x, (int) playerguy.y);
		//g.setFont((org.newdawn.slick.Font) new Font("TimesRoman", Font.PLAIN, 10)); 
                g.drawString("Health: " + playerguy.health + "    Plasma Left: " + bolt.plasmaLeft, camera.cameraX + 10,camera.cameraY + 10);
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
		//counter += delta;
		Input input = gc.getInput();
		float fdelta = delta * playerguy.speed;
		playerguy.setpdelta(fdelta);
		double rightlimit = (grassMap.getWidth() * SIZE) - (SIZE * 0.75);
		float projectedright = playerguy.x + fdelta + SIZE;
		boolean cangoright = projectedright < rightlimit;
		if (input.isKeyPressed(Input.KEY_SPACE) && bolt.plasmaLeft > 0) {
                    bolt.setX ((int)playerguy.x + 16);
                    bolt.setY ((int)playerguy.y + 16);
                    //magic8ball.hitbox.setX(bolt.getX());
                    //magic8ball.hitbox.setY(bolt.getY());
                    bolt.setIsVisible(true);
                    bolt.setTimeExists(50);
                    if (playerguy.sprite == playerguy.right) {
                        if (bolt.getTimeExists() > 0 && (!isBlocked(bolt.x + 10, bolt.y))) {
                            bolt.xmove = 20;
                        }
                        bolt.ymove = 0;
                    } else if (playerguy.sprite == playerguy.left) {
                        bolt.xmove = -20;
                        bolt.ymove = 0;
                    } else if (playerguy.sprite == playerguy.up) {
                        bolt.xmove = 0;
                        bolt.ymove = -20;
                    } else if (playerguy.sprite == playerguy.down) {
                        bolt.xmove = 0;
                        bolt.ymove = 20;
                    }
                    bolt.plasmaLeft -= 1;
                    //magic8ball.setIsVisible(!bolt.isIsVisible());
                }else if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) {
			playerguy.sprite = playerguy.up;
			float fdsc = (float) (fdelta - (SIZE * .15));
			if (!(isBlocked(playerguy.x, playerguy.y - fdelta) || isBlocked((float) (playerguy.x + SIZE + 1.5), playerguy.y - fdelta))) {
				playerguy.sprite.update(delta);
				playerguy.y -= fdelta;
                                
			}if (isTrap(playerguy.x, playerguy.y - fdelta) || isTrap((float) (playerguy.x + SIZE + 1.5), playerguy.y - fdelta)) {
                            newHitTime = (int) System.currentTimeMillis();
                            if (newHitTime - prevHitTime >= 500) {
                            playerguy.health -= 10;
                            prevHitTime = newHitTime;
                            }
                                //System.out.println("Ouch" + " X:" + playerguy.x + " Y:" + playerguy.y);
                            }
                } else if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) {
			playerguy.sprite = playerguy.down;
			if (!isBlocked(playerguy.x, playerguy.y + SIZE*2 + fdelta) && !isBlocked(playerguy.x + SIZE - 1, playerguy.y + SIZE*2 + fdelta)) {
				playerguy.sprite.update(delta);
				playerguy.y += fdelta;
                        }if (isTrap(playerguy.x, playerguy.y - fdelta) || isTrap(playerguy.x + SIZE - 1, playerguy.y - fdelta)) {
                            newHitTime = (int) System.currentTimeMillis();
                            if (newHitTime - prevHitTime >= 500) {
                            playerguy.health -= 10;
                            prevHitTime = newHitTime;
                            }
                                //System.out.println("Ouch" + " X:" + playerguy.x + " Y:" + playerguy.y);
                            }
		} else if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) {
			playerguy.sprite = playerguy.left;
                        if (!(isBlocked(playerguy.x - fdelta, playerguy.y) || isBlocked(playerguy.x - fdelta, playerguy.y + SIZE - 1))) {
				playerguy.sprite.update(delta);
				playerguy.x -= fdelta;
                        }if (isTrap(playerguy.x - fdelta, playerguy.y) || isTrap(playerguy.x - fdelta, playerguy.y + SIZE - 1)) {
                            newHitTime = (int) System.currentTimeMillis();
                            if (newHitTime - prevHitTime >= 500) {
                            playerguy.health -= 10;
                            prevHitTime = newHitTime;
                            }
                                //System.out.println("Ouch" + " X:" + playerguy.x + " Y:" + playerguy.y);
                            }
		} else if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) {
			playerguy.sprite = playerguy.right;
                        if (cangoright && (!(isBlocked(playerguy.x + SIZE + fdelta, playerguy.y) || isBlocked(playerguy.x + SIZE + fdelta, playerguy.y + SIZE - 1)))) {
				playerguy.sprite.update(delta);
				playerguy.x += fdelta;
                                
			}if (isTrap(playerguy.x + SIZE + fdelta, playerguy.y) || isTrap(playerguy.x + SIZE + fdelta, playerguy.y + SIZE - 1)) {
                            newHitTime = (int) System.currentTimeMillis();
                            if (newHitTime - prevHitTime >= 500) {
                            playerguy.health -= 10;
                            prevHitTime = newHitTime;
                            }
                                //ystem.out.println("Ouch" + " X:" + playerguy.x + " Y:" + playerguy.y);
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
                baddy.isVisible = false;
                baddy1.isVisible = false;
                baddy2.isVisible = false;
                
            });
                for (Enemy e: dudes) {
                    if (playerguy.rect.intersects(e.rect)) {
                        if (e.isVisible) {
                        playerguy.health -= 20;
                        e.isVisible = false;
                        }
                    }
                
                }
                for (Enemy e: dudes) {
                    if (bolt.hitbox.intersects(e.rect)) {
                        if (e.isVisible && e.health < 20) {
                            sound.play();
                            e.isVisible = false;
                        } else if (e.isVisible) {
                            newEnemyHitTime = (int) System.currentTimeMillis();
                            if (newEnemyHitTime - prevEnemyHitTime >= 300) {
                            e.health -=30;
                            sound.play();
                            bolt.setIsVisible(false);
                            prevEnemyHitTime = newEnemyHitTime;
                            }
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
                playerguy.health -= counter;
		if(playerguy.health <= 0){
			makevisible();
                        music.stop();
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
        private boolean isTrap(float tx, float ty) {
		int xBlock = (int) tx / SIZE;
		int yBlock = (int) ty / SIZE;
		return Trap.trap[xBlock][yBlock];
	}
}