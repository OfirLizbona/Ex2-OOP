package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.util.Vector2;

public class ExtraPaddle extends Paddle{

    private static final int NUM_COLLISIONS = 4;
    private int collisionsLeft;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param margin
     * @param gameManager
     */
    public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, float margin, BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, margin, gameManager);
        collisionsLeft = NUM_COLLISIONS;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionsLeft --;
        if (collisionsLeft <= 0) {
            gameManager.removeObject(this);
        }

    }
}