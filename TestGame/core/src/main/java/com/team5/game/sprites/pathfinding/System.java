package com.team5.game.sprites.pathfinding;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.team5.game.tools.Atlas;
import com.team5.game.tools.Constants;

public class System extends Room {

    /*
    System is used for the infiltrator pathfinding so they can
    access and break the systems.
     */

    boolean broken;

    //Positioning
    Vector2 spritePosition;

    //Sprites
    public TextureRegion currentSprite;

    //On means it's not been broken yet, off means it's broken.
    public TextureRegion onSprite;
    public TextureRegion offSprite;

    public System(String tag, float x, float y, String name,
                  float spriteX, float spriteY) {
        super(tag, x, y, name);
        this.spritePosition = new Vector2(spriteX * Constants.TILE_SIZE,
                spriteY * Constants.TILE_SIZE);

        setup();
    }

    //Sets up the system sprites
    public void setup() {
        broken = false;

        onSprite = Atlas.getInstance().findRegion("Systems/" + tag + "On");
        offSprite = Atlas.getInstance().findRegion("Systems/" + tag + "Off");

        currentSprite = onSprite;
    }

    //To be called every frame to draw the system sprites
    public void draw(SpriteBatch batch) {
        if (broken) {
            currentSprite = offSprite;
        }
        batch.draw(currentSprite, spritePosition.x, spritePosition.y);
        batch.draw(new Texture("Sprites/Minimap/Cursor.png"), x - 10, y - 10);
    }

    //destroy function is called when the infiltrator breaks the system
    public void destroy() {
        broken = true;
    }

    public boolean getBroken() {
        return broken;
    }

    //Only use in inital setup of systems
    public void setBroken(boolean broken) {
        this.broken = broken;
    }
}
