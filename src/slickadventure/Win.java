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
    	startimage = new Image("res/win.png");


        this.game = game;



// TODO AutoÃ¢â‚¬Âgenerated method stub


    }



   


    public void render(GameContainer container, StateBasedGame game, Graphics g)


            throws SlickException {


// TODO AutoÃ¢â‚¬Âgenerated method stub


            g.setColor(Color.white);
//
          startimage.draw();
//        g.drawString("You found the statue in time and saved the village!", 275, 200);
//        g.drawString("press 1 to play again", 400, 320);


       


    }



    


    public void update(GameContainer container, StateBasedGame game, int delta)


            throws SlickException {


// TODO AutoÃ¢â‚¬Âgenerated method stub


    }



  


    public int getID() {


// TODO AutoÃ¢â‚¬Âgenerated method stub


        return 3;


    }



    @Override


    public void keyReleased(int key, char c) {


        switch (key) {


            case Input.KEY_1:

                Player.health  = 90000;
                Player.speed = .4f;
                AGE.counter = 0;
                Player.x = 35f;
                Player.y = 34f;
                
                
                //item.isvisible = true;
                //item1.isvisible = true;
                //Itemwin.isvisible = true;
                Statue.isvisible = true;
                game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));


                break;


            case Input.KEY_2:


// TODO: Implement later


                break;


            case Input.KEY_3:


// TODO: Implement later


                break;


            default:


                break;


        }


    }


}
