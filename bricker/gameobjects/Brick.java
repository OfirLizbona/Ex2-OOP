package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.gui.ImageReader;
public class Brick extends GameObject {
    
    private final CollisionStrategy collisionStrategy;
    private static final String BRICK_IMAGE_PATH = "bricker/assets/brick.png";

    private int row;
    private int col;

    /**
     *
     * @param topLeftCorner
     * @param dimensions
     * @param collisionStrategy
     * @param gameManager
     * @param row
     * @param col
     */
    public Brick(Vector2 topLeftCorner,
                 Vector2 dimensions,
                 CollisionStrategy collisionStrategy, BrickerGameManager gameManager, int row, int col) {
         Renderable brickImage =
                 gameManager.readImage(BRICK_IMAGE_PATH, false);

         super(topLeftCorner, dimensions, brickImage);
        this.collisionStrategy = collisionStrategy;
        this.row = row;
        this.col = col;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}

