package slickadventure;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
public class Orb {
    public int x, y, width, height, xmove, ymove;
    private int dmg, hitboxX, hitboxY, timeExists;
    private boolean isVisible;
    Image orbpic;
    Shape hitbox;
    public Orb(int a, int b) throws SlickException {
        this.x = x;
        this.y = y;
        this.isVisible = false;
        this.timeExists = 50;
        this.orbpic = new Image("res/Orbs/Ninja_12.png");
        this.hitbox = new Rectangle (a, b, 64, 64);
    }

    public int getTimeExists() {
        return timeExists;
    }

    public void setTimeExists(int timeExists) {
        this.timeExists = timeExists;
    }
    public void countdown() {
        this.timeExists--;
    }
    /*
    Getters and setters are a common concept in Java,
    a design guideline in Java, and object oriented programming
    in general, is to encapsulate/isolate values as much as possible.
    Getters - are methods used to query the value of instance variables.
    this.getLocationX();
    Setters - are methods that set values for instance variables.
    this.setLocationX(45);
    */
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getDmg() {
        return dmg;
    }
    public void setDmg(int dmg) {
        this.dmg = dmg;
    }
    public int getHitboxX() {
        return hitboxX;
    }
    public void setHitboxX(int hitboxX) {
        this.hitboxX = hitboxX;
    }
    public int getHitboxY() {
        return hitboxY;
    }
    public void setHitboxY(int hitboxY) {
        this.hitboxY = hitboxY;
    }
    public boolean isIsVisible() {
        return isVisible;
    }
    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
    public Image getOrbpic() {
        return orbpic;
    }
    public void setOrbpic(Image orbpic) {
        this.orbpic = orbpic;
    }
    public Shape getHitbox() {
        return hitbox;
    }
    public void setHitbox(Shape hitbox) {
        this.hitbox = hitbox;
    }
    public void setLocation(int a, int b) {
        this.setX((int) Player.x);
        this.setY((int) Player.y);
        this.setHitboxX((int) Player.x);
        this.setHitboxY((int) Player.y);
    }
}
