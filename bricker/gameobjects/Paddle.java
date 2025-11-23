package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * Represents a controllable paddle that the player uses to hit the ball.
 * The paddle moves left and right based on keyboard input and stays within window bounds.
 */
public class Paddle extends GameObject {
    // Private static variables
    private static final float PADDLE_SPEED = 300;
    private static final String PADDLE_IMAGE_PATH = "bricker/assets/paddle.png";

    // Private non-static variables
    private final UserInputListener inputListener;
    private final float margin;

    // Protected variable (visible for ExtraPaddle)
    protected final BrickerGameManager gameManager;

    // Public functions
    /**
     * Constructs a new Paddle instance.
     *
     * @param topLeftCorner Position of the paddle in window coordinates.
     * @param dimensions Width and height of the paddle.
     * @param margin Distance to maintain from screen edges.
     * @param gameManager Reference to the game manager.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, float margin,
                  BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, gameManager.readImage(PADDLE_IMAGE_PATH, true));
        this.gameManager = gameManager;
        this.inputListener = gameManager.getInputListener();
        this.margin = margin;
    }
    /**
     * Checks if this is the main paddle (not an extra paddle).
     *
     * @return True for main paddle, false for extra paddle.
     */
    public boolean isMainPaddle() {
        return true;
    }
    /**
     * Updates the paddle position based on user input and enforces boundary constraints.
     *
     * @param deltaTime Time elapsed since last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 paddleVelocity = Vector2.ZERO;

        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            paddleVelocity = paddleVelocity.add(Vector2.LEFT.mult(PADDLE_SPEED));
        }

        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            paddleVelocity = paddleVelocity.add(Vector2.RIGHT.mult(PADDLE_SPEED));
        }

        // Keep paddle within left boundary
        if (getTopLeftCorner().x() <= 0) {
            setTopLeftCorner(new Vector2(margin, getTopLeftCorner().y()));
            paddleVelocity = Vector2.ZERO;
        }

        // Keep paddle within right boundary
        if (getTopLeftCorner().x() >= gameManager.getWindowDims().x() - getDimensions().x()) {
            setTopLeftCorner(new Vector2(
                    gameManager.getWindowDims().x() - margin - getDimensions().x(),
                    getTopLeftCorner().y()));
            paddleVelocity = Vector2.ZERO;
        }

        setVelocity(paddleVelocity);
    }
}

