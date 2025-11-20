package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import bricker.main.LiveManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a falling heart that grants an extra life when caught by the main paddle.
 * Hearts fall straight down and disappear if they fall off screen or are caught.
 */
public class Heart extends GameObject {
    private static final float FALL_SPEED = 100;

    private final LiveManager liveManager;
    private final BrickerGameManager gameManager;

    /**
     * Constructs a new Heart instance.
     *
     * @param topLeftCorner Position of the heart in window coordinates.
     * @param dimensions Width and height of the heart.
     * @param renderable The image to render for this heart.
     * @param gameManager Reference to the game manager.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.gameManager = gameManager;
        this.liveManager = gameManager.getLiveManager();
    }

    /**
     * Handles collision with the paddle by granting an extra life.
     * Only the main paddle (not extra paddle) can catch hearts.
     *
     * @param other The game object this heart collided with.
     * @param collision Details about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (other instanceof Paddle && ((Paddle) other).isMainPaddle()) {
            super.onCollisionEnter(other, collision);
            liveManager.increment();
            gameManager.removeObject(this);
        }
    }

    /**
     * Starts the heart falling downward.
     */
    public void startMove() {
        setVelocity(new Vector2(0, FALL_SPEED));
    }

    /**
     * Updates the heart's state each frame and removes it if it falls off screen.
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
}