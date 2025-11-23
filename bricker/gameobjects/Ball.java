package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.util.Random;

/**
 * Represents a ball object in the Bricker game.
 * The ball bounces around the screen and collides with paddles, bricks, and walls.
 */
public class Ball extends GameObject {
    // Private static variables
    private static final String STANDARD_BALL_PATH = "bricker/assets/ball.png";
    private static final String BLOP_SOUND_PATH = "bricker/assets/blop.wav";

    // Protected static variable (visible for Puck)
    protected static final int BALL_SPEED = 300;

    // Private non-static variable
    private final Sound collisionSound;

    // Protected non-static variable (visible for Puck)
    protected final BrickerGameManager gameManager;

    // Public functions
    /**
     * Constructs a new Ball instance with default ball image.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions Width and height in window coordinates.
     * @param gameManager Reference to the game manager for accessing resources.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, gameManager.readImage(STANDARD_BALL_PATH, true));
        this.gameManager = gameManager;
        this.collisionSound = gameManager.readSound(BLOP_SOUND_PATH);
    }
    /**
     * Constructs a new Ball instance with a custom renderable image.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     * @param dimensions Width and height in window coordinates.
     * @param gameManager Reference to the game manager for accessing resources.
     * @param ballImage The renderable image for this ball.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, BrickerGameManager gameManager,
                Renderable ballImage) {
        super(topLeftCorner, dimensions, ballImage);
        this.gameManager = gameManager;
        this.collisionSound = gameManager.readSound(BLOP_SOUND_PATH);
    }
    /**
     * Handles collision with other game objects.
     * Reflects the ball's velocity based on collision normal and plays sound.
     *
     * @param other The game object this ball collided with.
     * @param collision Details about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other instanceof Heart) {
            return;
        }
        setVelocity(getVelocity().flipped(collision.getNormal()));
        collisionSound.play();
    }
    /**
     * Starts the ball moving in a random direction with fixed speed.
     * The direction is randomly chosen to be up/down and left/right.
     */
    public void startMove() {
        Random rand = new Random();
        int ballXSpeed = BALL_SPEED;
        int ballYSpeed = BALL_SPEED;
        if (rand.nextBoolean()) {
            ballXSpeed = ballXSpeed * -1;
        }
        if (rand.nextBoolean()) {
            ballYSpeed = ballYSpeed * -1;
        }
        setVelocity(new Vector2(ballXSpeed, ballYSpeed));
    }
}

