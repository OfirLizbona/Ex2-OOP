package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.util.Vector2;

class ExtraPaddle extends Paddle{

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
    }

}