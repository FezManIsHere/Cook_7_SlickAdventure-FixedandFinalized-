/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slickadventure;

import java.util.ArrayList;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Ramborux
 */
public class AGES extends BasicGameState {
	public Statue artifact1;
        public ArrayList<Statue> winning1 = new ArrayList();
        public static Player playerguy1;
        public static Lightning bolt1;
        public static Enemy baddy3, baddy4, baddy5;
        public ArrayList<Enemy> dudes1 = new ArrayList();
	private static TiledMap grassMap1;
	private static AppGameContainer app;
	private static Camera camera;
	public static int counter = 0;
	private static final int SIZE = 32;
	private static final int SCREEN_WIDTH = 1000;
	private static final int SCREEN_HEIGHT = 750;
        int prevHitTime, newHitTime;
        public static Music music;
	public AGES(int xSize, int ySize) {
        prevHitTime = 0;
        newHitTime = 0;
        }
	public void init(GameContainer gc, StateBasedGame sbg)
	throws SlickException {
		gc.setTargetFrameRate(60);
		gc.setShowFPS(false);
                System.out.println("Working Directory = " +
              System.getProperty("user.dir"));
		grassMap1 = new TiledMap("Slick/res/lvl.tmx");
                music = new Music("res/Ahrix Nova.ogg");
		camera = new Camera(gc, grassMap1);
		BlockedMore.blockedmore = new boolean[grassMap1.getWidth()][grassMap1.getHeight()];
		for (int xAxis = 0; xAxis < grassMap1.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < grassMap1.getHeight(); yAxis++) {
				int tileID = grassMap1.getTileId(xAxis, yAxis, 1);
				String value = grassMap1.getTileProperty(tileID,
				"blockedmore", "false");
                                if ("true".equals(value)) {
                                    BlockedMore.blockedmore[xAxis][yAxis] = true;
				}
			}
		}
//                Trap.trap = new boolean[grassMap1.getWidth()][grassMap1.getHeight()];
//		for (int xAxis = 0; xAxis < grassMap1.getWidth(); xAxis++) {
//			for (int yAxis = 0; yAxis < grassMap1.getHeight(); yAxis++) {
//				int tileID = grassMap1.getTileId(xAxis, yAxis, 2);
//				String value = grassMap1.getTileProperty(tileID,
//				"trap", "false");
//                                if ("true".equals(value)) {
//                                    Trap.trap[xAxis][yAxis] = true;
//				}
//			}
//		}
                for (int xAxis = 0; xAxis < grassMap1.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < grassMap1.getHeight(); yAxis++) {
                int xBlock = (int) xAxis;
                int yBlock = (int) yAxis;
                if (!BlockedMore.blockedmore[xBlock][yBlock]) {
                    if (yBlock % 27 == 0 && xBlock % 37 == 0) {
                        
                    }
                }

            }
        }
                baddy3 = new Enemy(434, 2827);
                baddy4 = new Enemy(1815, 1826);
                baddy5 = new Enemy(3088, 3057);
                dudes1.add(baddy3);
                dudes1.add(baddy4);
                dudes1.add(baddy5);
                artifact1 = new Statue(3070, 75);
                winning1.add(artifact1);
                playerguy1 = new Player();
                bolt1 = new Lightning((int) playerguy1.x + 5, (int) playerguy1.y + 5);
                bolt1.plasmaLeft = 7;
                baddy3.setHealth(100);
                baddy4.setHealth(100);
                baddy5.setHealth(100);
                music.loop();
        }
        
        public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
	throws SlickException {
                camera.centerOn((int) playerguy1.x, (int) playerguy1.y);
		camera.drawMap();
		camera.translateGraphics();
		playerguy1.sprite.draw((int) playerguy1.x, (int) playerguy1.y);
		//g.setFont((org.newdawn.slick.Font) new Font("TimesRoman", Font.PLAIN, 10)); 
                g.drawString("Health: " + playerguy1.health + "    Plasma Left: " + bolt1.plasmaLeft + "      Player's X: " + playerguy1.x + "  Player's Y: " + playerguy1.y, camera.cameraX + 10,camera.cameraY + 10);
                winning1.stream().filter((s) -> (Statue.isvisible)).forEach((s) -> {
                    s.currentImage.draw(s.x, s.y);
                if (bolt1.isIsVisible()) {
                    bolt1.orbpic.draw(bolt1.getX(), bolt1.getY());
                }
                });
            for (Enemy e : dudes1) {
                if (e.isVisible) {
                    e.currentanime.draw(e.Bx, e.By);
                }
            }
        }
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
	throws SlickException {
		//counter += delta;
		Input input = gc.getInput();
		float fdelta = delta * playerguy1.speed;
		playerguy1.setpdelta(fdelta);
		double rightlimit = (grassMap1.getWidth() * SIZE) - (SIZE * 0.75);
		float projectedright = playerguy1.x + fdelta + SIZE;
		boolean cangoright = projectedright < rightlimit;
		if (input.isKeyPressed(Input.KEY_SPACE) && bolt1.plasmaLeft > 0) {
                    bolt1.setX ((int)playerguy1.x + 16);
                    bolt1.setY ((int)playerguy1.y + 16);
                    //magic8ball.hitbox.setX(bolt1.getX());
                    //magic8ball.hitbox.setY(bolt1.getY());
                    bolt1.setIsVisible(true);
                    bolt1.setTimeExists(75);
                    if (playerguy1.sprite == playerguy1.right) {
                        if (bolt1.getTimeExists() > 0 && (!isBlockedMore(bolt1.x + 10, bolt1.y))) {
                            bolt1.xmove = 20;
                        }
                        bolt1.ymove = 0;
                    } else if (playerguy1.sprite == playerguy1.left) {
                        bolt1.xmove = -20;
                        bolt1.ymove = 0;
                    } else if (playerguy1.sprite == playerguy1.up) {
                        bolt1.xmove = 0;
                        bolt1.ymove = -20;
                    } else if (playerguy1.sprite == playerguy1.down) {
                        bolt1.xmove = 0;
                        bolt1.ymove = 20;
                    }
                    bolt1.plasmaLeft -= 1;
                    //magic8ball.setIsVisible(!bolt1.isIsVisible());
                }else if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) {
			playerguy1.sprite = playerguy1.up;
			float fdsc = (float) (fdelta - (SIZE * .15));
			if (!(isBlockedMore(playerguy1.x, playerguy1.y - fdelta) || isBlockedMore((float) (playerguy1.x + SIZE + 1.5), playerguy1.y - fdelta))) {
				playerguy1.sprite.update(delta);
				playerguy1.y -= fdelta;
                                
			}
                } else if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) {
			playerguy1.sprite = playerguy1.down;
			if (!isBlockedMore(playerguy1.x, playerguy1.y + SIZE*2 + fdelta) && !isBlockedMore(playerguy1.x + SIZE - 1, playerguy1.y + SIZE*2 + fdelta)) {
				playerguy1.sprite.update(delta);
				playerguy1.y += fdelta;
                        }
		} else if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) {
			playerguy1.sprite = playerguy1.left;
                        if (!(isBlockedMore(playerguy1.x - fdelta, playerguy1.y) || isBlockedMore(playerguy1.x - fdelta, playerguy1.y + SIZE - 1))) {
				playerguy1.sprite.update(delta);
				playerguy1.x -= fdelta;
                        }
		} else if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) {
			playerguy1.sprite = playerguy1.right;
                        if (cangoright && (!(isBlockedMore(playerguy1.x + SIZE + fdelta, playerguy1.y) || isBlockedMore(playerguy1.x + SIZE + fdelta, playerguy1.y + SIZE - 1)))) {
				playerguy1.sprite.update(delta);
				playerguy1.x += fdelta;
                                
			}
                } 
		playerguy1.rect.setLocation(playerguy1.getplayershitboxX(), playerguy1.getplayershitboxY());
                winning1.stream().filter((s) -> (playerguy1.rect.intersects(s.hitbox))).filter((s) -> (Statue.isvisible)).map((s) -> {
                    Statue.isvisible = false;
                return s;
            }).map((_item) -> {
                makevisible();
                return _item;
            }).forEach((_item) -> {
                sbg.enterState(4, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            });
		
               
                for (Enemy e: dudes1) {
                    if (playerguy1.rect.intersects(e.rect)) {
                        if (e.isVisible) {
                        playerguy1.health -= 20;
                        e.isVisible = false;
                        }
                    }
                
                }
                for (Enemy e: dudes1) {
                    if (bolt1.hitbox.intersects(e.rect)) {
                        if (e.isVisible && e.health < 20) {
                            e.isVisible = false;
                        } else if (e.isVisible) {
                            e.health -=30;
                            if (e.mydirection == Enemy.Direction.UP) {
                                e.By -= 10;
                            } else if (e.mydirection == Enemy.Direction.DOWN) {
                                e.By =+ 10;
                            } else if (e.mydirection == Enemy.Direction.LEFT) {
                                e.Bx =+ 10;
                            } else if (e.mydirection == Enemy.Direction.RIGHT) {
                                e.Bx -= 10;
                            }
                        }
                    }
                }
                if (bolt1.isIsVisible()) {
                        if (bolt1.getTimeExists() > 0) {
                            bolt1.setX(bolt1.x += bolt1.xmove);
                            bolt1.setY(bolt1.y += bolt1.ymove);
                            bolt1.hitbox.setX(bolt1.getX());
                            bolt1.hitbox.setY(bolt1.getY());
                            bolt1.countdown();
                        } else {
                            bolt1.setIsVisible(false);
                        }
                }
                playerguy1.health -= counter;
		if(playerguy1.health <= 0){
			makevisible();
			sbg.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
                
	for (Enemy e : dudes1) {
            e.move();
        }
        }
	public int getID() {
		return 3;
	}
	public void makevisible(){
		}
	private boolean isBlockedMore(float tx, float ty) {
		int xBlock = (int) tx / SIZE;
		int yBlock = (int) ty / SIZE;
		return BlockedMore.blockedmore[xBlock][yBlock];
	}
        private boolean isTrap(float tx, float ty) {
		int xBlock = (int) tx / SIZE;
		int yBlock = (int) ty / SIZE;
		return Trap.trap[xBlock][yBlock];
	}
}