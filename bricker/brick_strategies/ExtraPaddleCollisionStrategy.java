package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.gameobjects.ExtraPaddle;
import bricker.main.BrickerGameManager;
import bricker.main.BricksManager;
import danogl.GameObject;
import danogl.util.Vector2;

/**
 * Collision strategy that spawns an extra paddle at the center of the screen.
 * Only one extra paddle can exist at a time. The extra paddle disappears after 4 collisions.
 * Package-private as it's only accessed through the CollisionStrategyFactory.
 */
class ExtraPaddleCollisionStrategy extends BasicCollisionStrategy {

    private static final Vector2 EXTRA_PADDLE_SIZE = new Vector2(80, 15);
    private static final float EXTRA_PADDLE_MARGIN = 2;

    /**
     * Constructs a new ExtraPaddleCollisionStrategy.
     *
     * @param gameManager Reference to the game manager.
     * @param bricksManager Reference to the bricks manager.
     */
    public ExtraPaddleCollisionStrategy(BrickerGameManager gameManager, BricksManager bricksManager) {
        super(gameManager, bricksManager);
    }

    /**
     * Handles collision by spawning an extra paddle if one doesn't already exist.
     *
     * @param caller The brick that was hit.
     * @param other The object that collided with the brick.
     */
    @Override
    public void onCollision(Brick caller, GameObject other) {
        super.onCollision(caller, other);
        // Check if there isn't another extra paddle already active
        if (!gameManager.hasExtraPaddle()) {
            ExtraPaddle paddle = new ExtraPaddle(Vector2.ZERO, EXTRA_PADDLE_SIZE,
                    EXTRA_PADDLE_MARGIN, gameManager);
            paddle.setCenter(gameManager.getWindowDims().mult(0.5f));
            gameManager.addObject(paddle);
            gameManager.setHasExtraPaddle(true);
        }
    }
}