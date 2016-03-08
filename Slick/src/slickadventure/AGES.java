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
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Geronimo
 */

   public class AGES extends BasicGameState {
	public Statue artifact;
        public ArrayList<Statue> winning = new ArrayList();
        public static Player playerguy;
        public static Lightning bolt;
        public static Enemy2 baddy3, baddy4, baddy5;
        public ArrayList<Enemy2> dudes = new ArrayList();
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

    AGES(int xSize, int ySize) {
    }
	public void init(GameContainer gc, StateBasedGame sbg)
	throws SlickException {
		gc.setTargetFrameRate(60);
		gc.setShowFPS(false);
		grassMap = new TiledMap("Slick/res/lvl.tmx");
                music = new Music("Slick/res/Ahrix Nova.ogg");
                sound = new Sound("Slick/res/bong.ogg");
		camera = new Camera(gc, grassMap);
		BlockedMore.blockedmore = new boolean[grassMap.getWidth()][grassMap.getHeight()];
		for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
				int tileID = grassMap.getTileId(xAxis, yAxis, 1);
				String value = grassMap.getTileProperty(tileID,
				"blockedmore", "false");
                                if ("true".equals(value)) {
                                    BlockedMore.blockedmore[xAxis][yAxis] = true;
				}
			}
		}
                TrapMore.trapmore = new boolean[grassMap.getWidth()][grassMap.getHeight()];
		for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
				int tileID = grassMap.getTileId(xAxis, yAxis, 0);
				String value = grassMap.getTileProperty(tileID,
				"trapmore", "false");
                                if ("true".equals(value)) {
                                    TrapMore.trapmore[xAxis][yAxis] = true;
				}
			}
		}
                for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
                int xBlock = (int) xAxis;
                int yBlock = (int) yAxis;
                if (!BlockedMore.blockedmore[xBlock][yBlock]) {
                    if (yBlock % 27 == 0 && xBlock % 37 == 0) {
                        
                    }
                }

            }
        }
//                baddy3 = new Enemy2(434, 2827);
//                baddy4 = new Enemy2(1815, 1826);
//                baddy5 = new Enemy2(3088, 3057);
//                dudes.add(baddy3);
//                dudes.add(baddy4);
//                dudes.add(baddy5);
                artifact = new Statue(3070, 75);
                winning.add(artifact);
                playerguy = new Player();
                playerguy.x = 35f;
                playerguy.y = 34f;
                bolt = new Lightning((int) playerguy.x + 5, (int) playerguy.y + 5);
                bolt.plasmaLeft = 10;
//                baddy3.setHealth(100);
//                baddy4.setHealth(100);
//                baddy5.setHealth(100);
                music.loop();
        }
        
        public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
	throws SlickException {
                camera.centerOn((int) playerguy.x, (int) playerguy.y);
		camera.drawMap();
		camera.translateGraphics();
		playerguy.sprite.draw((int) playerguy.x, (int) playerguy.y);
		//g.setFont((org.newdawn.slick.Font) new Font("TimesRoman", Font.PLAIN, 10)); 
                g.drawString("Health: " + playerguy.health, camera.cameraX + 10,camera.cameraY + 10);
                winning.stream().filter((s) -> (Statue.isvisible)).forEach((s) -> {
                    s.currentImage.draw(s.x, s.y);
                if (bolt.isIsVisible()) {
                    bolt.orbpic.draw(bolt.getX(), bolt.getY());
                }
                });
            for (Enemy2 e : dudes) {
                if (e.isVisible) {
                    e.currentanime.draw(e.Bx, e.By);
                    
                }
            } Statue.isvisible = true;
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
//		if (input.isKeyPressed(Input.KEY_SPACE) && bolt.plasmaLeft > 0) {
//                    bolt.setX ((int)playerguy.x + 16);
//                    bolt.setY ((int)playerguy.y + 16);
//                    //magic8ball.hitbox.setX(bolt.getX());
//                    //magic8ball.hitbox.setY(bolt.getY());
//                    bolt.setIsVisible(true);
//                    bolt.setTimeExists(50);
//                    if (playerguy.sprite == playerguy.right) {
//                        if (bolt.getTimeExists() > 0 && (!isBlockedMore(bolt.x + 10, bolt.y))) {
//                            bolt.xmove = 20;
//                        }
//                        bolt.ymove = 0;
//                    } else if (playerguy.sprite == playerguy.left) {
//                        bolt.xmove = -20;
//                        bolt.ymove = 0;
//                    } else if (playerguy.sprite == playerguy.up) {
//                        bolt.xmove = 0;
//                        bolt.ymove = -20;
//                    } else if (playerguy.sprite == playerguy.down) {
//                        bolt.xmove = 0;
//                        bolt.ymove = 20;
//                    }
//                    bolt.plasmaLeft -= 1;
//                    //magic8ball.setIsVisible(!bolt.isIsVisible());
//                }else 
                    if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) {
			playerguy.sprite = playerguy.up;
			float fdsc = (float) (fdelta - (SIZE * .15));
			if (!(isBlockedMore(playerguy.x, playerguy.y - fdelta) || isBlockedMore((float) (playerguy.x + SIZE + 1.5), playerguy.y - fdelta))) {
				playerguy.sprite.update(delta);
				playerguy.y -= fdelta;
                                
			}
                        if (isTrapMore(playerguy.x, playerguy.y - fdelta) || isTrapMore((float) (playerguy.x + SIZE + 1.5), playerguy.y - fdelta)) {
                            newHitTime = (int) System.currentTimeMillis();
                            if (newHitTime - prevHitTime >= 500) {
                            playerguy.health -= 40;
                            prevHitTime = newHitTime;
                            }
                                //System.out.println("Ouch" + " X:" + playerguy.x + " Y:" + playerguy.y);
                            }
                } else if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) {
			playerguy.sprite = playerguy.down;
			if (!isBlockedMore(playerguy.x, playerguy.y + SIZE*2 + fdelta) && !isBlockedMore(playerguy.x + SIZE - 1, playerguy.y + SIZE*2 + fdelta)) {
				playerguy.sprite.update(delta);
				playerguy.y += fdelta;
                        }
                        if (isTrapMore(playerguy.x, playerguy.y - fdelta) || isTrapMore(playerguy.x + SIZE - 1, playerguy.y - fdelta)) {
                            newHitTime = (int) System.currentTimeMillis();
                            if (newHitTime - prevHitTime >= 500) {
                            playerguy.health -= 40;
                            prevHitTime = newHitTime;
                            }
                                //System.out.println("Ouch" + " X:" + playerguy.x + " Y:" + playerguy.y);
                            }
		} else if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) {
			playerguy.sprite = playerguy.left;
                        if (!(isBlockedMore(playerguy.x - fdelta, playerguy.y) || isBlockedMore(playerguy.x - fdelta, playerguy.y + SIZE - 1))) {
				playerguy.sprite.update(delta);
				playerguy.x -= fdelta;
                        }
                        if (isTrapMore(playerguy.x - fdelta, playerguy.y) || isTrapMore(playerguy.x - fdelta, playerguy.y + SIZE - 1)) {
                            newHitTime = (int) System.currentTimeMillis();
                            if (newHitTime - prevHitTime >= 500) {
                            playerguy.health -= 40;
                            prevHitTime = newHitTime;
                            }
                                //System.out.println("Ouch" + " X:" + playerguy.x + " Y:" + playerguy.y);
                            }
		} else if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) {
			playerguy.sprite = playerguy.right;
                        if (cangoright && (!(isBlockedMore(playerguy.x + SIZE + fdelta, playerguy.y) || isBlockedMore(playerguy.x + SIZE + fdelta, playerguy.y + SIZE - 1)))) {
				playerguy.sprite.update(delta);
				playerguy.x += fdelta;
                                
			}
                        if (isTrapMore(playerguy.x + SIZE + fdelta, playerguy.y) || isTrapMore(playerguy.x + SIZE + fdelta, playerguy.y + SIZE - 1)) {
                            newHitTime = (int) System.currentTimeMillis();
                            if (newHitTime - prevHitTime >= 500) {
                            playerguy.health -= 40;
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
                sbg.enterState(4, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            });
                for (Enemy2 e: dudes) {
                    if (playerguy.rect.intersects(e.rect)) {
                        if (e.isVisible) {
                        playerguy.health -= 20;
                        e.isVisible = false;
                        }
                    }
                
                }
                for (Enemy2 e: dudes) {
                    if (bolt.hitbox.intersects(e.rect)) {
                        if (e.isVisible && e.health < 20) {
                            sound.play();
                            e.isVisible = false;
                        } else if (e.isVisible) {
                            newEnemyHitTime = (int) System.currentTimeMillis();
                            if (newEnemyHitTime - prevEnemyHitTime >= 100) {
                            e.health -=30;
                            sound.play();
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
                
	for (Enemy2 e : dudes) {
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
        private boolean isTrapMore(float tx, float ty) {
		int xBlock = (int) tx / SIZE;
		int yBlock = (int) ty / SIZE;
		return TrapMore.trapmore[xBlock][yBlock];
	}
}
