package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import bricker.main.BricksManager;
import danogl.GameObject;
import danogl.gui.Sound;

/**
 * Collision strategy that destroys neighboring bricks in a cross pattern.
 * When triggered, it removes adjacent bricks (up, down, left, right) and plays an explosion sound.
 * Package-private as it's only accessed through the CollisionStrategyFactory.
 */
class BlowingBrickCollisionStrategy extends BasicCollisionStrategy {
    // Private static variables
    private static final String BLOWUP_SOUND_PATH = "assets/explosion.wav";
    private static final int BLOWUP_RADIUS = 1;

    // Private non-static variable
    private final Sound blowupSound;

    // Public functions
    /**
     * Constructs a new BlowingBrickCollisionStrategy.
     *
     * @param gameManager Reference to the game manager.
     * @param bricksManager Reference to the bricks manager.
     */
    public BlowingBrickCollisionStrategy(BrickerGameManager gameManager, BricksManager bricksManager) {
        super(gameManager, bricksManager);
        this.blowupSound = gameManager.readSound(BLOWUP_SOUND_PATH);
    }
    /**
     * Handles collision by removing the brick and all adjacent bricks in a cross pattern.
     *
     * @param caller The brick that was hit.
     * @param other The object that collided with the brick.
     */
    @Override
    public void onCollision(GameObject caller, GameObject other) {
        if (caller instanceof Brick brick) {
            super.onCollision(caller, other);
            blowupSound.play();


            // Blow up all neighbors in a cross pattern
            int row = brick.getRow();
            int col = brick.getCol();
            for (int i = -1 * BLOWUP_RADIUS; i <= BLOWUP_RADIUS; i++) {
                if (i != 0) {
                    Brick neighbor = bricksManager.getBrick(row + i, col);
                    if (neighbor != null) {
                        neighbor.onCollisionEnter(neighbor, null);
                    }
                }
            }

            for (int j = -1 * BLOWUP_RADIUS; j <= BLOWUP_RADIUS; j++) {
                if (j != 0) {
                    Brick neighbor = bricksManager.getBrick(row, col + j);
                    if (neighbor != null) {
                        neighbor.onCollisionEnter(neighbor, null);
                    }
                }
            }
        }
    }
}