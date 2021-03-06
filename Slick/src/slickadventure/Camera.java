package slickadventure;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;
public class Camera {
   protected TiledMap map;
   protected int numTilesX;
   protected int numTilesY;
   protected int mapHeight;
   protected int mapWidth;
   protected int tileWidth;
   protected int tileHeight;
   protected GameContainer gc;
   protected float cameraX;
   protected float cameraY;
   public Camera(GameContainer gc, TiledMap map) {
      this.map = map;
      this.numTilesX = map.getWidth();
      this.numTilesY = map.getHeight();
      this.tileWidth = map.getTileWidth();
      this.tileHeight = map.getTileHeight();
      this.mapHeight = this.numTilesX * this.tileWidth;
      this.mapWidth = this.numTilesY * this.tileHeight;
      this.gc = gc;
   }
      public void centerOn(float x, float y) {
      cameraX = x - gc.getWidth()  / 2;
      cameraY = y - gc.getHeight() / 2;
      if(cameraX < 0) cameraX = 0;
      if(cameraX + gc.getWidth() > mapWidth) cameraX = mapWidth -
gc.getWidth();
    //if the camera is at the top or bottom edge lock it to prevent a black bar
    if(cameraY < 0) cameraY = 0;
    if(cameraY + gc.getHeight() > mapHeight) cameraY = mapHeight -

gc.getHeight();

   }

  

   /**

    * "locks" the camera on the center of the given Rectangle. The

camera tries to keep the location in its center.

    *

    * @param x the x-coordinate (in pixel) of the top-left corner of the

rectangle

    * @param y the y-coordinate (in pixel) of the top-left corner of the

rectangle

    * @param height the height (in pixel) of the rectangle

    * @param width the width (in pixel) of the rectangle

    */

   public void centerOn(float x, float y, float height, float width) {

      this.centerOn(x + width / 2, y + height / 2);

   }


   /**

    * "locks the camera on the center of the given Shape. The camera

tries to keep the location in its center.

    * @param shape the Shape which should be centered on the screen

    */

   public void centerOn(Shape shape) {

      this.centerOn(shape.getCenterX(), shape.getCenterY());

   }

  

   /**

    * draws the part of the map which is currently focused by the camera

on the screen

    */

   public void drawMap() {

      this.drawMap(0, 0);

   }

  

   /**

    * draws the part of the map which is currently focused by the camera

on the screen.<br>

    * You need to draw something over the offset, to prevent the edge of

the map to be displayed below it<br>

    * Has to be called before Camera.translateGraphics() !

    * @param offsetX the x-coordinate (in pixel) where the camera should

start drawing the map at

    * @param offsetY the y-coordinate (in pixel) where the camera should

start drawing the map at

    */

  

   public void drawMap(int offsetX, int offsetY) {

       //calculate the offset to the next tile (needed by TiledMap.render())

       int tileOffsetX = (int) - (cameraX % tileWidth);

       int tileOffsetY = (int) - (cameraY % tileHeight);

     

       //calculate the index of the leftmost tile that is being displayed

       int tileIndexX = (int) (cameraX / tileWidth);

       int tileIndexY = (int) (cameraY / tileHeight);

     

       //finally draw the section of the map on the screen

       map.render(  

             tileOffsetX + offsetX,

             tileOffsetY + offsetY,

             tileIndexX,

             tileIndexY,

                (gc.getWidth()  - tileOffsetX) / tileWidth  + 1,

                (gc.getHeight() - tileOffsetY) / tileHeight + 1);

   }

  

   /**

    * Translates the Graphics-context to the coordinates of the map -

now everything

    * can be drawn with it's NATURAL coordinates.

    */

   public void translateGraphics() {

      gc.getGraphics().translate(-cameraX, -cameraY);

   }

   /**

    * Reverses the Graphics-translation of Camera.translatesGraphics().

    * Call this before drawing HUD-elements or the like

    */

   public void untranslateGraphics() {

      gc.getGraphics().translate(cameraX, cameraY);

   }

  

}


