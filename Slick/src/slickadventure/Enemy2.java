/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slickadventure;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 *
 * @author Ramborux
 */
public class Enemy2 {
    private static int numberOfEnemies = 4;
    private int id;
    boolean[][] eblocked = Blocked.getblocked();
    private Animation skeleton, skup, skdown, skleft, skright, skwait, skdead;

    int SIZE = 64;
    static boolean isAlive = true;

    float Bx;
    float By;
    float projectedright;
    float projectedleft;
    float projecteddown;
    float projectedup;

    boolean canmove;
    boolean cangoright;
    boolean cangoleft;
    boolean cangoup;
    boolean cangodown;

    int health = 100;
    Animation currentanime = new Animation();
    private float fdelta;

    int MapWidth = 50;
    int MapHeight = 100;

    double rightlimit = (MapWidth * SIZE) - (SIZE * 0.75);
    double downlimit = (MapHeight * SIZE) - (SIZE * 0.75);

    private int startX, startY, width = 35, height = 42;

    float hitboxX = this.Bx + 8f;
    float hitboxY = this.By + 8f;
    boolean isVisible = true;
    public Shape rect;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT, WAIT
    }

    Direction mydirection;

    Enemy2(int a, int b) throws SlickException {
        Bx = a;
        By = b;
        hitboxX = this.getskhitboxX();
        hitboxY = this.getskhitboxY();
        rect = new Rectangle(hitboxX, hitboxY, width, height);
        int BHealth;

        boolean isBAlive = true;
        canmove = true;

        currentanime = skwait;
        id = ++numberOfEnemies;

        this.mydirection = Direction.WAIT;


        SpriteSheet skeletonSS = new SpriteSheet("Slick/res/ogrespritesheet.png",64, 64, 0);
        skup = new Animation();
        skup.setAutoUpdate(true);
        skup.addFrame(skeletonSS.getSprite(0, 8), 330);
        skup.addFrame(skeletonSS.getSprite(1, 8), 330);
        skup.addFrame(skeletonSS.getSprite(2, 8), 330);
        skup.addFrame(skeletonSS.getSprite(3, 8), 330);
        skup.addFrame(skeletonSS.getSprite(4, 8), 330);
        skup.addFrame(skeletonSS.getSprite(5, 8), 330);
        skup.addFrame(skeletonSS.getSprite(6, 8), 330);
        skup.addFrame(skeletonSS.getSprite(7, 8), 330);
        skup.addFrame(skeletonSS.getSprite(8, 8), 330);
        
        skdown = new Animation();
        skdown.setAutoUpdate(false);
        skdown.addFrame(skeletonSS.getSprite(0, 10), 330);
        skdown.addFrame(skeletonSS.getSprite(1, 10), 330);
        skdown.addFrame(skeletonSS.getSprite(2, 10), 330);
        skdown.addFrame(skeletonSS.getSprite(3, 10), 330);
        skdown.addFrame(skeletonSS.getSprite(4, 10), 330);
        skdown.addFrame(skeletonSS.getSprite(5, 10), 330);
        skdown.addFrame(skeletonSS.getSprite(6, 10), 330);
        skdown.addFrame(skeletonSS.getSprite(7, 10), 330);
        skdown.addFrame(skeletonSS.getSprite(8, 10), 330);

        skleft = new Animation();
        skleft.setAutoUpdate(false);
        skleft.addFrame(skeletonSS.getSprite(0, 9), 330);
        skleft.addFrame(skeletonSS.getSprite(1, 9), 330);
        skleft.addFrame(skeletonSS.getSprite(2, 9), 330);
        skleft.addFrame(skeletonSS.getSprite(3, 9), 330);
        skleft.addFrame(skeletonSS.getSprite(4, 9), 330);
        skleft.addFrame(skeletonSS.getSprite(5, 9), 330);
        skleft.addFrame(skeletonSS.getSprite(6, 9), 330);
        skleft.addFrame(skeletonSS.getSprite(7, 9), 330);
        skleft.addFrame(skeletonSS.getSprite(8, 9), 330);

        skright = new Animation();
        skright.setAutoUpdate(false);
        skright.addFrame(skeletonSS.getSprite(0, 11), 330);
        skright.addFrame(skeletonSS.getSprite(1, 11), 330);
        skright.addFrame(skeletonSS.getSprite(2, 11), 330);
        skright.addFrame(skeletonSS.getSprite(3, 11), 330);
        skright.addFrame(skeletonSS.getSprite(4, 11), 330);
        skright.addFrame(skeletonSS.getSprite(5, 11), 330);
        skright.addFrame(skeletonSS.getSprite(6, 11), 330);
        skright.addFrame(skeletonSS.getSprite(7, 11), 330);
        skright.addFrame(skeletonSS.getSprite(8, 11), 330);

        skwait = new Animation();
        skwait.setAutoUpdate(true); //turn autoupdate to false so he stops
        skwait.addFrame(skeletonSS.getSprite(0, 14), 733);
        skwait.addFrame(skeletonSS.getSprite(1, 14), 733);
        skwait.addFrame(skeletonSS.getSprite(2, 14), 733);
        skwait.addFrame(skeletonSS.getSprite(3, 14), 733);

        skdead = new Animation();
        skdead.setAutoUpdate(false); //turn autoupdate to false so he stops
        skdead.addFrame(skeletonSS.getSprite(2, 20), 733);
        skdead.addFrame(skeletonSS.getSprite(3, 20), 733);
        skdead.addFrame(skeletonSS.getSprite(4, 20), 733);
        skdead.addFrame(skeletonSS.getSprite(5, 20), 733);    

        currentanime = skwait;
    }
    
    boolean isBlocked(float xcheck, float ycheck) {

        int xBlock = (int) (xcheck / SIZE);
        int yBlock = (int) (ycheck / SIZE);

        if ((xBlock < MapWidth && yBlock < MapHeight) && (xBlock > 0 && yBlock > 0)) {
            return Blocked.blocked[xBlock][yBlock];
        } else {
            return true;
        }
    }


    private boolean canigoup() {
        fdelta = AGE.playerguy.getpdelta();
        return (!isBlocked(this.Bx, this.By - fdelta) || !isBlocked(this.Bx + SIZE - 1, this.By - fdelta));
    }

    private boolean canigodown() {
        fdelta = AGE.playerguy.getpdelta();
        return ((!isBlocked(this.Bx, this.By + SIZE + 8) || !isBlocked(this.Bx + SIZE - 1, this.By + SIZE + fdelta)));
    }

    private boolean canigoright() {
        return (!isBlocked(this.Bx + SIZE + 6, this.By - 16) || !isBlocked(this.Bx + SIZE + 16, this.By));
    }

    private boolean canigoleft() {

        fdelta = AGE.playerguy.getpdelta();

        return (!isBlocked(this.Bx - SIZE / 2, this.By + SIZE / 2) || !isBlocked(this.Bx - SIZE, this.By) || !isBlocked(this.Bx - fdelta, this.By + SIZE - 16));
    }


    void moveup() throws SlickException {
        if (this.canigoup()) {
            fdelta = AGE.playerguy.getpdelta();
            this.currentanime = skup;
            this.By -= fdelta;
            this.rect.setLocation(this.Bx, this.By);
        } else {
            this.currentanime = skwait;
        }
    }

    void movedown() throws SlickException {
        if (this.canigodown()) {
            fdelta = AGE.playerguy.getpdelta();
            this.currentanime = skdown;
            this.By += fdelta;
            this.rect.setLocation(this.Bx, this.By);
        }
    }


    void moveleft() throws SlickException {
        if (this.canigoleft()) {
            fdelta = AGE.playerguy.getpdelta();
            this.currentanime = skleft;
            this.Bx -= fdelta;
            this.rect.setLocation(this.Bx, this.By);
        }
    }

    void moveright() throws SlickException {
        if (this.canigoright()) {
            fdelta = AGE.playerguy.getpdelta();
            this.currentanime = skright;
            this.Bx += fdelta;
            this.rect.setLocation(this.Bx, this.By);
        }
    }


    void setdirection() {
        if (AGE.playerguy.getplayersY() < this.By) {
            this.mydirection = Direction.UP;
        }

        if ((AGE.playerguy.getplayersY() > this.By)) {
            this.mydirection = Direction.DOWN;
        }

        if ((AGE.playerguy.getplayersX() > this.Bx)) {
            this.mydirection = Direction.RIGHT;
        }

        if ((AGE.playerguy.getplayersX() < this.Bx) && canigoleft()) {
            this.mydirection = Direction.LEFT;
        } else {
            this.mydirection = Direction.DOWN;
        }
    }

    void move() throws SlickException {
        if (true){
            if (this.Bx > AGE.playerguy.getplayersX()) {
                this.moveleft();
            } else if (this.Bx < AGE.playerguy.getplayersX()) {
                this.moveright();
            } else {
            } 
            
            if (this.By > AGE.playerguy.getplayersY()) {
                this.moveup();
            } else if (this.By < AGE.playerguy.getplayersY()) {
                this.movedown();
            } else {
                int r = (int) (Math.random() * (5 - 1)) + 1;
                if (r == 1) {
                    this.moveup();
                } else if (r == 2) {
                    this.movedown();
                } else if (r == 3) {
                    this.moveleft();
                } else if (r == 4) {
                    this.moveright();
                }
            }
        } else {this.currentanime = skdead;}
    }
 
    public float getskX() {
        return this.Bx;
    }

    public float getskY() {
        return this.By;
    }

    public float getskhitboxX() {
        return this.Bx + 18f;
    }

    public float getskhitboxY() {
        return this.By + 18f;
    }

    public void setskhitboxX(float cBx) {
        this.hitboxX = cBx + 18f;
    }

    public void setskhitboxY(float cBy) {
        this.hitboxY = cBy + 18f;
    }

    public int getID() {
        return id;
    }

    public static int getNumberOfEnemies() {
        return numberOfEnemies;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}