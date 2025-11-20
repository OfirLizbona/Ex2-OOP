package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.util.Vector2;

/**
 * Represents a brick in the game that can be destroyed by the ball.
 * Each brick has a collision strategy that determines what happens when it's hit.
 */
public class Brick extends GameObject {

    private static final String BRICK_IMAGE_PATH = "bricker/assets/brick.png";

    private final CollisionStrategy collisionStrategy;
    private final int row;
    private final int col;

    /**
     * Constructs a new Brick instance.
     *
     * @param topLeftCorner Position of the brick in window coordinates.
     * @param dimensions Width and height of the brick.
     * @param collisionStrategy The strategy to execute when this brick is hit.
     * @param gameManager Reference to the game manager for loading resources.
     * @param row The row index of this brick in the brick grid.
     * @param col The column index of this brick in the brick grid.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions,
                 CollisionStrategy collisionStrategy, BrickerGameManager gameManager,
                 int row, int col) {
        super(topLeftCorner, dimensions, gameManager.readImage(BRICK_IMAGE_PATH, false));
        this.collisionStrategy = collisionStrategy;
        this.row = row;
        this.col = col;
    }

    /**
     * Handles collision by delegating to the brick's collision strategy.
     *
     * @param other The game object that collided with this brick.
     * @param collision Details about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other);
    }

    /**
     * Gets the row index of this brick in the grid.
     *
     * @return The row index.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column index of this brick in the grid.
     *
     * @return The column index.
     */
    public int getCol() {
        return col;
    }
}

