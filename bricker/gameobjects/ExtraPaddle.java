package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.util.Vector2;

/**
 * Represents an extra paddle that appears temporarily in the center of the screen.
 * The extra paddle disappears after a fixed number of collisions with balls.
 */
public class ExtraPaddle extends Paddle {

    private static final int NUM_COLLISIONS = 4;
    private int collisionsLeft;

    /**
     * Constructs a new ExtraPaddle instance.
     *
     * @param topLeftCorner Position of the paddle in window coordinates.
     * @param dimensions Width and height of the paddle.
     * @param margin Distance to maintain from screen edges.
     * @param gameManager Reference to the game manager.
     */
    public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, float margin,
                       BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, margin, gameManager);
        this.collisionsLeft = NUM_COLLISIONS;
    }

    /**
     * Identifies this as an extra paddle (not the main paddle).
     *
     * @return False, indicating this is not the main paddle.
     */
    @Override
    public boolean isMainPaddle() {
        return false;
    }

    /**
     * Handles collision with balls, counting down to self-destruction.
     * After NUM_COLLISIONS collisions, the extra paddle removes itself.
     *
     * @param other The game object that collided with this paddle.
     * @param collision Details about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other instanceof Ball) {
            collisionsLeft--;
            if (collisionsLeft <= 0) {
                gameManager.removeObject(this);
                gameManager.setHasExtraPaddle(false);
            }
        }
    }
}