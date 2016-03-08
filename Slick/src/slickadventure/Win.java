/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slickadventure;
import org.newdawn.slick.Color;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
public class Win extends BasicGameState {
    private StateBasedGame game;
    public Image startimage;
    public Win(int xSize, int ySize) {
    }
    
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
    	startimage = new Image("Slick/res/win.png");
        this.game = game;
    }
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {
            g.setColor(Color.white);
            startimage.draw();
    }
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException {
    }
    public int getID() {
        return 4;
    }
    @Override
    public void keyReleased(int key, char c) {
        switch (key) {
            case Input.KEY_1:
                AGE.playerguy.health  = 100;
                AGE.playerguy.speed = .4f;
                AGE.counter = 0;
                AGE.playerguy.x = 35f;
                AGE.playerguy.y = 34f;
                Statue.isvisible = true;
                Enemy.isAlive = true;
                AGE.baddy.Bx = 1000;
                AGE.baddy.By = 1000;
                AGE.baddy1.Bx = 2000;
                AGE.baddy1.By = 2000;
                AGE.baddy2.Bx = 3000;
                AGE.baddy2.By = 3000;
                AGE.bolt.plasmaLeft = 10;
                AGE.bolt.setIsVisible(false);
                AGE.baddy.isVisible = true;
                AGE.baddy1.isVisible = true;
                AGE.baddy2.isVisible = true;
                game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                break;
            case Input.KEY_2:
                break;
            case Input.KEY_3:
                break;
            default:
                break;
        }
    }
}
