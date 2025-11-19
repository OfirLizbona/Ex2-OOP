package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.gui.ImageReader;
public class Brick extends GameObject {
    
    private final CollisionStrategy collisionStrategy;
    private static final String BRICK_IMAGE_PATH = "bricker/assets/brick.png";

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Brick(Vector2 topLeftCorner,
                 Vector2 dimensions,
                 CollisionStrategy collisionStrategy, ImageReader imageReader) {
         Renderable brickImage =
                 imageReader.readImage(BRICK_IMAGE_PATH, false);
        super(topLeftCorner, dimensions, brickImage);
        this.collisionStrategy = collisionStrategy;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other);
    }
}

