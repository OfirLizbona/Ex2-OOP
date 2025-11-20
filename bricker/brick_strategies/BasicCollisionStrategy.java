package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import bricker.main.BricksManager;
import danogl.GameObject;

/**
 * Basic collision strategy that simply removes the brick when hit.
 * This serves as the base class for more complex collision strategies.
 * All strategies extend this to ensure the brick is removed on collision.
 */
public class BasicCollisionStrategy implements CollisionStrategy {
    protected final BricksManager bricksManager;
    protected final BrickerGameManager gameManager;

    /**
     * Constructs a new BasicCollisionStrategy.
     *
     * @param gameManager Reference to the game manager for accessing game resources.
     * @param bricksManager Reference to the bricks manager for removing bricks.
     */
    public BasicCollisionStrategy(BrickerGameManager gameManager, BricksManager bricksManager) {
        this.bricksManager = bricksManager;
        this.gameManager = gameManager;
    }

    /**
     * Handles collision by removing the brick from the game.
     *
     * @param caller The brick that was hit.
     * @param other The object that collided with the brick.
     */
    @Override
    public void onCollision(Brick caller, GameObject other) {
        bricksManager.removeBrick(caller.getRow(), caller.getCol());
    }
}

