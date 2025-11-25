package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.gameobjects.Heart;
import bricker.main.BrickerGameManager;
import bricker.main.BricksManager;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Collision strategy that spawns a falling heart that grants an extra life if caught.
 * The heart falls from the brick's position and must be caught by the main paddle.
 * Package-private as it's only accessed through the CollisionStrategyFactory.
 */
class ExtraLivesCollisionStrategy extends BasicCollisionStrategy {
    // Private static variables
    private static final float HEART_SIZE = 15;
    private static final String HEART_IMAGE_PATH = "bricker/assets/heart.png";

    // Public functions
    /**
     * Constructs a new ExtraLivesCollisionStrategy.
     *
     * @param gameManager Reference to the game manager.
     * @param bricksManager Reference to the bricks manager.
     */
    public ExtraLivesCollisionStrategy(BrickerGameManager gameManager, BricksManager bricksManager) {
        super(gameManager, bricksManager);
        //this.liveManager = gameManager.getLiveManager();
    }
    /**
     * Handles collision by spawning a falling heart at the brick's position.
     *
     * @param caller The brick that was hit.
     * @param other The object that collided with the brick.
     */
    @Override
    public void onCollision(Brick caller, GameObject other) {
        super.onCollision(caller, other);
        Renderable heartImage = gameManager.readImage(HEART_IMAGE_PATH, true);
        Heart heart = new Heart(Vector2.ZERO,
                new Vector2(HEART_SIZE, HEART_SIZE), heartImage, gameManager);
        heart.setCenter(caller.getCenter());
        gameManager.addObject(heart);
        heart.startMove();
    }
}
