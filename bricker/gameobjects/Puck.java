package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

/**
 * Represents a puck - a smaller ball that spawns from certain bricks.
 * Pucks behave like regular balls but automatically disappear when they fall off screen.
 */
public class Puck extends Ball {
    // Private static variable
    private static final String PUCK_IMAGE_PATH = "assets/mockBall.png";

    // Public functions
    /**
     * Constructs a new Puck instance.
     *
     * @param topLeftCorner Position of the puck in window coordinates.
     * @param dimensions Width and height of the puck.
     * @param gameManager Reference to the game manager.
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions, BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, gameManager, gameManager.readImage(PUCK_IMAGE_PATH, true));
    }
    /**
     * Updates the puck's state each frame and removes it if it falls off screen.
     *
     * @param deltaTime Time elapsed since last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (getTopLeftCorner().y() >= gameManager.getWindowDims().y()) {
            gameManager.removeObject(this);
        }
    }
    /**
     * Starts the ball moving in a random direction with fixed speed.
     * The angle direction is randomly chosen.
     */
    @Override
    public void startMove(){
        Random rand = new Random();
        double angle = rand.nextDouble() * Math.PI;
        float velocityX = (float) Math.cos(angle) * BALL_SPEED;
        float velocityY = (float) Math.sin(angle) * BALL_SPEED;
        setVelocity(new Vector2(velocityX, velocityY));
    }
}
