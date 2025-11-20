package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.gameobjects.Puck;
import bricker.main.BrickerGameManager;
import bricker.main.BricksManager;
import danogl.GameObject;
import danogl.util.Vector2;

/**
 * Collision strategy that spawns a puck (smaller ball) when a brick is hit.
 * The puck behaves like a regular ball but is smaller and disappears when it falls off screen.
 * Package-private as it's only accessed through the CollisionStrategyFactory.
 */
class PucksCollisionStrategy extends BasicCollisionStrategy {

    private static final float PUCK_SCALE = 0.75f;

    /**
     * Constructs a new PucksCollisionStrategy.
     *
     * @param gameManager Reference to the game manager.
     * @param bricksManager Reference to the bricks manager.
     */
    public PucksCollisionStrategy(BrickerGameManager gameManager, BricksManager bricksManager) {
        super(gameManager, bricksManager);
    }

    /**
     * Handles collision by spawning a puck at the brick's position.
     *
     * @param caller The brick that was hit.
     * @param other The object that collided with the brick.
     */
    @Override
    public void onCollision(Brick caller, GameObject other) {
        super.onCollision(caller, other);
        Puck puck = new Puck(Vector2.ZERO,
                Vector2.ONES.mult(gameManager.getBallRadius() * PUCK_SCALE),
                gameManager);
        puck.setCenter(caller.getCenter());
        gameManager.addObject(puck);
        puck.startMove();
    }
}

