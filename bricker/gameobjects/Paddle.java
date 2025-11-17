package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

import static bricker.main.BrickerGameManager.PADDLE_MARGIN;
import static bricker.main.BrickerGameManager.WINDOW_WIDTH;

public class Paddle  extends GameObject {

    private static final float PADDLE_SPEED = 300;
    private final UserInputListener inputListener;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param inputListener
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
    }

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

        // TODO: calculate the locations without importing the constants.
        if (getTopLeftCorner().x() <= 0 ){
            setTopLeftCorner(new Vector2(
                    PADDLE_MARGIN,getTopLeftCorner().y()
            ));
            paddleVelocity = Vector2.ZERO;
        }
        if (getTopLeftCorner().x() >=  WINDOW_WIDTH - getDimensions().x() ) {
            setTopLeftCorner(new Vector2(
                    WINDOW_WIDTH - PADDLE_MARGIN - getDimensions().x(),getTopLeftCorner().y()
            ));
            paddleVelocity = Vector2.ZERO;
        }

        setVelocity(paddleVelocity);
    }
}

